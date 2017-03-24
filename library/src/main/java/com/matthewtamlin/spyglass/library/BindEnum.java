package com.matthewtamlin.spyglass.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindEnum {
	int annotationId();

	Class<? extends Enum> enumClass();

	boolean ignoreIfAttributeMissing() default false;

	int defaultOrdinal() default 0;
}