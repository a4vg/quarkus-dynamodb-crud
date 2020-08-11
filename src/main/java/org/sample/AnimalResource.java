package org.sample;

import io.smallrye.common.constraint.NotNull;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/animals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnimalResource {

    @Inject
    AnimalService service;

    @GET
    public List<Animal> getAll() {
        return service.findAll();
    }

    @GET
    @Path("{name}")
    public Animal getSingle(@PathParam("name") String name) {
        return service.get(name);
    }

    @POST
    public List<Animal> add(Animal animal) {
        service.add(animal);
        return getAll();
    }
}