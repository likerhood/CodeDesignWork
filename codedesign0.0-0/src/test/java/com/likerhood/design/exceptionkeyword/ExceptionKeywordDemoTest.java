package com.likerhood.design.exceptionkeyword;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ExceptionKeywordDemoTest {

    @Test
    public void test_tryCatchReturnsFriendlyMessage() {
        assertEquals("result:5", ExceptionKeywordDemo.divideSafely(10, 2));
        assertEquals("error:/ by zero", ExceptionKeywordDemo.divideSafely(10, 0));
    }

    @Test
    public void test_finallyAlwaysRunsWhenExceptionHappens() {
        assertEquals(Arrays.asList("try", "catch", "finally"),
                ExceptionKeywordDemo.tryCatchFinallyTrace(true));
        assertEquals(Arrays.asList("try", "success", "finally"),
                ExceptionKeywordDemo.tryCatchFinallyTrace(false));
    }

    @Test
    public void test_finallyReturnCanCoverTryReturn() {
        assertEquals("finally-return", ExceptionKeywordDemo.badReturnInFinally());
    }

    @Test
    public void test_throwCreatesBusinessException() {
        try {
            ExceptionKeywordDemo.validateAge(16);
            fail("Expected InvalidAgeException");
        } catch (ExceptionKeywordDemo.InvalidAgeException e) {
            assertEquals("age must be greater than or equal to 18", e.getMessage());
        }
    }

    @Test
    public void test_throwsLeavesCheckedExceptionToCaller() {
        try {
            ExceptionKeywordDemo.loadClassByName("no.such.ClassName");
            fail("Expected ClassNotFoundException");
        } catch (ClassNotFoundException e) {
            assertEquals("no.such.ClassName", e.getMessage());
        }
    }

    @Test
    public void test_exceptionCauseKeepsRootProblem() {
        RuntimeException exception = ExceptionKeywordDemo.wrapIOException();

        assertEquals("read config failed", exception.getMessage());
        assertTrue(exception.getCause() instanceof IOException);
        assertEquals("disk error", exception.getCause().getMessage());
    }

    @Test
    public void test_tryWithResourcesClosesResource() throws Exception {
        ExceptionKeywordDemo.TestResource.reset();

        String value = ExceptionKeywordDemo.useTryWithResources(false, false);

        assertEquals("data", value);
        assertEquals(Arrays.asList("open", "read", "close"), ExceptionKeywordDemo.TestResource.events());
    }

    @Test
    public void test_tryWithResourcesKeepsSuppressedException() {
        ExceptionKeywordDemo.TestResource.reset();

        try {
            ExceptionKeywordDemo.useTryWithResources(true, true);
            fail("Expected IOException");
        } catch (IOException e) {
            assertEquals("read failed", e.getMessage());
            assertEquals(1, e.getSuppressed().length);
            assertEquals("close failed", e.getSuppressed()[0].getMessage());
        }
    }

    @Test
    public void test_manualFinallyCloseMayHideOriginalException() {
        ExceptionKeywordDemo.TestResource.reset();

        try {
            ExceptionKeywordDemo.useManualFinally(true, true);
            fail("Expected IOException");
        } catch (IOException e) {
            assertEquals("close failed", e.getMessage());
            assertEquals(0, e.getSuppressed().length);
        }
    }

    @Test
    public void test_nullPointerPreCheck() {
        assertEquals("UNKNOWN", ExceptionKeywordDemo.upperName(null));
        assertEquals("JAVA", ExceptionKeywordDemo.upperName("java"));
    }

    @Test
    public void test_parseNumberShowsReasonableRuntimeExceptionHandling() {
        assertEquals(123, ExceptionKeywordDemo.parseNumberOrDefault("123", -1));
        assertEquals(-1, ExceptionKeywordDemo.parseNumberOrDefault("abc", -1));
    }
}
