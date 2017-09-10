package com.matthewtamlin.spyglass.common.annotations.call_handler_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling a specific enum attribute. The method will only
 * be invoked if the Spyglass framework finds the specified ordinal mapped to the specified
 * attribute. This annotation should only be applied to methods which satisfy all of the
 * following criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has no default annotation.</li>
 * <li>Every parameter has a use annotation.</li>
 * </ul>
 * Is it valid for a method with this annotation to have no parameters.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface SpecificEnumHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();

	/**
	 * @return the specific ordinal handled by the method
	 */
	int ordinal();
}