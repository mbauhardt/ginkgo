package ginkgo.webapp.controller;

import ginkgo.webapp.controller.commandObjects.UserCommand;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IUserDao;
import ginkgo.webapp.persistence.model.User;
import ginkgo.webapp.persistence.model.User.Role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    private final IUserDao _userDao;

    @Autowired
    public UserController(IUserDao userDao) {
        _userDao = userDao;
    }

    @ModelAttribute(value = "users")
    public List<User> listUsersCommandObject() throws DaoException {
        return _userDao.getAll();
    }

    @ModelAttribute(value = "userCommand")
    public UserCommand createModelAttribute(Long id) throws DaoException {
        User user = new User();
        if (id != null) {
            user = _userDao.getById(id);
        }
        UserCommand userCommand = new UserCommand();
        userCommand.setId(user.getId());
        userCommand.setName(user.getName());
        userCommand.setPassword(user.getPassword());
        userCommand.setRole(user.getRole());
        return userCommand;
    }

    @ModelAttribute("roles")
    public Role[] listRoles() {
        return Role.values();
    }

    @RequestMapping(value = { "/administration/users.html" }, method = RequestMethod.GET)
    public String listUsers() throws DaoException {
        return "administration/users";
    }

    @RequestMapping(value = { "/administration/editUser.html" }, method = RequestMethod.GET)
    public String editUser() throws DaoException {
        return "administration/editUser";
    }

    @RequestMapping(value = { "/administration/editUser.html" }, method = RequestMethod.POST)
    public String editUser(@ModelAttribute("userCommand") UserCommand userCommand) throws DaoException {
        Long id = userCommand.getId();
        User user = null;
        if (id == null) {
            user = new User();
            _userDao.makePersistent(user);
        } else {
            user = _userDao.getById(id);
        }
        user.setName(userCommand.getName());
        user.setRole(userCommand.getRole());
        user.setPassword(userCommand.getPassword());
        return "redirect:users.html";
    }

}
