package ginkgo.webapp.security;

import java.security.Principal;

public class AnonymousPrincipal implements Principal {

    public String getName() {
        return "Anonymous";
    }

}
