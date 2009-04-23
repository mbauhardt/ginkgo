package ginkgo.webapp.controller;

import ginkgo.webapp.controller.commandObjects.BuildPlanCommand;
import ginkgo.webapp.controller.editor.BaseEditor;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.dao.IProjectDao;
import ginkgo.webapp.persistence.model.BuildPlan;
import ginkgo.webapp.persistence.model.Project;

import java.util.List;

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

    @Autowired
    public BuildPlanController(IBuildPlanDao buildPlanDao, IProjectDao projectDao) {
        _buildPlanDao = buildPlanDao;
        _projectDao = projectDao;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Project.class, new BaseEditor(_projectDao));
    }

    @ModelAttribute("projects")
    public List<Project> injectProjects() throws DaoException {
        return _projectDao.getAll();
    }

    @ModelAttribute("buildPlans")
    public List<BuildPlan> injectBuildPlans() throws DaoException {
        return _buildPlanDao.getAll();
    }

    @ModelAttribute(value = "buildPlanCommand")
    public BuildPlanCommand injectBuildPlanCommand(@RequestParam(value = "id", required = false) Long id)
            throws DaoException {
        BuildPlan buildPlan = new BuildPlan();
        if (id != null) {
            buildPlan = _buildPlanDao.getById(id);
        }
        BuildPlanCommand buildPlanCommand = new BuildPlanCommand();
        buildPlanCommand.setId(buildPlan.getId());
        buildPlanCommand.setName(buildPlan.getName());
        buildPlanCommand.setProject(buildPlan.getProject());
        return buildPlanCommand;
    }

    @RequestMapping(value = "/user/listBuildPlans.html", method = RequestMethod.GET)
    public String listBuildPlans() throws DaoException {
        return "user/listBuildPlans";
    }

    @RequestMapping(value = { "/user/editBuildPlan.html" }, method = RequestMethod.GET)
    public String editBuildPlan() throws DaoException {
        return "user/editBuildPlan";
    }

    @RequestMapping(value = { "/user/editBuildPlan.html" }, method = RequestMethod.POST)
    public String editBuildPlan(@ModelAttribute("buildPlanCommand") BuildPlanCommand buildPlanCommand)
            throws DaoException {
        Long id = buildPlanCommand.getId();
        BuildPlan buildPlan = null;
        if (id == null) {
            buildPlan = new BuildPlan();
            _buildPlanDao.makePersistent(buildPlan);
        } else {
            buildPlan = _buildPlanDao.getById(id);
        }
        buildPlan.setName(buildPlanCommand.getName());
        buildPlan.setProject(buildPlanCommand.getProject());
        return "redirect:listBuildPlans.html";
    }

    @RequestMapping(value = "/user/buildPlan.html", method = RequestMethod.GET)
    public String buildPlanTree(Model model, @RequestParam("id") Long id) throws DaoException {
        BuildPlan buildPlan = _buildPlanDao.getById(id);
        model.addAttribute("buildPlan", buildPlan);
        return "user/buildPlan";
    }
}
