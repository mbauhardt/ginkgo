package ginkgo.webapp.security;

import ginkgo.webapp.Environment;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IUserDao;
import ginkgo.webapp.persistence.model.User;
import ginkgo.webapp.persistence.model.User.Role;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;

public class GinkgoLoginModule implements javax.security.auth.spi.LoginModule {

    private Subject _subject;
    private CallbackHandler _callbackHandler;
    private String _userName;
    private char[] _password;
    private boolean _authenticated;
    private boolean _commited;
    private GinkgoPrincipal _pricipal;
    private Role _role;

    public boolean abort() throws LoginException {
        boolean bit = false;
        if (_authenticated && !_commited) {
            _userName = null;
            _password = null;
            _pricipal = null;
            _role = null;
            bit = true;
        } else {
            logout();
            bit = true;
        }
        return bit;
    }

    public boolean commit() throws LoginException {
        boolean bit = false;
        if (_authenticated) {
            Set<Principal> principals = _subject.getPrincipals();
            _pricipal = new GinkgoPrincipal(_userName, new String(_password), _role);
            principals.add(_pricipal);
            bit = true;
        }
        _commited = bit;
        return _commited;
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
            Map<String, ?> options) {
        _subject = subject;
        _callbackHandler = callbackHandler;
    }

    public boolean login() throws LoginException {

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("user name: ");
        callbacks[1] = new PasswordCallback("password: ", false);

        try {
            _callbackHandler.handle(callbacks);
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }

        _userName = ((NameCallback) callbacks[0]).getName();
        _password = ((PasswordCallback) callbacks[1]).getPassword();

        IUserDao userDao = Environment.getInstance().getUserDao();
        User byName;
        try {
            byName = userDao.getByName(_userName);
            String password2 = byName.getPassword();
            if (password2.equals(new String(_password))) {
                _authenticated = true;
                _role = byName.getRole();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }

        if (!_authenticated) {
            _userName = null;
            _password = null;
            _role = null;
        }
        return _authenticated;
    }

    public boolean logout() throws LoginException {
        Set<Principal> principals = _subject.getPrincipals();
        principals.remove(_pricipal);
        _authenticated = false;
        _commited = false;
        _pricipal = null;
        _role = null;
        return true;
    }

}
