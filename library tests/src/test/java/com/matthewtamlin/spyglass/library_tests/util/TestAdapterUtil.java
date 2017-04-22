package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.call_handler_adapters.CallHandlerAdapter;
import com.matthewtamlin.spyglass.library.call_handler_adapters.FlagHandlerAdapter;
import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseBooleanAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseCharAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseFloatAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseLongAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseNullAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseStringAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;
import com.matthewtamlin.spyglass.library.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.library.use_annotations.UseLong;
import com.matthewtamlin.spyglass.library.use_annotations.UseNull;
import com.matthewtamlin.spyglass.library.use_annotations.UseString;
import com.matthewtamlin.spyglass.library.util.AdapterUtil;
import com.matthewtamlin.spyglass.library.value_handler_adapters.IntegerHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.library_tests.util.MethodHelper.MethodTag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.matthewtamlin.spyglass.library.units.DimensionUnit.DP;
import static com.matthewtamlin.spyglass.library.util.AdapterUtil.getUseAdapters;
import static com.matthewtamlin.spyglass.library_tests.util.MethodHelper.getMethodWithTag;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class TestAdapterUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetValueHandlerAdapter_nullMethod() {
		AdapterUtil.getValueHandlerAdapter(null);
	}

	@Test
	public void testGetValueHandlerAdapter_noHandlerAnnotations() {
		final ValueHandlerAdapter adapter = AdapterUtil.getValueHandlerAdapter(getMethodWithTag(1,
				TestClass.class));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetValueHandlerAdapter_oneHandlerAnnotation() {
		final ValueHandlerAdapter adapter = AdapterUtil.getValueHandlerAdapter(getMethodWithTag(2,
				TestClass.class));

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(IntegerHandlerAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_nullMethod() {
		AdapterUtil.getDefaultAdapter(null);
	}

	@Test
	public void testGetDefaultAdapter_noDefaultAnnotations() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getMethodWithTag(3,
				TestClass.class));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetValueHandlerAdapter_oneDefaultAnnotation() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getMethodWithTag(4,
				TestClass.class));

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(DefaultToDimensionAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAdapters_nullMethod() {
		getUseAdapters(null);
	}

	@Test
	public void testGetUseAdapters_noArguments() {
		final Map<Integer, UseAdapter<?, Annotation>> adapters = getUseAdapters(getMethodWithTag(5,
				TestClass.class));

		assertThat(adapters.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAdapters_oneArgument_noUseAnnotations() {
		final Map<Integer, UseAdapter<?, Annotation>> adapters = getUseAdapters(getMethodWithTag(6,
				TestClass.class));

		assertThat(adapters.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAdapters_oneArgument_oneUseAnnotation() {
		final Map<Integer, UseAdapter<?, Annotation>> adapters = getUseAdapters(getMethodWithTag(7,
				TestClass.class));

		assertThat(adapters.size(), is(1));

		assertThat(adapters.keySet().contains(0), is(true));
		assertThat(adapters.get(0), is(notNullValue()));
		assertThat(adapters.get(0), instanceOf(UseNullAdapter.class));
	}

	@Test
	public void testGetUseAdapters_threeArguments_noUseAnnotations() {
		final Map<Integer, UseAdapter<?, Annotation>> adapters = getUseAdapters(getMethodWithTag(8,
				TestClass.class));

		assertThat(adapters.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAdapters_threeArguments_twoUseAnnotations() {
		final Map<Integer, UseAdapter<?, Annotation>> adapters = getUseAdapters(getMethodWithTag(9,
				TestClass.class));

		assertThat(adapters.size(), is(2));

		assertThat(adapters.keySet().contains(0), is(true));
		assertThat(adapters.get(0), is(notNullValue()));
		assertThat(adapters.get(0), instanceOf(UseBooleanAdapter.class));

		assertThat(adapters.keySet().contains(1), is(false));

		assertThat(adapters.keySet().contains(2), is(true));
		assertThat(adapters.get(2), is(notNullValue()));
		assertThat(adapters.get(2), instanceOf(UseFloatAdapter.class));
	}

	@Test
	public void testGetUseAdapters_threeArguments_threeUseAnnotations() {
		final Map<Integer, UseAdapter<?, Annotation>> adapters = getUseAdapters(getMethodWithTag(10,
				TestClass.class));

		assertThat(adapters.size(), is(3));

		assertThat(adapters.keySet().contains(0), is(true));
		assertThat(adapters.get(0), is(notNullValue()));
		assertThat(adapters.get(0), instanceOf(UseLongAdapter.class));

		assertThat(adapters.keySet().contains(1), is(true));
		assertThat(adapters.get(1), is(notNullValue()));
		assertThat(adapters.get(1), instanceOf(UseCharAdapter.class));

		assertThat(adapters.keySet().contains(2), is(true));
		assertThat(adapters.get(2), is(notNullValue()));
		assertThat(adapters.get(2), instanceOf(UseStringAdapter.class));
	}

	@Test
	public void testGetCallHandlerAdapter_noHandlerAnnotation() {
		final CallHandlerAdapter adapter = AdapterUtil.getCallHandlerAdapter(getMethodWithTag(11,
				TestClass.class));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetCallHandlerAdapter_oneHandlerAnnotation() {
		final CallHandlerAdapter adapter = AdapterUtil.getCallHandlerAdapter(getMethodWithTag(12,
				TestClass.class));

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(FlagHandlerAdapter.class));
	}

	@SuppressWarnings("unused")
	private static class TestClass {
		@MethodTag(1)
		private void method1() {}

		@MethodTag(2)
		@IntegerHandler(attributeId = 2)
		private Object method2(int i) {return null;}

		@MethodTag(3)
		private void method3() {}

		@MethodTag(4)
		@DefaultToDimension(value = 10, unit = DP)
		private Object method4(int i) {return null;}

		@MethodTag(5)
		private void method5() {}

		@MethodTag(6)
		private void method6(int i) {}

		@MethodTag(7)
		private void method7(@UseNull Object o) {}

		@MethodTag(8)
		private void method8(String s, boolean b, float f) {}

		@MethodTag(9)
		private void method9(@UseBoolean(false) boolean b, char c, @UseFloat(0F) Float f) {}

		@MethodTag(10)
		private void method10(@UseLong(0L) long l, @UseChar(0) char c, @UseString("s") String s) {}

		@MethodTag(11)
		private void method11() {}

		@MethodTag(12)
		@SpecificFlagHandler(attributeId = 12, handledFlags = 1)
		private void method12() {}
	}
}