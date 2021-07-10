package com.senla.configurations;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {
    @Getter // создаем геттер для сканера
    // расширяет возможности текущего Reflection в java
    private Reflections scanner;
    // содержит ключь-значение для интерфейсов у которых больше 1-й имплиментации
    private Map<Class, Class> ifc2ImplClass;

    // в конструкторе добавляем String с путем пакетов для сканирования например: "com.senla"
    // и Map с указанным интерфейсом и его конкретной имплиментацией
    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        // инициализируем Reflections scanner и в параметр передаем путь к папке которую надо просканировать
        this.scanner = new Reflections(packageToScan);
        // инициализируем ifc2ImplClass
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @Override
    // получает интерфейс - возвращает его имплиментацию
    public <T> Class<? extends T> getImpClass(Class<T> ifc) {
        // если ifc есть в ifc2ImplClass, то ничего не делаем, иначе выполянем лямбду
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            // computeIfAbsent() - принимает ключь и возвращаети для него значение, ечли ключа нету - запускает лямбду

            // scanner дай все имплиментации интерфейса ifc и закинь их в Set classes
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);

            // если у интерфейса (ifc) нету реализаций или больше одной
            if (classes.size() != 1) {

                // выкидываем исключение
                throw new RuntimeException("У интерфейса 0 реализаций или больше одной, обновите свой config");
            }
            // если прошел проверку и имеет одну имплиментацию, то возвращаем ее
            return classes.iterator().next();
        });
    }
}
