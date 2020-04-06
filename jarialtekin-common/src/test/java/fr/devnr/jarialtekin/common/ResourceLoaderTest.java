package fr.devnr.jarialtekin.common;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class ResourceLoaderTest {

    @Test
    void loadObjectFromJSON() {
        Dummy dummy = ResourceLoader.loadJSON("/test-1.json", Dummy.class);
        Assert.assertEquals("Hello World !", dummy.prop1);
        Assert.assertEquals(Integer.valueOf(42), dummy.prop2);
        Assert.assertEquals(Double.valueOf(4.2), dummy.prop3);
        Assert.assertArrayEquals(new String[]{ "e1", "e2", "e3" }, dummy.prop4.toArray());
        Assert.assertNull(dummy.prop5);
        Assert.assertEquals("A", dummy.dummyField.a);
        Assert.assertEquals(Integer.valueOf(0), dummy.dummyField.b);
    }

    @Test
    void loadObjectFromJSONField() {
        Dummy dummy = ResourceLoader.loadJSON("/test-2.json", "path.to.dummy", Dummy.class);
        Assert.assertEquals("Nested Dummy", dummy.prop1);
        Assert.assertEquals(Integer.valueOf(24), dummy.prop2);
    }

}

class Dummy {
    public String prop1;
    public Integer prop2;
    public Double prop3;
    public List<String> prop4;
    public String prop5;
    public DummyField dummyField;
}

class DummyField {
    public String a;
    public Integer b;
}
