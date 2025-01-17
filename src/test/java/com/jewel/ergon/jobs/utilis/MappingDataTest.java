package com.jewel.ergon.jobs.utilis;

import com.jewel.ergon.jobs.exceptions.IncompatibleSourceAndTargetFieldsTypesException;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// test class for MappingData
class MappingDataTest {

    @Test
    public void testNullSource() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            MappingData.getAndSet(null, new Target());
        });
        assertEquals("Source or target cannot be null", thrown.getMessage());
    }

    @Test
    public void testNullTarget() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            MappingData.getAndSet(new Source(), null);
        });
        assertEquals("Source or target cannot be null", thrown.getMessage());
    }

    @Test
    public void testBothNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            MappingData.getAndSet(null, null);
        });
        assertEquals("Source or target cannot be null", thrown.getMessage());
    }

    @Test
    public void testEmptyObjects() throws Exception {
        Source source = new Source();
        Target target = new Target();
            MappingData.getAndSet(source, target);
        // No assertion, just making sure no exceptions are thrown
    }

    @Test
    public void testMatchingFields() throws IllegalAccessException, IncompatibleSourceAndTargetFieldsTypesException {
        Source source = new Source();
        source.setName("Test");
        Target target = new Target();
        MappingData.getAndSet(source, target);
        assertEquals("Test", target.getName());
    }

    @Test
    public void testNonMatchingFields() throws IllegalAccessException, IncompatibleSourceAndTargetFieldsTypesException {
        Source source = new Source();
        source.setName("Test");
        Target target = new Target();
        // Only "name" in source should be mapped to "name" in target, not "age"
        MappingData.getAndSet(source, target);
        assertNull(target.getAge()); // Target should not be modified
    }

    @Test
    public void testPrivateFields() throws IllegalAccessException, IncompatibleSourceAndTargetFieldsTypesException {
        Source source = new Source();
        source.setName("Test");
        Target target = new Target();
        MappingData.getAndSet(source, target);
        assertEquals("Test", target.getName()); // Private field access should work
    }

    @Test
    public void testFieldInSuperclass() throws IllegalAccessException, IncompatibleSourceAndTargetFieldsTypesException {
        SourceWithParent source = new SourceWithParent();
        source.setParentName("Parent");
        TargetWithParent target = new TargetWithParent();
        MappingData.getAndSet(source, target);
        assertEquals("Parent", target.getParentName());
    }

    @Test
    public void testPrimitiveToWrapperConversion() throws IllegalAccessException, IncompatibleSourceAndTargetFieldsTypesException {
        Source source = new Source();
        source.setAge(30);
        Target target = new Target();
        MappingData.getAndSet(source, target);
        assertEquals(Integer.valueOf(30), target.getAge());
    }

    @Test
    public void testArrayField() throws IllegalAccessException, IncompatibleSourceAndTargetFieldsTypesException {
        Source source = new Source();
        source.setNumbers(new int[]{1, 2, 3});
        Target target = new Target();
        MappingData.getAndSet(source, target);
        assertArrayEquals(new int[]{1, 2, 3}, target.getNumbers());
    }

    @Test
    public void testCollectionField() throws IllegalAccessException, IncompatibleSourceAndTargetFieldsTypesException {
        Source source = new Source();
        source.setTags(List.of("tag1", "tag2"));
        Target target = new Target();
        MappingData.getAndSet(source, target);
        assertEquals(List.of("tag1", "tag2"), target.getTags());
    }

    @Test
    public void testFieldWithEnum() throws IllegalAccessException, IncompatibleSourceAndTargetFieldsTypesException {
        Source source = new Source();
        source.setColor(Color.RED);
        Target target = new Target();
        MappingData.getAndSet(source, target);
        assertEquals(Color.RED, target.getColor());
    }



    // Sample Source and Target classes for testing
    @Getter
    @Setter
    static class Source {
        private String name;
        private Integer age;
        private int[] numbers;
        private List<String> tags;
        private Color color;
    }


    @Getter
    @Setter
    static class Target {
        private String name;
        private Integer age;
        private int[] numbers;
        private List<String> tags;
        private Color color;
    }

    static class SourceWithParent extends Source {
        private String parentName;

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }
    }

    static class TargetWithParent extends Target {
        private String parentName;

        public String getParentName() {
            return parentName;
        }
    }

    enum Color {
        RED, GREEN, BLUE;
    }
}