package ginkgo.webapp.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.springframework.stereotype.Service;

@Service
public class JUserJPasswordCallbackHandler implements javax.security.auth.callback.CallbackHandler {

    private String _userName;
    
    private Object _password;

    public void setUserName(String userName) {
        _userName = userName;
    }

    public void setPassword(Object password) {
        _password = password;
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                NameCallback nameCallback = (NameCallback) callback;
                nameCallback.setName(_userName);
            } else if (callback instanceof PasswordCallback) {
                PasswordCallback passwordCallback = (PasswordCallback) callback;
                passwordCallback.setPassword(_password.toString().toCharArray());
            }
        }

    }

}
