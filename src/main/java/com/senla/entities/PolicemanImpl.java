package com.senla.entities;

import com.senla.annotations.Autowired;

import javax.annotation.PostConstruct;

public class PolicemanImpl implements Policeman {
    @Autowired
    Recommendator recommendator;

    @PostConstruct // запустит метод после создания объекта
    public void init() {
        System.out.println(recommendator.getClass());
    }

    @Override
    public void makePeopleLeaveRoom() {
        System.out.println(PolicemanImpl.class.getSimpleName() + ": пиф-паф!");
    }
}
