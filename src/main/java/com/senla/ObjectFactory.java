package com.senla;

import com.senla.configurations.ApplicationContext;
import com.senla.configurations.ObjectConfigurator;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {

    // список возможных реализаций интерфейса ObjectConfigurator
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    private final ApplicationContext context;

    // приватный конструктор
    public ObjectFactory(ApplicationContext context) {
        this.context = context;

        // инициализируем config добавляя в него путь для сканера и map в нужным (интерфейс, его имплиментация)
        // config = new JavaConfig("com.senla", new HashMap<>(Map.of(Policeman.class, PolicemanAngryImpl.class)));

        // дай сканер, дай все реализации(ObjectConfigurator.class), проитерируемся по ним
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
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
    public <T> T createObject(Class<T> implClass) {

        /* если конструктор private
        Constructor<T> constructor = (Constructor<T>) implClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance(); */
        // конвенция: конструктор должен быть дефолтным
        T t = create(implClass);

        // насраиваем объект всеми конфигураторами из configurators
        configure(t);

        // запускаем у объекта метод с пометкой @PostConstruct
        invokeInit(implClass, t);

        return t;
    }

    private <T> void invokeInit(Class<T> implClass, T t) {
        // Берем все методы класса, итерируемся по ним
        for (Method method : implClass.getMethods()) {
            // если метод содержит аннотацию @PostConstruct
            if (method.isAnnotationPresent(PostConstruct.class)) {
                // запускаем этот метод
                try {
                    method.invoke(t);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private <T> void configure(T initialized) {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(initialized, context));
    }

    private <T> T create(Class<T> implClass) {
        T t = null;
        try {
            t = implClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println("Что-то пошло не так с созданием объекта из класса!");
            e.printStackTrace();
        }
        return t;
    }
}
