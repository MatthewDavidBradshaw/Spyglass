package com.matthewtamlin.spyglass.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindBoolean {
	int annotationId();

	boolean ignoreIfAttributeMissing() default false;

	boolean defaultValue() default false;
}