package com.matthewtamlin.spyglass.library.call_handler_adapters;

import android.content.res.TypedArray;

import java.lang.annotation.Annotation;

/**
 * Common interface for interacting with call handler annotations.
 *
 * @param <A>
 * 		the type of annotation this adapter interfaces with
 */
public interface CallHandlerAdapter<A extends Annotation> {
	/**
	 * Determines whether or not a method annotated with a call handler should be called.
	 *
	 * @param annotation
	 * 		the annotation to evaluate, not null
	 * @param attrs
	 * 		a set of attributes to base the evaluation on
	 *
	 * @return true if the method should be called, false otherwise
	 */
	boolean shouldCallMethod(A annotation, TypedArray attrs);
}