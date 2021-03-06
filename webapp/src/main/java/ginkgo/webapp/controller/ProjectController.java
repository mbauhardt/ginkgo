package ginkgo.webapp.controller;

import ginkgo.api.VcsPlugin;
import ginkgo.shared.InvalidArgumentException;
import ginkgo.webapp.PluginRepository;
import ginkgo.webapp.controller.commandObjects.BuildPlanCommand;
import ginkgo.webapp.controller.commandObjects.ConfigurationTupleCommand;
import ginkgo.webapp.controller.commandObjects.ProjectCommand;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.dao.IConfigurationTupleDao;
import ginkgo.webapp.persistence.dao.IProjectDao;
import ginkgo.webapp.persistence.dao.IStageDao;
import ginkgo.webapp.persistence.dao.IStepDao;
import ginkgo.webapp.persistence.model.BuildPlan;
import ginkgo.webapp.persistence.model.ConfigurationTuple;
import ginkgo.webapp.persistence.model.Project;
import ginkgo.webapp.persistence.model.Stage;
import ginkgo.webapp.persistence.model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {

    private final IProjectDao _projectDao;
    private final IConfigurationTupleDao _configurationTupleDao;
    private final VcsPlugin[] _vcsPlugins;
    private final IBuildPlanDao _buildPlanDao;
    private final PluginRepository _pluginRepository;
    private final IStageDao _stageDao;
    private final IStepDao _stepDao;

    @Autowired
    public ProjectController(PluginRepository pluginRepository, IBuildPlanDao buildPlanDao, IStageDao stageDao,
            IStepDao stepDao, IProjectDao projectDao, IConfigurationTupleDao configurationTupleDao,
            VcsPlugin... vcsPlugins) {
        _pluginRepository = pluginRepository;
        _buildPlanDao = buildPlanDao;
        _stageDao = stageDao;
        _stepDao = stepDao;
        _projectDao = projectDao;
        _configurationTupleDao = configurationTupleDao;
        _vcsPlugins = vcsPlugins;
    }

    @ModelAttribute("vcsList")
    public List<String> injectVcs() {
        List<String> list = new ArrayList<String>();
        for (VcsPlugin vcs : _vcsPlugins) {
            list.add(vcs.getName());
        }
        return list;
    }

    @ModelAttribute("projects")
    public List<Project> injectProjects() throws DaoException {
        return _projectDao.getAll();
    }

    @ModelAttribute("buildPlanCommand")
    public BuildPlanCommand injectBuildPlan() {
        return new BuildPlanCommand();
    }

    @ModelAttribute(value = "projectCommand")
    public ProjectCommand createModelAttribute(@RequestParam(value = "projectId", required = false) Long id,
            @RequestParam(value = "vcs", required = false) String vcs) throws DaoException {
        Project project = new Project();
        if (id != null) {
            project = _projectDao.getById(id);
        }
        ProjectCommand projectCommand = new ProjectCommand();
        projectCommand.setId(project.getId());
        projectCommand.setName(project.getName());
        projectCommand.setVcs(project.getVcs());
        List<ConfigurationTuple> configurationTuples = project.getConfigurationTuples();
        for (ConfigurationTuple configurationTuple : configurationTuples) {
            projectCommand.addConfigurationTupleCommand(new ConfigurationTupleCommand(configurationTuple));
        }

        if (projectCommand.getVcs() == null) {
            projectCommand.setVcs(vcs);
        }

        if (projectCommand.getConfigurationTupleCommands().isEmpty()) {
            for (VcsPlugin vcsPlugin : _vcsPlugins) {
                if (vcsPlugin.getName().equalsIgnoreCase(projectCommand.getVcs())) {
                    String[] parameterNames = vcsPlugin.getParameterNames();
                    for (String parameter : parameterNames) {
                        ConfigurationTupleCommand command = new ConfigurationTupleCommand();
                        command.setTupleKey(parameter);
                        projectCommand.addConfigurationTupleCommand(command);
                    }
                }
            }
        }

        return projectCommand;
    }

    @RequestMapping(value = "/user/projects.html", method = RequestMethod.GET)
    public String listProjects2() throws DaoException {
        return "user/projects";
    }

    @RequestMapping(value = "/user/project.html", method = RequestMethod.GET)
    public String project(@RequestParam(value = "projectId") Long id, Model model) throws DaoException {
        Project byId = _projectDao.getById(id);
        model.addAttribute("project", byId);
        return "user/project";
    }

    @RequestMapping(value = { "/user/editProject.html", "/user/addProject.html" }, method = RequestMethod.GET)
    public String editProject() throws DaoException {
        return "user/editProject";
    }

    @RequestMapping(value = { "/user/editProject.html", "/user/addProject.html" }, method = RequestMethod.POST)
    public String editProject(@ModelAttribute("projectCommand") ProjectCommand projectCommand,
            HttpServletRequest request) throws DaoException {
        Long id = projectCommand.getId();
        Project project = null;
        if (id == null) {
            project = new Project();
            _projectDao.makePersistent(project);
        } else {
            project = _projectDao.getById(id);
        }
        project.setName(projectCommand.getName());
        project.setVcs(projectCommand.getVcs());

        List<ConfigurationTupleCommand> tupleCommands = projectCommand.getConfigurationTupleCommands();
        List<ConfigurationTuple> configurationTuples = project.getConfigurationTuples();
        if (configurationTuples.size() > 0 && configurationTuples.size() != tupleCommands.size()) {
            // FIXME validate
            throw new DaoException("tuple on db has not the same size of the command", null);
        }
        if (configurationTuples.size() > 0) {
            for (int i = 0; i < tupleCommands.size(); i++) {
                ConfigurationTuple configurationTuple = configurationTuples.get(i);
                configurationTuple.setTupleKey(tupleCommands.get(i).getTupleKey());
                configurationTuple.setTupleValue(tupleCommands.get(i).getTupleValue());
            }
        } else {
            for (int i = 0; i < tupleCommands.size(); i++) {
                ConfigurationTuple configurationTuple = new ConfigurationTuple();
                configurationTuple.setTupleKey(tupleCommands.get(i).getTupleKey());
                configurationTuple.setTupleValue(tupleCommands.get(i).getTupleValue());
                _configurationTupleDao.makePersistent(configurationTuple);
                project.addConfigurationTuple(configurationTuple);
            }
        }

        return "redirect:project.html?projectId=" + project.getId();
    }

    @RequestMapping(value = { "/user/deleteProject.html" }, method = RequestMethod.GET)
    public String deleteProject(Model model, @RequestParam(value = "projectId", required = true) Long projectId)
            throws DaoException {
        Project project = _projectDao.getById(projectId);
        model.addAttribute("project", project);
        return "/user/deleteProject";
    }

    @RequestMapping(value = "/user/deleteProject.html", method = RequestMethod.POST)
    public String deleteProject(@RequestParam(value = "projectId", required = true) Long id) throws DaoException {
        Project byId = _projectDao.getById(id);
        List<ConfigurationTuple> configurationTuples = byId.getConfigurationTuples();
        for (ConfigurationTuple configurationTuple : configurationTuples) {
            _configurationTupleDao.makeTransient(configurationTuple);
            _configurationTupleDao.flush();
        }
        List<BuildPlan> buildPlans = byId.getBuildPlans();
        for (BuildPlan buildPlan : buildPlans) {
            Set<Stage> stages = buildPlan.getStages();
            for (Stage stage : stages) {
                List<Step> steps = stage.getSteps();
                for (Step step : steps) {
                    _stepDao.makeTransient(step);
                    _stepDao.flush();
                }
                _stageDao.makeTransient(stage);
                _stageDao.flush();
            }
            _buildPlanDao.makeTransient(buildPlan);
            _buildPlanDao.flush();
        }
        _projectDao.makeTransient(byId);
        return "redirect:projects.html";
    }

}
