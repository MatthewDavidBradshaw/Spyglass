/*
 * Copyright 2017 Matthew David Tamlin
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

package com.matthewtamlin.spyglass.processor.validation;


import com.google.common.collect.ImmutableList;
import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.annotationretrievers.ConditionalHandlerRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.DefaultRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.UnconditionalHandlerRetriever;
import com.matthewtamlin.spyglass.processor.definitions.AnnotationRegistry;

import javax.inject.Inject;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.*;

import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

@Tested(testMethod = "automated")
public class BasicValidator implements Validator {
  private final List<Rule> rules = ImmutableList.of(
      // Every element must have no more than one handler annotation
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (countValueHandlerAnnotations(element) + countCallHandlerAnnotations(element) > 1) {
            return Result.createFailure("Methods must not have multiple handler annotations.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      // Every element must have no more than one default annotation
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (countDefaultAnnotations(element) > 1) {
            return Result.createFailure("Methods must not have multiple default annotations.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      // Every element with a default annotation must also have a value handler annotation
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (DefaultRetriever.hasAnnotation(element) &&
              !UnconditionalHandlerRetriever.hasAnnotation(element)) {
            return Result.createFailure(
                "Methods without handler annotations must not have default annotations.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      // Every element with a default annotation must not have a call handler annotation
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (DefaultRetriever.hasAnnotation(element) &&
              ConditionalHandlerRetriever.hasAnnotation(element)) {
            
            return Result.createFailure(
                "Methods with handlers annotations that pass no value must not have default " +
                    "annotations.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      // Every element with a value handle annotation must have at least one parameter
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          final int parameterCount = ((ExecutableElement) element).getParameters().size();
          
          if (UnconditionalHandlerRetriever.hasAnnotation(element) && parameterCount < 1) {
            return Result.createFailure(
                "Methods with handler annotations that pass a value must have at least one parameter.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      // Every parameter must have at most one use-annotations
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          final Map<Integer, Set<Annotation>> useAnnotations = getUseAnnotations(element);
          
          for (final Integer paramIndex : useAnnotations.keySet()) {
            if (useAnnotations.get(paramIndex).size() > 1) {
              return Result.createFailure("Parameters must not have multiple use-annotations.");
            }
          }
          
          return Result.createSuccessful();
        }
      },
      
      // Every element with a value handler must have a use-annotation on every parameter except one
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          final int paramCount = ((ExecutableElement) element).getParameters().size();
          final int annotatedParamCount = countNonEmptySets(getUseAnnotations(element).values());
          
          if (UnconditionalHandlerRetriever.hasAnnotation(element) &&
              annotatedParamCount != paramCount - 1) {
            
            return Result.createFailure(
                "Methods with handler annotations which pass a value must have use " +
                    "annotations on every parameter except one.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      // Every element with a call handler must have a use-annotation on every parameter
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          final int paramCount = ((ExecutableElement) element).getParameters().size();
          final int annotatedParamCount = countNonEmptySets(getUseAnnotations(element).values());
          
          if (ConditionalHandlerRetriever.hasAnnotation(element) && annotatedParamCount != paramCount) {
            return Result.createFailure(
                "Methods with handler annotations which pass no value must have " +
                    "use-annotations on every parameter.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      // Every element must be public, protected or package-private
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (element.getModifiers().contains(PRIVATE)) {
            return Result.createFailure(
                "Methods with handler annotations must have public, protected, or default access. " +
                    "Private methods are not compatible with the Spyglass Framework.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      // Every element must belong to a class which can be statically instantiated
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (!hasStaticRoot(element)) {
            return Result.createFailure(
                "Methods with handler annotations must be accessible from static context.");
          }
          
          return Result.createSuccessful();
        }
        
        private boolean hasStaticRoot(final Element element) {
          final TypeElement parent = (TypeElement) element.getEnclosingElement();
          
          if (parent == null) {
            return true;
          }
          
          switch (parent.getNestingKind()) {
            case TOP_LEVEL:
              return true;
            case MEMBER:
              return parent.getModifiers().contains(STATIC);
            case LOCAL:
              return false;
            case ANONYMOUS:
              return false;
          }
          
          throw new RuntimeException("Should never get here.");
        }
      });
  
  @Inject
  public BasicValidator() {}
  
  public Result validate(final ExecutableElement element) {
    for (final Rule rule : rules) {
      final Result result = rule.checkElement(element);
      
      if (!result.isSuccessful()) {
        return result;
      }
    }
    
    return Result.createSuccessful();
  }
  
  private static int countCallHandlerAnnotations(final ExecutableElement method) {
    int count = 0;
    
    for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.CALL_HANDLER_ANNOS) {
      if (method.getAnnotation(annotationClass) != null) {
        count++;
      }
    }
    
    return count;
  }
  
  private static int countValueHandlerAnnotations(final ExecutableElement method) {
    int count = 0;
    
    for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.VALUE_HANDLER_ANNOS) {
      if (method.getAnnotation(annotationClass) != null) {
        count++;
      }
    }
    
    return count;
  }
  
  private static int countDefaultAnnotations(final ExecutableElement method) {
    int count = 0;
    
    for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.DEFAULT_ANNOS) {
      if (method.getAnnotation(annotationClass) != null) {
        count++;
      }
    }
    
    return count;
  }
  
  private static Map<Integer, Set<Annotation>> getUseAnnotations(final ExecutableElement method) {
    final Map<Integer, Set<Annotation>> useAnnotations = new HashMap<>();
    
    final List<? extends VariableElement> params = method.getParameters();
    
    for (int i = 0; i < params.size(); i++) {
      useAnnotations.put(i, new HashSet<Annotation>());
      
      for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.USE_ANNOS) {
        final Annotation foundAnnotation = params.get(i).getAnnotation(annotationClass);
        
        if (foundAnnotation != null) {
          useAnnotations.get(i).add(foundAnnotation);
        }
      }
    }
    
    return useAnnotations;
  }
  
  private static int countNonEmptySets(final Collection<? extends Set> collection) {
    int count = 0;
    
    for (final Set<?> s : collection) {
      if (!s.isEmpty()) {
        count++;
      }
    }
    
    return count;
  }
  
  private interface Rule {
    public Result checkElement(ExecutableElement element);
  }
}