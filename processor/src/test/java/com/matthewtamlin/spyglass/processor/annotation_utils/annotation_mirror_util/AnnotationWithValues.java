package com.matthewtamlin.spyglass.processor.annotation_utils.annotation_mirror_util;

public @interface AnnotationWithValues {
	public static final String DEFAULT_VALUE = "default value";

	String value() default DEFAULT_VALUE;
}