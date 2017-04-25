package com.matthewtamlin.spyglass.processors.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetUtil {
	@SafeVarargs
	public static <T> Set<T> setOf(T... objects) {
		final Set<T> set = new HashSet<>();

		Collections.addAll(set, objects);

		return set;
	}
}
