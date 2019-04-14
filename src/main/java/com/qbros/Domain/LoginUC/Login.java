package com.qbros.Domain.LoginUC;

import java.io.Serializable;

/**
 * Created by V.Ghasemi
 * on 11/24/2018.
 */
public abstract class Login implements Serializable {
    abstract void logIn();
    abstract void logOut();

    private String user;
    private String pass;
    private Boolean rememberMe;

    //-----Getter/Setter------------------------------------------------------------------------------------------------
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean lembrar) {
        this.rememberMe = lembrar;
    }
}
