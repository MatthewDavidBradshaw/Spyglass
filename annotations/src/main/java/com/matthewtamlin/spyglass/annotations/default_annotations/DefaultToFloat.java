package com.matthewtamlin.spyglass.annotations.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFloatAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a default for the annotated method, so that the Spyglass framework can invoke the
 * method if its handler annotation is not satisfied. This annotation should only be applied to
 * methods which satisfy all of the following criteria:
 * <ul>
 * <li>The method has a handler annotation.</li>
 * <li>The method has no other default annotations.</li>
 * <li>The method has at least one float parameter.</li>
 * <li>Except for one float parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToFloatAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultToFloat {
	/**
	 * @return the default value
	 */
	float value();
}