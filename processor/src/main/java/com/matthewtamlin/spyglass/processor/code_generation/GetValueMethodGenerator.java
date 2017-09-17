package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.common.class_definitions.AndroidClassNames;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.common.enum_util.EnumUtil;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processor.mirror_helpers.AnnotationMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class GetValueMethodGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, MethodSpec>> methodSpecSuppliers;

	private final AnnotationMirrorHelper annotationMirrorHelper;

	{
		methodSpecSuppliers = new HashMap<>();

		methodSpecSuppliers.put(
				BooleanHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getBoolean($L, false)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(Boolean.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				ColorHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getColor($L, 1)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				ColorStateListHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getColorStateList($L)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(AndroidClassNames.COLOR_STATE_LIST)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getDimension($L, Float.NEGATIVE_INFINITY)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				DrawableHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getDrawable($L)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(AndroidClassNames.DRAWABLE)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final String enumClassName = getLiteralFromAnnotation(anno, "enumClass");

						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"final int ordinal = $N().getInt($L, 1)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.addStatement(
										"return $T.getEnumConstant($L, ordinal)",
										TypeName.get(EnumUtil.class),
										enumClassName)
								.build();

						return getBaseMethodSpec()
								.returns(TypeName.OBJECT)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				EnumOrdinalHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getInt($L, 1)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				FloatHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getFloat($L, Float.NEGATIVE_INFINITY)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				FractionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getFraction($L, $L, $L, Float.NEGATIVE_INFINITY)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"),
										getLiteralFromAnnotation(anno, "baseMultiplier"),
										getLiteralFromAnnotation(anno, "parentMultiplier"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				IntegerHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getInt($L, 1)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				StringHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getString($L)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(String.class)
								.addCode(body).build();
					}
				}
		);
	}

	public GetValueMethodGenerator(final CoreHelpers coreHelpers) {
		checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");

		annotationMirrorHelper = coreHelpers.getAnnotationMirrorHelper();
	}

	/**
	 * Creates a method spec equivalent to the following:
	 * <pre>{@code
	 * public Object getValue(final TypedArray attrs) {
	 * 	dynamic implementation here
	 * }}</pre>
	 * <p>
	 * The body of the method is dynamically generated based on the supplied annotation. In general terms, the method
	 * queries the supplied typed array and returns a value from it. Exactly what is returned is determined by each
	 * specific implementation.
	 *
	 * @param valueHandlerAnno
	 * 		the annotation to use when generating the method body, not null
	 *
	 * @return the method spec, not null
	 *
	 * @throws IllegalArgumentException
	 * 		if {@code valueHandlerAnno} is null
	 * @throws IllegalArgumentException
	 * 		if {@code valueHandlerAnno} is not a value handler annotation
	 */
	public MethodSpec generateFor(final AnnotationMirror valueHandlerAnno) {
		checkNotNull(valueHandlerAnno, "Argument \'valueHandlerAnno\' cannot be null.");

		final String annoClassName = valueHandlerAnno.getAnnotationType().toString();

		if (!methodSpecSuppliers.containsKey(annoClassName)) {
			throw new IllegalArgumentException("Argument \'valueHandlerAnno\' cannot contain null.");
		}

		return methodSpecSuppliers.get(annoClassName).supplyFor(valueHandlerAnno);
	}

	private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
		return annotationMirrorHelper.getValueUsingDefaults(mirror, key).toString();
	}

	private MethodSpec.Builder getBaseMethodSpec() {
		return MethodSpec.methodBuilder("getValue");
	}
}