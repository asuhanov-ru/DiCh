package com.chemista.dev.DiCh.framework.config.jcache;

import java.util.Properties;
import javax.cache.Cache;


import org.hibernate.cache.jcache.JCacheRegionFactory;
import org.hibernate.cache.spi.CacheDataDescription;
/**
 * Extends the default {@code JCacheRegionFactory} but makes sure all caches already exist to prevent
 * spontaneous creation of badly configured caches (e.g. {@code new MutableConfiguration()}.
 *
 * See http://www.ehcache.org/blog/2017/03/15/spontaneous-cache-creation.html for more information.
 */
@SuppressWarnings("serial")
public class NoDefaultJCacheRegionFactory extends JCacheRegionFactory {

    public static final String EXCEPTION_MESSAGE = "All Hibernate caches should be created upfront. " +
            "Please update CacheConfiguration.java to add";

    @Override
    protected Cache<Object, Object> createCache(String regionName, Properties properties, CacheDataDescription
            metadata) {
        throw new IllegalStateException(EXCEPTION_MESSAGE + " " + regionName);
    }
}
