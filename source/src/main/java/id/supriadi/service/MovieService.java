package id.supriadi.service;

import id.supriadi.model.Movie;
import id.supriadi.repository.MovieRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
public class MovieService {
    Logger logger = Logger.getLogger(MovieService.class.getName());
    @Inject
    MovieRepository movieRepository;

    public List<Map<String, Object>> getMovieList() {
        logger.info("getting all movies");
        return movieRepository.findAll();
    }

    public List<Map<String, Object>> getMovieDetail(int id) {
        logger.info("finding movie by id");
        return movieRepository.findById(id);
    }

    public Map<String, Object> addMovie(Movie movie) {
        Map<String, Object> result = new HashMap<>();
        result.put("isSuccess", false);

        List<Map<String, Object>> rows = movieRepository.findById(movie.getId());
        if (rows != null && !rows.isEmpty()) {
            result.put("errorType", "bad_request");
            result.put("message", "id is already exist");
            return result;
        }
        boolean isSuccess = movieRepository.save(movie);
        if (!isSuccess) {
            result.put("errorType", "internal_server_error");
            result.put("message", "failed to add movie to database");
            return result;
        }
        result.put("isSuccess", true);
        return result;
    }

    public Map<String, Object> updateMovie(Movie movie, int id) {
        Map<String, Object> result = new HashMap<>();
        result.put("isSuccess", false);

        List<Map<String, Object>> rows = movieRepository.findById(id);
        if (rows != null && rows.isEmpty()) {
            result.put("errorType", "bad_request");
            result.put("message", "id is not exist");
            return result;
        }
        rows = movieRepository.findById(movie.getId());
        if (rows != null && !rows.isEmpty()) {
            result.put("errorType", "bad_request");
            result.put("message", "expected new id is already exist");
            return result;
        }
        boolean isSuccess = movieRepository.update(movie, id);
        if (!isSuccess) {
            result.put("errorType", "internal_server_error");
            result.put("message", "failed to update movie to database");
            return result;
        }
        result.put("isSuccess", true);
        return result;
    }

    public Map<String, Object> deleteMovie(int id) {
        Map<String, Object> result = new HashMap<>();
        result.put("isSuccess", false);

        List<Map<String, Object>> rows = movieRepository.findById(id);
        if (rows != null && rows.isEmpty()) {
            result.put("errorType", "bad_request");
            result.put("message", "id is not exist");
            return result;
        }
        boolean isSuccess = movieRepository.delete(id);
        if (!isSuccess) {
            result.put("errorType", "internal_server_error");
            result.put("message", "failed to delete movie in database");
            return result;
        }
        result.put("isSuccess", true);
        return result;
    }
}
