package ginkgo.webapp.controller;

import ginkgo.webapp.controller.commandObjects.StageCommand;
import ginkgo.webapp.controller.editor.BaseEditor;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.dao.IStageDao;
import ginkgo.webapp.persistence.model.BuildPlan;
import ginkgo.webapp.persistence.model.Stage;

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
public class StageController {

    private final IStageDao _stageDao;
    private final IBuildPlanDao _buildPlanDao;

    @Autowired
    public StageController(IStageDao stageDao, IBuildPlanDao buildPlanDao) {
        _stageDao = stageDao;
        _buildPlanDao = buildPlanDao;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(BuildPlan.class, new BaseEditor(_buildPlanDao));
    }

    @ModelAttribute("stages")
    public List<Stage> injectStages() throws DaoException {
        return _stageDao.getAll();
    }

    @ModelAttribute("buildPlans")
    public List<BuildPlan> injectBuildPlans() throws DaoException {
        return _buildPlanDao.getAll();
    }

    @ModelAttribute(value = "stageCommand")
    public StageCommand injectStageCommand(@RequestParam(value = "id", required = false) Long id) throws DaoException {
        Stage stage = new Stage();
        if (id != null) {
            stage = _stageDao.getById(id);
        }
        StageCommand stageCommand = new StageCommand();
        stageCommand.setId(stage.getId());
        stageCommand.setName(stage.getName());
        stageCommand.setBuildPlan(stage.getBuildPlan());
        return stageCommand;
    }

    @RequestMapping(value = "/user/listStages.html", method = RequestMethod.GET)
    public String listStages() throws DaoException {
        return "user/listStages";
    }

//    @RequestMapping(value = { "/user/editStage.html" }, method = RequestMethod.GET)
//    public String editStage() throws DaoException {
//        return "user/editStage";
//    }

//    @RequestMapping(value = { "/user/editStage.html" }, method = RequestMethod.POST)
//    public String editBuildPlan(@ModelAttribute("stageCommand") StageCommand stageCommand) throws DaoException {
//        System.out.println("StageController.editBuildPlan() " +stageCommand.getName());
//        Long id = stageCommand.getId();
//        Stage stage = null;
//        if (id == null) {
//            stage = new Stage();
//            _stageDao.makePersistent(stage);
//        } else {
//            stage = _stageDao.getById(id);
//        }
//        stage.setName(stageCommand.getName());
//        stage.setBuildPlan(stageCommand.getBuildPlan());
//        return "redirect:buildPlan.html?id="+stage.getBuildPlan().getId();
//    }

//    @RequestMapping(value = { "/user/editStage.html" }, method = RequestMethod.POST)
//    public String editBuildPlan(@ModelAttribute("foo") StageCommand stageCommand
//            ) throws DaoException {
//        //System.out.println("StageController.editBuildPlan() " +buildPlanCommand.getStageCommands().get(0).getName());
//        return "redirect:buildPlan.html?id=1";
//    }

}
