package com.matthewtamlin.spyglass.library.util;

import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ValidationUtil {
	private static Set<FieldRule> fieldRules = new HashSet<>();

	private static Set<MethodRule> methodRules = new HashSet<>();

	static {
		createFieldRules();
		createMethodRules();
	}

	public static void validateAnnotations(final Field field) throws SpyglassValidationException {
		for (final FieldRule rule : fieldRules) {
			rule.checkFieldComplies(field);
		}
	}

	public static void validateAnnotations(final Method method) throws SpyglassValidationException {
		for (final MethodRule rule : methodRules) {
			rule.checkMethodComplies(method);
		}
	}

	private static void createFieldRules() {
		fieldRules.add(new FieldRule() {
			@Override
			public void checkFieldComplies(final Field field) {
				final Annotation[] annotations = field.getDeclaredAnnotations();
				final int handlerAnnotationCount = countAnnotations(annotations, Handler.class);

				if (handlerAnnotationCount > 1) {
					throw new SpyglassValidationException("Field " + field + " has multiple " +
							"handler annotations.");
				}
			}
		});

		fieldRules.add(new FieldRule() {
			@Override
			public void checkFieldComplies(final Field field) {
				final Annotation[] annotations = field.getDeclaredAnnotations();
				final int defaultAnnotationCount = countAnnotations(annotations, Default.class);

				if (defaultAnnotationCount > 1) {
					throw new SpyglassValidationException("Field " + field + " has multiple " +
							"default annotations.");
				}
			}
		});

		fieldRules.add(new FieldRule() {
			@Override
			public void checkFieldComplies(final Field field) {
				final Annotation[] annotations = field.getDeclaredAnnotations();
				final int handlerAnnotationCount = countAnnotations(annotations, Handler.class);
				final int defaultAnnotationCount = countAnnotations(annotations, Default.class);

				if (handlerAnnotationCount == 0 && defaultAnnotationCount > 0) {
					throw new SpyglassValidationException("Field " + field + " has a default " +
							"annotation but no handler annotation.");
				}
			}
		});
	}

	private static void createMethodRules() {
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final Annotation[] annotations = method.getDeclaredAnnotations();
				final int handlerAnnotationCount = countAnnotations(annotations, Handler.class);

				if (handlerAnnotationCount > 1) {
					throw new SpyglassValidationException("Method " + method + " has multiple " +
							"handler annotations.");
				}
			}
		});

		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final Annotation[] annotations = method.getDeclaredAnnotations();
				final int defaultAnnotationCount = countAnnotations(annotations, Default.class);

				if (defaultAnnotationCount > 1) {
					throw new SpyglassValidationException("Method " + method + " has multiple " +
							"default annotations.");
				}
			}
		});

		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final Annotation[] annotations = method.getDeclaredAnnotations();
				final int handlerAnnotationCount = countAnnotations(annotations, Handler.class);
				final int defaultAnnotationCount = countAnnotations(annotations, Default.class);

				if (handlerAnnotationCount == 0 && defaultAnnotationCount > 0) {
					throw new SpyglassValidationException("Method " + method + " has a default " +
							"annotation but no handler annotation.");
				}
			}
		});
	}

	private static int countAnnotations(
			final Annotation[] annotations,
			final Class<? extends Annotation> metaAnnotation) {

		int count = 0;

		for (final Annotation a : annotations) {
			if (a.annotationType().isAnnotationPresent(metaAnnotation)) {
				count++;
			}
		}

		return count;
	}

	private interface FieldRule {
		public void checkFieldComplies(Field field);
	}

	private interface MethodRule {
		public void checkMethodComplies(Method method);
	}
}