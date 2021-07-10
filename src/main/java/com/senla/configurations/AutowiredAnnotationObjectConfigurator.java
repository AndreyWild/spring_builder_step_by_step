package com.senla.configurations;

import com.senla.ObjectFactory;
import com.senla.annotations.Autowired;

import java.lang.reflect.Field;

public class AutowiredAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    public void configure(Object t, ApplicationContext context) {
        // получаем все филды объекта и итерируемся по ним
        for (Field field : t.getClass().getDeclaredFields()) {
            // если поле имеет аннотацию Autowired.class
            if (field.isAnnotationPresent(Autowired.class)) {
                // открываем доступ к полю
                field.setAccessible(true);
                // фабрика создай объект из класса полученного из поля
//                Object object = ObjectFactory.getInstance().createObject(field.getType());
                Object object = context.getObject(field.getType());
                // присвоить полю объекта t значение  object
                try {
                    field.set(t, object);
                } catch (IllegalAccessException e) {
                    System.err.println("Доспут к полю закрыт!");
                    e.printStackTrace();
                }

            }
        }
    }
}
