package com.qbros.Domain.LoginUC;

import com.qbros.Domain.AppHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletRequest;
import java.io.IOException;
import java.net.URL;

/*
The servlet container is attached to a web server that listens for HTTP requests on a certain port number
(port 8080 is usually used during development and port 80 in production).
When a client (user with a web browser) sends an HTTP request, the servlet container creates new  HttpServletRequest
and HttpServletResponse objects and passes them through any defined Filter chain and, eventually, the Servlet instance.

HttpSession
When a client visits the webapp for the first time and/or the HttpSession is obtained for the first time via request.getSession(),
the servlet container creates a new HttpSession object, generates a long and unique ID (which you can get by session.getId()),
and store it in the server's memory. The servlet container also sets a Cookie in the Set-Cookie header of the HTTP
response with JSESSIONID as its name and the unique session ID as its value.

As per the HTTP cookie specification (a contract a decent web browser and web server have to adhere to), the client
(the web browser) is required to send this cookie back in subsequent requests in the Cookie header for as long
as the cookie is valid (i.e. the unique ID must refer to an unexpired session and the domain and path are correct).
Using your browser's built-in HTTP traffic monitor, you can verify that the cookie is valid
(press F12 in Chrome / Firefox 23+ / IE9+, and check the Net/Network tab).
The servlet container will check the Cookie header of every incoming HTTP request for the presence of the cookie
with the name JSESSIONID and use its value (the session ID) to get the associated HttpSession from server's memory.

The HttpSession stays alive until it has not been used for more than the timeout value specified in <session-timeout>,
a setting in web.xml. The timeout value defaults to 30 minutes. So, when the client doesn't visit the web app for longer
than the time specified, the servlet container trashes the session. Every subsequent request, even with the cookie
specified, will not have access to the same session anymore; the servlet container will create a new session.

On the client side, the session cookie stays alive for as long as the browser instance is running. So, if the client
closes the browser instance (all tabs/windows), then the session is trashed on the client's side. In a new browser
instance, the cookie associated with the session wouldn't exist, so it would no longer be sent. This causes an
entirely new HTTPSession to be created, with an entirely new session cookie begin used.

Link:https://stackoverflow.com/a/3106909/3593084

So the application server and the browser are in charge of maintaining the session and then the
beans which are annotated with @sessionScoped will maintain their state.
*/

/*note that SessionScoped should cdi to understand cdi injections (CDI is a bridge between ejb and jsf)
be https://stackoverflow.com/questions/15057564/why-are-there-different-bean-management-annotations
*/

/*ManagedBean is deprecated use Named (unless your container is full EE container and does not
understand CDI) https://stackoverflow.com/questions/4347374/backing-beans-managedbean-or-cdi-beans-named
*/

@Named(value = "loginService")
@SessionScoped
public class LoginService extends Login {

    private static final long serialVersionUID = 1L;

    @Inject
    AppHandler appHandler;

    @Override
    void logIn() {
        loginRedirect();
    }

    @Override
    void logOut() {
        logoutRedirect();
    }

    //--------------------------------------Private Methods-------------------------------------------------------------

    public void logoutRedirect() {
        SecurityUtils.getSubject().logout();
        getFacesExternalContext().invalidateSession();
        appHandler.goHome();
    }

    public void loginRedirect() {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(getUser(), getPass(), getRememberMe());
        try {
            subject.login(token);
            redirect();
        } catch (UnknownAccountException ex) {
            appHandler.showMessageInfo("Unknown account");
            ex.printStackTrace();
        } catch (IncorrectCredentialsException ex) {
            appHandler.showMessageInfo("Wrong password");
            ex.printStackTrace();
        } catch (LockedAccountException ex) {
            appHandler.showMessageInfo("Locked account");
            ex.printStackTrace();
        } catch (IOException ex) {
            appHandler.showMessageInfo("IO exception Ex");
            ex.printStackTrace();
        } catch (Exception ex) {
            appHandler.showMessageInfo("general exception Ex");
            ex.printStackTrace();
        } finally {
            token.clear();
        }
    }

    private void redirect() throws IOException {
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest((ServletRequest) getFacesExternalContext().getRequest());
        if (savedRequest != null) {
            appHandler.redirectToPage(savedRequest.getRequestUrl());
        } else {
            appHandler.redirectToHome();
        }
    }

    private ExternalContext getFacesExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

}
