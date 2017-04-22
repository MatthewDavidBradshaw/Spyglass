package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a default value to the Spyglass framework. This annotation should only be applied to
 * methods which accept booleans and have handler annotations, and it should not be used in
 * conjunction with other defaults.
 */
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToBooleanAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultToBoolean {
	/**
	 * @return the default value
	 */
	boolean value();
}