package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
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
import com.matthewtamlin.spyglass.common.class_definitions.CallerDef;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processor.mirror_helpers.AnnotationMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class ValueIsAvailableMethodGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>> methodBodySuppliers;

	private final AnnotationMirrorHelper annotationMirrorHelper;

	{
		methodBodySuppliers = new HashMap<>();

		methodBodySuppliers.put(
				BooleanHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"$1N().getBoolean($2L, false) == false && \n" +
												"$1N().getBoolean($2L, true) == true",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				ColorHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"$1N().getColor($2L, 1) == 1 && \n" +
												"$1N().getColor($2L, 2) == 2",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				ColorStateListHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $N().getColorStateList($L) != null",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement("final float negInf = Float.NEGATIVE_INFINITY")
								.addStatement("final float posInf = Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"$1N().getDimension($2L, negInf) == negInf && \n" +
												"$1N().getDimension($2L, posInf) == posInf",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DrawableHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $N().getDrawable($L) != null",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"$1N().getInt($2L, 1) == 1 && \n" +
												"$1N().getInt($2L, 2) == 2",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				EnumOrdinalHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"$1N().getInt($2L, 1) == 1 && \n" +
												"$1N().getInt($2L, 2) == 2",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				FloatHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement("final float negInf = Float.NEGATIVE_INFINITY")
								.addStatement("final float posInf = Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"$1N().getFloat($2L, negInf) == negInf && \n" +
												"$1N().getFloat($2L, posInf) == posInf",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				FractionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement("final float negInf = Float.NEGATIVE_INFINITY")
								.addStatement("final float posInf = Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"$1N().getFraction($2L, 1, 1, negInf) == negInf && \n" +
												"$1N().getFraction($2L, 1, 1, posInf) == posInf",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				IntegerHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"$1N().getInt($2L, 1) == 1 && \n" +
												"$1N().getInt($2L, 2) == 2",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				StringHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $N().hasValue($L)",
										CallerDef.GET_ATTRS,
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);
	}

	public ValueIsAvailableMethodGenerator(final CoreHelpers coreHelpers) {
		checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");

		annotationMirrorHelper = coreHelpers.getAnnotationMirrorHelper();
	}

	/**
	 * Creates a method spec equivalent to the following:
	 * <pre>{@code
	 * public boolean valueIsAvailable(final TypedArray attrs) {
	 * 	dynamic implementation here
	 * }}</pre>
	 * <p>
	 * The body of the method is dynamically generated based on the supplied annotation. In general terms, the method
	 * queries the supplied typed array to determine if any value is available for some attribute. The method returns
	 * true if a value is available, and false otherwise. What exactly it means for a value to be available and which
	 * value is of interest is defined by each specific implementation.
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

		if (!methodBodySuppliers.containsKey(annoClassName)) {
			throw new IllegalArgumentException("Argument \'valueHandlerAnno\' is not a value handler annotation.");
		}

		return MethodSpec
				.methodBuilder("valueIsAvailable")
				.returns(Boolean.class)
				.addCode(methodBodySuppliers.get(annoClassName).supplyFor(valueHandlerAnno))
				.build();
	}

	private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
		return annotationMirrorHelper.getValueUsingDefaults(mirror, key).toString();
	}
}