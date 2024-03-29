package com.kodilla.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Default;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.none;

public class Agent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Kodilla agent is running.");
        System.out.println(
                "Is redefine classes allowed: " + instrumentation.isRedefineClassesSupported());
        System.out.println(
                "Is retransform classes allowed: " + instrumentation.isRetransformClassesSupported());

        AgentBuilder agentBuilder;
        agentBuilder = new Default()
                .with(AgentBuilder.TypeStrategy.Default.DECORATE)
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.nameStartsWith("com.kodilla"))
                .transform((builder, typeDescription, classLoader, module) -> {
                    System.out.println("Class " + typeDescription);
                    return builder.visit(Advice.to(MyMethodMonitor.class).on(ElementMatchers.isMethod()));
                });

        agentBuilder.installOn(instrumentation);
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        premain(args, instrumentation);
    }

}