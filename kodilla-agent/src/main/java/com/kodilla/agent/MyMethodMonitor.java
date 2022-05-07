package com.kodilla.agent;

import net.bytebuddy.asm.Advice;

public class MyMethodMonitor {

    @Advice.OnMethodEnter(suppress = Throwable.class)
    public static void enter( @Advice.Origin("#m") String methodName) {
        System.out.println("Entering method: " + methodName);
    }

    @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
    public static void exit() {
        System.out.println("Exiting method.");
    }

}