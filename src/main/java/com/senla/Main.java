package com.senla;

import com.senla.configurations.Application;
import com.senla.configurations.ApplicationContext;
import com.senla.entities.CoronaDisinfector;
import com.senla.entities.Policeman;
import com.senla.entities.PolicemanAngryImpl;
import com.senla.entities.Room;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        CoronaDisinfector disinfector = ObjectFactory.getInstance().createObject(CoronaDisinfector.class);
        ApplicationContext context = Application.run("com.senla", new HashMap<>(Map.of(Policeman.class, PolicemanAngryImpl.class)));
        CoronaDisinfector disinfector = context.getObject(CoronaDisinfector.class);

        disinfector.start(new Room());
    }
}
