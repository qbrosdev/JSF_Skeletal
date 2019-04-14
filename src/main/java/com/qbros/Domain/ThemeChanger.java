package com.qbros.Domain;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by V.Ghasemi
 * on 11/24/2018.
 */
public abstract class ThemeChanger {

    protected String appThemeName = "afternoon";
    private ThemType currentThemType;
    protected String morningThemeName;
    protected String afternoonThemeName;
    protected String nightThemeName;

    public void initThemChanger (String appThemeName, ThemType currentThemType, String morningThemeName,
                        String afternoonThemeName, String nightThemeName) {
        this.appThemeName = appThemeName;
        this.currentThemType = currentThemType;
        this.morningThemeName = morningThemeName;
        this.afternoonThemeName = afternoonThemeName;
        this.nightThemeName = nightThemeName;
    }

    protected enum ThemType {
        MORNING, AFTERNOON, NIGHT
    }

    public void synchTheme(final String date) {
        ThemType themType = getThemType(date);
        if(!themType.equals(currentThemType)){
            switchThem(themType);
        }
    }

    private ThemType getThemType(String date){
        System.out.println("date is   ");
        return ThemType.AFTERNOON;
    }

    private void switchThem(ThemType themType){
        currentThemType = themType;
        applyThem(currentThemType);
    }

    private void applyThem(ThemType themType){
        System.out.println("applying new Theme "+ themType);
            doApply(themType);
    }

    private void doApply(ThemType themType) {

        switch (themType) {
            case MORNING:
                setAppThemeName(getMorningThemeName());
                break;
            case AFTERNOON:
                setAppThemeName(getAfternoonThemeName());
                break;
            case NIGHT:
                setAppThemeName(getNightThemeName());
                break;
        }

        reloadCurrentPage();
    }

    private void reloadCurrentPage() {


        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        FacesContext context = FacesContext.getCurrentInstance();
//        String currentPage = context.getViewRoot().getViewId().replaceAll(".xhtml", ".htm");
//
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect(currentPage);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    //-----------------------------------Getter/Setter---------------------------------------------------------------------

    public ThemType getCurrentThemType() {
        return currentThemType;
    }

    public void setCurrentThemType(ThemType currentThemType) {
        this.currentThemType = currentThemType;
    }

    public String getMorningThemeName() {
        return morningThemeName;
    }

    public void setMorningThemeName(String morningThemeName) {
        this.morningThemeName = morningThemeName;
    }

    public String getAfternoonThemeName() {
        return afternoonThemeName;
    }

    public void setAfternoonThemeName(String afternoonThemeName) {
        this.afternoonThemeName = afternoonThemeName;
    }

    public String getNightThemeName() {
        return nightThemeName;
    }

    public void setNightThemeName(String nightThemeName) {
        this.nightThemeName = nightThemeName;
    }

    public String getAppThemeName() {
        return appThemeName;
    }

    public void setAppThemeName(String appThemeName) {
        this.appThemeName = appThemeName;
    }
}
