package com.matthewtamlin.spyglass.library.value_handler_annotations;


import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;
import com.matthewtamlin.spyglass.library.value_handler_adapters.BooleanHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a method/field capable of receiving a boolean from the Spyglass framework, and defines
 * the handled view attribute.
 */
@Tested(testMethod = "automated")
@ValueHandler(adapterClass = BooleanHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BooleanHandler {
	/**
	 * @return the resource ID of the handled view attribute
	 */
	int attributeId();
}