package ginkgo.webapp.controller;

import ginkgo.api.VcsPlugin;
import ginkgo.webapp.PluginRepository;
import ginkgo.webapp.controller.commandObjects.BuildPlanCommand;
import ginkgo.webapp.controller.editor.BaseEditor;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildDao;
import ginkgo.webapp.persistence.dao.IBuildNumberDao;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.dao.IProjectDao;
import ginkgo.webapp.persistence.dao.IStageDao;
import ginkgo.webapp.persistence.dao.IStepDao;
import ginkgo.webapp.persistence.model.BuildCommand;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildPlan;
import ginkgo.webapp.persistence.model.Project;
import ginkgo.webapp.persistence.model.Stage;
import ginkgo.webapp.persistence.model.Step;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BuildPlanController {

    private final IBuildPlanDao _buildPlanDao;
    private final IProjectDao _projectDao;
    private final IStageDao _stageDao;
    private final IStepDao _stepDao;
    private final IBuildNumberDao _buildNumberDao;
    private final IBuildDao _buildDao;
    private final PluginRepository _pluginRepository;

    @Autowired
    public BuildPlanController(IBuildPlanDao buildPlanDao, IProjectDao projectDao, IStageDao stageDao,
            IStepDao stepDao, IBuildNumberDao buildNumberDao, IBuildDao buildDao, PluginRepository pluginRepository) {
        _buildPlanDao = buildPlanDao;
        _projectDao = projectDao;
        _stageDao = stageDao;
        _stepDao = stepDao;
        _buildNumberDao = buildNumberDao;
        _buildDao = buildDao;
        _pluginRepository = pluginRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Project.class, new BaseEditor(_projectDao));
    }

    @RequestMapping(value = "/user/buildPlan.html", method = RequestMethod.GET)
    public String buildPlanTree(@RequestParam("buildPlanId") Long id, Model model) throws DaoException {
        BuildPlan buildPlan = _buildPlanDao.getById(id);
        model.addAttribute("buildPlan", buildPlan);
        return "user/buildPlan";
    }

    @RequestMapping(value = { "/user/runBuildPlan.html" }, method = RequestMethod.POST)
    public String runBuildPlan(@RequestParam("id") Long id) throws DaoException {

        BuildPlan buildPlan = _buildPlanDao.getById(id);
        int bn = 1;
        if (!buildPlan.getBuildNumbers().isEmpty()) {
            BuildNumber highestBuildNumber = _buildNumberDao.getHighestBuildNumber(buildPlan);
            bn = highestBuildNumber.getBuildNumber() + 1;
        }

        BuildNumber buildNumber = new BuildNumber();
        buildNumber.setBuildNumber(bn);
        buildNumber.setBuildPlan(buildPlan);
        _buildNumberDao.makePersistent(buildNumber);
        Set<Stage> stages = buildPlan.getStages();
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
        return "redirect:/user/listBuildNumbers.html?buildPlanId=" + id;
    }

    @RequestMapping(value = "/user/deleteBuildPlan.html", method = RequestMethod.GET)
    public String deleteBuildPlan(@RequestParam(value = "buildPlanId", required = true) Long id, Model model)
            throws DaoException {
        BuildPlan byId = _buildPlanDao.getById(id);
        model.addAttribute("buildPlan", byId);
        return "user/deleteBuildPlan";
    }

    @RequestMapping(value = "/user/deleteBuildPlan.html", method = RequestMethod.POST)
    public String postDeleteBuildPlan(@RequestParam(value = "buildPlanId", required = true) Long id)
            throws DaoException {
        BuildPlan byId = _buildPlanDao.getById(id);
        Set<Stage> stages = byId.getStages();
        for (Stage stage : stages) {
            List<Step> steps = stage.getSteps();
            for (Step step : steps) {
                _stepDao.makeTransient(step);
                _stepDao.flush();
            }
            _stageDao.makeTransient(stage);
            _stageDao.flush();
        }
        _buildPlanDao.makeTransient(byId);
        return "redirect:/user/projects.html";
    }

    @RequestMapping(value = "/user/addBuildPlan.html", method = RequestMethod.GET)
    public String addBuildPlan(@RequestParam(value = "projectId", required = true) Long id, Model model)
            throws DaoException {
        Project project = _projectDao.getById(id);
        model.addAttribute("project", project);
        return "user/addBuildPlan";
    }

    @RequestMapping(value = "/user/addBuildPlan.html", method = RequestMethod.POST)
    public String postAddBuildPlan(@RequestParam(value = "projectId", required = true) Long projectId,
            @ModelAttribute("buildPlanCommand") BuildPlanCommand buildPlanCommand) throws DaoException {

        Long id = buildPlanCommand.getId();
        Project project = _projectDao.getById(projectId);
        BuildPlan buildPlan = null;
        if (id == null) {
            buildPlan = new BuildPlan();
            buildPlan.setProject(project);
            _buildPlanDao.makePersistent(buildPlan);
        } else {
            buildPlan = _buildPlanDao.getById(id);
        }
        buildPlan.setName(buildPlanCommand.getName());

        String vcs = project.getVcs();
        VcsPlugin vcsPlugin = _pluginRepository.create(vcs);
        // String[] parameters=null;
        // vcsPlugin.parametrize(parameters);
        String checkoutCommand = vcsPlugin.getCheckoutCommand();
        String updateCommand = vcsPlugin.getUpdateCommand();

        Stage stage = new Stage();
        stage.setName("Vcs Prepare");

        Step checkoutStep = new Step();
        checkoutStep.setCommand(checkoutCommand);
        stage.addStep(checkoutStep);

        Step updateStep = new Step();
        updateStep.setCommand(updateCommand);
        stage.addStep(updateStep);

        buildPlan.addStage(stage);

        _stepDao.makePersistent(checkoutStep);
        _stepDao.makePersistent(updateStep);
        _stageDao.makePersistent(stage);
        _buildPlanDao.makePersistent(buildPlan);
        project.addBuildPlan(buildPlan);
        return updateCommand;

    }

}
