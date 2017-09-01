package com.loraneo.test.infinispan;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CDILookup {

    private static final Logger LOG = Logger.getLogger(CDILookup.class.getName());

    private CDILookup() {
        super();
    }

    public static <T> T find(final Class<T> type,
                             final Annotation... qualifiers) {
        try {
            final BeanManager beanManager = InitialContext.doLookup("java:comp/BeanManager");
            final Set<Bean<?>> beans = beanManager.getBeans(type,
                    qualifiers);
            final Bean<?> bean = beanManager.resolve(beans);
            return getBeanInstance(type,
                    beanManager,
                    bean);
        } catch (final NamingException | IllegalArgumentException ex) {
            LOG.log(Level.SEVERE,
                    "Can't lookup object by {0}\n{1}",
                    new Object[] {type,
                                  ex.getMessage() });
            return null;
        }
    }

    public static <T> T getOrThrow(final Class<T> type,
                                   final Annotation... qualifiers) {
        return Optional.ofNullable(find(type,
                qualifiers))
                .orElseThrow(() -> new RuntimeException("Cant find any bean for class: " + type));
    }

    public static Object find(final String name) {
        try {
            final BeanManager beanManager = InitialContext.doLookup("java:comp/BeanManager");
            final Set<Bean<?>> beans = beanManager.getBeans(name);
            final Bean<?> bean = beanManager.resolve(beans);

            return getBeanInstance(bean.getBeanClass(),
                    beanManager,
                    bean);
        } catch (final NamingException | IllegalArgumentException ex) {
            LOG.log(Level.SEVERE,
                    "Can't lookup object by {0}\n{1}",
                    new Object[] {name,
                                  ex.getMessage() });
            return null;
        }
    }

    public static <T> T getBeanInstance(final Class<T> type,
                                        final BeanManager beanManager,
                                        final Bean<?> bean) {

        final CreationalContext<?> cc = beanManager.createCreationalContext(bean);

        @SuppressWarnings("unchecked")
        final T object = (T) beanManager.getReference(bean,
                type,
                cc);
        return object;
    }
}
