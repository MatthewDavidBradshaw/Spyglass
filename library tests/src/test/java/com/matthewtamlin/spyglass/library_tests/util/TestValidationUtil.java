package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;
import com.matthewtamlin.spyglass.library.use_annotations.UseInt;
import com.matthewtamlin.spyglass.library.use_annotations.UseLong;
import com.matthewtamlin.spyglass.library.util.SpyglassValidationException;
import com.matthewtamlin.spyglass.library.util.ValidationUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.DP;
import static com.matthewtamlin.spyglass.library_tests.util.FieldHelper.getFieldWithTag;
import static com.matthewtamlin.spyglass.library_tests.util.MethodHelper.getMethodWithTag;

@RunWith(JUnit4.class)
public class TestValidationUtil {
	@Test
	public void testValidateField_noHandlerAnnotation() {
		ValidationUtil.validateField(getFieldWithTag(1, TestClass.class));
	}

	@Test
	public void testValidateField_oneHandlerAnnotation() {
		ValidationUtil.validateField(getFieldWithTag(2, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_twoHandlerAnnotations() {
		ValidationUtil.validateField(getFieldWithTag(3, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_threeHandlerAnnotations() {
		ValidationUtil.validateField(getFieldWithTag(4, TestClass.class));
	}

	@Test
	public void testValidateField_oneDefaultAnnotation() {
		ValidationUtil.validateField(getFieldWithTag(5, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_twoDefaultAnnotations() {
		ValidationUtil.validateField(getFieldWithTag(6, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_threeDefaultAnnotations() {
		ValidationUtil.validateField(getFieldWithTag(7, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_defaultAnnotationWithoutHandlerAnnotation() {
		ValidationUtil.validateField(getFieldWithTag(8, TestClass.class));
	}

	@Test
	public void testValidateMethod_noHandlerAnnotation() {
		ValidationUtil.validateMethod(getMethodWithTag(1, TestClass.class));
	}

	@Test
	public void testValidateMethod_oneHandlerAnnotation() {
		ValidationUtil.validateMethod(getMethodWithTag(2, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_twoHandlerAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(3, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_threeHandlerAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(4, TestClass.class));
	}

	@Test
	public void testValidateMethod_oneDefaultAnnotation() {
		ValidationUtil.validateMethod(getMethodWithTag(5, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_twoDefaultAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(6, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_threeDefaultAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(7, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_defaultAnnotationWithoutHandlerAnnotation() {
		ValidationUtil.validateMethod(getMethodWithTag(8, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_standardHandler_noArguments() {
		ValidationUtil.validateMethod(getMethodWithTag(9, TestClass.class));
	}

	@Test
	public void testValidateMethod_standardHandler_oneArgumentNoUseAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(10, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_standardHandler_oneArgumentOneUseAnnotation() {
		ValidationUtil.validateMethod(getMethodWithTag(11, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_standardHandler_threeArgumentsNoUseAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(12, TestClass.class));
	}

	@Test
	public void testValidateMethod_standardHandler_threeArgumentsTwoUseAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(13, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_standardHandler_threeArgumentsThreeUseAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(14, TestClass.class));
	}

	@Test
	public void testValidateMethod_enumConstantHandler_noArguments() {
		ValidationUtil.validateMethod(getMethodWithTag(15, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_enumConstantHandler_oneArgumentNoUseAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(16, TestClass.class));
	}

	@Test
	public void testValidateMethod_enumConstantHandler_oneArgumentOneUseAnnotation() {
		ValidationUtil.validateMethod(getMethodWithTag(17, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_enumConstantHandler_threeArgumentsNoUseAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(18, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_enumConstantHandler_threeArgumentsTwoUseAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(19, TestClass.class));
	}

	@Test
	public void testValidateMethod_enumConstantHandler_threeArgumentsThreeUseAnnotations() {
		ValidationUtil.validateMethod(getMethodWithTag(20, TestClass.class));
	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_multipleUseAnnotationsOnOneParameter() {
		
	}

	@SuppressWarnings("unused")
	public class TestClass {
		private Object field1;

		@BooleanHandler(attributeId = 2)
		private Object field2;

		@FloatHandler(attributeId = 3)
		@DimensionHandler(attributeId = 3)
		private Object field3;

		@StringHandler(attributeId = 4)
		@IntegerHandler(attributeId = 4)
		@FractionHandler(attributeId = 4)
		private Object field4;

		@BooleanHandler(attributeId = 5)
		@DefaultToBoolean(true)
		private Object field5;

		@DimensionHandler(attributeId = 6)
		@DefaultToInteger(6)
		@DefaultToDrawableResource(6)
		private Object field6;

		@StringHandler(attributeId = 7)
		@DefaultToEnumConstant(enumClass = TestEnum.class, ordinal = 0)
		@DefaultToBooleanResource(7)
		@DefaultToStringResource(7)
		private Object field7;

		@DefaultToColorStateListResource(1)
		private Object field8;

		private void method1() {}

		@BooleanHandler(attributeId = 2)
		private void method2(Object o) {}

		@StringHandler(attributeId = 3)
		@FloatHandler(attributeId = 3)
		private void method3(Object o) {}

		@FractionHandler(attributeId = 4)
		@DimensionHandler(attributeId = 4)
		@IntegerHandler(attributeId = 4)
		private void method4(Object o) {}

		@FractionHandler(attributeId = 5)
		@DefaultToFloat(5)
		private void method5(Object o) {}

		@BooleanHandler(attributeId = 6)
		@DefaultToColorResource(6)
		@DefaultToNull
		private void method6(Object o) {}

		@BooleanHandler(attributeId = 6)
		@DefaultToColorResource(6)
		@DefaultToDimensionResource(6)
		@DefaultToDimension(value = 10, unit = DP)
		private void method7(Object o) {}

		@DefaultToString("something")
		private void method8(Object o) {}

		@IntegerHandler(attributeId = 9)
		private void method9() {}

		@DimensionHandler(attributeId = 10)
		private void method10(Object o) {}

		@FractionHandler(attributeId = 11)
		private void method11(@UseBoolean(true) Object o) {}

		@StringHandler(attributeId = 12)
		private void method12(Object o1, Object o2, Object o3) {}

		@BooleanHandler(attributeId = 13)
		private void method13(@UseChar('A') Object o1, Object o2, @UseBoolean(true) Object o3) {}

		@EnumConstantHandler(attributeId = 14, enumClass = TestEnum.class, ordinal = 0)
		private void method14(
				@UseLong(0L) Object o1,
				@UseInt(1) Object o2,
				@UseByte(0) Object o3) {}

		@EnumConstantHandler(attributeId = 15, enumClass = TestEnum.class, ordinal = 0)
		private void method15() {}

		@EnumConstantHandler(attributeId = 16, enumClass = TestEnum.class, ordinal = 0)
		private void method16(Object o) {}

		@EnumConstantHandler(attributeId = 17, enumClass = TestEnum.class, ordinal = 0)
		private void method17(@UseBoolean(true) Object o) {}

		@EnumConstantHandler(attributeId = 18, enumClass = TestEnum.class, ordinal = 0)
		private void method18(Object o1, Object o2, Object o3) {}

		@EnumConstantHandler(attributeId = 19, enumClass = TestEnum.class, ordinal = 0)
		private void method19(@UseChar('A') Object o1, Object o2, @UseBoolean(true) Object o3) {}

		@EnumConstantHandler(attributeId = 20, enumClass = TestEnum.class, ordinal = 0)
		private void method20(
				@UseLong(0L) Object o1,
				@UseInt(1) Object o2,
				@UseByte(0) Object o3) {}
	}

	private enum TestEnum {
		CONST1
	}
}