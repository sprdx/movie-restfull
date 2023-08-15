package id.supriadi.repository;

import id.supriadi.model.Movie;
import id.supriadi.util.MainUtil;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

@Singleton
public class MySQLMovieRepository implements MovieRepository{
    Logger logger = Logger.getLogger(MySQLMovieRepository.class.getName());
    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String url;
    @ConfigProperty(name = "quarkus.datasource.username")
    String username;
    @ConfigProperty(name = "quarkus.datasource.password")
    String password;

    public List<Map<String, Object>> findAll() {
        String statement = "SELECT * FROM movies";

        try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = conn.prepareStatement(statement)) {

            List<Map<String, Object>> rows =  new ArrayList<>();
            try (ResultSet resultSet = pstmt.executeQuery()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        row.put(resultSetMetaData.getColumnName(i).toLowerCase(), resultSet.getObject(i));
                    }
                    rows.add(row);
                }
            }
            logger.info("total row: " + rows.size());
            return rows;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    public List<Map<String, Object>> findById(int id) {
        String statement = "SELECT * FROM movies WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setInt(1, id);

            List<Map<String, Object>> rows =  new ArrayList<>();
            try (ResultSet resultSet = pstmt.executeQuery()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        row.put(resultSetMetaData.getColumnName(i).toLowerCase(), resultSet.getObject(i));
                    }
                    rows.add(row);
                }
            }
            logger.info("total row: " + rows.size());
            return rows;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    public boolean save(Movie movie) {
        String statement = "INSERT INTO movies(id, title, description, rating, image, created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setInt(1, movie.getId());
            pstmt.setString(2, movie.getTitle());
            pstmt.setString(3, movie.getDescription());
            pstmt.setFloat(4, movie.getRating());
            pstmt.setString(5, movie.getImage());

            Timestamp createdAt = MainUtil.getTimestamp(movie.getCreatedAt());
            pstmt.setTimestamp(6, createdAt);
            Timestamp updatedAt = MainUtil.getTimestamp(movie.getUpdatedAt());
            pstmt.setTimestamp(7, updatedAt);

            int affectedRow = pstmt.executeUpdate();
            if (affectedRow == 1) {
                return true;
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return  false;
        }
        return false;
    }

    public boolean update(Movie movie, int id) {
        String statement = "UPDATE movies SET id = ?, title = ?, description = ?, rating = ?, image = ?, created_at = ?, updated_at = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setInt(1, movie.getId());
            pstmt.setString(2, movie.getTitle());
            pstmt.setString(3, movie.getDescription());
            pstmt.setFloat(4, movie.getRating());
            pstmt.setString(5, movie.getImage());

            Timestamp createdAt = MainUtil.getTimestamp(movie.getCreatedAt());
            pstmt.setTimestamp(6, createdAt);
            Timestamp updatedAt = MainUtil.getTimestamp(movie.getUpdatedAt());
            pstmt.setTimestamp(7, updatedAt);
            pstmt.setInt(8, id);

            int affectedRow = pstmt.executeUpdate();
            if (affectedRow == 1) {
                return true;
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return  false;
        }
        return false;
    }

    public boolean delete(int id) {
        String statement = "DELETE FROM movies WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setInt(1, id);

            int affectedRow = pstmt.executeUpdate();
            if (affectedRow == 1) {
                return true;
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return  false;
        }
        return false;
    }
}
