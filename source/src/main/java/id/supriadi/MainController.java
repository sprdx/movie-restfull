package id.supriadi;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.supriadi.model.Movie;
import id.supriadi.service.MovieService;
import id.supriadi.util.MainUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/Movies")
public class MainController {
    Logger logger = Logger.getLogger(MainController.class.getName());
    @Inject
    MovieService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieList() {
        try {
            List<Map<String, Object>> movie = service.getMovieList();
            return Response.status(Response.Status.OK).entity(movie).build();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \"Id is should be number\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @Path("/{ID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieDetail(@PathParam("ID") String id) {
        try {
            Integer numberId = Integer.parseInt(id);
            List<Map<String, Object>> movie = service.getMovieDetail(numberId);
            return Response.status(Response.Status.OK).entity(movie).build();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \"Id is should be number\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMovie(String requestBody) {
        logger.info("adding movie...");
        try {
            logger.info("deserializing json to object");
            Movie newMovie = MainUtil.fromJson(requestBody, Movie.class);

            logger.info("validating object");
            logger.info(newMovie.toString());
            Map<String, Object> validationResult = newMovie.validate();
            logger.info("after validating object");
            if (Boolean.FALSE.equals(validationResult.get("isValid"))) {
                logger.log(Level.WARNING, "object is not valid: {0}", new Object[]{validationResult.get("message")});
                String message = "{\"message\" : \""+validationResult.get("message")+"\"}";
                return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            }
            logger.info("object is valid");

            Map<String, Object> result = service.addMovie(newMovie);
            if (Boolean.FALSE.equals(result.get("isSuccess"))) {
                if (result.get("errorType").equals("bad_request")) {
                    throw new IllegalArgumentException((String)result.get("message"));
                } else {
                    throw new IllegalStateException((String)result.get("message"));
                }
            }
            return Response.status(Response.Status.CREATED).entity("{\"message\": \"Successfully added a new movie\"}").build();
        } catch (JsonProcessingException e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \"Request body is invalid or id should be integer\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        } catch(IllegalArgumentException e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \""+e.getMessage()+"\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        } catch(Exception e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \""+e.getMessage()+"\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }

    @Path("/{ID}")
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("ID") String id, String requestBody) {
        logger.info("updating movie...");
        try {
            Integer numberId = Integer.parseInt(id);
            logger.info("deserializing json to object");
            Movie newMovie = MainUtil.fromJson(requestBody, Movie.class);

            logger.info("validating object");
            logger.info(newMovie.toString());
            Map<String, Object> validationResult = newMovie.validate();
            logger.info("after validating object");
            if (Boolean.FALSE.equals(validationResult.get("isValid"))) {
                logger.log(Level.WARNING, "object is not valid: {0}", new Object[]{validationResult.get("message")});
                String message = "{\"message\" : \""+validationResult.get("message")+"\"}";
                return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            }
            logger.info("object is valid");

            Map<String, Object> result = service.updateMovie(newMovie, numberId);
            if (Boolean.FALSE.equals(result.get("isSuccess"))) {
                if (result.get("errorType").equals("bad_request")) {
                    throw new IllegalArgumentException((String)result.get("message"));
                } else {
                    throw new IllegalStateException((String)result.get("message"));
                }
            }
            return Response.status(Response.Status.OK).entity("{\"message\": \"Successfully updated a movie\"}").build();
        } catch (JsonProcessingException e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \"Request body is invalid or id should be integer\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        } catch(IllegalArgumentException e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \""+e.getMessage()+"\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        } catch(Exception e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \""+e.getMessage()+"\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }

    @Path("/{ID}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("ID") String id) {
        logger.info("deleting movie...");
        try {
            Integer numberId = Integer.parseInt(id);

            Map<String, Object> result = service.deleteMovie(numberId);
            if (Boolean.FALSE.equals(result.get("isSuccess"))) {
                if (result.get("errorType").equals("bad_request")) {
                    throw new IllegalArgumentException((String)result.get("message"));
                } else {
                    throw new IllegalStateException((String)result.get("message"));
                }
            }
            return Response.status(Response.Status.OK).entity("{\"message\": \"Successfully deleted a movie\"}").build();
        } catch(IllegalArgumentException e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \""+e.getMessage()+"\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        } catch(Exception e) {
            logger.warning(e.getMessage());
            String message = "{\"message\" : \""+e.getMessage()+"\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }
}
