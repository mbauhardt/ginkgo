package ginkgo.webapp.controller;

import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildDao;
import ginkgo.webapp.persistence.dao.IBuildNumberDao;
import ginkgo.webapp.persistence.dao.IProjectDao;
import ginkgo.webapp.persistence.model.BuildCommand;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildPlan;
import ginkgo.webapp.persistence.model.Project;
import ginkgo.webapp.persistence.model.Stage;
import ginkgo.webapp.persistence.model.Step;
import ginkgo.webapp.server.AgentRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/activity.html")
public class ActivityController {

    private final IProjectDao _projectDao;
    private final IBuildDao _buildDao;
    private final IBuildNumberDao _buildNumberDao;

    @Autowired
    public ActivityController(IProjectDao projectDao, IBuildDao buildDao, IBuildNumberDao buildNumberDao) {
        _projectDao = projectDao;
        _buildDao = buildDao;
        _buildNumberDao = buildNumberDao;
    }

    @ModelAttribute("projects")
    public List<Project> injectProjects() throws DaoException {
        return _projectDao.getAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String activity() {
        return "/user/activity";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postActivity(@RequestParam("id") Long id) throws DaoException {

        Project project = _projectDao.getById(id);
        List<BuildPlan> buildPlans = project.getBuildPlans();
        for (BuildPlan buildPlan : buildPlans) {
            int bn = 1;
            if (!buildPlan.getBuildNumbers().isEmpty()) {
                BuildNumber highestBuildNumber = _buildNumberDao.getHighestBuildNumber(buildPlan);
                bn = highestBuildNumber.getBuildNumber() + 1;
            }

            BuildNumber buildNumber = new BuildNumber();
            buildNumber.setBuildNumber(bn);
            buildNumber.setBuildPlan(buildPlan);
            _buildNumberDao.makePersistent(buildNumber);
            List<Stage> stages = buildPlan.getStages();
            Long parentBuildId = -1L;
            for (Stage stage : stages) {
                List<Step> steps = stage.getSteps();
                for (Step step : steps) {
                    String command = step.getCommand();
                    BuildCommand nextBuild = new BuildCommand();
                    nextBuild.setCommand(command);
                    _buildDao.makePersistent(nextBuild);
                    if (parentBuildId != -1) {
                        BuildCommand parentBuild = _buildDao.getById(parentBuildId);
                        parentBuild.setNextBuild(nextBuild);
                    } else {
                        buildNumber.setBuildCommand(nextBuild);
                    }
                    parentBuildId = nextBuild.getId();
                }
            }
        }
        return "redirect:/user/activity.html";
    }
}
