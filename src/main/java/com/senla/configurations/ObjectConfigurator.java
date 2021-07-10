package com.senla.configurations;
/**
 * Интерфейс принимает объект и настраивает его
 */
public interface ObjectConfigurator {
    void configure(Object t, ApplicationContext context);
}
