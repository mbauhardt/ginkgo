package ginkgo.webapp.security;

import ginkgo.webapp.Environment;

import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.mortbay.jetty.Request;
import org.springframework.stereotype.Service;

@Service
public class UserRealm implements org.mortbay.jetty.security.UserRealm {

    public Principal authenticate(String userName, Object password, Request request) {
        Principal ret = null;
        try {
            LoginContext loginContext = Environment.getInstance().createLoginContext(request);
            loginContext.login();
            Subject subject = loginContext.getSubject();
            Set<Principal> principals = subject.getPrincipals();
            for (Principal principal : principals) {
                ret = principal;
                break;
            }
        } catch (LoginException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public boolean reauthenticate(Principal principal) {
        return (principal instanceof GinkgoPrincipal);
    }

    public boolean isUserInRole(Principal principal, String role) {
        boolean bit = false;
        if (principal instanceof GinkgoPrincipal) {
            GinkgoPrincipal ginkgoPrincipal = (GinkgoPrincipal) principal;
            bit = ginkgoPrincipal.isInRole(role);
        }
        return bit;
    }

    public void disassociate(Principal principal) {
        // nothing todo
    }

    public String getName() {
        throw new UnsupportedOperationException("");
    }

    public Principal getPrincipal(String arg0) {
        throw new UnsupportedOperationException("");
    }

    public void logout(Principal principal) {
        // nothing todo
    }

    public Principal popRole(Principal principal) {
        // nothing todo
        return principal;
    }

    public Principal pushRole(Principal principal, String role) {
        // nothing todo
        return principal;
    }

}
