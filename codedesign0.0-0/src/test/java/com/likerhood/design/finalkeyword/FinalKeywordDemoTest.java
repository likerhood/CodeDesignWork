package com.likerhood.design.finalkeyword;

import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FinalKeywordDemoTest {

    @Test
    public void test_finalConstant() {
        assertEquals(3, FinalKeywordDemo.MAX_RETRY);
        assertEquals("final-demo", FinalKeywordDemo.APP_NAME);
    }

    @Test
    public void test_finalFieldMustBeInitializedAndCannotReassign() {
        FinalKeywordDemo demo = new FinalKeywordDemo("order-1001", Arrays.asList("java", "final"));

        assertEquals("order-1001", demo.getId());
        assertEquals(Arrays.asList("java", "final"), demo.getTags());
        assertTrue(demo.getCreatedAt() > 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_finalFieldWithDefensiveCopy() {
        FinalKeywordDemo demo = new FinalKeywordDemo("order-1002", Arrays.asList("safe"));

        demo.getTags().add("break");
    }

    @Test
    public void test_finalReferenceCanStillChangeObjectState() {
        FinalKeywordDemo demo = new FinalKeywordDemo("order-1003", Arrays.asList("x"));

        List<String> values = demo.mutableFinalReference();

        assertEquals(Arrays.asList("A", "B"), values);
        values.add("C");
        assertEquals(Arrays.asList("A", "B", "C"), values);
    }

    @Test
    public void test_finalParameter() {
        FinalKeywordDemo demo = new FinalKeywordDemo("order-1004", Arrays.asList("x"));

        assertEquals("abc-suffix", demo.appendSuffix("abc"));
    }

    @Test
    public void test_finalClassCannotBeInherited() {
        int modifiers = FinalKeywordDemo.FinalUtility.class.getModifiers();

        assertTrue(Modifier.isFinal(modifiers));
        assertEquals("java", FinalKeywordDemo.FinalUtility.normalize("  JAVA  "));
    }

    @Test
    public void test_finalMethodCannotBeOverridden() throws Exception {
        FinalKeywordDemo.ChildService service = new FinalKeywordDemo.ChildService();

        assertEquals("stable-api", service.stableApi());
        assertEquals("child-extension", service.extensionPoint());
        assertTrue(Modifier.isFinal(
                FinalKeywordDemo.ParentService.class.getDeclaredMethod("stableApi").getModifiers()
        ));
    }

    @Test
    public void test_blankFinalField() {
        FinalKeywordDemo.BlankFinalExample example = new FinalKeywordDemo.BlankFinalExample("blank-final");

        assertEquals("blank-final", example.getName());
    }

    @Test
    public void test_finalReferenceIsNotImmutableObject() {
        FinalKeywordDemo.FinalReferenceHolder holder = new FinalKeywordDemo.FinalReferenceHolder();

        assertEquals("start-A", holder.append("-A"));
        assertEquals("start-A-B", holder.append("-B"));
    }
}
