package com.matthewtamlin.spyglass.processor.annotation_utils;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorUtil;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processor.util.SetUtil.unmodifiableSetOf;

@Tested(testMethod = "automated")
public class CallHandlerAnnotationUtil {
	public static AnnotationMirror getCallHandlerAnnotationMirror(final ExecutableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : getCallHandlerAnnotationClasses()) {
			final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasCallHandlerAnnotation(final ExecutableElement element) {
		return getCallHandlerAnnotationMirror(element) != null;
	}

	public static Set<Class<? extends Annotation>> getCallHandlerAnnotationClasses() {
		return unmodifiableSetOf(
				SpecificEnumHandler.class,
				SpecificFlagHandler.class);
	}

	private CallHandlerAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}