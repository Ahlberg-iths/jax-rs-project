package blabla.ahlberg.jaxrsproject;

import blabla.ahlberg.jaxrsproject.dto.FootballerDTO;
import blabla.ahlberg.jaxrsproject.dto.FootballerWithIdDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

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
        return repo.findAll().stream().map(
                f -> mapper.mapToFootballerWithIdDTO(f))
                .toList();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public FootballerWithIdDTO getFootballer(@PathParam("id") Long id) {
        var optionalFootballer = repo.findOne(id);
        //TODO:: Exception handling if no footballer with that ID is found --> 404 - now is 500
        return optionalFootballer.map(footballer -> mapper.mapToFootballerWithIdDTO(footballer)).orElseThrow();
    }

    @POST
    @Consumes("application/json")
    public void postFootballer(FootballerDTO dto) {
        //TODO:: Exception handling. ConstraintViolationException etc.
        //TODO:: On Success --> return status code 201 - now is 204
        repo.insertOne(dto);
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public void updateFootballer(@PathParam("id") Long id, FootballerDTO dto) {
        //TODO:: Exception handling. If not parsable/if no id found - now 500
        repo.updateOne(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deleteFootballer(@PathParam("id") Long id) {
        repo.deleteOne(id);
    }

}
