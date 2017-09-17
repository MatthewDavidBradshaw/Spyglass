package com.matthewtamlin.spyglass.processor.grouper.grouper;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.processor.grouper.TypeElementWrapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static com.matthewtamlin.spyglass.processor.grouper.Grouper.groupByEnclosingClass;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class TestGrouper {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/grouper/grouper/Data.java")
			.build();

	private TypeElement primaryClass;

	private TypeElement secondaryClass;

	private TypeElement innerClass;

	private TypeElement veryNestedClass;

	private Set<Element> primaryClassChildren;

	private Set<Element> secondaryClassChildren;

	private Set<Element> innerClassChildren;

	private Set<Element> veryNestedClassChildren;

	@Before
	public void setup() {
		primaryClass = avatarRule.getElementWithUniqueId("primary class");
		secondaryClass = avatarRule.getElementWithUniqueId("secondary class");
		innerClass = avatarRule.getElementWithUniqueId("inner class");
		veryNestedClass = avatarRule.getElementWithUniqueId("very nested class");

		primaryClassChildren = new HashSet<>();
		primaryClassChildren.add(avatarRule.getElementWithUniqueId("primary class field"));
		primaryClassChildren.add(avatarRule.getElementWithUniqueId("primary class method"));
		primaryClassChildren.add(avatarRule.getElementWithUniqueId("inner class"));

		secondaryClassChildren = new HashSet<>();
		secondaryClassChildren.add(avatarRule.getElementWithUniqueId("secondary class field"));
		secondaryClassChildren.add(avatarRule.getElementWithUniqueId("secondary class method"));

		innerClassChildren = new HashSet<>();
		innerClassChildren.add(avatarRule.getElementWithUniqueId("inner class field"));
		innerClassChildren.add(avatarRule.getElementWithUniqueId("inner class method"));

		veryNestedClassChildren = new HashSet<>();
		veryNestedClassChildren.add(avatarRule.getElementWithUniqueId("very nested class field"));
		veryNestedClassChildren.add(avatarRule.getElementWithUniqueId("very nested class method"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGroupByEnclosingClass_nullSupplied() {
		groupByEnclosingClass(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGroupByEnclosingClass_collectionContainsNull() {
		final Set<Element> set = new HashSet<>();
		set.add(null);

		groupByEnclosingClass(set);
	}

	@Test
	public void testGroupByEnclosingClass_primaryClassComponents() {
		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(primaryClassChildren);

		assertThat("There should only be one group.", groupedByClass.size(), is(1));

		assertThat(
				"Wrong children found.",
				groupedByClass.get(new TypeElementWrapper(primaryClass)),
				is(primaryClassChildren));
	}

	@Test
	public void testGroupByEnclosingClass_secondaryClassComponents() {
		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(secondaryClassChildren);

		assertThat("There should only be one group.", groupedByClass.size(), is(1));

		assertThat(
				"Wrong children found.",
				groupedByClass.get(new TypeElementWrapper(secondaryClass)),
				is(secondaryClassChildren));
	}

	@Test
	public void testGroupByEnclosingClass_innerClassComponents() {
		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(innerClassChildren);

		assertThat("There should only be one group.", groupedByClass.size(), is(1));

		assertThat(
				"Wrong children found.",
				groupedByClass.get(new TypeElementWrapper(innerClass)),
				is(innerClassChildren));
	}

	@Test
	public void testGroupByEnclosingClass_veryNestedClassComponents() {
		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(veryNestedClassChildren);

		assertThat("There should only be one group.", groupedByClass.size(), is(1));

		assertThat(
				"Wrong children found.",
				groupedByClass.get(new TypeElementWrapper(veryNestedClass)),
				is(veryNestedClassChildren));
	}

	@Test
	public void testGroupByEnclosingClass_allComponents() {
		final Set<Element> allChildren = new HashSet<>();
		allChildren.addAll(primaryClassChildren);
		allChildren.addAll(secondaryClassChildren);
		allChildren.addAll(innerClassChildren);
		allChildren.addAll(veryNestedClassChildren);

		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(allChildren);

		assertThat("There should only be four groups.", groupedByClass.size(), is(4));

		assertThat("Wrong children found for primary class.",
				groupedByClass.get(new TypeElementWrapper(primaryClass)),
				is(primaryClassChildren));

		assertThat("Wrong children found for secondary class.",
				groupedByClass.get(new TypeElementWrapper(secondaryClass)),
				is(secondaryClassChildren));

		assertThat("Wrong children found for inner class.",
				groupedByClass.get(new TypeElementWrapper(innerClass)),
				is(innerClassChildren));

		assertThat("Wrong children found for very nested class.",
				groupedByClass.get(new TypeElementWrapper(veryNestedClass)),
				is(veryNestedClassChildren));
	}
}