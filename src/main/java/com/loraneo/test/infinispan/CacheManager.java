package com.loraneo.test.infinispan;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.util.concurrent.IsolationLevel;

@Dependent
public class CacheManager {

    @Produces
    @ApplicationScoped
    public EmbeddedCacheManager defaultEmbeddedCacheManager() {
        final EmbeddedCacheManager manager = new DefaultCacheManager(GlobalConfigurationBuilder.defaultClusteredBuilder()
                .transport()
                .nodeName("jgroup-tcp")
                .addProperty("configurationFile",
                        "jgroups-infi.xml")
                .build());

        manager.defineConfiguration("entity",

                new ConfigurationBuilder().clustering()
                        .cacheMode(CacheMode.INVALIDATION_SYNC)
                        .partitionHandling()
                        .enabled(true)
                        .locking()
                        .isolationLevel(IsolationLevel.REPEATABLE_READ)

                        .build());

        return manager;
    }

}
