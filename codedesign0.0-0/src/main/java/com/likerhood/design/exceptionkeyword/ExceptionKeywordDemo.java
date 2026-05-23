package com.likerhood.design.exceptionkeyword;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExceptionKeywordDemo {

    public static String divideSafely(int left, int right) {
        try {
            return "result:" + (left / right);
        } catch (ArithmeticException e) {
            return "error:" + e.getMessage();
        }
    }

    public static List<String> tryCatchFinallyTrace(boolean throwException) {
        List<String> trace = new ArrayList<>();
        try {
            trace.add("try");
            if (throwException) {
                throw new IllegalStateException("business failed");
            }
            trace.add("success");
        } catch (IllegalStateException e) {
            trace.add("catch");
        } finally {
            trace.add("finally");
        }
        return trace;
    }

    public static String badReturnInFinally() {
        try {
            return "try-return";
        } finally {
            return "finally-return";
        }
    }

    public static void validateAge(int age) {
        if (age < 18) {
            throw new InvalidAgeException("age must be greater than or equal to 18");
        }
    }

    public static Class<?> loadClassByName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public static RuntimeException wrapIOException() {
        try {
            readConfig();
            throw new IllegalStateException("unreachable");
        } catch (IOException e) {
            return new IllegalStateException("read config failed", e);
        }
    }

    private static void readConfig() throws IOException {
        throw new IOException("disk error");
    }

    public static String useTryWithResources(boolean readFailed, boolean closeFailed) throws IOException {
        try (TestResource resource = new TestResource(readFailed, closeFailed)) {
            return resource.read();
        }
    }

    public static String useManualFinally(boolean readFailed, boolean closeFailed) throws IOException {
        TestResource resource = new TestResource(readFailed, closeFailed);
        try {
            return resource.read();
        } finally {
            resource.close();
        }
    }

    public static String upperName(String name) {
        if (name == null) {
            return "UNKNOWN";
        }
        return name.toUpperCase();
    }

    public static int parseNumberOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static class InvalidAgeException extends RuntimeException {
        public InvalidAgeException(String message) {
            super(message);
        }
    }

    public static class TestResource implements Closeable {
        private static final List<String> EVENTS = new ArrayList<>();

        private final boolean readFailed;
        private final boolean closeFailed;

        public TestResource(boolean readFailed, boolean closeFailed) {
            this.readFailed = readFailed;
            this.closeFailed = closeFailed;
            EVENTS.add("open");
        }

        public String read() throws IOException {
            EVENTS.add("read");
            if (readFailed) {
                throw new IOException("read failed");
            }
            return "data";
        }

        @Override
        public void close() throws IOException {
            EVENTS.add("close");
            if (closeFailed) {
                throw new IOException("close failed");
            }
        }

        public static void reset() {
            EVENTS.clear();
        }

        public static List<String> events() {
            return Collections.unmodifiableList(EVENTS);
        }
    }
}
