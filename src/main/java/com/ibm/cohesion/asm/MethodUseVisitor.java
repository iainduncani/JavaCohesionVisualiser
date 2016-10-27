package com.ibm.cohesion.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.ibm.cohesion.data.Method;

public class MethodUseVisitor extends MethodVisitor {

    private final List<String> methodsCalled = new ArrayList<>();
    private final List<String> fieldsUsed = new ArrayList<>();
    private final String methodName;
    private final String methodDesc;
    private final List<Method> methodsToAppendTo;
    private final String className;

    public MethodUseVisitor(String methodName, String methodDesc, List<Method> methodsToAppendTo, String className) {
        super(Opcodes.ASM5);
        this.methodName = methodName;
        this.methodDesc = methodDesc;
        this.methodsToAppendTo = methodsToAppendTo;
        this.className = className;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (className.equals(owner)) {
            methodsCalled.add(getId(name, desc));
        }
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        if (className.equals(owner)) {
            fieldsUsed.add(name);
        }
    }

    @Override
    public void visitEnd() {
        methodsToAppendTo.add(new Method(this.methodName, getId(methodName, methodDesc), fieldsUsed, methodsCalled));
    }

    private String getId(String methodName, String desc) {
        return methodName + "_" + desc;
    }

}
