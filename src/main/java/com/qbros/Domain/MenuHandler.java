package com.qbros.Domain;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by V.Ghasemi
 * on 12/4/2018.
 */
@SessionScoped
@Named
public class MenuHandler implements Serializable {

    @Inject
    AppHandler appHandler;

    public void save() {
        appHandler.showMessageInfo("Success", "Data saved");
        appHandler.setPageId("savePanel");
    }

    public void update() {
        appHandler.showMessageInfo("Success", "Data updated");
        appHandler.setPageId("updatePanel");
    }

    public void delete() {
        appHandler.showMessageInfo("Success", "Data deleted");
        appHandler.setPageId("deletePanel");
    }
}
