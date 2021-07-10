package com.senla.entities;

import com.senla.ObjectFactory;
import com.senla.annotations.Autowired;

public class AnnouncerImpl implements Announcer {

    @Autowired
    private Recommendator recommmendator;
    @Override
    public void announce(String message) {
        System.out.println(AnnouncerImpl.class.getSimpleName() + ": " + message);
        recommmendator.recommend();
    }
}
