package com.likerhood.design.finalkeyword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FinalKeywordDemo {

    public static final int MAX_RETRY = 3;
    public static final String APP_NAME = "final-demo";

    private final String id;
    private final List<String> tags;

    private final long createdAt;

    public FinalKeywordDemo(String id, List<String> tags) {
        this.id = id;
        this.tags = new ArrayList<>(tags);
        this.createdAt = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public int retryLimit() {
        return MAX_RETRY;
    }

    public String appendSuffix(final String value) {
        // value = "changed"; // Compile error: final parameter cannot be reassigned.
        return value + "-suffix";
    }

    public List<String> mutableFinalReference() {
        final List<String> values = new ArrayList<>();
        values.add("A");
        values.add("B");
        // values = new ArrayList<>(); // Compile error: final reference cannot point to another object.
        return values;
    }

    public Runnable effectivelyFinalLocalVariable() {
        String message = "hello";
        // message = "changed"; // Compile error in lambda capture: variable must be final or effectively final.
        return () -> System.out.println(message);
    }

    public static final class FinalUtility {
        private FinalUtility() {
        }

        public static String normalize(String value) {
            return value == null ? "" : value.trim().toLowerCase();
        }
    }

    public static class ParentService {
        public final String stableApi() {
            return "stable-api";
        }

        public String extensionPoint() {
            return "parent-extension";
        }
    }

    public static class ChildService extends ParentService {
        // public String stableApi() { return "changed"; }
        // Compile error: final method cannot be overridden.

        @Override
        public String extensionPoint() {
            return "child-extension";
        }
    }

    public static class BlankFinalExample {
        private final String name;

        public BlankFinalExample(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class FinalReferenceHolder {
        private final StringBuilder builder = new StringBuilder("start");

        public String append(String value) {
            builder.append(value);
            return builder.toString();
        }

        public StringBuilder getBuilder() {
            return builder;
        }
    }
}
