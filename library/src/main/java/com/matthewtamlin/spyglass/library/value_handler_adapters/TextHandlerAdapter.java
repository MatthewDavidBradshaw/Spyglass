package com.matthewtamlin.spyglass.library.value_handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.value_handler_annotations.TextHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class TextHandlerAdapter implements ValueHandlerAdapter<CharSequence, TextHandler> {
	@Override
	public TypedArrayAccessor<CharSequence> getAccessor(final TextHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return new TypedArrayAccessor<CharSequence>() {
			@Override
			public boolean valueExistsInArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				return array.getText(annotation.attributeId()) != null;
			}

			@Override
			public CharSequence getValueFromArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				if (valueExistsInArray(array)) {
					return array.getText(annotation.attributeId());
				} else {
					throw new RuntimeException("No attribute found for attribute ID " +
							annotation.attributeId());
				}
			}
		};
	}

	@Override
	public int getAttributeId(final TextHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.attributeId();
	}

	@Override
	public boolean isMandatory(final TextHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}