package com.ibm.cohesion;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.ibm.cohesion.data.Method;

public class JavaClassAnalyzerTest {

    @Test
    public void classMethodsAreRecorded() throws FileNotFoundException, IOException {
        File exampleClass = new File(System.getProperty("testClassLocation"));
        MockOutputGenerator output = new MockOutputGenerator();
        JavaClassAnalyzer testObject = new JavaClassAnalyzer(exampleClass, output);

        testObject.analyseClass();

        assertThat(output.capturedMethods, hasSize(3));
        for (Method method : output.capturedMethods) {
            switch (method.name) {
                case "a":
                    assertThat(method.fieldsUsed, hasSize(0));
                    assertThat(method.methodsCalled, hasSize(1));
                    assertThat(method.methodsCalled, contains("b_()V"));
                    break;
                case "b":
                    assertThat(method.fieldsUsed, hasSize(1));
                    assertThat(method.fieldsUsed, contains("c"));
                    assertThat(method.methodsCalled, hasSize(0));
                    break;
                case "<init>":
                    break;
                default:
                    fail("Unknown method " + method.name);
            }
        }
    }

    public static class MockOutputGenerator implements OutputGenerator {

        public List<Method> capturedMethods;

        @Override
        public void generateOutput(List<Method> methods) {
            capturedMethods = methods;
        }

    }

}
