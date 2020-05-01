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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.PageWidgetInstanceDefinition;
import com.liferay.headless.delivery.client.dto.v1_0.WidgetPermission;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageWidgetInstanceDefinitionSerDes {

	public static PageWidgetInstanceDefinition toDTO(String json) {
		PageWidgetInstanceDefinitionJSONParser
			pageWidgetInstanceDefinitionJSONParser =
				new PageWidgetInstanceDefinitionJSONParser();

		return pageWidgetInstanceDefinitionJSONParser.parseToDTO(json);
	}

	public static PageWidgetInstanceDefinition[] toDTOs(String json) {
		PageWidgetInstanceDefinitionJSONParser
			pageWidgetInstanceDefinitionJSONParser =
				new PageWidgetInstanceDefinitionJSONParser();

		return pageWidgetInstanceDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		PageWidgetInstanceDefinition pageWidgetInstanceDefinition) {

		if (pageWidgetInstanceDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageWidgetInstanceDefinition.getWidget() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"widget\": ");

			sb.append(String.valueOf(pageWidgetInstanceDefinition.getWidget()));
		}

		if (pageWidgetInstanceDefinition.getWidgetConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"widgetConfig\": ");

			sb.append(_toJSON(pageWidgetInstanceDefinition.getWidgetConfig()));
		}

		if (pageWidgetInstanceDefinition.getWidgetPermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"widgetPermissions\": ");

			sb.append("[");

			for (int i = 0;
				 i < pageWidgetInstanceDefinition.getWidgetPermissions().length;
				 i++) {

				sb.append(
					String.valueOf(
						pageWidgetInstanceDefinition.getWidgetPermissions()
							[i]));

				if ((i + 1) < pageWidgetInstanceDefinition.
						getWidgetPermissions().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageWidgetInstanceDefinitionJSONParser
			pageWidgetInstanceDefinitionJSONParser =
				new PageWidgetInstanceDefinitionJSONParser();

		return pageWidgetInstanceDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageWidgetInstanceDefinition pageWidgetInstanceDefinition) {

		if (pageWidgetInstanceDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageWidgetInstanceDefinition.getWidget() == null) {
			map.put("widget", null);
		}
		else {
			map.put(
				"widget",
				String.valueOf(pageWidgetInstanceDefinition.getWidget()));
		}

		if (pageWidgetInstanceDefinition.getWidgetConfig() == null) {
			map.put("widgetConfig", null);
		}
		else {
			map.put(
				"widgetConfig",
				String.valueOf(pageWidgetInstanceDefinition.getWidgetConfig()));
		}

		if (pageWidgetInstanceDefinition.getWidgetPermissions() == null) {
			map.put("widgetPermissions", null);
		}
		else {
			map.put(
				"widgetPermissions",
				String.valueOf(
					pageWidgetInstanceDefinition.getWidgetPermissions()));
		}

		return map;
	}

	public static class PageWidgetInstanceDefinitionJSONParser
		extends BaseJSONParser<PageWidgetInstanceDefinition> {

		@Override
		protected PageWidgetInstanceDefinition createDTO() {
			return new PageWidgetInstanceDefinition();
		}

		@Override
		protected PageWidgetInstanceDefinition[] createDTOArray(int size) {
			return new PageWidgetInstanceDefinition[size];
		}

		@Override
		protected void setField(
			PageWidgetInstanceDefinition pageWidgetInstanceDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "widget")) {
				if (jsonParserFieldValue != null) {
					pageWidgetInstanceDefinition.setWidget(
						WidgetSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "widgetConfig")) {
				if (jsonParserFieldValue != null) {
					pageWidgetInstanceDefinition.setWidgetConfig(
						(Map)PageWidgetInstanceDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "widgetPermissions")) {
				if (jsonParserFieldValue != null) {
					pageWidgetInstanceDefinition.setWidgetPermissions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> WidgetPermissionSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new WidgetPermission[size]
						));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}