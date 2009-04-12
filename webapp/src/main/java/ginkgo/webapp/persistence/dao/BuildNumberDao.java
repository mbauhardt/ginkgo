package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildPlan;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildNumberDao extends BaseDao<BuildNumber> implements IBuildNumberDao {

    @Autowired
    public BuildNumberDao(PersistenceService persistenceService) {
        super(BuildNumber.class, persistenceService);
    }

    public BuildNumber getHighestBuildNumber(BuildPlan buildPlan) {
        Query query = _persistenceService.createQuery("select bn from " + _clazz.getSimpleName()
                + " as bn where bn._buildPlan._id = ?1 order by bn._buildNumber desc");
        query.setParameter(1, buildPlan.getId());
        query.setMaxResults(1);
        return (BuildNumber) query.getSingleResult();
    }

}
