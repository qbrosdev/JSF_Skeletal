//package com.qbros.Domain.LoginUC;
//
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import com.stormpath.sdk.account.Account;
//import com.stormpath.sdk.application.Application;
//import com.stormpath.sdk.authc.AuthenticationRequest;
//import com.stormpath.sdk.client.Client;
//import com.stormpath.sdk.group.Group;
//import com.stormpath.sdk.group.GroupList;
//import com.stormpath.sdk.impl.authc.DefaultUsernamePasswordRequest;
//import com.stormpath.sdk.resource.ResourceException;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.Permission;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.apache.shiro.util.CollectionUtils;
//import org.apache.shiro.util.StringUtils;
//
///**
// * Created by V.Ghasemi
// * on 12/12/2018.
// */
//public class MyAppRealmT extends AuthorizingRealm {
//
//
//    private String applicationRestUrl;
//    private Application application; //acquired via the client at runtime, not configurable by the Realm user
//
//    public ApplicationRealm() {
//        //Stormpath authenticates user accounts directly, no need to perform that here in Shiro:
//        setCredentialsMatcher(new AllowAllCredentialsMatcher());
//        setGroupRoleResolver(new DefaultGroupRoleResolver());
//        setGroupPermissionResolver(new GroupCustomDataPermissionResolver());
//        setAccountPermissionResolver(new AccountCustomDataPermissionResolver());
//        setApplicationResolver(new DefaultApplicationResolver());
//    }
//
//
//
//    @Override
//    protected void onInit() {
//        super.onInit();
//        assertState();
//        if (application == null) {
//            this.application = ensureApplicationReference();
//        }
//    }
//
//
//
//
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
//
//        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//
//        AuthenticationRequest request = createAuthenticationRequest(token);
//
//        Application application = ensureApplicationReference();
//
//        Account account;
//
//        try {
//            account = application.authenticateAccount(request).getAccount();
//        } catch (ResourceException e) {
//            //todo error code translation to throw more detailed exceptions
//            String msg = StringUtils.clean(e.getMessage());
//            if (msg == null) {
//                msg = StringUtils.clean(e.getDeveloperMessage());
//            }
//            if (msg == null) {
//                msg = "Invalid login or password.";
//            }
//            throw new AuthenticationException(msg, e);
//        }
//
//        PrincipalCollection principals;
//
//        try {
//            principals = createPrincipals(account);
//        } catch (Exception e) {
//            throw new AuthenticationException("Unable to obtain authenticated account properties.", e);
//        }
//
//        return new SimpleAuthenticationInfo(principals, null);
//    }
//
//    protected AuthenticationRequest createAuthenticationRequest(UsernamePasswordToken token) {
//        String username = token.getUsername();
//        char[] password = token.getPassword();
//        String host = token.getHost();
//
//        DefaultUsernamePasswordRequest usernamePasswordRequest = new DefaultUsernamePasswordRequest(username, password);
//
//        if (host != null) {
//            usernamePasswordRequest.setHost(host);
//        }
//
//        return usernamePasswordRequest;
//    }
//
//    protected PrincipalCollection createPrincipals(Account account) {
//
//        LinkedHashMap<String, String> props = new LinkedHashMap<String, String>();
//
//        props.put("href", account.getHref());
//        nullSafePut(props, "username", account.getUsername());
//        nullSafePut(props, "email", account.getEmail());
//        nullSafePut(props, "givenName", account.getGivenName());
//        nullSafePut(props, "middleName", account.getMiddleName());
//        nullSafePut(props, "surname", account.getSurname());
//
//        Collection<Object> principals = new ArrayList<Object>(2);
//        principals.add(account.getHref());
//        principals.add(props);
//
//        return new SimplePrincipalCollection(principals, getName());
//    }
//
//    private void nullSafePut(Map<String, String> props, String propName, String value) {
//        String cleanValue = StringUtils.clean(value);
//        if (cleanValue != null) {
//            props.put(propName, cleanValue);
//        }
//    }
//
//    protected String getAccountHref(PrincipalCollection principals) {
//        Collection c = principals.fromRealm(getName());
//        //Based on the createPrincipals implementation above, the first one is the Account href:
//        return (String) c.iterator().next();
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//
//        assertState();
//
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//
//        String href = getAccountHref(principals);
//
//        //TODO resource expansion (account + groups in one request instead of two):
//        Account account = getClient().getDataStore().getResource(href, Account.class);
//
//        GroupList groups = account.getGroups();
//
//        for (Group group : groups) {
//            Set<String> groupRoles = resolveRoles(group);
//            for (String roleName : groupRoles) {
//                info.addRole(roleName);
//            }
//
//            Set<Permission> permissions = resolvePermissions(group);
//            for (Permission permission : permissions) {
//                info.addObjectPermission(permission);
//            }
//        }
//
//        //since 0.3:
//        Set<String> accountRoles = resolveRoles(account);
//        for (String roleName : accountRoles) {
//            info.addRole(roleName);
//        }
//
//        //since 0.3:
//        Set<Permission> accountPermissions = resolvePermissions(account);
//        for (Permission permission : accountPermissions) {
//            info.addObjectPermission(permission);
//        }
//
//        if (CollectionUtils.isEmpty(info.getRoles()) &&
//                CollectionUtils.isEmpty(info.getObjectPermissions()) &&
//                CollectionUtils.isEmpty(info.getStringPermissions())) {
//            //no authorization data associated with the Account
//            return null;
//        }
//
//        return info;
//    }
//
//}
