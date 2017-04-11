package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.handler_adapters.EnumConstantHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.EnumOrdinalHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.EnumOrdinalHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestEnumOrdinalHandlerAdapter {
	private static final int ATTRIBUTE_ID = 2993;

	private int correctOrdinal = 3;

	private TypedArray containingAttributeWithCorrectOrdinal;

	private TypedArray containingAttributeWithWrongOrdinal;

	private TypedArray missingAttribute;

	private EnumOrdinalHandler withMandatoryFlag;

	private EnumOrdinalHandler missingMandatoryFlag;

	private EnumOrdinalHandlerAdapter adapter;

	@Before
	public void setup() {
		containingAttributeWithCorrectOrdinal = mock(TypedArray.class);
		when(containingAttributeWithCorrectOrdinal.hasValue(ATTRIBUTE_ID))
				.thenReturn(true);
		when(containingAttributeWithCorrectOrdinal.getInt(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(correctOrdinal);
		when(containingAttributeWithCorrectOrdinal.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(correctOrdinal);

		containingAttributeWithWrongOrdinal = mock(TypedArray.class);
		when(containingAttributeWithWrongOrdinal.hasValue(ATTRIBUTE_ID))
				.thenReturn(true);
		when(containingAttributeWithWrongOrdinal.getInt(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(correctOrdinal - 1);
		when(containingAttributeWithWrongOrdinal.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(correctOrdinal - 1);

		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getInt(eq(ATTRIBUTE_ID), anyInt())).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) throws Throwable {
				// Always return the second argument since it's the default
				return invocation.getArgumentAt(1, Integer.class);
			}
		});
		when(missingAttribute.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since it's the default
						return invocation.getArgumentAt(1, Integer.class);
					}
				});

		withMandatoryFlag = mock(EnumOrdinalHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(withMandatoryFlag.mandatory()).thenReturn(true);

		missingMandatoryFlag = mock(EnumOrdinalHandler.class);
		when(withMandatoryFlag.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(withMandatoryFlag.mandatory()).thenReturn(false);

		adapter = new EnumOrdinalHandlerAdapter();
	}

	@Test
	public void testReflectiveInstantiation() throws Exception {
		EnumConstantHandlerAdapter.class.newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_nullAnnotation() {
		adapter.getAccessor(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callValueExistsInArray_nullSupplied() {
		adapter.getAccessor(withMandatoryFlag).valueExistsInArray(null);
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueAvailableAndCorrectOrdinal() {
		final boolean result = adapter.getAccessor(withMandatoryFlag)
				.valueExistsInArray(containingAttributeWithCorrectOrdinal);

		assertThat(result, is(true));
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueAvailableAndIncorrectOrdinal() {
		final boolean result = adapter.getAccessor(withMandatoryFlag)
				.valueExistsInArray(containingAttributeWithWrongOrdinal);

		assertThat(result, is(false));
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueMissing() {
		final boolean result = adapter.getAccessor(withMandatoryFlag)
				.valueExistsInArray(missingAttribute);

		assertThat(result, is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_nullSupplied() {
		adapter.getAccessor(withMandatoryFlag).getValueFromArray(null);
	}

	@Test
	public void testGetAccessor_callGetValueFromArray_valueAvailableAndCorrectOrdinal() {
		final int value = adapter.getAccessor(withMandatoryFlag)
				.getValueFromArray(containingAttributeWithCorrectOrdinal);

		assertThat(value, is(correctOrdinal));
	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_valueAvailableAndIncorrectOrdinal() {
		adapter.getAccessor(withMandatoryFlag)
				.getValueFromArray(containingAttributeWithWrongOrdinal);
	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_valueMissing() {
		adapter.getAccessor(withMandatoryFlag).getValueFromArray(missingAttribute);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeId_nullSupplied() {
		adapter.getAttributeId(null);
	}

	@Test
	public void testGetAttributeId_nonNullSupplied() {
		final int attributeId = adapter.getAttributeId(withMandatoryFlag);

		assertThat(attributeId, is(ATTRIBUTE_ID));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsMandatory_nullAnnotation() {
		adapter.isMandatory(null);
	}

	@Test
	public void testIsMandatory_mandatoryFlagPresent() {
		final boolean mandatory = adapter.isMandatory(withMandatoryFlag);

		assertThat(mandatory, is(true));
	}

	@Test
	public void testIsMandatory_mandatoryFlagMissing() {
		final boolean mandatory = adapter.isMandatory(missingMandatoryFlag);

		assertThat(mandatory, is(false));
	}
}