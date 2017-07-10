package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

@Tested(testMethod = "automated")
public final class CallerDef {
	public static final String PACKAGE = "com.matthewtamlin.spyglass.processors.code_generation";

	public static final String CLASS_NAME = "Caller";

	public static final String METHOD_NAME = "call";

	public static JavaFile getJavaFile() {
		final TypeVariableName targetType = TypeVariableName.get("T");

		final MethodSpec methodSpec = MethodSpec
				.methodBuilder(METHOD_NAME)
				.addModifiers(PUBLIC, ABSTRACT)
				.returns(void.class)
				.build();

		final TypeSpec typeSpec = TypeSpec
				.classBuilder(CLASS_NAME)
				.addModifiers(PUBLIC, ABSTRACT)
				.addTypeVariable(targetType)
				.addMethod(methodSpec)
				.build();

		return JavaFile
				.builder(PACKAGE, typeSpec)
				.addFileComment("Spyglass auto-generated file. Do not modify!")
				.build();
	}

	private CallerDef() {
		throw new RuntimeException("Contract class. Do not instantiate.");
	}
}