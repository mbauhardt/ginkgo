package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.model.BuildCommand;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildPlan;

public interface IBuildNumberDao extends IBaseDao<BuildNumber> {

    BuildNumber getHighestBuildNumber(BuildPlan buildPlan);

    BuildNumber getByBuildCommand(BuildCommand buildCommand);
}
