package ginkgo.webapp;

import ginkgo.vcs.svn.Svn;
import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.dao.IConfigurationTupleDao;
import ginkgo.webapp.persistence.dao.IProjectDao;
import ginkgo.webapp.persistence.dao.IStageDao;
import ginkgo.webapp.persistence.dao.IStepDao;
import ginkgo.webapp.persistence.dao.IUserDao;
import ginkgo.webapp.persistence.model.BuildPlan;
import ginkgo.webapp.persistence.model.ConfigurationTuple;
import ginkgo.webapp.persistence.model.Project;
import ginkgo.webapp.persistence.model.Stage;
import ginkgo.webapp.persistence.model.Step;
import ginkgo.webapp.persistence.model.User;
import ginkgo.webapp.persistence.model.User.Role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInjector {

    private final IUserDao _userDao;
    private final PersistenceService _persistenceService;
    private final IProjectDao _projectDao;
    private final IBuildPlanDao _buildPlanDao;
    private final IStageDao _stageDao;
    private final IStepDao _stepDao;
    private final Svn _svn;
    private final IConfigurationTupleDao _configurationTupleDao;

    @Autowired
    public DataInjector(IUserDao userDao, IProjectDao projectDao, IBuildPlanDao buildPlanDao, IStageDao stageDao,
            IStepDao stepDao, IConfigurationTupleDao configurationTupleDao, PersistenceService persistenceService,
            Svn svn) throws DaoException {
        _userDao = userDao;
        _projectDao = projectDao;
        _buildPlanDao = buildPlanDao;
        _stageDao = stageDao;
        _stepDao = stepDao;
        _configurationTupleDao = configurationTupleDao;
        _persistenceService = persistenceService;
        _svn = svn;
        injectUser();
        injectProject();
        injectBuildPlan();
        injectStage();
        injectStep();
    }

    private void injectStep() throws DaoException {
        _persistenceService.beginTransaction();
        List<Stage> stages = _stageDao.getByName("Vcs Prepare");
        Step step = new Step();
        step.setCommand("ls");
        Stage stage = stages.get(0);
        if (stage.getSteps().size() == 0) {
            stage.addStep(step);
            step.setStage(stage);
            _stepDao.makePersistent(step);
        }

        stages = _stageDao.getByName("Compile");
        step = new Step();
        step.setCommand("pwd");
        stage = stages.get(0);
        if (stage.getSteps().size() == 0) {
            stage.addStep(step);
            step.setStage(stage);
            _stepDao.makePersistent(step);
        }

        step = new Step();
        step.setCommand("foo");
        if (stage.getSteps().size() == 0) {
            stage.addStep(step);
            step.setStage(stage);
            _stepDao.makePersistent(step);
        }

        _persistenceService.commitTransaction();

    }

    private void injectStage() throws DaoException {
        _persistenceService.beginTransaction();
        List<BuildPlan> buildPlans = _buildPlanDao.getByName("TEST-CORE");
        Stage stage = new Stage();
        stage.setName("Vcs Prepare");
        stage.setBuildPlan(buildPlans.get(0));
        if (!_stageDao.exists("Vcs Prepare")) {
            _stageDao.makePersistent(stage);
        }
        Stage stage2 = new Stage();
        stage2.setName("Compile");
        stage2.setBuildPlan(buildPlans.get(0));
        if (!_stageDao.exists("Compile")) {
            _stageDao.makePersistent(stage2);
        }
        _persistenceService.commitTransaction();
    }

    private void injectBuildPlan() throws DaoException {
        _persistenceService.beginTransaction();
        BuildPlan buildPlan = new BuildPlan();
        buildPlan.setName("TEST-CORE");
        List<Project> projects = _projectDao.getByName("test-project");
        buildPlan.setProject(projects.get(0));
        if (!_buildPlanDao.exists("TEST-CORE")) {
            _buildPlanDao.makePersistent(buildPlan);
        }
        _persistenceService.commitTransaction();

    }

    private void injectProject() throws DaoException {
        _persistenceService.beginTransaction();
        Project project = new Project();
        project.setName("test-project");
        project.setVcs(_svn.getName());
        if (!_projectDao.exists("test-project")) {
            List<ConfigurationTuple> tupleList = new ArrayList<ConfigurationTuple>();
            String[] parameterNames = _svn.getParameterNames();
            for (String string : parameterNames) {
                ConfigurationTuple configurationTuple = new ConfigurationTuple();
                configurationTuple.setTupleKey(string);
                if (string.equals("Trunk Url")) {
                    configurationTuple.setTupleValue("trunk");
                } else if (string.equals("Branches Url")) {
                    configurationTuple.setTupleValue("branches");
                } else if (string.equals("Tags Url")) {
                    configurationTuple.setTupleValue("tags");
                } else if (string.equals("User Name")) {
                    configurationTuple.setTupleValue("mb");
                } else if (string.equals("Password")) {
                    configurationTuple.setTupleValue("password");
                }
                _configurationTupleDao.makePersistent(configurationTuple);
                tupleList.add(configurationTuple);
            }
            project.setConfigurationTuples(tupleList);
            _projectDao.makePersistent(project);
        }
        _persistenceService.commitTransaction();
    }

    public void injectUser() throws DaoException {
        User admin = new User();
        admin.setName("admin");
        admin.setPassword("admin");
        admin.setRole(Role.ADMIN);
        _persistenceService.beginTransaction();
        if (!_userDao.exists("admin")) {
            _userDao.makePersistent(admin);
        }
        _persistenceService.commitTransaction();

    }

}
