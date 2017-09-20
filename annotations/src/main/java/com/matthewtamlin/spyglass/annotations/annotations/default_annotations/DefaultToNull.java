package com.matthewtamlin.spyglass.annotations.annotations.default_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a default for the annotated method, so that the Spyglass framework can invoke the method if its handler
 * annotation is not satisfied. This annotation should only be applied to methods which satisfy all of the following
 * criteria:
 * <ul>
 * <li>The method has a handler annotation.</li>
 * <li>The method has no other default annotations.</li>
 * <li>The method has at least one non-primitive parameter.</li>
 * <li>Except for one non-primitive parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface DefaultToNull {}