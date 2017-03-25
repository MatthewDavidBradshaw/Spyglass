package com.matthewtamlin.spyglass.library.handler_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Handles
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HandlesEnumConstant {
	int annotationId();

	Class<? extends Enum> enumClass();

	int ordinal();
}