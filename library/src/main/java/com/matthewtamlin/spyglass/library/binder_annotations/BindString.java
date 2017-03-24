package com.matthewtamlin.spyglass.library.binder_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindString {
	int annotationId();

	boolean ignoreIfAttributeMissing() default false;
}