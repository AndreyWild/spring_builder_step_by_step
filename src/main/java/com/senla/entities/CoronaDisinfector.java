package com.senla.entities;

import com.senla.ObjectFactory;

public class CoronaDisinfector {

                                  //создаем синглтон фабрику через конструктор объект ObjectFactory создай объект из Announcer.class
    private Announcer announcer = ObjectFactory.getInstance().createObject(Announcer.class);
    private Policeman policeman = ObjectFactory.getInstance().createObject(Policeman.class);


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
