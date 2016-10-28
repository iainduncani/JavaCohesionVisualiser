package com.ibm.cohesion.asm;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;

public class MethodRecordingVisitorTest {

    @Test
    public void methodUseVisitorCreatedForMethod() {
        MethodRecordingVisitor testObject = new MethodRecordingVisitor(new ArrayList<>());

        testObject.visit(0, 0, "TestClassName", "", "", new String[] {});
        MethodVisitor methodVisitor = testObject.visitMethod(0, "testMethod", "()V", null, null);

        assertThat(methodVisitor, instanceOf(MethodUseVisitor.class));
    }

}
