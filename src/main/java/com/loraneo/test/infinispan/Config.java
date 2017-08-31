package com.loraneo.test.infinispan;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.infinispan.cdi.ConfigureCache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.manager.EmbeddedCacheManager;

@Dependent
public class Config {

    @Inject
    EmbeddedCacheManager embeddedCacheManager;

    @Produces
    @EntityCache
    @ConfigureCache("entity")
    public Configuration defaultEmbeddedCacheManager() {
        return embeddedCacheManager.getCacheConfiguration("entity");

    }

}
