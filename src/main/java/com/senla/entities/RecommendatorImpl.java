package com.senla.entities;

import com.senla.annotations.InjectProperty;

public class RecommendatorImpl implements Recommendator {

    @InjectProperty("kvas") // если оставить пустым то будет искать в файле "alcohol"
    private String alcohol;
    @Override
    public void recommend() {
        System.out.println(RecommendatorImpl.class.getSimpleName() + ": to protect from drink " + alcohol);
    }
}