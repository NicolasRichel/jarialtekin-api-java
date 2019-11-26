package fr.devnr.jarialtekinapi.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class ConfigTest {

    @Test
    void GetProperty() {
        Config config = Config.getConfig();
        String prop1 = config.get("prop1");
        assertEquals("Hello World !", prop1);
    }

    @Test
    void GetProperties() {
        Config config = Config.getConfig();
        String[] propNames = { "prop1", "prop2.value", "prop3", "prop4", "prop5" };
        Map<String, String> props = config.get( propNames );
        assertAll(
            () -> assertEquals("Hello World !", props.get("prop1")),
            () -> assertEquals("0",             props.get("prop2.value")),
            () -> assertEquals("1.0",           props.get("prop3")),
            () -> assertEquals("\"abcd\"",      props.get("prop4")),
            () -> assertEquals("{a:1,b:2,c:3}", props.get("prop5"))
        );
    }

}
