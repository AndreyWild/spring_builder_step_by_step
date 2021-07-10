package com.senla;

import com.senla.configurations.Config;
import com.senla.configurations.JavaConfig;
import com.senla.configurations.ObjectConfigurator;
import com.senla.entities.Policeman;
import com.senla.entities.PolicemanAngryImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFactory {
    // инициализируем объект класса ObjectFactory
    private static ObjectFactory ourInstance = new ObjectFactory();
    // список возможных реализаций интерфейса ObjectConfigurator
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    // объект класса Config
    private Config config;

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    // приватный конструктор
    private ObjectFactory() {
        // инициализируем config добавляя в него путь для сканера и map в нужным (интерфейс, его имплиментация)
        config = new JavaConfig("com.senla", new HashMap<>(Map.of(Policeman.class, PolicemanAngryImpl.class)));
        // дай сканер, дай все реализации(ObjectConfigurator.class), проитерируемся по ним
        for (Class<? extends ObjectConfigurator> aClass : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            // добавляем все его имплиментации в configurators;
            try {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.out.println("Something with constructor access!");
                e.printStackTrace();
            }
        }
    }


    // метод создает объект класса полученного из type
    public <T> T createObject(Class<T> type) {
        // проверяем есть ли у type имплиментации
        Class<? extends T> implClass = type;
        // если type является интерфейсом
        if (type.isInterface()) {
            // тогда implClass = объекту который будети принимать type проверять является ли он интерфесом и возвращать его имплиментацию
            implClass = config.getImpClass(type);
        }
        /* если конструктор private
        Constructor<T> constructor = (Constructor<T>) implClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
        */

        // конвенция: конструктор должен быть дефолтным
        T t = null;
        try {
            t = implClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println("Что-то пошло не так с созданием объекта из класса!");
            e.printStackTrace();
        }

        // проиниицализированный объект
        T initialized = t;

        // насраиваем объект всеми конфигураторами из configurators
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(initialized));

        return initialized;
    }
}
