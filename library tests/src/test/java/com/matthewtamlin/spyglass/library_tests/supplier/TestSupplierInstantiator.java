package com.matthewtamlin.spyglass.library_tests.supplier;

import com.matthewtamlin.spyglass.library.supplier.Supplier;
import com.matthewtamlin.spyglass.library.supplier.SupplierInstantiationException;
import com.matthewtamlin.spyglass.library.supplier.SupplierInstantiator;

import org.junit.Test;

public class TestSupplierInstantiator {
	@Test(expected = IllegalArgumentException.class)
	public void testInstantiateSupplier_nullClass() {
		SupplierInstantiator.instantiateSupplier(null);
	}

	@Test(expected = SupplierInstantiationException.class)
	public void testInstantiateSupplier_classHasPrivateConstructor() {
		SupplierInstantiator.instantiateSupplier(getNonWildcardSupplierWithPrivateConstructor());
	}

	@Test
	public void testInstantiateSupplier_classHasDefaultConstructor() {
		SupplierInstantiator.instantiateSupplier(getNonWildcardSupplierWithDefaultConstructor());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInstantiateWildcardSupplier_nullClass() {
		SupplierInstantiator.instantiateWildcardSupplier(null);
	}

	private Class<? extends Supplier<Object>> getNonWildcardSupplierWithPrivateConstructor() {
		return NonWildcardSupplierPrivateConstructor.class;
	}

	private Class<? extends Supplier<Object>> getNonWildcardSupplierWithDefaultConstructor() {
		return NonWildcardSupplierDefaultConstructor.class;
	}

	private class NonWildcardSupplierPrivateConstructor implements Supplier<Object> {
		private NonWildcardSupplierPrivateConstructor() {}

		@Override
		public Object get() {
			return null;
		}
	}

	private class NonWildcardSupplierDefaultConstructor implements Supplier<Object> {
		@Override
		public Object get() {
			return null;
		}
	}
}