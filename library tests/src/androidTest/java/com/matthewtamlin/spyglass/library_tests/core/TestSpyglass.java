package com.matthewtamlin.spyglass.library_tests.core;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.core.IllegalThreadException;
import com.matthewtamlin.spyglass.library.core.InvalidBuilderStateException;
import com.matthewtamlin.spyglass.library.core.MandatoryAttributeMissingException;
import com.matthewtamlin.spyglass.library.core.SpyglassFieldBindException;
import com.matthewtamlin.spyglass.library.core.SpyglassMethodCallException;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestSpyglass {
	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_noViewEverSupplied() {

	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_nullViewSupplied() {

	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_noContextEverSupplied() {

	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_nullContextSupplied() {

	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_noStyleableResourceEverSupplied() {

	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_nullStyleableResourceSupplied() {

	}

	@Test
	public void testInstantiationViaBuilder_noAttributeSetEverSupplied() {

	}

	@Test
	public void testInstantiationViaBuilder_noDefStyleAttrSetEverSupplied() {

	}

	@Test
	public void testInstantiationViaBuilder_noDefStyleResEverSupplied() {

	}

	@Test(expected = IllegalThreadException.class)
	public void testBindDataToFields_calledOnNonUiThread() {

	}

	@Test
	public void testBindDataToFields_noHandlerAnnotations() {

	}

	@Test
	public void testBindDataToFields_attrSupplied() {

	}

	@Test
	public void testBindDataToFields_attrMissing_noDefault_notMandatory() {

	}

	@Test(expected = MandatoryAttributeMissingException.class)
	public void testBindDataToFields_attrMissing_noDefault_isMandatory() {

	}

	@Test
	public void testBindDataToFields_attrMissing_hasDefault_notMandatory() {

	}

	@Test
	public void testBindDataToFields_attrMissing_hasDefault_isMandatory() {

	}

	@Test(expected = SpyglassFieldBindException.class)
	public void testBindDataToFields_dataTypeMismatch() {

	}

	@Test
	public void testPassDataToMethods_noHandlerAnnotations() {

	}

	@Test
	public void testPassDataToMethods_attrSupplied() {

	}

	@Test
	public void testPassDataToMethods_attrMissing_noDefault_notMandatory() {

	}

	@Test(expected = MandatoryAttributeMissingException.class)
	public void testPassDataToMethods_attrMissing_noDefault_isMandatory() {

	}

	@Test
	public void testPassDataToMethods_attrMissing_hasDefault_notMandatory() {

	}

	@Test
	public void testPassDataToMethods_attrMissing_hasDefault_isMandatory() {

	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_dataTypeMismatch() {

	}
}