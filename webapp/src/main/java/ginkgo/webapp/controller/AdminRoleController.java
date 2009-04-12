package ginkgo.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = { "/administration/welcome.html" })
public class AdminRoleController {

    @RequestMapping(method = RequestMethod.GET)
    public String welcome() {
        return "administration/welcome";
    }

}
