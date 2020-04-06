package fr.devnr.jarialtekin.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ResourceLoader {

    public static Properties load(String propertyFile) {
        try ( InputStream input = getInputStream(propertyFile) ) {
            Properties props = new Properties();
            props.load( input );
            return props;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonNode loadJSON(String jsonFile) {
        try ( InputStream input = getInputStream(jsonFile) ) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T loadJSON(String jsonFile, Class<T> clazz) {
        try ( InputStream input = getInputStream(jsonFile) ) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(input, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T loadJSON(String jsonFile, String fieldPath, Class<T> clazz) {
        try ( InputStream input = getInputStream(jsonFile) ) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(input);
            String[] fields = fieldPath.split("\\.");
            for (String field : fields) {
                json = json.get(field);
            }
            return mapper.readValue(json.toString(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static InputStream getInputStream(String path) {
        String filePath = path.startsWith("/") ? path : "/"+path;
        return ResourceLoader.class.getResourceAsStream( filePath );
    }

}
