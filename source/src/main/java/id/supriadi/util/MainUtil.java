package id.supriadi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class MainUtil {
    public static Logger logger = Logger.getLogger(MainUtil.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(json, valueType);
    }

    public static Timestamp getTimestamp(String stringTimestamp) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(stringTimestamp);
            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return null;
    }
}
