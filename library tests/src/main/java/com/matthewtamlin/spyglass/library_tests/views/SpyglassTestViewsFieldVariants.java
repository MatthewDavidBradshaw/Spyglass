package com.matthewtamlin.spyglass.library_tests.views;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;

import static com.matthewtamlin.spyglass.library_tests.R.styleable.SpyglassTestView_string_attr;

public class SpyglassTestViewsFieldVariants {
	public static final String INITIAL_STRING = "initial string";

	public static final String DEFAULT_STRING = "default string";

	public static class NoAnnotations extends SpyglassTestView {
		public String spyglassField = INITIAL_STRING;

		public NoAnnotations(final Context context) {
			super(context);
		}
	}

	public static class OptionalStringHandlerNoDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public String spyglassField = INITIAL_STRING;

		public OptionalStringHandlerNoDefault(final Context context) {
			super(context);
		}
	}

	public static class OptionalStringHandlerWithDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		@DefaultToString(DEFAULT_STRING)
		public String spyglassField = INITIAL_STRING;

		public OptionalStringHandlerWithDefault(final Context context) {
			super(context);
		}
	}

	public static class MandatoryStringHandlerNoDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public String spyglassField = INITIAL_STRING;

		public MandatoryStringHandlerNoDefault(final Context context) {
			super(context);
		}
	}

	public static class MandatoryStringHandlerWithDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		@DefaultToString(DEFAULT_STRING)
		public String spyglassField = INITIAL_STRING;

		public MandatoryStringHandlerWithDefault(final Context context) {
			super(context);
		}
	}

	public static class HandlerTypeMismatch extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public boolean spyglassField = false;

		public HandlerTypeMismatch(final Context context) {
			super(context);
		}
	}

	public static class DefaultTypeMismatch extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		@DefaultToBoolean(false)
		public String spyglassField = INITIAL_STRING;

		public DefaultTypeMismatch(final Context context) {
			super(context);
		}
	}
}