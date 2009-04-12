package ginkgo.webapp.controller;

import ginkgo.webapp.controller.commandObjects.StepCommand;
import ginkgo.webapp.controller.editor.BaseEditor;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IStageDao;
import ginkgo.webapp.persistence.dao.IStepDao;
import ginkgo.webapp.persistence.model.Stage;
import ginkgo.webapp.persistence.model.Step;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public StepController(IStepDao stepDao, IStageDao stageDao) {
        _stepDao = stepDao;
        _stageDao = stageDao;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Stage.class, new BaseEditor(_stageDao));
    }

    @ModelAttribute("steps")
    public List<Step> injectSteps() throws DaoException {
        return _stepDao.getAll();
    }

    @ModelAttribute("stages")
    public List<Stage> injectStages() throws DaoException {
        return _stageDao.getAll();
    }

    @ModelAttribute(value = "stepCommand")
    public StepCommand injectStepCommand(@RequestParam(value = "id", required = false) Long id) throws DaoException {
        Step step = new Step();
        if (id != null) {
            step = _stepDao.getById(id);
        }
        StepCommand stepCommand = new StepCommand();
        stepCommand.setId(step.getId());
        stepCommand.setName(step.getName());
        stepCommand.setCommand(step.getCommand());
        stepCommand.setStage(step.getStage());
        return stepCommand;
    }

    @RequestMapping(value = "/user/listSteps.html", method = RequestMethod.GET)
    public String listSteps() throws DaoException {
        return "user/listSteps";
    }

    @RequestMapping(value = { "/user/editStep.html" }, method = RequestMethod.GET)
    public String editStep() throws DaoException {
        return "user/editStep";
    }

    @RequestMapping(value = { "/user/editStep.html" }, method = RequestMethod.POST)
    public String editStep(@ModelAttribute("stepCommand") StepCommand stepCommand) throws DaoException {
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
        step.setStage(stepCommand.getStage());
        return "redirect:listSteps.html";
    }

}
