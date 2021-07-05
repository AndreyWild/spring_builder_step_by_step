package com.senla.configurations;

public interface Config {
    // получает интерфейс - возвращает его имплиментацию
    <T> Class<? extends T> getImpClass(Class<T> ifc);
}
