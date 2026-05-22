package com.likerhood.design.statickeyword;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StaticKeywordDemoTest {

    @Before
    public void setUp() {
        StaticKeywordDemo.resetForTest();
    }

    @Test
    public void test_staticFieldIsSharedByAllObjects() {
        assertEquals(0, StaticKeywordDemo.getTotalCreated());

        StaticKeywordDemo first = new StaticKeywordDemo("first");
        StaticKeywordDemo second = new StaticKeywordDemo("second");

        assertEquals("first", first.getName());
        assertEquals("second", second.getName());
        assertEquals(2, StaticKeywordDemo.getTotalCreated());
    }

    @Test
    public void test_staticMethodCanBeCalledWithoutObject() {
        assertEquals("java", StaticKeywordDemo.normalize("  JAVA  "));
        assertEquals("", StaticKeywordDemo.normalize(null));
    }

    @Test
    public void test_staticBlockRunsBeforeObjectCreation() {
        StaticKeywordDemo demo = new StaticKeywordDemo("alpha");

        assertEquals("alpha", demo.getName());
        assertEquals(Arrays.asList(
                "static-block",
                "instance-block",
                "constructor:alpha"
        ), StaticKeywordDemo.getInitLog());
    }

    @Test
    public void test_staticConfigIsSharedState() {
        StaticKeywordDemo one = new StaticKeywordDemo("one");
        StaticKeywordDemo two = new StaticKeywordDemo("two");

        StaticKeywordDemo.changeGlobalConfig("prod-config");

        assertEquals("prod-config", StaticKeywordDemo.getGlobalConfig());
        assertEquals(2, StaticKeywordDemo.getTotalCreated());
        assertNotSame(one, two);
    }

    @Test
    public void test_staticNestedClassDoesNotNeedOuterObject() {
        StaticKeywordDemo.StaticNestedFormatter formatter = new StaticKeywordDemo.StaticNestedFormatter();

        assertEquals("[spring]", formatter.format(" Spring "));
        assertTrue(Modifier.isStatic(StaticKeywordDemo.StaticNestedFormatter.class.getModifiers()));
    }

    @Test
    public void test_nonStaticInnerClassNeedsOuterObject() {
        StaticKeywordDemo demo = new StaticKeywordDemo("outer");
        StaticKeywordDemo.InnerPrinter printer = demo.new InnerPrinter();

        assertEquals("outer-name:outer", printer.print());
        assertFalse(Modifier.isStatic(StaticKeywordDemo.InnerPrinter.class.getModifiers()));
    }

    @Test
    public void test_staticHolderSingletonReturnsSameInstance() {
        StaticKeywordDemo.HolderSingleton first = StaticKeywordDemo.HolderSingleton.getInstance();
        StaticKeywordDemo.HolderSingleton second = StaticKeywordDemo.HolderSingleton.getInstance();

        assertSame(first, second);
    }

    @Test
    public void test_staticFinalConstant() throws Exception {
        assertEquals("static-demo", StaticKeywordDemo.APP_NAME);
        assertTrue(Modifier.isStatic(StaticKeywordDemo.class.getField("APP_NAME").getModifiers()));
        assertTrue(Modifier.isFinal(StaticKeywordDemo.class.getField("APP_NAME").getModifiers()));
    }
}
