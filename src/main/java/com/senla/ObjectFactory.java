package com.senla;

import com.senla.configurations.Config;
import com.senla.configurations.JavaConfig;
import com.senla.entities.Policeman;
import com.senla.entities.PolicemanAngryImpl;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    // объект класса Config
    private Config config;

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    // приватный конструктор
    private ObjectFactory() {
        // инициализируем config добавляя в него путь для сканера и map в нужным (интерфейс, его имплиментация)
        this.config = new JavaConfig("com.senla", new HashMap<>(Map.of(Policeman.class, PolicemanAngryImpl.class)));
    }

    @SneakyThrows
    // метод создает объект класса полученного из type
    public <T> T createObject(Class<T> type) {
        // проверяем есть ли у type имплиментации
        Class<? extends T> implClass = type;
        // если type является интерфейсом
        if (type.isInterface()) {
            // тогда implClass = объекту который будети принимать type проверять является ли он интерфесом и возвращать его имплиментацию
            implClass = config.getImpClass(type);
        }


        // todo
        /* если конструктор private
        Constructor<T> constructor = (Constructor<T>) implClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
        */

        // конвенция: конструктор должен быть дефолтным
        return implClass.getDeclaredConstructor().newInstance();
    }
}
