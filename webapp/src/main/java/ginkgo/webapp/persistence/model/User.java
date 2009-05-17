package ginkgo.webapp.persistence.model;

import javax.persistence.Entity;

@Entity
public class User extends UniqueBaseName {

    public static enum Role {
        GUEST("GUEST"), USER("USER"), ADMIN("ADMIN");

        private final String _name;

        private Role(String name) {
            _name = name;
        }

        public String getName() {
            return _name;
        }
    }

    private String _password;

    private Role _role;

    public User() {
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }

    public Role getRole() {
        return _role;
    }

    public void setRole(Role role) {
        _role = role;
    }

}
