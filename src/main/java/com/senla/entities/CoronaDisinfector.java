package com.senla.entities;

import com.senla.ObjectFactory;
import com.senla.annotations.Autowired;

public class CoronaDisinfector {

                                  //создаем синглтон фабрику через конструктор объект ObjectFactory создай объект из Announcer.class
    @Autowired
    private Announcer announcer;
    @Autowired
    private Policeman policeman;


    public void start(Room room) {

        announcer.announce("Начинаем дезинфекцию!");
        policeman.makePeopleLeaveRoom();
        disinfect(room);
        announcer.announce("Можете возвращаться!");

    }

    private void disinfect(Room room) {
        System.out.println(CoronaDisinfector.class.getSimpleName() + ": Изгоняю корону! Изыди! - Вирус Уничтожен!");
    }
}
