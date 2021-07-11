package com.ken.material.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-10
 */
public class EHCacheUtils {

    private static final CacheManager cacheManager = CacheManager.getInstance();
    
    /**
     * 设置缓存对象
     * @param name
     * @param key
     * @param object
     */
    public static void setCache(String name, String key, Object object){
        Cache cache = cacheManager.getCache(name);
        Element element = new Element(key, object);
        cache.put(element);
    }
    /**
     * 从缓存中取出对象
     * @param key
     * @return
     */
    public static Object getCache(String name, String key){
        Object object = null;
        Cache cache = cacheManager.getCache(name);
        if(cache.get(key) != null) {
            object = cache.get(key).getObjectValue();
        }
        return object;
    }

    public static void removeCache(String name, String key) {
        Cache cache = cacheManager.getCache(name);
        cache.remove(key);
    }

}
