package net.minecraftforge.eventbus.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBusImpl implements IEventBus {

    private static class Entry {
        final Class<? extends Event> eventType;
        final EventListener<?> listener;
        final EventPriority priority;
        Entry(Class<? extends Event> t, EventListener<?> l, EventPriority p) { eventType = t; listener = l; priority = p; }
    }

    private static class GenericEntry {
        final Class<?> objectType;
        final EventListener<?> listener;
        GenericEntry(Class<?> t, EventListener<?> l) { objectType = t; listener = l; }
    }

    private static class Adapter implements EventListener<Event> {
        final Method method;
        final Object target;
        Adapter(Method m, Object t) {
            m.setAccessible(true);
            method = m;
            target = t;
        }
        public void invoke(Event event) {
            try { method.invoke(target, event); } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ignored) {}
        }
    }

    private final List<Entry> entries = new CopyOnWriteArrayList<>();
    private final List<GenericEntry> generics = new CopyOnWriteArrayList<>();
    private final Map<Class<?>, List<Entry>> cache = new ConcurrentHashMap<>();

    private static List<Class<? extends Event>> hierarchy(Class<? extends Event> type) {
        List<Class<? extends Event>> out = new ArrayList<>();
        for (Class<?> c = type; c != null && Event.class.isAssignableFrom(c); c = c.getSuperclass()) out.add((Class<? extends Event>) c);
        return out;
    }

    private static Class<? extends Event> inferType(EventListener<?> listener) {
        try {
            for (Method m : listener.getClass().getMethods()) {
                if (m.getName().equals("invoke") && m.getParameterCount() == 1) {
                    Class<?> t = m.getParameterTypes()[0];
                    if (Event.class.isAssignableFrom(t)) return (Class<? extends Event>) t;
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    private void add(Class<? extends Event> type, EventListener<?> listener, EventPriority priority) {
        entries.add(new Entry(type, listener, priority));
        entries.sort(Comparator.comparing(e -> e.priority));
        cache.clear();
    }

    @Override
    public <T extends Event> void addListener(EventListener<T> listener) {
        Class<? extends Event> inferred = inferType(listener);
        if (inferred == null) inferred = Event.class;
        add(inferred, listener, EventPriority.NORMAL);
    }

    @Override
    public <T extends Event> void addListener(EventListener<T> listener, EventPriority priority) {
        Class<? extends Event> inferred = inferType(listener);
        if (inferred == null) inferred = Event.class;
        add(inferred, listener, priority);
    }

    @Override
    public <T extends Event> void addListener(Class<? extends Event> eventType, EventListener<T> listener) {
        add(eventType, listener, EventPriority.NORMAL);
    }

    @Override
    public <T extends Event> void addGenericListener(Class<?> objectType, EventListener<T> listener) {
        generics.add(new GenericEntry(objectType, listener));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean post(Event event) {
        List<Entry> matched = cache.get(event.getClass());
        if (matched == null) {
            List<Class<? extends Event>> hier = hierarchy(event.getClass());
            matched = new ArrayList<>();
            for (Entry e : entries) if (hier.contains(e.eventType)) matched.add(e);
            cache.put(event.getClass(), List.copyOf(matched));
        }
        for (Entry e : matched) {
            if (event.isCanceled()) break;
            try { ((EventListener<Event>) e.listener).invoke(event); } catch (RuntimeException ignored) {}
        }
        Object gt = genericTypeOf(event);
        if (gt != null) {
            for (GenericEntry g : generics) {
                if (g.objectType.isInstance(gt)) {
                    try { ((EventListener<Event>) g.listener).invoke(event); } catch (RuntimeException ignored) {}
                }
            }
        }
        return event.isCanceled();
    }

    @Override
    public void register(Object listener) {
        for (Method m : listener.getClass().getDeclaredMethods()) {
            if (!m.isAnnotationPresent(SubscribeEvent.class) || m.getParameterCount() != 1) continue;
            Class<?> t = m.getParameterTypes()[0];
            if (Event.class.isAssignableFrom(t)) {
                add((Class<? extends Event>) t, new Adapter(m, listener), m.getAnnotation(SubscribeEvent.class).priority());
            }
        }
    }

    @Override
    public void register(Class<?> listenerClass) {
        for (Method m : listenerClass.getDeclaredMethods()) {
            if (!Modifier.isStatic(m.getModifiers()) || !m.isAnnotationPresent(SubscribeEvent.class) || m.getParameterCount() != 1) continue;
            Class<?> t = m.getParameterTypes()[0];
            if (Event.class.isAssignableFrom(t)) {
                add((Class<? extends Event>) t, new Adapter(m, null), m.getAnnotation(SubscribeEvent.class).priority());
            }
        }
    }

    @Override
    public void unregister(Object listener) {
        entries.removeIf(e -> e.listener instanceof Adapter a && a.target == listener);
        cache.clear();
    }

    @Override
    public void unregister(Class<?> listenerClass) {
        entries.removeIf(e -> e.listener instanceof Adapter a && (a.target == null ? a.method.getDeclaringClass() : a.target.getClass()) == listenerClass);
        cache.clear();
    }

    @Override
    public boolean hasListeners(Class<? extends Event> eventType) {
        return entries.stream().anyMatch(e -> e.eventType == eventType);
    }

    private static Object genericTypeOf(Event event) {
        try {
            return event.getClass().getMethod("getGenericType").invoke(event);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            return null;
        }
    }
}