package com.likerhood.design.statickeyword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StaticKeywordDemo {

    public static final String APP_NAME = "static-demo";

    private static final AtomicInteger TOTAL_CREATED = new AtomicInteger();
    private static final List<String> INIT_LOG = new ArrayList<>();
    private static String globalConfig;

    private final String name;

    static {
        globalConfig = "default-config";
        INIT_LOG.add("static-block");
    }

    {
        INIT_LOG.add("instance-block");
    }

    public StaticKeywordDemo(String name) {
        this.name = name;
        TOTAL_CREATED.incrementAndGet();
        INIT_LOG.add("constructor:" + name);
    }

    public String getName() {
        return name;
    }

    public static int getTotalCreated() {
        return TOTAL_CREATED.get();
    }

    public static String getGlobalConfig() {
        return globalConfig;
    }

    public static void changeGlobalConfig(String value) {
        globalConfig = value;
    }

    public static String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    public static List<String> getInitLog() {
        return Collections.unmodifiableList(INIT_LOG);
    }

    public static void resetForTest() {
        TOTAL_CREATED.set(0);
        globalConfig = "default-config";
        INIT_LOG.clear();
        INIT_LOG.add("static-block");
    }

    public static class StaticNestedFormatter {
        public String format(String value) {
            return "[" + normalize(value) + "]";
        }
    }

    public class InnerPrinter {
        public String print() {
            return "outer-name:" + name;
        }
    }

    public static class HolderSingleton {
        private HolderSingleton() {
        }

        private static class Holder {
            private static final HolderSingleton INSTANCE = new HolderSingleton();
        }

        public static HolderSingleton getInstance() {
            return Holder.INSTANCE;
        }
    }
}
