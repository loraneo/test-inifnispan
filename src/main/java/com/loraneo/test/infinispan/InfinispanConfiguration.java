package com.loraneo.test.infinispan;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

@ApplicationScoped
public class InfinispanConfiguration {

    @Produces
    @ApplicationScoped
    public EmbeddedCacheManager cacheManager() {
        try {
            return new DefaultCacheManager("infinispan.xml");
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}
