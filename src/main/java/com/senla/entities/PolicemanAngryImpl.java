package com.senla.entities;

public class PolicemanAngryImpl implements Policeman {
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println(PolicemanAngryImpl.class.getSimpleName() + ": Всех убью! Вон пошли!");
    }
}