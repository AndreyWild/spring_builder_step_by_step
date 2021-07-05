package com.senla.entities;

public class PolicemanImpl implements Policeman {
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println(PolicemanImpl.class.getName() + ": пиф-паф!");
    }
}
