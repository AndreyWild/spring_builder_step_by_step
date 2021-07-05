package com.senla.entities;

public class AnnouncerImpl implements Announcer {
    @Override
    public void announce(String message) {
        System.out.println(AnnouncerImpl.class.getName() + ": " + message);
    }
}
