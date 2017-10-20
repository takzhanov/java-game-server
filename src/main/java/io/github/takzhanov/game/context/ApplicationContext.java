package io.github.takzhanov.game.context;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private static final ApplicationContext INSTANCE = new ApplicationContext();
    private Map<Class<?>, Object> ctx = new HashMap<>();

    private ApplicationContext() {
    }

    public static void register(Class<?> clazz, Object service) {
        INSTANCE.ctx.putIfAbsent(clazz, service);
    }

    public static void unregister(Class<?> clazz) {
        INSTANCE.ctx.remove(clazz);
    }

    public static <T> T get(Class<T> clazz) {
        return (T) INSTANCE.ctx.get(clazz);
    }
}
