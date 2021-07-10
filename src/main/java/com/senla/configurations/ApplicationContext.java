package com.senla.configurations;

import com.senla.ObjectFactory;
import com.senla.annotations.Singleton;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс работает с объектами, он получает класс е если класс помечен @Singleton, то возвращает уже созданный объект
 */

public class ApplicationContext {
    // объект фабрики
    @Setter
    private ObjectFactory factory;
    // содержит все (классы, их объекты) которые помечены аннотацией Singleton
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) {
        // если кэш уже содержит такой класс
        if (cache.containsKey(type)) {
            // то верни его объект
            return (T) cache.get(type);
        }

        // ---проверяем есть ли у type имплиментации---
        Class<? extends T> implClass = type;

        // если type является интерфейсом
        if (type.isInterface()) {
            // тогда implClass = объекту который будети принимать type проверять является ли он интерфесом и возвращать его имплиментацию
            implClass = config.getImpClass(type);
        }

        // создать объект указанного в параметре класса
        T t = factory.createObject(implClass);

        // Если объект содержит аннотацию @Singleton
        if(implClass.isAnnotationPresent(Singleton.class)){
            // тогда добавляем этот класс и его объект в cache
            cache.put(type, t);
        }
        return t;
    }
}
