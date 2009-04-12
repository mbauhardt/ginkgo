package ginkgo.webapp.controller;

import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildNumberDao;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.model.BuildCommand;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BuildNumberController {

    private final IBuildNumberDao _buildNumberDao;
    private final IBuildPlanDao _buildPlanDao;

    @Autowired
    public BuildNumberController(IBuildPlanDao buildPlanDao, IBuildNumberDao buildNumberDao) {
        _buildPlanDao = buildPlanDao;
        _buildNumberDao = buildNumberDao;
    }

    @RequestMapping(value = "/user/listBuildNumbers.html", method = RequestMethod.GET)
    public String buildNumbers(Model model, @RequestParam(value = "buildPlanId", required = true) Long id)
            throws DaoException {
        BuildPlan buildPlan = _buildPlanDao.getById(id);
        List<BuildNumber> buildNumbers = buildPlan.getBuildNumbers();
        Collections.reverse(buildNumbers);
        model.addAttribute("buildNumbers", buildNumbers);
        return "user/listBuildNumbers";
    }

    @RequestMapping(value = "/user/buildNumber.html", method = RequestMethod.GET)
    public String buildNumber(Model model, @RequestParam(value = "buildNumberId", required = true) Long id)
            throws DaoException {
        BuildNumber buildNumber = _buildNumberDao.getById(id);
        List<BuildCommand> list = new ArrayList<BuildCommand>();
        collectBuildCommands(buildNumber.getBuildCommand(), list);
        model.addAttribute("buildNumber", buildNumber);
        model.addAttribute("buildCommands", list);
        return "user/buildNumber";
    }

    private void collectBuildCommands(BuildCommand buildCommand, List<BuildCommand> list) {
        list.add(buildCommand);
        BuildCommand nextBuild = buildCommand.getNextBuild();
        if (nextBuild != null) {
            collectBuildCommands(nextBuild, list);
        }
    }
}
