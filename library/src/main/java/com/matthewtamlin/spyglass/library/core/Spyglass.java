package com.matthewtamlin.spyglass.library.core;

import android.content.res.TypedArray;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.HandlerAdapter;
import com.matthewtamlin.spyglass.library.util.AnnotationUtil;
import com.matthewtamlin.spyglass.library.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.library.util.AdapterUtil.getDefaultAdapter;
import static com.matthewtamlin.spyglass.library.util.AdapterUtil.getHandlerAdapter;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getDefaultAnnotation;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getHandlerAnnotation;
import static com.matthewtamlin.spyglass.library.util.ValidationUtil.validateField;
import static com.matthewtamlin.spyglass.library.util.ValidationUtil.validateMethod;

public class Spyglass {
	private View view;

	private TypedArray attrSource;

	private Spyglass(final Builder builder) {
		this.view = builder.view;

		this.attrSource = view.getContext().obtainStyledAttributes(
				builder.attributeSet,
				builder.styleableRes,
				builder.defStyleAttr,
				builder.defStyleRes);
	}

	public void applyAttributesTo(final View view) {
		checkNotNull(view, "Argument \'view\' cannot be null.");
		checkMainThread();

		for (final Field f : view.getClass().getDeclaredFields()) {
			validateField(f);
			processField(f);
		}

		for (final Method m : view.getClass().getDeclaredMethods()) {
			validateMethod(m);
			processMethod(m);
		}
	}

	private void checkMainThread() {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new IllegalThreadException("Spyglasses must only be touched by the UI thread.");
		}
	}

	private void processField(final Field field) {
		field.setAccessible(true);

		AnnotationUtil.validateAnnotations(field);

		final Annotation handlerAnnotation = getHandlerAnnotation(field);

		if (handlerAnnotation != null) {
			final HandlerAdapter<?, Annotation> handlerAdapter = getHandlerAdapter(field);

			if (handlerAdapter.attributeValueIsAvailable(attrSource, handlerAnnotation)) {
				final Object value = handlerAdapter.getAttributeValue(
						attrSource,
						handlerAnnotation);

				// Assign value to field

			} else {
				final Annotation defaultAnnotation = getDefaultAnnotation(field);

				if (defaultAnnotation != null) {
					final DefaultAdapter<?, Annotation> defaultAdapter = getDefaultAdapter(field);

					final Object defaultValue = defaultAdapter.getDefault(
							defaultAnnotation,
							view.getContext());

					// Assign value to field

				} else if (handlerAdapter.attributeIsMandatory(handlerAnnotation)) {
					// throw exception
				}
			}
		}
	}

	private void processMethod(final Method method) {
		method.setAccessible(true);

		AnnotationUtil.validateAnnotations(method);

		final Annotation handlerAnnotation = getHandlerAnnotation(method);

		if (handlerAnnotation != null) {
			final HandlerAdapter<?, Annotation> handlerAdapter = getHandlerAdapter(method);

			if (handlerAdapter.attributeValueIsAvailable(attrSource, handlerAnnotation)) {
				final Object value = handlerAdapter.getAttributeValue(
						attrSource,
						handlerAnnotation);

				// call method

			} else {
				final Annotation defaultAnnotation = getDefaultAnnotation(method);

				if (defaultAnnotation != null) {
					final DefaultAdapter<?, Annotation> defaultAdapter = getDefaultAdapter(method);

					final Object defaultValue = defaultAdapter.getDefault(
							defaultAnnotation,
							view.getContext());

					// call method

				} else if (handlerAdapter.attributeIsMandatory(handlerAnnotation)) {
					// throw exception
				}
			}
		}
	}

	public static Builder builder(final View view) {
		return new Builder(view);
	}

	public static class Builder {
		private View view;

		private int styleableRes[];

		private AttributeSet attributeSet;

		private int defStyleAttr;

		private int defStyleRes;

		private Builder(final View view) {
			this.view = checkNotNull(view, "Argument 'view' cannot be null.");
		}

		public void withStyleableResource(final int[] styleableRes) {
			this.styleableRes = styleableRes;
		}

		public void withAttributeSet(final AttributeSet attributeSet) {
			this.attributeSet = attributeSet;
		}

		public void withDefStyleAttr(final int defStyleAttr) {
			this.defStyleAttr = defStyleAttr;
		}

		public void withDefStyleRes(final int defStyleRes) {
			this.defStyleRes = defStyleRes;
		}

		public Spyglass build() {
			checkNotNull(styleableRes, new InvalidBuilderStateException("Unable to build " +
					"Spyglass without a styleable resource. Call method withStyleableRes(int[]) " +
					"before calling build()"));

			return new Spyglass(this);
		}
	}
}