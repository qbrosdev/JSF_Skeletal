package com.qbros.components;

import com.qbros.Domain.ThemeChanger;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * info composite component input
 * https://stackoverflow.com/tags/composite-component/info
 * http://balusc.omnifaces.org/2013/01/composite-component-with-multiple-input.html
 *
 * Faces component backing bean:
 * https://stackoverflow.com/a/15926721/3593084
 */

@FacesComponent("dynamicTheme")
public class DynamicThemeTemplateHolderComp extends UINamingContainer {

    private String templateName;
    private int intervalTimeMinutes;
    private ThemeChanger themeChanger;

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        setTemplateName(getAttributeValue("templateName", "template.xhtml"));
        setThemeChanger(getAttributeValue("themeChangerBean",null));
        //setCurrentThemType(ThemType.MORNING);
        themeChanger.setMorningThemeName(getAttributeValue("themeMorning", "cssMorning"));
        themeChanger.setMorningThemeName(getAttributeValue("themeAfternoon", "cssAfternoon"));
        themeChanger.setMorningThemeName(getAttributeValue("themeNight", "cssNight"));
       // setCurrentThemeBasedOnCurrentTime();
        getStateHelper().put("renderUserId", themeChanger);
        getStateHelper().put("tt", "abc");
        super.encodeBegin(context);
    }

    private enum ThemType {
        MORNING, AFTERNOON, NIGHT
    }

    public void synchTheme(final String date) {
        ThemType themType = getThemType(date);
        //todo check theme cahnger value here!
//        if(!themType.equals(currentThemType)){
//            switchThem(themType);
//        }
    }

    private void setCurrentThemeBasedOnCurrentTime(){
      //  setCurrentThemeName(getMorningThemeName());
    }

    private ThemType getThemType(String date){
        themeChanger = (ThemeChanger)getStateHelper().eval("renderUserId", null);
      String s =   (String)getStateHelper().eval("tt", null);
        System.out.println("date is   " + themeChanger.getAfternoonThemeName());
        return ThemType.AFTERNOON;
    }

    private void switchThem(ThemType themType){
        //currentThemType = themType;
        //applyThem(currentThemType);
    }

    private void applyThem(ThemType themType){
        System.out.println("applying new Theme "+ themType);
        if(themeChanger == null){
            doNothing();
        } else {
            doApply(themType);
        }
    }

    private void doNothing() {
        System.out.println("theme Changer is null");
    }

    private void doApply(ThemType themType) {
//        switch (themType) {
//            case MORNING:
//                themeChanger.setAppThemeName(getMorningThemeName());
//                break;
//            case AFTERNOON:
//                themeChanger.setAppThemeName(getAfternoonThemeName());
//                break;
//            case NIGHT:
//                themeChanger.setAppThemeName(getNightThemeName());
//                break;
//        }

        FacesContext context = FacesContext.getCurrentInstance();
        String currentPage = context.getViewRoot().getViewId().replaceAll(".xhtml", ".htm");

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(currentPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-------------HELPERS----------------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    private <T> T getAttributeValue(String key, T defaultValue) {
        T value = (T) getAttributes().get(key);
        return (value != null) ? value : defaultValue;
    }

    //-------------GETTER/SETTER----------------------------------------------------------------------------------------

    public int getIntervalTimeMinutes() {
        return intervalTimeMinutes;
    }

    public void setIntervalTimeMinutes(int intervalTimeMinutes) {
        this.intervalTimeMinutes = intervalTimeMinutes;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public ThemeChanger getThemeChanger() {
        return themeChanger;
    }

    public void setThemeChanger(ThemeChanger themeChanger) {
        this.themeChanger = themeChanger;
    }
}
