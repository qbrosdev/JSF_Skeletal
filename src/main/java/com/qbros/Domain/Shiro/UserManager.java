package com.qbros.Domain.Shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created by V.Ghasemi
 * on 12/4/2018.
 */
@SessionScoped
public class UserManager implements Serializable {


    public boolean hasUserThisRole(Role role){
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRole(role.getRoleName());
    }

    public enum Role {

        ADMIN("admin"), USER("user");

        String roleName;

        Role(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleName() {
            return roleName;
        }
    }
}
