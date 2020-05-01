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

package com.liferay.portlet.ratings.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsEntryModel;
import com.liferay.ratings.kernel.model.RatingsEntrySoap;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the RatingsEntry service. Represents a row in the &quot;RatingsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>RatingsEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link RatingsEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntryImpl
 * @generated
 */
@JSON(strict = true)
public class RatingsEntryModelImpl
	extends BaseModelImpl<RatingsEntry> implements RatingsEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ratings entry model instance should use the <code>RatingsEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "RatingsEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"entryId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"score", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("entryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("score", Types.DOUBLE);
	}

	public static final String TABLE_SQL_CREATE =
		"create table RatingsEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,score DOUBLE)";

	public static final String TABLE_SQL_DROP = "drop table RatingsEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY ratingsEntry.entryId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY RatingsEntry.entryId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.ratings.kernel.model.RatingsEntry"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.ratings.kernel.model.RatingsEntry"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.ratings.kernel.model.RatingsEntry"),
		true);

	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	public static final long SCORE_COLUMN_BITMASK = 8L;

	public static final long USERID_COLUMN_BITMASK = 16L;

	public static final long UUID_COLUMN_BITMASK = 32L;

	public static final long ENTRYID_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static RatingsEntry toModel(RatingsEntrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		RatingsEntry model = new RatingsEntryImpl();

		model.setUuid(soapModel.getUuid());
		model.setEntryId(soapModel.getEntryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setScore(soapModel.getScore());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<RatingsEntry> toModels(RatingsEntrySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<RatingsEntry> models = new ArrayList<RatingsEntry>(
			soapModels.length);

		for (RatingsEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.ratings.kernel.model.RatingsEntry"));

	public RatingsEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _entryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _entryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return RatingsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return RatingsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<RatingsEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<RatingsEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<RatingsEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((RatingsEntry)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<RatingsEntry, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<RatingsEntry, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(RatingsEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<RatingsEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<RatingsEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, RatingsEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			RatingsEntry.class.getClassLoader(), RatingsEntry.class,
			ModelWrapper.class);

		try {
			Constructor<RatingsEntry> constructor =
				(Constructor<RatingsEntry>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<RatingsEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<RatingsEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<RatingsEntry, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<RatingsEntry, Object>>();
		Map<String, BiConsumer<RatingsEntry, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<RatingsEntry, ?>>();

		attributeGetterFunctions.put("uuid", RatingsEntry::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<RatingsEntry, String>)RatingsEntry::setUuid);
		attributeGetterFunctions.put("entryId", RatingsEntry::getEntryId);
		attributeSetterBiConsumers.put(
			"entryId",
			(BiConsumer<RatingsEntry, Long>)RatingsEntry::setEntryId);
		attributeGetterFunctions.put("companyId", RatingsEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<RatingsEntry, Long>)RatingsEntry::setCompanyId);
		attributeGetterFunctions.put("userId", RatingsEntry::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<RatingsEntry, Long>)RatingsEntry::setUserId);
		attributeGetterFunctions.put("userName", RatingsEntry::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<RatingsEntry, String>)RatingsEntry::setUserName);
		attributeGetterFunctions.put("createDate", RatingsEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<RatingsEntry, Date>)RatingsEntry::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", RatingsEntry::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<RatingsEntry, Date>)RatingsEntry::setModifiedDate);
		attributeGetterFunctions.put(
			"classNameId", RatingsEntry::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<RatingsEntry, Long>)RatingsEntry::setClassNameId);
		attributeGetterFunctions.put("classPK", RatingsEntry::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<RatingsEntry, Long>)RatingsEntry::setClassPK);
		attributeGetterFunctions.put("score", RatingsEntry::getScore);
		attributeSetterBiConsumers.put(
			"score", (BiConsumer<RatingsEntry, Double>)RatingsEntry::setScore);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getEntryId() {
		return _entryId;
	}

	@Override
	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@JSON
	@Override
	public double getScore() {
		return _score;
	}

	@Override
	public void setScore(double score) {
		_columnBitmask |= SCORE_COLUMN_BITMASK;

		if (!_setOriginalScore) {
			_setOriginalScore = true;

			_originalScore = _score;
		}

		_score = score;
	}

	public double getOriginalScore() {
		return _originalScore;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(RatingsEntry.class.getName()),
			getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), RatingsEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public RatingsEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, RatingsEntry>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		RatingsEntryImpl ratingsEntryImpl = new RatingsEntryImpl();

		ratingsEntryImpl.setUuid(getUuid());
		ratingsEntryImpl.setEntryId(getEntryId());
		ratingsEntryImpl.setCompanyId(getCompanyId());
		ratingsEntryImpl.setUserId(getUserId());
		ratingsEntryImpl.setUserName(getUserName());
		ratingsEntryImpl.setCreateDate(getCreateDate());
		ratingsEntryImpl.setModifiedDate(getModifiedDate());
		ratingsEntryImpl.setClassNameId(getClassNameId());
		ratingsEntryImpl.setClassPK(getClassPK());
		ratingsEntryImpl.setScore(getScore());

		ratingsEntryImpl.resetOriginalValues();

		return ratingsEntryImpl;
	}

	@Override
	public int compareTo(RatingsEntry ratingsEntry) {
		long primaryKey = ratingsEntry.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RatingsEntry)) {
			return false;
		}

		RatingsEntry ratingsEntry = (RatingsEntry)obj;

		long primaryKey = ratingsEntry.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		RatingsEntryModelImpl ratingsEntryModelImpl = this;

		ratingsEntryModelImpl._originalUuid = ratingsEntryModelImpl._uuid;

		ratingsEntryModelImpl._originalCompanyId =
			ratingsEntryModelImpl._companyId;

		ratingsEntryModelImpl._setOriginalCompanyId = false;

		ratingsEntryModelImpl._originalUserId = ratingsEntryModelImpl._userId;

		ratingsEntryModelImpl._setOriginalUserId = false;

		ratingsEntryModelImpl._setModifiedDate = false;

		ratingsEntryModelImpl._originalClassNameId =
			ratingsEntryModelImpl._classNameId;

		ratingsEntryModelImpl._setOriginalClassNameId = false;

		ratingsEntryModelImpl._originalClassPK = ratingsEntryModelImpl._classPK;

		ratingsEntryModelImpl._setOriginalClassPK = false;

		ratingsEntryModelImpl._originalScore = ratingsEntryModelImpl._score;

		ratingsEntryModelImpl._setOriginalScore = false;

		ratingsEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<RatingsEntry> toCacheModel() {
		RatingsEntryCacheModel ratingsEntryCacheModel =
			new RatingsEntryCacheModel();

		ratingsEntryCacheModel.uuid = getUuid();

		String uuid = ratingsEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			ratingsEntryCacheModel.uuid = null;
		}

		ratingsEntryCacheModel.entryId = getEntryId();

		ratingsEntryCacheModel.companyId = getCompanyId();

		ratingsEntryCacheModel.userId = getUserId();

		ratingsEntryCacheModel.userName = getUserName();

		String userName = ratingsEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			ratingsEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			ratingsEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			ratingsEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			ratingsEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			ratingsEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		ratingsEntryCacheModel.classNameId = getClassNameId();

		ratingsEntryCacheModel.classPK = getClassPK();

		ratingsEntryCacheModel.score = getScore();

		return ratingsEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<RatingsEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<RatingsEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<RatingsEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((RatingsEntry)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<RatingsEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<RatingsEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<RatingsEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((RatingsEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, RatingsEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private String _uuid;
	private String _originalUuid;
	private long _entryId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private double _score;
	private double _originalScore;
	private boolean _setOriginalScore;
	private long _columnBitmask;
	private RatingsEntry _escapedModel;

}