package com.example.webservice;

import javax.ws.rs.*;
import java.util.LinkedList;
import java.util.List;

@Path("applications")
@Produces("application/json")
@Consumes("application/json")
public class ApplicationResource {

    @GET
    public List<Application> getAll() { //path is http://localhost:8080/javaee-7.0/rest/applications
        List<Application> testList = new LinkedList<>();
        testList.add(new Application(1, "name 1", "description 1"));
        testList.add(new Application(2, "name 2", "description 2"));
        testList.add(new Application(3, "name 3", "description 3"));

        return testList;
    }


    @POST
    public Application addApplication(Application application) {
        return application;
    }

    @GET
    @Path("{id}")
    public Application getApplication(@PathParam("id") int id) {
        return new Application(id, "name " + id, "description " + id);
    }

    @GET
    @Path("{id}/{name}")
    public Application getApplicationMultiParam(@PathParam("id") int id, @PathParam("name") String name) {
        return new Application(id, name + id, "description for " + name);
    }
}
