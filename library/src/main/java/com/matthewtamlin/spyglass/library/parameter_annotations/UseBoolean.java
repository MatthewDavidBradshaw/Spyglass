package com.matthewtamlin.spyglass.library.parameter_annotations;

import com.matthewtamlin.spyglass.library.meta_annotations.Use;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Use(UseBoolean.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface UseBoolean {
	boolean value();
}