package com.loraneo.test.infinispan;

import javax.enterprise.inject.Produces;

import org.infinispan.cdi.ConfigureCache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;

public class Config {

    /**
     * <p>
     * This producer defines the greeting cache configuration.
     * </p>
     *
     * <p>
     * This cache will have:
     * <ul>
     * <li>a maximum of 4 entries</li>
     * <li>use the strategy LRU for eviction</li>
     * </ul>
     * </p>
     *
     * @return the greeting cache configuration.
     */
    @ConfigureCache("greeting-cache")
    @Produces
    public Configuration greetingCache() {
        return new ConfigurationBuilder().eviction()
                .strategy(EvictionStrategy.LRU)
                .build();
    }

    /**
     * <p>
     * This producer overrides the default cache configuration used by the default cache manager.
     * </p>
     *
     * <p>
     * The default cache configuration defines that a cache entry will have a lifespan of 60000 ms.
     * </p>
     */
    @Produces
    public Configuration defaultCacheConfiguration() {
        return new ConfigurationBuilder().expiration()
                .lifespan(60000l)
                .build();
    }

}
