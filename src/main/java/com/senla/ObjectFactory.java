package com.senla;

import com.senla.annotations.InjectProperty;
import com.senla.configurations.Config;
import com.senla.configurations.JavaConfig;
import com.senla.entities.Policeman;
import com.senla.entities.PolicemanAngryImpl;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

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

        T t = implClass.getDeclaredConstructor().newInstance();

        // итерируем все поля класса implClass
        for (Field field : implClass.getDeclaredFields()) {
            // получаем аннотацию поля
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);

            // ClassLoader дай ресурсы из "application.properties" дай к нему путь
            String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();

            // считываем из файла в String
            Stream<String> lines = new BufferedReader(new FileReader(path)).lines();

            // создаем map из lines - сплиттер "=" ключь 0 элемент значет 1 й элемент
            Map<String, String> propertiesMap = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));

            String value;

            // если аннотация не равно null
            if (annotation != null) {
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
                field.set(t, value);
            }
        }




        /* если конструктор private
        Constructor<T> constructor = (Constructor<T>) implClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
        */

        // конвенция: конструктор должен быть дефолтным
        return t;
    }
}
