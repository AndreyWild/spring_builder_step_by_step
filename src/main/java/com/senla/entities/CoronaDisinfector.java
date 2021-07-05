package com.senla.entities;

public class CoronaDisinfector {


     private Announcer announcer = new AnnouncerImpl();
    private Policeman policeman = new PolicemanImpl();


    public void start(Room room){

        announcer.announce("Начинаем дезинфекцию!");
        policeman.makePeopleLeaveRoom();
        disinfect(room);
        announcer.announce("Можете возвращаться!");

    }

    private void disinfect(Room room){
        System.out.println(CoronaDisinfector.class.getName() + ": Изгоняю корону! Изыди! - Вирус Уничтожен!");
    }
}
