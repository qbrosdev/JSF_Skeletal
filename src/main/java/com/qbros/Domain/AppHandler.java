package com.qbros.Domain;

import com.qbros.Domain.Shiro.UserManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@SessionScoped
@Named
public class AppHandler extends ThemeChanger implements Serializable {

    @Inject
    UserManager userManager;

    private String panelPath;
    private String pageId = "defaultPanel";
    static String PAGE_NAME_EXT_FACELET = ".xhtml";
    static String LOGIN_URL = "login.xhtml";
    static String USER_PANELS_DIR = "/app/user/panels/";
    static String ADMIN_PANELS_DIR = "/app/admin/panels/";
    static String BASE_PAGE_USER = "app/user/pages/rootPage.xhtml";
    static String BASE_PAGE_ADMIN = "app/admin/pages/rootPage.xhtml";


    @PostConstruct
    private void init() {

        initThemChanger("admin", ThemType.AFTERNOON, "admin", "admin", "admin");
    }

    public void showMessageInfo(String summary, String detail) {

        // growl global message: https://stackoverflow.com/a/15599070/3593084
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void showMessageInfo(String summary) {

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void redirectToPage(String url) {
        try {
            getFacesExternalContext().redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goHome() {
        redirectToPage(LOGIN_URL);
    }

    public void redirectToHome() {

        //NOTE: do not use slash in front of url redirect path
        if (userManager.hasUserThisRole(UserManager.Role.ADMIN)) {
            redirectToPage(BASE_PAGE_ADMIN);
        } else {
            redirectToPage(BASE_PAGE_USER);
        }
    }

    private ExternalContext getFacesExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    //-------------------------Getter/Setters---------------------------------------------------------------------------

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPanelPath() {

        // PATH format: /app/panels/defaultPanel.xhtml
        if(userManager.hasUserThisRole(UserManager.Role.ADMIN)){
            return ADMIN_PANELS_DIR + getPageId() + PAGE_NAME_EXT_FACELET;
        } else {
            return USER_PANELS_DIR + getPageId() + PAGE_NAME_EXT_FACELET;
        }
    }

    public void doNothing(){
        System.out.println("nkjkjkj");
    }

}
