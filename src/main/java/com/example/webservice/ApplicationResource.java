package com.example.webservice;

import com.example.webservice.dao.ApplicationDAOImpl;
import com.example.webservice.dao.RuntimeSQLException;
import com.example.webservice.dao.RuntimeSQLIntegrityException;
import com.example.webservice.service.ApplicationService;
import com.example.webservice.service.ApplicationServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("applications")
@Produces("application/json")
@Consumes("application/json")
public class ApplicationResource {

    private static final int HTTP_200 = 200;
    private final ApplicationService service;

    public ApplicationResource() {
        service = new ApplicationServiceImpl(new ApplicationDAOImpl());
    }

    @GET
    public Response getAll() { //path is http://localhost:8080/webservice-1.0-SNAPSHOT/rest/applications

        List<Application> applications = service.findAll();

        return Response.status(HTTP_200).entity(applications).build();
    }


    @POST
    public Response addApplication(Application application) {
        Response response = Response.status(201).build();
        try {
            service.addApplication(application);
        } catch (RuntimeSQLIntegrityException e) {
            e.printStackTrace();
            response = Response.status(409).build();
        } catch (RuntimeSQLException e) {
            response = Response.status(403).build();
        }

        return response;
    }

    @GET
    @Path("{id}")
    public Response getApplication(@PathParam("id") int id) {
        Application application = service.findById(id);
        return Response.status(HTTP_200).entity(application).build();
    }

    @GET
    @Path("{id}/{name}")
    public Response getApplicationMultiParam(@PathParam("id") int id, @PathParam("name") String name) {
        Application application = service.findByIdAndName(id, name);
        return Response.status(HTTP_200).entity(application).build();
    }

    @PUT
    public Response updateApplication(Application application) {
        Response response = Response.status(200).build();
        try {
            service.updateApplication(application);
        } catch (RuntimeSQLException e) {
            e.printStackTrace();
            response = Response.status(403).build();
        }


        return response;
    }
}
