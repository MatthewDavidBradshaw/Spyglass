package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToTextResource;
import com.matthewtamlin.spyglass.annotations.units.DimensionUnit;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.TextArrayHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.TextHandler;
import com.matthewtamlin.spyglass.processors.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processors.util.EnumUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.annotation_utils.AnnotationMirrorUtil.getAnnotationValueWithDefaults;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

public class CallerComponentGenerator {
	public static final String SHOULD_CALL_METHOD_METHOD_NAME = "shouldCallMethod";

	public static final String VALUE_IS_AVAILABLE_METHOD_NAME = "valueIsAvailable";

	public static final String GET_VALUE_METHOD_NAME = "getValue";

	public static final String GET_DEFAULT_VALUE_METHOD_NAME = "getDefaultValue";

	private static final TypeName ENUM_UTIL_TYPE_NAME = ClassName.get(EnumUtil.class);

	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>>
			shouldCallMethodMethodBodySuppliers;

	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>>
			valueIsAvailableBodySuppliers;

	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>>
			getValueMethodBodySuppliers;

	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>>
			getDefaultValueMethodBodySuppliers;

	private final Elements elementsUtil;

	{
		shouldCallMethodMethodBodySuppliers = new HashMap<>();

		shouldCallMethodMethodBodySuppliers.put(
				SpecificEnumHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", getAttributeId(object))
								.addStatement("final int value2 = attrs.getInt($L, 2)", getAttributeId(object))
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"(value1 == 1) && (value2 == 2)")
								.add("\n")
								.beginControlFlow("if (defaultConsistentlyReturned)")
								.addStatement("return false")
								.nextControlFlow("else")
								.addStatement("return value1 == $L", getValueLiteral(object, "ordinal"))
								.endControlFlow()
								.build();
					}
				}
		);

		shouldCallMethodMethodBodySuppliers.put(
				SpecificFlagHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", getAttributeId(object))
								.addStatement("final int value2 = attrs.getInt($L, 2)", getAttributeId(object))
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.add("\n")
								.beginControlFlow("if (defaultConsistentlyReturned)")
								.addStatement("return false")
								.nextControlFlow("else")
								.addStatement("return (value1 & $L) > 0", getValueLiteral(object, "handledFlags"))
								.endControlFlow()
								.build();
					}
				}
		);
	}

	{
		valueIsAvailableBodySuppliers = new HashMap<>();

		valueIsAvailableBodySuppliers.put(
				BooleanHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final boolean value1 = attrs.getBoolean($L, false)",
										getAttributeId(object))
								.addStatement("final boolean value2 = attrs.getBoolean($L, true)",
										getAttributeId(object))
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == false && value2 == true")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				ColorHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getColor($L, 1)", getAttributeId(object))
								.addStatement("final int value2 = attrs.getColor($L, 2)", getAttributeId(object))
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				ColorStateListHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getColorStateList($L) != null", getAttributeId(object))
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final float value1 = attrs.getDimension($L, Float.NEGATIVE_INFINITY)",
										getAttributeId(object))
								.addStatement("final float value2 = attrs.getDimension($L, Float.POSITIVE_INFINITY)",
										getAttributeId(object))
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == Float.NEGATIVE_INFINITY && value2 == Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				DrawableHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getDrawable($L) != null", getAttributeId(object))
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final int value1 = array.getInt($L, 1)", getAttributeId(object))
								.addStatement("final int value2 = array.getInt($L, 2)", getAttributeId(object))
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				EnumOrdinalHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final int value1 = array.getInt($L, 1)", getAttributeId(object))
								.addStatement("final int value2 = array.getInt($L, 2)", getAttributeId(object))
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				FloatHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final float value1 = attrs.getFloat($L, Float.NEGATIVE_INFINITY)",
										getAttributeId(object))
								.addStatement("final float value2 = attrs.getFloat($L, Float.POSITIVE_INFINITY)",
										getAttributeId(object))
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == Float.NEGATIVE_INFINITY && value2 == Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				FractionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final float value1 = attrs.getFraction(" +
										"$L, 1, 1, Float.NEGATIVE_INFINITY)", getAttributeId(object))
								.addStatement("final float value2 = attrs.getFraction(" +
										"$L, 1, 1, Float.POSITIVE_INFINITY)", getAttributeId(object))
								.addStatement("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == Float.NEGATIVE_INFINITY && value2 == Float.POSITIVE_INFINITY")
								.addStatement("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				IntegerHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", getAttributeId(object))
								.addStatement("final int value2 = attrs.getInt($L, 2)", getAttributeId(object))
								.addStatement("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.addStatement("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				StringHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.hasValue($L)", getAttributeId(object))
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				TextArrayHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getTextArray($L) != null", getAttributeId(object))
								.build();
					}
				}
		);

		valueIsAvailableBodySuppliers.put(
				TextHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getText($L) != null", getAttributeId(object))
								.build();
					}
				}
		);
	}

	{
		getValueMethodBodySuppliers = new HashMap<>();

		getValueMethodBodySuppliers.put(
				BooleanHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getBoolean($L, false)", getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				ColorHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getColor($L, 1)", getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				ColorStateListHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getColorStateList($L)", getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getDimension($L, Float.NEGATIVE_INFINITY)",
										getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				DrawableHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getDrawable($L)", getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final String enumClass = getValueLiteral(object, "enumClass");

						return CodeBlock
								.builder()
								.addStatement("final int ordinal = attrs.getInt($L, 0)", getAttributeId(object))
								.addStatement("return $T.getEnumConstant($L, ordinal)", ENUM_UTIL_TYPE_NAME, enumClass)
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				EnumOrdinalHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return array.getInt($L, 1)", getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				FloatHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getFloat($L, Float.NEGATIVE_INFINITY)",
										getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				FractionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getFraction($L, $L, $L, Float.NEGATIVE_INFINITY)",
										getAttributeId(object),
										getValueLiteral(object, "baseMultiplier"),
										getValueLiteral(object, "parentMultiplier"))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				IntegerHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getInt($L, 1)", getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				StringHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getString($L)", getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				TextArrayHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getTextArray($L)", getAttributeId(object))
								.build();
					}
				}
		);

		getValueMethodBodySuppliers.put(
				TextHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return attrs.getText($L)", getAttributeId(object))
								.build();
					}
				}
		);
	}

	{
		getDefaultValueMethodBodySuppliers = new HashMap<>();

		getDefaultValueMethodBodySuppliers.put(
				DefaultToBoolean.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $L",
										getValueLiteral(object, "value"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToBooleanResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"return context.getResources().getBoolean($L)",
										getValueLiteral(object, "resId"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToColorResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $T.getColor(context, $L)",
										getValueLiteral(object, "resId"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToColorStateListResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $T.getColorStateList(context, $L)",
										getValueLiteral(object, "resId"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToDimension.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						try {
							final String rawDimensionValue = getValueLiteral(object, "value");

							final String unitLiteral = getValueLiteral(object, "unit");
							final DimensionUnit unit = (DimensionUnit) EnumUtil.getEnumConstant(unitLiteral);

							return CodeBlock
									.builder()
									.addStatement(
											"$T metrics = context.getResources().getDisplayMetrics()",
											AndroidClassNames.DISPLAY_METRICS)
									.addStatement(
											"return $T.applyDimension($T.$L, $L, metrics)",
											AndroidClassNames.TYPED_VALUE,
											AndroidClassNames.TYPED_VALUE,
											getComplexUnitLiteral(unit),
											rawDimensionValue)
									.build();

						} catch (final ClassNotFoundException e) {
							throw new RuntimeException("DimensionUnit class not found. This should never happen.");
						}
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToDimensionResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"context.getResources().getDimension($L",
										getValueLiteral(object, "resId"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToDrawableResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $T.getDrawable(context, $L)",
										AndroidClassNames.CONTEXT_COMPAT,
										getValueLiteral(object, "resId"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToEnumConstant.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $T.getEnumConstant($L, $L)",
										ENUM_UTIL_TYPE_NAME,
										getValueLiteral(object, "enumClass"),
										getValueLiteral(object, "ordinal"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToFloat.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return $L", getValueLiteral(object, "value"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToFractionResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"context.getResources().getFraction($L, $L, $L)",
										getValueLiteral(object, "resId"),
										getValueLiteral(object, "baseMultiplier"),
										getValueLiteral(object, "parentMultiplier"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToInteger.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return $L", getValueLiteral(object, "value"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToIntegerResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("context.getResources().getInteger($L)", getValueLiteral(object, "resId"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToNull.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("return null")
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToString.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.add("return $L", getValueLiteral(object, "value"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToStringResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"return context.getResources().getString($L)",
										getValueLiteral(object, "resId"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToTextArrayResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"context.getResources().getTextArray($L)",
										getValueLiteral(object, "resId"))
								.build();
					}
				}
		);

		getDefaultValueMethodBodySuppliers.put(
				DefaultToTextResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement("context.getResources().getText($L)", getValueLiteral(object, "resId"))
								.build();
					}
				}
		);
	}

	public CallerComponentGenerator(final Elements elementsUtil) {
		this.elementsUtil = checkNotNull(elementsUtil, "Argument \'elementsUtil\' cannot be null.");
	}

	/**
	 * Returns a method spec equivalent to the following:
	 * <pre>{@code
	 * public boolean shouldCallMethod(TypedArray attrs) {
	 * 	// Some implementation, varies
	 * }}</pre>
	 * <p>
	 * The body is dynamically generated based on the attr checks defined by the supplied attribute.
	 *
	 * @param anno
	 * 		the annotation to base the method body on, not null
	 *
	 * @return the method spec, not null
	 *
	 * @throws IllegalArgumentException
	 * 		if {@code annotation} is null
	 */
	public MethodSpec generateShouldCallMethodSpecFor(final AnnotationMirror anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		final String annotationTypeName = anno.getAnnotationType().toString();

		if (!shouldCallMethodMethodBodySuppliers.containsKey(annotationTypeName)) {
			throw new IllegalArgumentException("Argument \'anno\' must be a mirror of a call handler annotation.");
		}

		final CodeBlock methodBody = shouldCallMethodMethodBodySuppliers.get(annotationTypeName).supplyFor(anno);

		return MethodSpec
				.methodBuilder("shouldCallMethod")
				.addModifiers(PUBLIC)
				.returns(boolean.class)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL)
				.addCode(methodBody)
				.build();
	}

	public MethodSpec generateValueIsAvailableSpecFor(final AnnotationMirror anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		final String annotationTypeName = anno.getAnnotationType().toString();

		if (!valueIsAvailableBodySuppliers.containsKey(annotationTypeName)) {
			throw new IllegalArgumentException("Argument \'anno\' must be a mirror of a value handler annotation.");
		}

		final CodeBlock methodBody = valueIsAvailableBodySuppliers.get(annotationTypeName).supplyFor(anno);

		return MethodSpec
				.methodBuilder("valueIsAvailable")
				.addModifiers(PUBLIC)
				.returns(boolean.class)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}

	public MethodSpec generateGetValueSpecFor(final AnnotationMirror anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		final String annotationTypeName = anno.getAnnotationType().toString();

		if (!getValueMethodBodySuppliers.containsKey(annotationTypeName)) {
			throw new IllegalArgumentException("Argument \'anno\' must be a mirror of a value handler annotation.");
		}

		final CodeBlock methodBody = getValueMethodBodySuppliers.get(annotationTypeName).supplyFor(anno);

		return MethodSpec
				.methodBuilder("getValue")
				.addModifiers(PUBLIC)
				.returns(Object.class)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}

	public MethodSpec generateGetDefaultValueSpecFor(final AnnotationMirror anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		final String annotationTypeName = anno.getAnnotationType().toString();

		if (!getDefaultValueMethodBodySuppliers.containsKey(annotationTypeName)) {
			throw new IllegalArgumentException("Argument \'anno\' must be a default annotation.");
		}

		final CodeBlock methodBody = getDefaultValueMethodBodySuppliers.get(annotationTypeName).supplyFor(anno);

		return MethodSpec
				.methodBuilder("getDefaultValue")
				.addModifiers(PUBLIC)
				.returns(Object.class)
				.addParameter(AndroidClassNames.CONTEXT, "context")
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}

	private String getAttributeId(final AnnotationMirror mirror) {
		return getValueLiteral(mirror, "attributeId");
	}

	private String getValueLiteral(final AnnotationMirror mirror, final String key) {
		final AnnotationValue value = getAnnotationValueWithDefaults(
				mirror,
				key,
				elementsUtil);

		return value.toString();
	}

	private String getComplexUnitLiteral(final DimensionUnit unit) {
		switch (unit) {
			case PX: {return "COMPLEX_UNIT_PX";}
			case DP: {return "COMPLEX_UNIT_DIP";}
			case PT: {return "COMPLEX_UNIT_PT";}
			case IN: {return "COMPLEX_UNIT_IN";}
			case SP: {return "COMPLEX_UNIT_SP";}
			case MM: {return "COMPLEX_UNIT_MM";}
		}

		throw new RuntimeException("Should never get here.");
	}
}