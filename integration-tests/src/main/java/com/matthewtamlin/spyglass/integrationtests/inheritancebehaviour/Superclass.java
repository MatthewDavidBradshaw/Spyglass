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

package com.matthewtamlin.spyglass.integrationtests.inheritancebehaviour;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.defaults.DefaultToString;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.StringHandler;

public class Superclass extends View {
  public static final String EXPECTED_VALUE = "superclass expected value";
  
  private ReceivedValue<String> receivedValue = ReceivedValue.none();
  
  public Superclass(final Context context) {
    super(context);
    init(null, 0, 0);
  }
  
  public Superclass(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0, 0);
  }
  
  public Superclass(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }
  
  @TargetApi(21)
  @RequiresApi(21)
  public Superclass(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs, defStyleAttr, defStyleRes);
  }
  
  @StringHandler(attributeId = R.styleable.Superclass_superclassAttr)
  @DefaultToString(EXPECTED_VALUE)
  public void superclassHandlerMethod(final String s) {
    receivedValue = ReceivedValue.of(s);
  }
  
  public ReceivedValue<String> getSuperclassReceivedValue() {
    return receivedValue;
  }
  
  private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    Superclass_SpyglassCompanion
        .builder()
        .setTarget(this)
        .setContext(getContext())
        .setStyleableResource(R.styleable.Superclass)
        .setAttributeSet(attrs)
        .setDefaultStyleAttribute(defStyleAttr)
        .setDefaultStyleResource(defStyleRes)
        .build()
        .callTargetMethodsNow();
  }
}