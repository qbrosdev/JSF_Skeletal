package com.qbros.Domain.NotificationUC;

import com.qbros.Model.TimeEvent;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by V.Ghasemi
 * on 11/14/2018.
 */
@Named
@SessionScoped
public class AppEventHandler implements Serializable {

    @Inject
    Event<TimeEvent> evt;

    public void fireStarted() {
        TimeEvent timeEvent = new TimeEvent();
        timeEvent.setTime("time");
        evt.fire(timeEvent);
    }
}
