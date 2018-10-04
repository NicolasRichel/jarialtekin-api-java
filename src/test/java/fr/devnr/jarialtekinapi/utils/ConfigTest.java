package fr.devnr.jarialtekinapi.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    @Test
    void GetProperty() {
        // --{ ARRANGE / ACT / ASSERT }--
        assertEquals( "testValue", Config.get("testProperty") );
    }

    @Test
    void GetPropertyFromFile() {
        // --{ ARRANGE }--
        String filename = "/testconfig.properties";

        // --{ ACT }--
        String prop1 = Config.get(filename, "prop1");
        String prop2 = Config.get(filename, "prop2");
        String prop3 = Config.get(filename, "prop3");

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals("Hello World !", prop1),
            () -> assertEquals("0", prop2),
            () -> assertEquals("1.0", prop3)
        );
    }

    @Test
    void GetListOfPropertiesFromFile() {
        // --{ ARRANGE }--
        String filename = "/testconfig.properties";
        String[] properties = { "prop1", "prop4", "prop5" };

        // --{ ACT }--
        Map<String, String> propMap = Config.get(filename, properties);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals("Hello World !", propMap.get("prop1")),
            () -> assertEquals("\"abcd\"", propMap.get("prop4")),
            () -> assertEquals("{a:1,b:2,c:3}", propMap.get("prop5"))
        );
    }

}
