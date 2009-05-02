package ginkgo.webapp.controller;

import ginkgo.webapp.controller.commandObjects.BuildPlanCommand;
import ginkgo.webapp.controller.commandObjects.StageCommand;
import ginkgo.webapp.controller.commandObjects.StepCommand;
import ginkgo.webapp.controller.editor.BaseEditor;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.dao.IProjectDao;
import ginkgo.webapp.persistence.dao.IStageDao;
import ginkgo.webapp.persistence.model.BuildPlan;
import ginkgo.webapp.persistence.model.Project;
import ginkgo.webapp.persistence.model.Stage;
import ginkgo.webapp.persistence.model.Step;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @Autowired
    public BuildPlanController(IBuildPlanDao buildPlanDao, IProjectDao projectDao, IStageDao stageDao) {
        _buildPlanDao = buildPlanDao;
        _projectDao = projectDao;
        _stageDao = stageDao;
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

        List<Stage> stages = buildPlan.getStages();
        for (Stage stage : stages) {
            StageCommand stageCommand = new StageCommand();
            stageCommand.setId(stage.getId());
            stageCommand.setName(stage.getName());
            stageCommand.setBuildPlan(buildPlan);
            buildPlanCommand.addStageCommand(stageCommand);
            List<Step> steps = stage.getSteps();
            for (Step step : steps) {
                String name = step.getName();
                String command = step.getCommand();
                StepCommand stepCommand = new StepCommand();
                stepCommand.setName(name);
                stepCommand.setCommand(command);
                stageCommand.addStepCommand(stepCommand);
            }
        }
        StageCommand stageCommand = new StageCommand();
        stageCommand.setBuildPlan(buildPlan);
        buildPlanCommand.addStageCommand(stageCommand);
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
        int size = buildPlan.getStages().size();
        model.addAttribute("maxStages", size);
        return "user/buildPlan";
    }

    @RequestMapping(value = { "/user/editStage.html" }, method = RequestMethod.POST)
    public String editStage(@ModelAttribute("buildPlanCommand") BuildPlanCommand buildPlanCommand,
            @RequestParam(value = "listIndex", required = true) Integer listIndex) throws DaoException {
        StageCommand stageCommand = buildPlanCommand.getStageCommands().get(listIndex);
        Long id = stageCommand.getId();
        Stage stage = null;
        if (id == null) {
            stage = new Stage();
            _stageDao.makePersistent(stage);
        } else {
            stage = _stageDao.getById(id);
        }
        stage.setName(stageCommand.getName());
        stage.setBuildPlan(stageCommand.getBuildPlan());
        return "redirect:buildPlan.html?id=" + buildPlanCommand.getId();
    }

    @RequestMapping(value = { "/user/deleteStage.html" }, method = RequestMethod.POST)
    public String deleteStage(@ModelAttribute("buildPlanCommand") BuildPlanCommand buildPlanCommand,
            @RequestParam(value = "listIndex", required = true) Integer listIndex) throws DaoException {
        System.out.println("BuildPlanController.deleteStage()");
        return "redirect:buildPlan.html?id=" + buildPlanCommand.getId();
    }
}
