package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.common.exception.SpyglassCastException;
import com.matthewtamlin.spyglass.processor.annotation_utils.AnnotationMirrorUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.CallHandlerAnnotationUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.UseAnnotationUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.ValueHandlerAnnotationUtil;
import com.matthewtamlin.spyglass.processor.functional.ParametrisedSupplier;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static javax.lang.model.element.Modifier.FINAL;

public class DoInvocationGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, String>> useAnnoValueSuppliers;

	private final Elements elementUtil;

	private final Types typeUtil;

	{
		useAnnoValueSuppliers = new HashMap<>();

		useAnnoValueSuppliers.put(
				UseBoolean.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		useAnnoValueSuppliers.put(
				UseByte.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		useAnnoValueSuppliers.put(
				UseChar.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		useAnnoValueSuppliers.put(
				UseDouble.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		useAnnoValueSuppliers.put(
				UseFloat.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		useAnnoValueSuppliers.put(
				UseInt.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		useAnnoValueSuppliers.put(
				UseLong.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		useAnnoValueSuppliers.put(
				UseNull.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						return "null";
					}
				});

		useAnnoValueSuppliers.put(
				UseShort.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return "(short)" + rawValue.toString();
					}
				});

		useAnnoValueSuppliers.put(
				UseString.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});
	}

	public DoInvocationGenerator(final Elements elementUtil, final Types typeUtil) {
		this.elementUtil = checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");
		this.typeUtil = checkNotNull(typeUtil, "Argument \'typeUtil\' cannot be null.");
	}

	public MethodSpec getMethod(final ExecutableElement method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		if (CallHandlerAnnotationUtil.hasCallHandlerAnnotation(method)) {
			return getMethodForCallHandlerCase(method);
		} else if (ValueHandlerAnnotationUtil.hasValueHandlerAnnotation(method)) {
			return getMethodForValueHandlerCase(method);
		} else {
			throw new IllegalArgumentException("Argument \'element\' must have a handler annotation.");
		}
	}

	private MethodSpec getMethodForCallHandlerCase(final ExecutableElement method) {
		final TypeName targetTypeName = TypeName.get(method.getEnclosingElement().asType());

		return MethodSpec
				.methodBuilder("doInvocation")
				.returns(TypeName.VOID)
				.addParameter(targetTypeName, "target", FINAL)
				.addCode(getInvocationLine(method, null))
				.build();
	}

	private MethodSpec getMethodForValueHandlerCase(final ExecutableElement method) {
		final MethodSpec.Builder methodSpecBuilder = MethodSpec
				.methodBuilder("doInvocation")
				.returns(TypeName.VOID)
				.addParameter(TypeName.get(method.getEnclosingElement().asType()), "target", FINAL)
				.addParameter(TypeName.OBJECT, "value", FINAL);

		final TypeMirror recipientType = getRecipientType(method);

		final CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.beginControlFlow("if (value == null)");

		if (isPrimitive(recipientType)) {
			codeBlockBuilder.addStatement(
					"throw new $T(\"Spyglass cannot pass null to method $L in class $L.\")",
					SpyglassCastException.class,
					method.getSimpleName(),
					method.getEnclosingElement().getSimpleName());
		} else {
			final CodeBlock nonUseArg = CodeBlock.of("($T) null", recipientType);
			codeBlockBuilder.add(getInvocationLine(method, nonUseArg));
		}

		if (isNumber(recipientType) || isCharacter(recipientType)) {
			final String valueAssignableFrom = "value.getClass().isAssignableFrom($T.class)";

			codeBlockBuilder.nextControlFlow(
					"else if (" + valueAssignableFrom + " || " + valueAssignableFrom + ")",
					Number.class,
					Character.class);

			codeBlockBuilder.add(getInvocationLine(method, getNumberConversionCode(recipientType)));
		} else {
			codeBlockBuilder
					.nextControlFlow("else if (value.getClass().isAssignableFrom($T.class))", recipientType);

			codeBlockBuilder.add(getInvocationLine(method, CodeBlock.of("($T) value", recipientType)));
		}

		codeBlockBuilder
				.nextControlFlow("else")
				.addStatement(
						"throw new $T(\"Spyglass cannot pass data of type $L to method $L in class $L.\")",
						SpyglassCastException.class,
						recipientType.toString(),
						method.getSimpleName(),
						method.getEnclosingElement().getSimpleName())
				.endControlFlow();

		methodSpecBuilder.addCode(codeBlockBuilder.build());

		return methodSpecBuilder.build();
	}

	private CodeBlock getInvocationLine(final ExecutableElement method, final CodeBlock nonUseArgValue) {
		final CodeBlock.Builder invocationLine = CodeBlock
				.builder()
				.add("target.$L(", method.getSimpleName());

		for (int i = 0; i < method.getParameters().size(); i++) {
			final VariableElement parameter = method.getParameters().get(i);

			if (UseAnnotationUtil.hasUseAnnotation(parameter)) {
				final AnnotationMirror useAnnotationMirror = UseAnnotationUtil.getUseAnnotationMirror(parameter);
				final String useAnnotationType = useAnnotationMirror.getAnnotationType().toString();

				invocationLine.add(useAnnoValueSuppliers.get(useAnnotationType).supplyFor(useAnnotationMirror));

			} else if (nonUseArgValue == null) {
				throw new RuntimeException("A non-use arg is required for value handler cases.");

			} else {
				invocationLine.add(nonUseArgValue);
			}

			if (i < method.getParameters().size() - 1) {
				invocationLine.add(", ");
			}
		}

		invocationLine.add(");\n");

		return invocationLine.build();
	}

	private TypeMirror getRecipientType(final ExecutableElement method) {
		for (final VariableElement parameter : method.getParameters()) {
			if (!UseAnnotationUtil.hasUseAnnotation(parameter)) {
				return parameter.asType();
			}
		}

		return null;
	}

	private boolean isPrimitive(final TypeMirror typeMirror) {
		final String typeMirrorString = typeMirror.toString();

		return typeMirrorString.equals("byte") ||
				typeMirrorString.equals("char") ||
				typeMirrorString.equals("short") ||
				typeMirrorString.equals("int") ||
				typeMirrorString.equals("long") ||
				typeMirrorString.equals("double") ||
				typeMirrorString.equals("float") ||
				typeMirrorString.equals("boolean");
	}

	private boolean isNumber(final TypeMirror typeMirror) {
		final String typeMirrorString = typeMirror.toString();

		final TypeMirror numberType = elementUtil.getTypeElement(Number.class.getCanonicalName()).asType();

		return typeUtil.isAssignable(typeMirror, numberType) ||
				typeMirrorString.equals("byte") ||
				typeMirrorString.equals("char") ||
				typeMirrorString.equals("short") ||
				typeMirrorString.equals("int") ||
				typeMirrorString.equals("long") ||
				typeMirrorString.equals("double") ||
				typeMirrorString.equals("float");
	}

	private boolean isCharacter(final TypeMirror typeMirror) {
		final TypeMirror characterType = elementUtil.getTypeElement(Character.class.getCanonicalName()).asType();
		return typeUtil.isAssignable(typeMirror, characterType);
	}

	private CodeBlock getNumberConversionCode(final TypeMirror targetTypeMirror) {
		final String targetType = targetTypeMirror.toString();

		if (targetType.equals("byte") || targetType.equals(Byte.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).byteValue()", targetTypeMirror);

		} else if (targetType.equals("char") || targetType.equals(Character.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).byteValue()", targetTypeMirror);

		} else if (targetType.equals("short") || targetType.equals(Short.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).shortValue()", targetTypeMirror);

		} else if (targetType.equals("int") || targetType.equals(Integer.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).intValue()", targetTypeMirror);

		} else if (targetType.equals("long") || targetType.equals(Long.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).longValue()", targetTypeMirror);

		} else if (targetType.equals("float") || targetType.equals(Float.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).floatValue()", targetTypeMirror);

		} else if (targetType.equals("double") || targetType.equals(Double.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).doubleValue()", targetTypeMirror);

		} else {
			throw new IllegalArgumentException("Argument \'recipientTypeMirror\' is not a number.");
		}
	}
}