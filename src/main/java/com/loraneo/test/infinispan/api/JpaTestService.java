package com.loraneo.test.infinispan.api;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.loraneo.test.infinispan.dal.TestEntityRepository;
import com.loraneo.test.infinispan.model.Customer;
import com.loraneo.test.infinispan.model.Request;
import com.loraneo.test.infinispan.model.TestEntity;

@Transactional
@ApplicationScoped
@Path("/jpa")
public class JpaTestService {

    @Inject
    TestEntityRepository test;

    @GET
    @Path("get/{id}")
    @Produces("text/plain")
    public TestEntity getMessage(@PathParam("id") final Long id) {
        return test.getEm()
                .find(TestEntity.class,
                        id);
    }

    @GET
    @Path("update/{id}/{name}")
    @Produces("text/plain")
    public Long getMessage(@PathParam("id") final Long id,
                           @PathParam("name") final String name) {
        final TestEntity te = test.getEm()
                .find(TestEntity.class,
                        id);
        te.setName(name);
        test.getEm()
                .persist(te);
        test.getEm()
                .flush();
        return te.getVersion();
    }

    @GET
    @Path("crate/{name}")
    @Produces("text/plain")
    public Long putMessage(@PathParam("name") final String name) {
        final TestEntity rn = new TestEntity();
        rn.setName(name);
        test.getEm()
                .persist(rn);
        test.getEm()
                .flush();
        return rn.getId();
    }

    @GET
    @Path("test")
    @Produces("text/plain")
    public String test() {
        final Customer c1 = new Customer();
        c1.setEmail("c1@c1.c1");
        c1.setName("Name c1");
        c1.setPhoneNumber("+43c1");
        c1.setAddress("Address c1");
        final Request r1 = new Request();
        r1.setCustomer(c1);
        r1.setQuantity(10);

        final Request r2 = new Request();
        r2.setCustomer(c1);
        r2.setQuantity(10);

        test.getEm()
                .persist(c1);
        c1.setRequests(new ArrayList<>());
        c1.getRequests()
                .add(r1);
        c1.getRequests()
                .add(r2);

        final Long id = c1.getId();
        test.save(r1);
        test.save(r2);
        test.getEm()
                .flush();

        test.getEm()
                .refresh(c1);

        final Customer c2 = new Customer();
        c2.setEmail("c2@c2.c2");
        c2.setName("Name c2");
        c2.setPhoneNumber("+43c2");
        c2.setAddress("Address c2");

        r1.setCustomer(c2);
        test.getEm()
                .merge(c2);
        test.getEm()
                .merge(r2);
        test.getEm()
                .flush();

        test.getEm()
                .refresh(c2);
        test.getEm()
                .refresh(c1);

        return "ID: " + id + "  \n" + c1.toString() + "\n" + c2.toString();
    }
}
