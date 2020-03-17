package com.chemista.dev.DiCh.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import com.chemista.dev.DiCh.framework.config.ServletTunerProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(ServletTunerProperties servletTunerProperties) {

        ServletTunerProperties.Cache.Ehcache ehcache =
                servletTunerProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                        ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                        .build());
    }
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }
    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.chemista.dev.DiCh.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.chemista.dev.DiCh.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.chemista.dev.DiCh.domain.User.class.getName());
            createCache(cm, com.chemista.dev.DiCh.domain.Authority.class.getName());
            createCache(cm, com.chemista.dev.DiCh.domain.User.class.getName() + ".authorities");
            createCache(cm, com.chemista.dev.DiCh.domain.PersistentToken.class.getName());
            createCache(cm, com.chemista.dev.DiCh.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.chemista.dev.DiCh.domain.Book.class.getName());
            createCache(cm, com.chemista.dev.DiCh.domain.Collection.class.getName());
            createCache(cm, com.chemista.dev.DiCh.domain.Page.class.getName());
            //createCache(cm, com.chemista.dev.DiCh.domain.Word.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }
    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}