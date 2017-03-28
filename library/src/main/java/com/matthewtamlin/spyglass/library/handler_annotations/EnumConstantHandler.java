package com.matthewtamlin.spyglass.library.handler_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Handler(EnumConstantHandler.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EnumConstantHandler {
	int attributeId();

	Class<? extends Enum> enumClass();

	int ordinal();

	boolean mandatory() default false;
}