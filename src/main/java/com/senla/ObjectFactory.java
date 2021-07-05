package com.senla;

import com.senla.configurations.Config;
import com.senla.configurations.ConfigJava;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    // объект класса Config
    private Config config = new ConfigJava("com.senla");

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    // приватный конструктор
    private ObjectFactory() {
    }

    @SneakyThrows
    // метод создает объект класса полученного из type
    public <T> T createObject(Class<T> type) {
        // проверяем есть ли у type имплиментации
        Class<? extends T> implClass = type;
        // если type является интерфейсом
        if (type.isInterface()){
            // тогда implClass = объекту который будети принимать type проверять является ли он интерфесом и возвращать его имплиментацию
            implClass = config.getImpClass(type);
        }

        /* если конструктор private
        Constructor<T> constructor = (Constructor<T>) implClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
        */

        // конвенция: конструктор должен быть дефолтным
        return implClass.getDeclaredConstructor().newInstance();
    }
}
