package com.senla.entities;

import com.senla.ObjectFactory;

public class AnnouncerImpl implements Announcer {

    private Recommendator recommmendator = ObjectFactory.getInstance().createObject(Recommendator.class);
    @Override
    public void announce(String message) {
        System.out.println(AnnouncerImpl.class.getSimpleName() + ": " + message);
        recommmendator.recommend();
    }
}
