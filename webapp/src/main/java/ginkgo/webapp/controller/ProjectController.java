package ginkgo.webapp.controller;

import ginkgo.api.IVcsable;
import ginkgo.webapp.controller.commandObjects.ConfigurationTupleCommand;
import ginkgo.webapp.controller.commandObjects.ProjectCommand;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IConfigurationTupleDao;
import ginkgo.webapp.persistence.dao.IProjectDao;
import ginkgo.webapp.persistence.model.ConfigurationTuple;
import ginkgo.webapp.persistence.model.Project;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {

    private final IProjectDao _projectDao;
    private final IVcsable[] _vcsables;
    private final IConfigurationTupleDao _configurationTupleDao;

    @Autowired
    public ProjectController(IProjectDao projectDao, IConfigurationTupleDao configurationTupleDao, IVcsable... vcsables) {
        _projectDao = projectDao;
        _configurationTupleDao = configurationTupleDao;
        _vcsables = vcsables;
    }

    @ModelAttribute("vcsList")
    public List<String> injectVcs() {
        List<String> list = new ArrayList<String>();
        for (IVcsable vcs : _vcsables) {
            list.add(vcs.getName());
        }
        return list;
    }

    @ModelAttribute("projects")
    public List<Project> injectProjects() throws DaoException {
        return _projectDao.getAll();
    }

    @ModelAttribute(value = "projectCommand")
    public ProjectCommand createModelAttribute(@RequestParam(value = "id", required = false) Long id,
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
            for (IVcsable vcsable : _vcsables) {
                if (vcsable.getName().equalsIgnoreCase(projectCommand.getVcs())) {
                    String[] parameterNames = vcsable.getParameterNames();
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

    @RequestMapping(value = "/user/listProjects.html", method = RequestMethod.GET)
    public String listProjects() throws DaoException {
        return "user/listProjects";
    }

    @RequestMapping(value = { "/user/editProject.html" }, method = RequestMethod.GET)
    public String editProject() throws DaoException {
        return "user/editProject";
    }

    @RequestMapping(value = { "/user/editProject.html" }, method = RequestMethod.POST)
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

        return "redirect:listProjects.html";
    }

    @RequestMapping(value = "/user/deleteProject.html", method = RequestMethod.POST)
    public String deleteProject(@RequestParam(value = "id", required = true) Long id) throws DaoException {
        Project byId = _projectDao.getById(id);
        List<ConfigurationTuple> configurationTuples = byId.getConfigurationTuples();
        for (ConfigurationTuple configurationTuple : configurationTuples) {
            _configurationTupleDao.makeTransient(configurationTuple);
        }
        _projectDao.makeTransient(byId);
        return "redirect:listProjects.html";
    }

}
