package com.ibm.cohesion.asm;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.objectweb.asm.Opcodes;

import com.ibm.cohesion.data.Method;

public class MethodUseRecorderTest {

    private final String testMethodName = "testMethod";
    private final String testClassName = "TestClass";
    private final String testMethodDesc = "()V";
    private List<Method> methodsRecorded = null;

    @Before
    public void createMethodsRecorded() {
        methodsRecorded = new ArrayList<>();
    }
    
    @Test
    public void methodRecordedWithNameAndId() {
        MethodUseRecorder testObject = new MethodUseRecorder(testMethodName, testMethodDesc, methodsRecorded, testClassName);

        testObject.visitEnd();

        assertThat(methodsRecorded, hasSize(1));
        Method method = methodsRecorded.get(0);
        assertThat(method.name, is(testMethodName));
        assertThat(method.id, is(testMethodName + "_" + testMethodDesc));
    }

    @Test
    public void recordsFieldUsed() {
        MethodUseRecorder testObject = new MethodUseRecorder(testMethodName, testMethodDesc, methodsRecorded, testClassName);
        String testFieldName = "testField";

        testObject.visitFieldInsn(Opcodes.ASM5, testClassName, testFieldName, "");
        testObject.visitEnd();

        assertThat(methodsRecorded, hasSize(1));
        Method method = methodsRecorded.get(0);
        assertThat(method.fieldsUsed, hasSize(1));
        assertThat(method.fieldsUsed, contains(testFieldName));
    }

    @Test
    public void fieldUsedFromOtherClassNotRecorded() {
        MethodUseRecorder testObject = new MethodUseRecorder(testMethodName, testMethodDesc, methodsRecorded, testClassName);
        String testFieldName = "testField";

        testObject.visitFieldInsn(Opcodes.ASM5, "OtherClass", testFieldName, "");
        testObject.visitEnd();

        assertThat(methodsRecorded, hasSize(1));
        assertThat(methodsRecorded.get(0).fieldsUsed, hasSize(0));
    }
    
    @Test
    public void recordsMethodCall() {
        MethodUseRecorder testObject = new MethodUseRecorder(testMethodName, testMethodDesc, methodsRecorded, testClassName);
        String calledMethodName = "testMethod";
        String calledMethodDesc = "()V";

        testObject.visitMethodInsn(Opcodes.ASM5, testClassName, calledMethodName, calledMethodDesc, false);
        testObject.visitEnd();

        assertThat(methodsRecorded, hasSize(1));
        Method method = methodsRecorded.get(0);
        assertThat(method.methodsCalled, hasSize(1));
        assertThat(method.methodsCalled, contains(calledMethodName + "_" + calledMethodDesc));
    }
    
    @Test
    public void methodCallFromOtherClassNotRecorded() {
        MethodUseRecorder testObject = new MethodUseRecorder(testMethodName, testMethodDesc, methodsRecorded, testClassName);
        String calledMethodName = "testMethod";
        String calledMethodDesc = "()V";

        testObject.visitMethodInsn(Opcodes.ASM5, "OtherClass", calledMethodName, calledMethodDesc, false);
        testObject.visitEnd();

        assertThat(methodsRecorded, hasSize(1));
        assertThat(methodsRecorded.get(0).methodsCalled, hasSize(0));
    }

}
