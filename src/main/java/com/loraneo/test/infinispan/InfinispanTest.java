package com.loraneo.test.infinispan;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.infinispan.Cache;

@ApplicationScoped
@Path("/cache")
public class InfinispanTest {

    @Inject
    @EntityCache
    Cache<String, String> cache;

    @GET
    @Path("get")
    @Produces("text/plain")
    public Response getMessage() {
        return Response.ok(cache.keySet()
                .toString() + "\n"
                + cache.values()
                        .toString())
                .build();
    }

    @GET
    @Path("put/{key}/{data}")
    @Produces("text/plain")
    public Response putMessage(@PathParam("key") final String key,
                               @PathParam("data") final String data) {
        cache.put(key,
                data);
        return Response.created(null)
                .build();

    }

}
