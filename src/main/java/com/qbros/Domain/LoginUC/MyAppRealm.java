package com.qbros.Domain.LoginUC;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by V.Ghasemi
 * on 12/12/2018.
 */
public class MyAppRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;

//        User user = userAccess.getUserByEmail(userPassToken.getUsername());
//        if (user == null) {
//            return null;
//        }

        AuthenticationInfo info = new SimpleAuthenticationInfo();
        // set data in AuthenticationInfo based on data from the user object

        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO
        return null;
    }
}