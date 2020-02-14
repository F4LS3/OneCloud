package de.f4ls3.netty.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    String name() default "NewCommand";
    String[] aliases() default {};
    String description() default "A new Command";
    String syntax() default "How To Use This Command";

}
