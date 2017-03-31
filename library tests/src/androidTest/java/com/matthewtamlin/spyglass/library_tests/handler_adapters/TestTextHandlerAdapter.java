package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_adapters.TextHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.TextHandler;

import org.junit.Before;

import java.util.Random;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestTextHandlerAdapter
		extends TestHandlerAdapter<CharSequence, TextHandler, TextHandlerAdapter> {
	private CharSequence expectedValue;

	private TypedArray containingAttribute;

	private TypedArray missingAttribute;

	private TextHandler withMandatoryFlag;

	private TextHandler missingMandatoryFlag;

	private TextHandlerAdapter adapter;

	@Before
	public void setup() {
		final int attributeId = new Random().nextInt(Integer.MAX_VALUE);

		expectedValue = "something";

		containingAttribute = mock(TypedArray.class);
		when(containingAttribute.hasValue(attributeId)).thenReturn(true);
		when(containingAttribute.getText(eq(attributeId))).thenReturn(expectedValue);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(attributeId)).thenReturn(false);
		when(missingAttribute.getText(eq(attributeId))).thenReturn(null);

		withMandatoryFlag = mock(TextHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(TextHandler.class);
		when(missingMandatoryFlag.attributeId()).thenReturn(attributeId);
		when(missingMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new TextHandlerAdapter();
	}

	@Override
	public CharSequence getExpectedValue() {
		return expectedValue;
	}

	@Override
	public TypedArray getTypedArrayContainingAttribute() {
		return containingAttribute;
	}

	@Override
	public TypedArray getTypedArrayMissingAttribute() {
		return missingAttribute;
	}

	@Override
	public TextHandler getAnnotationWithMandatoryFlag() {
		return withMandatoryFlag;
	}

	@Override
	public TextHandler getAnnotationMissingMandatoryFlag() {
		return missingMandatoryFlag;
	}

	@Override
	public TextHandlerAdapter getAdapter() {
		return adapter;
	}
}