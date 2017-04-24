package com.matthewtamlin.spyglass.library.value_handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FloatHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.POSITIVE_INFINITY;

/**
 * Adapter for interfacing with FloatHandler annotations.
 */
@Tested(testMethod = "automated")
public class FloatHandlerAdapter implements ValueHandlerAdapter<Float, FloatHandler> {
	@Override
	public TypedArrayAccessor<Float> getAccessor(final FloatHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return new TypedArrayAccessor<Float>() {
			@Override
			public boolean valueExistsInArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				// Compare two different results to see if the default is consistently returned
				final float reading1 = array.getFloat(annotation.attributeId(), NEGATIVE_INFINITY);
				final float reading2 = array.getFloat(annotation.attributeId(), POSITIVE_INFINITY);

				return !((reading1 == NEGATIVE_INFINITY) && (reading2 == POSITIVE_INFINITY));
			}

			@Override
			public Float getValueFromArray(final TypedArray array) {
				if (valueExistsInArray(array)) {
					return array.getFloat(annotation.attributeId(), 0);
				} else {
					throw new RuntimeException("No attribute found for attribute ID " +
							annotation.attributeId());
				}
			}
		};
	}
}