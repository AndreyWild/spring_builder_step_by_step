package com.senla;

import com.senla.entities.CoronaDisinfector;
import com.senla.entities.Room;

public class Main {
    public static void main(String[] args) {
        CoronaDisinfector disinfector = new CoronaDisinfector();
        disinfector.start(new Room());
    }
}
