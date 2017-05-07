package com.matthewtamlin.spyglass.processors.core;

import com.matthewtamlin.spyglass.processors.code_generation.CallerDef;
import com.matthewtamlin.spyglass.processors.code_generation.CompanionClassGenerator;
import com.matthewtamlin.spyglass.processors.util.TypeUtil;
import com.matthewtamlin.spyglass.processors.validation.ValidationException;
import com.matthewtamlin.spyglass.processors.validation.Validator;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static com.matthewtamlin.spyglass.processors.core.Grouper.groupByEnclosingClass;
import static javax.tools.Diagnostic.Kind.ERROR;

public class MainProcessor extends AbstractProcessor {
	private static final Set<Element> PROCESSED_CLASSES = new HashSet<>();

	private Messager messager;

	private Filer filer;

	private static final Set<Class<? extends Annotation>> SUPPORTED_ANNOTATIONS;

	static {
		final Set<Class<? extends Annotation>> intermediateSet = new HashSet<>();

		intermediateSet.addAll(AnnotationRegistry.CALL_HANDLER_ANNOTATIONS);
		intermediateSet.addAll(AnnotationRegistry.VALUE_HANDLER_ANNOTATIONS);
		intermediateSet.addAll(AnnotationRegistry.DEFAULT_ANNOTATIONS);

		SUPPORTED_ANNOTATIONS = Collections.unmodifiableSet(intermediateSet);
	}

	@Override
	public synchronized void init(final ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);

		messager = processingEnvironment.getMessager();
		filer = processingEnvironment.getFiler();

		createRequiredFiles();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		final Set<String> supportedTypes = new HashSet<>();

		for (Class<? extends Annotation> clazz : SUPPORTED_ANNOTATIONS) {
			supportedTypes.add(clazz.getCanonicalName());
		}

		return supportedTypes;
	}

	@Override
	public boolean process(
			final Set<? extends TypeElement> annotations,
			final RoundEnvironment roundEnv) {

		final Set<ExecutableElement> allElements = findSupportedElements(roundEnv);
		final Map<TypeElement, Set<ExecutableElement>> elementsByEnclosingClass = groupByEnclosingClass(allElements);

		validateElements(allElements);

		for (final TypeElement target : elementsByEnclosingClass.keySet()) {
			final String targetPackage = TypeUtil.getPackageOfType(target);
			final String targetClass = TypeUtil.getSimpleNameOfType(target);

			final Set<ExecutableElement> targetElements = elementsByEnclosingClass.get(target);

			final JavaFile companionForTarget = CompanionClassGenerator
					.forTarget(targetPackage, targetClass)
					.generateCompanionFromElements(targetElements);

			try {
				companionForTarget.writeTo(filer);
			} catch (IOException e) {
				messager.printMessage(ERROR, "Unable to create Spyglass source file.");
			}
		}

		return false;
	}

	private void createRequiredFiles() {
		try {
			CallerDef.getJavaFile().writeTo(filer);
		} catch (final IOException e) {
			messager.printMessage(ERROR, "Unable to create required Spyglass framework file.");
		}
	}

	private Set<ExecutableElement> findSupportedElements(final RoundEnvironment roundEnv) {
		final Set<ExecutableElement> supportedElements = new HashSet<>();

		for (final Class<? extends Annotation> annoType : SUPPORTED_ANNOTATIONS) {
			for (final Element foundElement : roundEnv.getElementsAnnotatedWith(annoType)) {
				// Based on design of processor, all elements should be executable elements
				supportedElements.add((ExecutableElement) foundElement);
			}
		}

		return supportedElements;
	}

	private void validateElements(final Set<? extends Element> elements) {
		for (final Element element : elements) {
			try {
				Validator.validateElement(element);

			} catch (final ValidationException e1) {
				messager.printMessage(ERROR, e1.getMessage(), element);

			} catch (final Exception e2) {
				messager.printMessage(ERROR, "Spyglass validation failure.", element);
			}
		}
	}
}