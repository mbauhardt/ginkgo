package ginkgo.webapp.controller;

import ginkgo.webapp.controller.commandObjects.BuildPlanCommand;
import ginkgo.webapp.controller.commandObjects.StageCommand;
import ginkgo.webapp.controller.commandObjects.StepCommand;
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

    @Autowired
    public BuildPlanController(IBuildPlanDao buildPlanDao, IProjectDao projectDao, IStageDao stageDao,
            IStepDao stepDao, IBuildNumberDao buildNumberDao, IBuildDao buildDao) {
        _buildPlanDao = buildPlanDao;
        _projectDao = projectDao;
        _stageDao = stageDao;
        _stepDao = stepDao;
        _buildNumberDao = buildNumberDao;
        _buildDao = buildDao;
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

        Set<Stage> stages = buildPlan.getStages();
        for (Stage stage : stages) {
            StageCommand stageCommand = new StageCommand();
            stageCommand.setId(stage.getId());
            stageCommand.setName(stage.getName());
            stageCommand.setBuildPlan(buildPlan);
            buildPlanCommand.addStageCommand(stageCommand);
            Set<Step> steps = stage.getSteps();
            for (Step step : steps) {
                StepCommand stepCommand = new StepCommand();
                stepCommand.setId(step.getId());
                stepCommand.setName(step.getName());
                stepCommand.setCommand(step.getCommand());
                stageCommand.addStepCommand(stepCommand);
            }
            StepCommand stepCommand = new StepCommand();
            stageCommand.addStepCommand(stepCommand);
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
        return "user/buildPlan";
    }

    // STAGE
    @RequestMapping(value = { "/user/editStage.html" }, method = RequestMethod.POST)
    public String editStage(@ModelAttribute("buildPlanCommand") BuildPlanCommand buildPlanCommand,
            @RequestParam(value = "stageIndex", required = true) Integer stageIndex) throws DaoException {
        StageCommand stageCommand = buildPlanCommand.getStageCommands().get(stageIndex);
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
            @RequestParam(value = "stageIndex", required = true) Integer stageIndex) throws DaoException {
        List<StageCommand> stageCommands = buildPlanCommand.getStageCommands();
        StageCommand stageCommand = stageCommands.get(stageIndex);
        Long id = stageCommand.getId();
        Stage stageById = _stageDao.getById(id);
        Set<Step> steps = stageById.getSteps();
        for (Step step : steps) {
            _stepDao.makeTransient(step);
        }
        _stageDao.makeTransient(stageById);
        return "redirect:buildPlan.html?id=" + buildPlanCommand.getId();
    }

    // STEP
    @RequestMapping(value = { "/user/editStep.html" }, method = RequestMethod.POST)
    public String editStep(@ModelAttribute("buildPlanCommand") BuildPlanCommand buildPlanCommand,
            @RequestParam(value = "stageIndex", required = true) Integer stageIndex,
            @RequestParam(value = "stepIndex", required = true) Integer stepIndex) throws DaoException {
        StageCommand stageCommand = buildPlanCommand.getStageCommands().get(stageIndex);
        List<StepCommand> stepCommands = stageCommand.getStepCommands();
        StepCommand stepCommand = stepCommands.get(stepIndex);
        Long id = stepCommand.getId();
        Step step = null;
        if (id == null) {
            step = new Step();
            _stepDao.makePersistent(step);
        } else {
            step = _stepDao.getById(id);
        }
        step.setName(stepCommand.getName());
        step.setCommand(stepCommand.getCommand());

        Stage stage = _stageDao.getById(stageCommand.getId());
        step.setStage(stage);
        return "redirect:buildPlan.html?id=" + buildPlanCommand.getId();
    }

    @RequestMapping(value = { "/user/deleteStep.html" }, method = RequestMethod.POST)
    public String deleteStep(@ModelAttribute("buildPlanCommand") BuildPlanCommand buildPlanCommand,
            @RequestParam(value = "stageIndex", required = true) Integer stageIndex,
            @RequestParam(value = "stepIndex", required = true) Integer stepIndex) throws DaoException {
        List<StageCommand> stageCommands = buildPlanCommand.getStageCommands();
        StageCommand stageCommand = stageCommands.get(stageIndex);
        List<StepCommand> stepCommands = stageCommand.getStepCommands();
        StepCommand stepCommand = stepCommands.get(stepIndex);
        Long id = stepCommand.getId();
        Step stepById = _stepDao.getById(id);
        _stepDao.makeTransient(stepById);
        return "redirect:buildPlan.html?id=" + buildPlanCommand.getId();
    }

    // run buildplan
    @RequestMapping(value = { "/user/runBuildPlan.html" }, method = RequestMethod.POST)
    public String runBuildPLan(@RequestParam("id") Long id) throws DaoException {

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
            Set<Step> steps = stage.getSteps();
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

}
