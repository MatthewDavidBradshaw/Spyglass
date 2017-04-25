package com.matthewtamlin.spyglass.annotations.use_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.Use;
import com.matthewtamlin.spyglass.library.use_adapters.UseCharAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the value to supply to the annotated parameter when the Spyglass framework invokes the
 * method. This annotation should only be applied to parameters which satisfy all of the following
 * criteria:
 * <ul>
 * <li>The parameter is part of a method which has a handler annotation.</li>
 * <li>The parameter has no other use annotations.</li>
 * <li>The parameter accepts a char value.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@Use(adapterClass = UseCharAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface UseChar {
	/**
	 * @return the value to pass
	 */
	char value();
}