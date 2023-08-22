package blabla.ahlberg.jaxrsproject;

import blabla.ahlberg.jaxrsproject.dto.FootballerDTO;
import blabla.ahlberg.jaxrsproject.dto.FootballerWithIdDTO;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/footballers")
public class FootballerResource {

    @Inject
    FootballerRepo repo;

    @Inject
    Mapper mapper;

    @GET
    @Produces("application/json")
    public List<FootballerWithIdDTO> getAllFootballers() {
        return repo.findAll().stream().map(mapper::mapToFootballerWithIdDTO).toList();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public FootballerWithIdDTO getFootballer(@PathParam("id") Long id) {
        return mapper.mapToFootballerWithIdDTO(repo.findOne(id));
    }

    @POST
    @Consumes("application/json")
    public Response postFootballer(FootballerDTO dto) {
        try {
            repo.insertOne(dto);
        } catch (ConstraintViolationException e) {
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public void updateFootballer(@PathParam("id") Long id, FootballerDTO dto) {
        repo.updateOne(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deleteFootballer(@PathParam("id") Long id) {
        repo.deleteOne(id);
    }

}
