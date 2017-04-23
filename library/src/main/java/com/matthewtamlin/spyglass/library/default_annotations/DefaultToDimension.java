package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.units.DimensionUnit;

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
 * <li>The method has at least one integer parameter.</li>
 * <li>One integer parameter has no use annotation.</li>
 * <li>Every other parameter has a use annotation.</li>
 * </ul>
 * <p>
 * The dimension value is converted to units of pixels before being passed to the method.
 */
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToDimensionAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultToDimension {
	/**
	 * @return the numerical value of the default dimension, measured in the units passed to {@code
	 * unit()}
	 */
	float value();

	/**
	 * @return the units of the value passed to {@code value()}
	 */
	DimensionUnit unit();
}