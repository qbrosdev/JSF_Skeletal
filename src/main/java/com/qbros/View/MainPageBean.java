package com.qbros.View;


import com.qbros.Domain.CarService;
import com.qbros.Model.Car;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named(value="dtBasicView")
@ViewScoped
public class MainPageBean {

//    https://service.com/questions/12215979/inject-vs-managedproperty
    @Inject
    private CarService service;

    private List<Car> cars;

    @PostConstruct
    public void init() {
        cars = service.createCars(10);
        service.testMethod();
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setService(CarService service) {
        this.service = service;
    }
}
