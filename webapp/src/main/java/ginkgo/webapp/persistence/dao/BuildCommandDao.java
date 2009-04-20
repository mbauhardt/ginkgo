package ginkgo.webapp.persistence.dao;

import java.util.List;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.BuildCommand;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildCommandDao extends BaseDao<BuildCommand> implements IBuildCommandDao {

    @Autowired
    public BuildCommandDao(PersistenceService persistenceService) {
        super(BuildCommand.class, persistenceService);
    }

    @SuppressWarnings("unchecked")
    public BuildCommand getFirstBuildCommand(BuildCommand buildCommand) {
        Query query = _persistenceService.createQuery("select bc from " + _clazz.getSimpleName()
                + " as bc where bc._nextBuildCommand._id = ?1");
        query.setParameter(1, buildCommand.getId());
        List<BuildCommand> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return getFirstBuildCommand(resultList.get(0));
        }
        return buildCommand;

    }

}
