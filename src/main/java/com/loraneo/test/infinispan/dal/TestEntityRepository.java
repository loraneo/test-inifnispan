package com.loraneo.test.infinispan.dal;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Dependent
@Transactional
public class TestEntityRepository {

    @PersistenceContext(unitName = "user")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void save(final Object e) {
        em.persist(e);
        em.flush();
    }

}
