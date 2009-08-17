package ginkgo.webapp.controller;

import ginkgo.webapp.controller.commandObjects.StageCommand;
import ginkgo.webapp.controller.editor.BaseEditor;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.dao.IStageDao;
import ginkgo.webapp.persistence.dao.IStepDao;
import ginkgo.webapp.persistence.model.BuildPlan;
import ginkgo.webapp.persistence.model.Stage;
import ginkgo.webapp.persistence.model.Step;

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
public class StageController {

    private final IStageDao _stageDao;
    private final IBuildPlanDao _buildPlanDao;
    private final IStepDao _stepDao;

    @Autowired
    public StageController(final IBuildPlanDao buildPlanDao, final IStageDao stageDao, final IStepDao stepDao) {
        _buildPlanDao = buildPlanDao;
        _stageDao = stageDao;
        _stepDao = stepDao;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(BuildPlan.class, new BaseEditor(_buildPlanDao));
    }

    @RequestMapping(value = { "/user/addStage.html" }, method = RequestMethod.GET)
    public String addStage(Model model, @RequestParam(value = "buildPlanId", required = true) Long buildPlanId)
            throws DaoException {
        StageCommand stageCommand = new StageCommand();
        BuildPlan buildPlan = _buildPlanDao.getById(buildPlanId);
        stageCommand.setBuildPlan(buildPlan);
        model.addAttribute("stageCommand", stageCommand);
        return "/user/stage";
    }

    @RequestMapping(value = { "/user/editStage.html" }, method = RequestMethod.GET)
    public String editStep(Model model, @RequestParam(value = "stageId", required = true) Long stageId)
            throws DaoException {
        StageCommand stageCommand = new StageCommand();
        Stage stage = _stageDao.getById(stageId);
        stageCommand.setId(stage.getId());
        stageCommand.setName(stage.getName());
        stageCommand.setBuildPlan(stage.getBuildPlan());
        model.addAttribute("stageCommand", stageCommand);
        return "/user/stage";
    }

    @RequestMapping(value = { "/user/editStage.html", "/user/addStage.html" }, method = RequestMethod.POST)
    public String postAddOrEditStep(@ModelAttribute("stageCommand") StageCommand stageCommand) throws DaoException {
        Long id = stageCommand.getId();
        Stage stage = null;
        if (id == null) {
            stage = new Stage();
            stage.setBuildPlan(stageCommand.getBuildPlan());
            _stageDao.makePersistent(stage);
        } else {
            stage = _stageDao.getById(id);
        }
        stage.setName(stageCommand.getName());
        return "redirect:buildPlan.html?buildPlanId=" + stageCommand.getBuildPlan().getId();
    }

    @RequestMapping(value = { "/user/deleteStage.html" }, method = RequestMethod.GET)
    public String deleteStage(Model model, @RequestParam(value = "stageId", required = true) Long stageId)
            throws DaoException {
        Stage stage = _stageDao.getById(stageId);
        model.addAttribute("stage", stage);
        return "/user/deleteStage";
    }

    @RequestMapping(value = { "/user/deleteStage.html" }, method = RequestMethod.POST)
    public String postDeleteStage(@RequestParam(value = "stageId", required = true) Long id) throws DaoException {
        Stage stage = _stageDao.getById(id);
        List<Step> steps = stage.getSteps();
        for (Step step : steps) {
            _stepDao.makeTransient(step);
            _stepDao.flush();
        }
        _stageDao.makeTransient(stage);
        return "redirect:buildPlan.html?buildPlanId=" + stage.getBuildPlan().getId();
    }

}
