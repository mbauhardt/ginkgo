package ginkgo.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserRoleController {

    @RequestMapping(value = { "/user/welcome.html" }, method = RequestMethod.GET)
    public String welcome() {
        return "user/welcome";
    }

}
