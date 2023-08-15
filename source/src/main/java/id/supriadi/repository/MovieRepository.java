package id.supriadi.repository;

import id.supriadi.model.Movie;

import java.util.List;
import java.util.Map;

public interface MovieRepository {
    List<Map<String, Object>> findAll();
    List<Map<String, Object>> findById(int id);
    boolean save(Movie movie);
    boolean update(Movie movie, int id);
    boolean delete(int id);
}
