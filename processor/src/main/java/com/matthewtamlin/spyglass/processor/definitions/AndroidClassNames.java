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

/**
 * Definitions for classes available in Android projects.
 */
public class AndroidClassNames {
  public static final ClassName CONTEXT = ClassName.get("android.content", "Context");
  
  public static final ClassName TYPED_ARRAY = ClassName.get("android.content.res", "TypedArray");
  
  public static final ClassName CONTEXT_COMPAT = ClassName.get("android.support.v4.content", "ContextCompat");
  
  public static final ClassName DISPLAY_METRICS = ClassName.get("android.util", "DisplayMetrics");
  
  public static final ClassName TYPED_VALUE = ClassName.get("android.util", "TypedValue");
  
  public static final ClassName COLOR_STATE_LIST = ClassName.get("android.content.res", "ColorStateList");
  
  public static final ClassName DRAWABLE = ClassName.get("android.graphics.drawable", "Drawable");
  
  public static final ClassName ATTRIBUTE_SET = ClassName.get("android.util", "AttributeSet");
  
  public static final ClassName APP_COMPAT_RESOURCES = ClassName.get(
      "android.support.v7.content.res",
      "AppCompatResources");
  
  private AndroidClassNames() {
    throw new RuntimeException("Constants class. Do not instantiate.");
  }
}