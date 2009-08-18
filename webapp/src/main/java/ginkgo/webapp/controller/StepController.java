package ginkgo.webapp.controller;

import ginkgo.webapp.controller.commandObjects.StepCommand;
import ginkgo.webapp.controller.editor.BaseEditor;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IStageDao;
import ginkgo.webapp.persistence.dao.IStepDao;
import ginkgo.webapp.persistence.model.Stage;
import ginkgo.webapp.persistence.model.Step;

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
public class StepController {

    private final IStepDao _stepDao;
    private final IStageDao _stageDao;

    @Autowired
    public StepController(final IStepDao stepDao, final IStageDao stageDao) {
        _stepDao = stepDao;
        _stageDao = stageDao;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Stage.class, new BaseEditor(_stageDao));
    }

    @RequestMapping(value = { "/user/addStep.html" }, method = RequestMethod.GET)
    public String addStep(Model model, @RequestParam(value = "stageId", required = true) Long stageId)
            throws DaoException {
        StepCommand stepCommand = new StepCommand();
        Stage stage = _stageDao.getById(stageId);
        stepCommand.setStage(stage);
        model.addAttribute("stepCommand", stepCommand);
        return "/user/editStep";
    }

    @RequestMapping(value = { "/user/editStep.html" }, method = RequestMethod.GET)
    public String editStep(Model model, @RequestParam(value = "stepId", required = true) Long stepId)
            throws DaoException {
        StepCommand stepCommand = new StepCommand();
        Step step = _stepDao.getById(stepId);
        stepCommand.setId(step.getId());
        stepCommand.setCommand(step.getCommand());
        stepCommand.setStage(step.getStage());
        model.addAttribute("stepCommand", stepCommand);
        return "/user/editStep";
    }

    @RequestMapping(value = { "/user/editStep.html", "/user/addStep.html" }, method = RequestMethod.POST)
    public String postAddOrEditStep(@ModelAttribute("stepCommand") StepCommand stepCommand) throws DaoException {
        Long id = stepCommand.getId();
        Step step = null;
        if (id == null) {
            step = new Step();
            step.setStage(stepCommand.getStage());
            _stepDao.makePersistent(step);
        } else {
            step = _stepDao.getById(id);
        }
        step.setCommand(stepCommand.getCommand());
        return "redirect:buildPlan.html?buildPlanId=" + stepCommand.getStage().getBuildPlan().getId();
    }

    @RequestMapping(value = { "/user/deleteStep.html" }, method = RequestMethod.GET)
    public String deleteStep(Model model, @RequestParam(value = "stepId", required = true) Long stepId)
            throws DaoException {
        Step step = _stepDao.getById(stepId);
        model.addAttribute("step", step);
        return "/user/deleteStep";
    }

    @RequestMapping(value = { "/user/deleteStep.html" }, method = RequestMethod.POST)
    public String postDeleteStep(@RequestParam(value = "stepId", required = true) Long id) throws DaoException {
        Step stepById = _stepDao.getById(id);
        _stepDao.makeTransient(stepById);
        return "redirect:buildPlan.html?buildPlanId=" + stepById.getStage().getBuildPlan().getId();
    }

}
