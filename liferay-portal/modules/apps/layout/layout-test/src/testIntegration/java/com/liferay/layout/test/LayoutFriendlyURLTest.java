/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLsException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class LayoutFriendlyURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groups.add(_group);
	}

	@Test
	public void testDifferentFriendlyURLDifferentLocaleDifferentGroup()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		addLayout(group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testDifferentFriendlyURLDifferentLocaleDifferentLayoutSet()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		addLayout(group.getGroupId(), true, friendlyURLMap);
	}

	@Test
	public void testDifferentFriendlyURLDifferentLocaleSameLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testFriendlyURLWithSpecialCharacter() throws Exception {
		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/Football⚽"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		String friendlyURL = FriendlyURLNormalizerUtil.normalizeWithEncoding(
			"/Football⚽");

		Layout layout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
			_group.getGroupId(), false, friendlyURL);

		Assert.assertNotNull(layout);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLLanguageId() throws Exception {
		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/es"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLLanguageIdAndCountryId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/es_ES"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testInvalidFriendlyURLMapperURLInDefaultLocale()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/tags"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 1,
				layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException layoutFriendlyURLException =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(
				"tags", layoutFriendlyURLException.getKeywordConflict());
		}

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/home/tags"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 1,
				layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException layoutFriendlyURLException =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(
				"tags", layoutFriendlyURLException.getKeywordConflict());
		}

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/tags/home"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 1,
				layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException layoutFriendlyURLException =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(
				"tags", layoutFriendlyURLException.getKeywordConflict());
		}

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/blogs/-/home"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 1,
				layoutFriendlyURLExceptions.size());

			LayoutFriendlyURLException layoutFriendlyURLException =
				(LayoutFriendlyURLException)layoutFriendlyURLExceptions.get(0);

			Assert.assertEquals(
				"/-/", layoutFriendlyURLException.getKeywordConflict());
		}
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLMapperURLInNondefaultLocale()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/tags/two"
		).put(
			LocaleUtil.US, "/two"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLStartingWithLanguageId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/es/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLStartingWithLanguageIdAndCountryId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/es_ES/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test(expected = LayoutFriendlyURLsException.class)
	public void testInvalidFriendlyURLStartingWithLowerCaseLanguageIdAndCountryId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/es_es/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testMultipleInvalidFriendlyURLMapperURL() throws Exception {
		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/tags/dos"
		).put(
			LocaleUtil.US, "/tags/two"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
			Map<Locale, Exception> localizedExceptionsMap =
				layoutFriendlyURLsException.getLocalizedExceptionsMap();

			List<Exception> layoutFriendlyURLExceptions =
				ListUtil.fromCollection(localizedExceptionsMap.values());

			Assert.assertEquals(
				layoutFriendlyURLExceptions.toString(), 2,
				layoutFriendlyURLExceptions.size());

			for (Exception exception : layoutFriendlyURLExceptions) {
				LayoutFriendlyURLException layoutFriendlyURLException =
					(LayoutFriendlyURLException)exception;

				String keywordConflict =
					layoutFriendlyURLException.getKeywordConflict();

				Assert.assertEquals("tags", keywordConflict);
			}
		}
	}

	@Test
	public void testSameFriendlyURLDifferentLocaleDifferentGroup()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/home"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		addLayout(group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testSameFriendlyURLDifferentLocaleDifferentLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/home"
		).put(
			LocaleUtil.US, "/welcome"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
		}
	}

	@Test
	public void testSameFriendlyURLDifferentLocaleDifferentLayoutSet()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/home"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
		addLayout(_group.getGroupId(), true, friendlyURLMap);
	}

	@Test
	public void testSameFriendlyURLDifferentLocaleSameLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/home"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testSameFriendlyURLSameLocaleDifferentLayout()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/casa"
		).put(
			LocaleUtil.US, "/house"
		).build();

		try {
			addLayout(_group.getGroupId(), false, friendlyURLMap);

			Assert.fail();
		}
		catch (LayoutFriendlyURLsException layoutFriendlyURLsException) {
		}
	}

	@Test
	public void testValidFriendlyURLEndingWithLanguageId() throws Exception {
		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/home/es"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testValidFriendlyURLEndingWithLanguageIdAndCountryId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/home/es_ES"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testValidFriendlyURLEndingWithLowerCaseLanguageIdAndCountryId()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/home/es_es"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testValidFriendlyURLMapperURLInDefaultLocale()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/blogs"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/home/blogs"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);

		friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/blogs/home"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testValidFriendlyURLMapperURLInNondefaultLocale()
		throws Exception {

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "/blogs/two"
		).put(
			LocaleUtil.US, "/two"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	@Test
	public void testValidFriendlyURLStartingWithLanguageId() throws Exception {
		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.US, "/eshome"
		).build();

		addLayout(_group.getGroupId(), false, friendlyURLMap);
	}

	protected void addLayout(
			long groupId, boolean privateLayout,
			Map<Locale, String> friendlyURLMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), groupId, privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			LayoutConstants.TYPE_PORTLET, StringPool.BLANK, false,
			friendlyURLMap, serviceContext);
	}

	private Group _group;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

}