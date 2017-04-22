package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanResourceAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a default value resource to the Spyglass framework. This annotation should only be
 * applied to methods which accept a boolean and have a handler annotation, and it should not be
 * used in conjunction with other defaults.
 */
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToBooleanResourceAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultToBooleanResource {
	/**
	 * @return the resource ID of the default value, must resolve to a boolean resource
	 */
	int value();
}