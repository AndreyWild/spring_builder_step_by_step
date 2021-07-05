package com.senla.configurations;

import org.reflections.Reflections;

import java.util.Set;

public class ConfigJava implements Config {
    // расширяет возможности текущего Reflection в java
    private Reflections scanner;

    // в конструкторе добавляем String с путем пакетов для сканирования например: "com.senla"
    public ConfigJava(String packageToScan) {
        // инициализируем Reflections scanner и в рпараметр передаем путь к папке которую надо просканировать
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    // получает интерфейс - возвращает его имплиментацию
    public <T> Class<? extends T> getImpClass(Class<T> ifc) {
        // scanner дай все имплиментации интерфейса ifc и закинь их в Set classes
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);

        // если у интерфейса (ifc) нету реализаций или больше одной
        if (classes.size() != 1) {

            // выкидываем исключение
            throw new RuntimeException("У интерфейса 0 реализаций или больше одной");
        }
        // если прошел проверку и имеет одну имплиментацию, то возвращаем ее
        return classes.iterator().next();
    }
}
