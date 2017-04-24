package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToTextArrayResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with DefaultToTextArrayResource annotations.
 */
@Tested(testMethod = "automated")
public class DefaultToTextArrayResourceAdapter
		implements DefaultAdapter<CharSequence[], DefaultToTextArrayResource> {

	@Override
	public CharSequence[] getDefault(final DefaultToTextArrayResource annotation,
			final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return context.getResources().getTextArray(annotation.value());
	}
}