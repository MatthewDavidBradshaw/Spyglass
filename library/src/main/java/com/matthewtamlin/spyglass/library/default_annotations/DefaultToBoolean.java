package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.default_processors.DefaultToBooleanProcessor;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Default(processorClass = DefaultToBooleanProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefaultToBoolean {
	boolean value();
}