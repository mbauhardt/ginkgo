package ginkgo.webapp.security;

import ginkgo.webapp.persistence.model.User.Role;

import java.security.Principal;


public class GinkgoPrincipal implements Principal {

    private final String _userName;
    private final String _password;
    private final Role _role;

    public GinkgoPrincipal(String userName, String password, Role role) {
        _userName = userName;
        _password = password;
        _role = role;
    }

    public String getName() {
        return _userName;
    }

    public String getPassword() {
        return _password;
    };

    public boolean authenticate(String password) {
        return _password.equals(password);
    }
    
    public boolean isInRole(String role) {
        return _role.toString().equalsIgnoreCase(role);
    }
    
}
