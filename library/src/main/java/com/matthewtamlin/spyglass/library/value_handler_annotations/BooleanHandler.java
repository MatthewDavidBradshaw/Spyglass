package com.matthewtamlin.spyglass.library.value_handler_annotations;


import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;
import com.matthewtamlin.spyglass.library.value_handler_adapters.BooleanHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tags methods and fields which can be used by Spyglasses to handle boolean attributes.
 */
@ValueHandler(adapterClass = BooleanHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BooleanHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();

	/**
	 * @return true if the method/field must be used, false otherwise
	 */
	boolean mandatory() default false;
}