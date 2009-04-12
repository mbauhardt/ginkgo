package ginkgo.webapp;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.dao.IUserDao;
import ginkgo.webapp.security.JUserJPasswordCallbackHandler;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.mortbay.jetty.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Environment {

    private static Environment _instance;
    private final JUserJPasswordCallbackHandler _callbackHandler;
    private final IUserDao _userDao;
    private final PersistenceService _persistenceService;

    @Autowired
    public Environment(JUserJPasswordCallbackHandler callbackHandler, PersistenceService persistenceService,
            IUserDao userDao) {
        _callbackHandler = callbackHandler;
        _persistenceService = persistenceService;
        _userDao = userDao;
        _instance = this;
    }

    public static Environment getInstance() {
        return _instance;
    }

    private CallbackHandler createCallBackHandler(Request request) {
        String userName = request.getParameter("j_username");
        String password = request.getParameter("j_password");
        _callbackHandler.setUserName(userName);
        _callbackHandler.setPassword(password);
        return _callbackHandler;
    }

    public LoginContext createLoginContext(Request request) throws LoginException {
        CallbackHandler callBackHandler = createCallBackHandler(request);
        LoginContext loginContext = new LoginContext("GinkgoLoginModule", callBackHandler);
        return loginContext;
    }

    public IUserDao getUserDao() {
        return _userDao;
    }

    public PersistenceService getPersistenceService() {
        return _persistenceService;
    }
}
