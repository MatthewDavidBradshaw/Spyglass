package com.matthewtamlin.spyglass.processor.annotation_utils;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorUtil;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processor.util.SetUtil.unmodifiableSetOf;

@Tested(testMethod = "automated")
public class UseAnnotationUtil {
	public static AnnotationMirror getUseAnnotationMirror(final VariableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : getUseAnnotationClasses()) {
			final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasUseAnnotation(final VariableElement element) {
		return getUseAnnotationMirror(element) != null;
	}

	public static Set<Class<? extends Annotation>> getUseAnnotationClasses() {
		return unmodifiableSetOf(
				UseBoolean.class,
				UseByte.class,
				UseChar.class,
				UseDouble.class,
				UseFloat.class,
				UseInt.class,
				UseLong.class,
				UseNull.class,
				UseShort.class,
				UseString.class);
	}

	private UseAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}