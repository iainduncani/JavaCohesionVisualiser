package com.ibm.cohesion.asm;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.ibm.cohesion.data.Method;

public class MethodRecordingVisitor extends ClassVisitor {

    private final List<Method> methodsToAppendTo;
    private String className;

    public MethodRecordingVisitor(List<Method> methodsToAppendTo) {
        super(Opcodes.ASM5);
        this.methodsToAppendTo = methodsToAppendTo;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return new MethodUseVisitor(name, desc, methodsToAppendTo, className);
    }

}
