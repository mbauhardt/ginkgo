package ginkgo.webapp.controller;

import ginkgo.shared.BuildStatus;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildNumberDao;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.model.BuildCommand;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildPlan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

    @RequestMapping(value = "/user/buildNumbers.html", method = RequestMethod.GET)
    public String buildNumbers(Model model, @RequestParam(value = "buildPlanId", required = true) Long id)
            throws DaoException {
        BuildPlan buildPlan = _buildPlanDao.getById(id);
        List<BuildNumber> buildNumbers = buildPlan.getBuildNumbers();
        Collections.reverse(buildNumbers);
        for (BuildNumber buildNumber : buildNumbers) {
            List<BuildCommand> list = new ArrayList<BuildCommand>();
            BuildCommand buildCommand = buildNumber.getBuildCommand();
            collectBuildCommands(buildCommand, list);
            calculateBuildStatus(buildNumber, list);
        }
        model.addAttribute("buildNumbers", buildNumbers);
        model.addAttribute("buildPlan", buildPlan);
        return "user/buildNumbers";
    }

    @RequestMapping(value = "/user/buildNumber.html", method = RequestMethod.GET)
    public String buildNumber(Model model, @RequestParam(value = "buildNumberId", required = true) Long id)
            throws DaoException {
        BuildNumber buildNumber = _buildNumberDao.getById(id);
        List<BuildCommand> list = new ArrayList<BuildCommand>();
        collectBuildCommands(buildNumber.getBuildCommand(), list);
        calculateBuildStatus(buildNumber, list);
        model.addAttribute("buildNumber", buildNumber);
        model.addAttribute("buildCommands", list);
        return "user/buildNumber";
    }

    private void collectBuildCommands(BuildCommand buildCommand, List<BuildCommand> list) {
        list.add(buildCommand);
        BuildCommand nextBuild = buildCommand.getNextBuildCommand();
        if (nextBuild != null) {
            collectBuildCommands(nextBuild, list);
        }
    }

    private void calculateBuildStatus(BuildNumber buildNumber, List<BuildCommand> buildCommands) {
        for (BuildCommand buildCommand : buildCommands) {
            Map<String, BuildStatus> buildAgentStatus = buildCommand.getBuildAgentStatus();
            Collection<BuildStatus> values = buildAgentStatus.values();
            Set<BuildStatus> set = new TreeSet<BuildStatus>(values);
            if(set.isEmpty() && buildNumber.getBuildStatus() != BuildStatus.NOT_RUNNING) {
                buildNumber.setBuildStatus(BuildStatus.RUNNING);
            }
            if (set.contains(BuildStatus.RUNNING)) {
                buildNumber.setBuildStatus(BuildStatus.RUNNING);
            } else if (set.contains(BuildStatus.NOT_RUNNING)) {
                if (set.contains(BuildStatus.FAILURE) || set.contains(BuildStatus.SUCCESS)) {
                    buildNumber.setBuildStatus(BuildStatus.RUNNING);
                }
            } else if (set.contains(BuildStatus.FAILURE)) {
                buildNumber.setBuildStatus(BuildStatus.FAILURE);
            } else if (set.contains(BuildStatus.SUCCESS)) {
                buildNumber.setBuildStatus(BuildStatus.SUCCESS);
            }
        }
    }

}
