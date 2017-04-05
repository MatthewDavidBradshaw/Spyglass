package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.handler_annotations.DimensionHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.POSITIVE_INFINITY;

@Tested(testMethod = "automated")
public class DimensionHandlerAdapter implements HandlerAdapter<Float, DimensionHandler> {
	@Override
	public TypedArrayAccessor<Float> getAccessor(final DimensionHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return new TypedArrayAccessor<Float>() {
			@Override
			public boolean valueExistsInArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				// Compare two different results to see if the default is consistently returned
				float reading1 = array.getDimension(
						annotation.attributeId(),
						NEGATIVE_INFINITY);
				final float reading2 = array.getDimension(
						annotation.attributeId(),
						POSITIVE_INFINITY);

				return !((reading1 == NEGATIVE_INFINITY) && (reading2 == POSITIVE_INFINITY));
			}

			@Override
			public Float getValueFromArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				if (valueExistsInArray(array)) {
					return array.getDimension(annotation.attributeId(), 0f);
				} else {
					throw new RuntimeException("No attribute found for attribute ID " +
							annotation.attributeId());
				}
			}
		};
	}

	@Override
	public boolean attributeIsMandatory(final DimensionHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}