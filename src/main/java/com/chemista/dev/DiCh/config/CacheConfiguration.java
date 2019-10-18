package com.chemista.dev.DiCh.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import com.chemista.dev.DiCh.framework.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import com.chemista.dev.DiCh.framework.config.ServletTunerProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(ServletTunerProperties servletTunerProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        ServletTunerProperties.Cache.Ehcache ehcache =
                servletTunerProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                        ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                        .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.chemista.dev.DiCh.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.chemista.dev.DiCh.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.chemista.dev.DiCh.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.chemista.dev.DiCh.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.chemista.dev.DiCh.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.chemista.dev.DiCh.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.chemista.dev.DiCh.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.chemista.dev.DiCh.domain.Book.class.getName(), jcacheConfiguration);
            cm.createCache(com.chemista.dev.DiCh.domain.Collection.class.getName(), jcacheConfiguration);
            cm.createCache(com.chemista.dev.DiCh.domain.Page.class.getName(), jcacheConfiguration);
            //cm.createCache(com.chemista.dev.DiCh.domain.Word.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}