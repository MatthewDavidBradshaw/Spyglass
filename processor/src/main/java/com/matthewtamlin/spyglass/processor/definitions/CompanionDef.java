/*
 * Copyright 2017-2018 Matthew David Tamlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.matthewtamlin.spyglass.processor.definitions;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Code generation definition for the Companion class.
 */
public class CompanionDef {
  public static final MethodSpec ACTIVATE_CALLERS;
  
  public static final TypeSpec INTERFACE;
  
  public static final JavaFile SRC_FILE;
  
  static {
    ACTIVATE_CALLERS = MethodSpec
        .methodBuilder("passDataToMethods")
        .addModifiers(PUBLIC, ABSTRACT)
        .returns(void.class)
        .build();
    
    INTERFACE = TypeSpec
        .interfaceBuilder("Companion")
        .addModifiers(PUBLIC)
        .addMethod(ACTIVATE_CALLERS)
        .build();
    
    SRC_FILE = JavaFile
        .builder("com.matthewtamlin.spyglass.core", INTERFACE)
        .addFileComment("Spyglass auto-generated file. Do not modify!")
        .skipJavaLangImports(true)
        .indent("\t")
        .build();
  }
  
  private CompanionDef() {
    throw new RuntimeException("Contract class. Do not instantiate.");
  }
  
  public static ClassName getCompanionAsClassName() {
    return ClassName.get(SRC_FILE.packageName, INTERFACE.name);
  }
  
  public static TypeSpec.Builder getNewCompanionImplementationPrototype(final String className) {
    return TypeSpec
        .classBuilder(className)
        .addSuperinterface(getCompanionAsClassName());
  }
  
  public static MethodSpec.Builder getNewActivateCallersMethodPrototype() {
    return MethodSpec
        .methodBuilder(ACTIVATE_CALLERS.name)
        .addModifiers(PUBLIC)
        .addAnnotation(Override.class)
        .returns(void.class);
  }
}
