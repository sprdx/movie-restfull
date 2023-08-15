package id.supriadi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.supriadi.util.MainUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class Movie {
    int id;
    String title;
    String description;
    float rating;
    String image;
    @JsonProperty("created_at")
    String createdAt;
    @JsonProperty("updated_at")
    String updatedAt;

    public Movie() {
    }

    public Movie(int id, String title, String description, float rating, String image, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

    public Map<String, Object> validate() {
        Map<String, Object> result = new HashMap<>();
        result.put("isValid", false);
        if (id < 1) {
            result.put("message", "id is not valid");
            return result;
        }
        if (title == null || title.isBlank()) {
            result.put("message", "title is mandatory");
            return result;
        }
        if (description == null || description.isBlank()) {
            result.put("message", "description is mandatory");
            return result;
        }
        if (rating < 1) {
            result.put("message", "rating is mandatory");
            return result;
        }
        if (image == null) {
            MainUtil.logger.info("invalid image");
            result.put("message", "image shouldn't be null");
            return result;
        }
        if (!isValidDatetime(createdAt)) {
            System.out.println("invalid datetime");
            result.put("message", "invalid created_at format, format should be yyyy-MM-dd HH:mm:ss");
            return result;
        }
        if (!isValidDatetime(updatedAt)) {
            System.out.println("invalid datetime");
            result.put("message", "invalid updated_at format, format should be yyyy-MM-dd HH:mm:ss");
            return result;
        }
        if (!isValidUpdatedAt()) {
            result.put("message", "updated_at should be after created_at");
            return result;
        }
        result.put("isValid", true);
        return result;
    }

    private boolean isValidDatetime (String datetimeString) {
        if (datetimeString == null) {
            System.out.println("Invalid datetime format");
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            formatter.parse(datetimeString);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid datetime format");
            return false;
        }
        return true;
    }

    private boolean isValidUpdatedAt () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime createdDatetime = LocalDateTime.parse(this.createdAt, formatter);
        LocalDateTime updatedDatetime = LocalDateTime.parse(this.updatedAt, formatter);

        return !updatedDatetime.isBefore(createdDatetime);
    }
}
