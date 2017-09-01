package com.loraneo.test.infinispan;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Dependent
@Transactional
public class Test {

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void save(final Object e) {
        em.persist(e);
        em.flush();
    }

}
