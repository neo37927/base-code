package com.main.function.ssist;

import javassist.*;
import javassist.util.HotSwapper;

public class ReconstructTest {
    public static void main(String[] args) throws NotFoundException, CannotCompileException {
        Student student = new Student();
        student.execute();
        replaceMethodBody("com.main.function.ssist.Student", "execute", "System.out.println(\"this method is changed dynamically!\");");
        Student student1 = new Student();
        student1.execute();
    }

    public static void replaceMethodBody(String clazzName, String methodName, String newMethodBody) {
        try {
            CtClass clazz = ClassPool.getDefault().get(clazzName);
            CtMethod method = clazz.getDeclaredMethod(methodName);
            method.setBody(newMethodBody);
            clazz.toClass();
        } catch (NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }
}
