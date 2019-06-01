package site.aboy.album.utils;

import java.util.HashMap;
import java.util.Map;

public class Store {

    private static Map<String, Object> store = new HashMap<>();

    public static void save(String key, Object object){
        store.put(key, object);
    }

    public static Object get(String key){
        return store.get(key);
    }
}
