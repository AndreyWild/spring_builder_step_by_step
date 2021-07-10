package com.senla.configurations;

import com.senla.annotations.InjectProperty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    Map<String, String> propertiesMap; // Map содержит значения из файла application.properties

    public InjectPropertyAnnotationObjectConfigurator() {
        // ClassLoader дай ресурсы из "application.properties" дай к нему путь
        String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();

        // считываем из файла в String
        Stream<String> lines = null;
        try {
            lines = new BufferedReader(new FileReader(path)).lines();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
            e.printStackTrace();
        }

        // создаем map из lines - сплиттер "=" ключь 0 элемент значет 1 й элемент
        propertiesMap = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    /** Метод проверяет все поля объекта в цикле на наличие аннотации и конфигурирует эти поля в зависимости от значения в аннотации */
    @Override
    public void configure(Object t) {
        // получаем Class из объекта
        Class<?> implClass = t.getClass();

        // итерируем все поля класса implClass
        for (Field field : implClass.getDeclaredFields()) {
            // получаем аннотацию поля
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);

            // если аннотация не равно null
            if (annotation != null) {
                String value;
                // если annotation.value нету значеня
                if (annotation.value().isEmpty()) {
                    // идем в propertiesMap и вынимаем его значение по ключу(имя поля)
                    value = propertiesMap.get(field.getName());
                } else {
                    // идем в propertiesMap и вынимаем его значение по ключу из значения value в аннотации
                    value = propertiesMap.get(annotation.value());
                }
                // открываем доступ к private полю
                field.setAccessible(true);
                // полю объекту t присваиваем значение из String value;
                try {
                    field.set(t, value);
                } catch (IllegalAccessException e) {
                    System.err.println("Access to the field is closed!");
                    e.printStackTrace();
                }
            }
        }
    }
}
