package com.matthewtamlin.spyglass.processors.annotation_utils;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.CALL_HANDLER_ANNOTATIONS;

@Tested(testMethod = "automated")
public class CallHandlerAnnotationUtil {
	public static AnnotationMirror getCallHandlerAnnotationMirror(final Element element) {
		checkNotNull(element, "Argument \'element \' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : CALL_HANDLER_ANNOTATIONS) {
			final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasCallHandlerAnnotation(final Element element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> a : CALL_HANDLER_ANNOTATIONS) {
			if (element.getAnnotation(a) != null) {
				return true;
			}
		}

		return false;
	}

	private CallHandlerAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}