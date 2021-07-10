package com.senla.configurations;

import org.reflections.Reflections;

public interface Config {
    // получает интерфейс - возвращает его имплиментацию
    <T> Class<? extends T> getImpClass(Class<T> ifc);

    Reflections getScanner();
}
