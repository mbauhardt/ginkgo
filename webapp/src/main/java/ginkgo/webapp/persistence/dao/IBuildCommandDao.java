package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.model.BuildCommand;

public interface IBuildCommandDao extends IBaseDao<BuildCommand> {

    BuildCommand getFirstBuildCommand(BuildCommand buildCommand);

}
