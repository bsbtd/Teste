/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.saml.persistence.model.SamlSpMessage;
import com.liferay.saml.persistence.model.SamlSpMessageModel;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the SamlSpMessage service. Represents a row in the &quot;SamlSpMessage&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>SamlSpMessageModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SamlSpMessageImpl}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpMessageImpl
 * @generated
 */
public class SamlSpMessageModelImpl
	extends BaseModelImpl<SamlSpMessage> implements SamlSpMessageModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a saml sp message model instance should use the <code>SamlSpMessage</code> interface instead.
	 */
	public static final String TABLE_NAME = "SamlSpMessage";

	public static final Object[][] TABLE_COLUMNS = {
		{"samlSpMessageId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"samlIdpEntityId", Types.VARCHAR},
		{"samlIdpResponseKey", Types.VARCHAR},
		{"expirationDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("samlSpMessageId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("samlIdpEntityId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("samlIdpResponseKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table SamlSpMessage (samlSpMessageId LONG not null primary key,companyId LONG,createDate DATE null,samlIdpEntityId VARCHAR(1024) null,samlIdpResponseKey VARCHAR(75) null,expirationDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table SamlSpMessage";

	public static final String ORDER_BY_JPQL =
		" ORDER BY samlSpMessage.samlSpMessageId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY SamlSpMessage.samlSpMessageId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long EXPIRATIONDATE_COLUMN_BITMASK = 1L;

	public static final long SAMLIDPENTITYID_COLUMN_BITMASK = 2L;

	public static final long SAMLIDPRESPONSEKEY_COLUMN_BITMASK = 4L;

	public static final long SAMLSPMESSAGEID_COLUMN_BITMASK = 8L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public SamlSpMessageModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _samlSpMessageId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSamlSpMessageId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlSpMessageId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SamlSpMessage.class;
	}

	@Override
	public String getModelClassName() {
		return SamlSpMessage.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<SamlSpMessage, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<SamlSpMessage, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SamlSpMessage, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((SamlSpMessage)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<SamlSpMessage, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<SamlSpMessage, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(SamlSpMessage)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<SamlSpMessage, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<SamlSpMessage, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, SamlSpMessage>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			SamlSpMessage.class.getClassLoader(), SamlSpMessage.class,
			ModelWrapper.class);

		try {
			Constructor<SamlSpMessage> constructor =
				(Constructor<SamlSpMessage>)proxyClass.getConstructor(
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

	private static final Map<String, Function<SamlSpMessage, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<SamlSpMessage, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<SamlSpMessage, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<SamlSpMessage, Object>>();
		Map<String, BiConsumer<SamlSpMessage, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<SamlSpMessage, ?>>();

		attributeGetterFunctions.put(
			"samlSpMessageId", SamlSpMessage::getSamlSpMessageId);
		attributeSetterBiConsumers.put(
			"samlSpMessageId",
			(BiConsumer<SamlSpMessage, Long>)SamlSpMessage::setSamlSpMessageId);
		attributeGetterFunctions.put("companyId", SamlSpMessage::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<SamlSpMessage, Long>)SamlSpMessage::setCompanyId);
		attributeGetterFunctions.put(
			"createDate", SamlSpMessage::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<SamlSpMessage, Date>)SamlSpMessage::setCreateDate);
		attributeGetterFunctions.put(
			"samlIdpEntityId", SamlSpMessage::getSamlIdpEntityId);
		attributeSetterBiConsumers.put(
			"samlIdpEntityId",
			(BiConsumer<SamlSpMessage, String>)
				SamlSpMessage::setSamlIdpEntityId);
		attributeGetterFunctions.put(
			"samlIdpResponseKey", SamlSpMessage::getSamlIdpResponseKey);
		attributeSetterBiConsumers.put(
			"samlIdpResponseKey",
			(BiConsumer<SamlSpMessage, String>)
				SamlSpMessage::setSamlIdpResponseKey);
		attributeGetterFunctions.put(
			"expirationDate", SamlSpMessage::getExpirationDate);
		attributeSetterBiConsumers.put(
			"expirationDate",
			(BiConsumer<SamlSpMessage, Date>)SamlSpMessage::setExpirationDate);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getSamlSpMessageId() {
		return _samlSpMessageId;
	}

	@Override
	public void setSamlSpMessageId(long samlSpMessageId) {
		_samlSpMessageId = samlSpMessageId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public String getSamlIdpEntityId() {
		if (_samlIdpEntityId == null) {
			return "";
		}
		else {
			return _samlIdpEntityId;
		}
	}

	@Override
	public void setSamlIdpEntityId(String samlIdpEntityId) {
		_columnBitmask |= SAMLIDPENTITYID_COLUMN_BITMASK;

		if (_originalSamlIdpEntityId == null) {
			_originalSamlIdpEntityId = _samlIdpEntityId;
		}

		_samlIdpEntityId = samlIdpEntityId;
	}

	public String getOriginalSamlIdpEntityId() {
		return GetterUtil.getString(_originalSamlIdpEntityId);
	}

	@Override
	public String getSamlIdpResponseKey() {
		if (_samlIdpResponseKey == null) {
			return "";
		}
		else {
			return _samlIdpResponseKey;
		}
	}

	@Override
	public void setSamlIdpResponseKey(String samlIdpResponseKey) {
		_columnBitmask |= SAMLIDPRESPONSEKEY_COLUMN_BITMASK;

		if (_originalSamlIdpResponseKey == null) {
			_originalSamlIdpResponseKey = _samlIdpResponseKey;
		}

		_samlIdpResponseKey = samlIdpResponseKey;
	}

	public String getOriginalSamlIdpResponseKey() {
		return GetterUtil.getString(_originalSamlIdpResponseKey);
	}

	@Override
	public Date getExpirationDate() {
		return _expirationDate;
	}

	@Override
	public void setExpirationDate(Date expirationDate) {
		_columnBitmask |= EXPIRATIONDATE_COLUMN_BITMASK;

		if (_originalExpirationDate == null) {
			_originalExpirationDate = _expirationDate;
		}

		_expirationDate = expirationDate;
	}

	public Date getOriginalExpirationDate() {
		return _originalExpirationDate;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), SamlSpMessage.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SamlSpMessage toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, SamlSpMessage>
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
		SamlSpMessageImpl samlSpMessageImpl = new SamlSpMessageImpl();

		samlSpMessageImpl.setSamlSpMessageId(getSamlSpMessageId());
		samlSpMessageImpl.setCompanyId(getCompanyId());
		samlSpMessageImpl.setCreateDate(getCreateDate());
		samlSpMessageImpl.setSamlIdpEntityId(getSamlIdpEntityId());
		samlSpMessageImpl.setSamlIdpResponseKey(getSamlIdpResponseKey());
		samlSpMessageImpl.setExpirationDate(getExpirationDate());

		samlSpMessageImpl.resetOriginalValues();

		return samlSpMessageImpl;
	}

	@Override
	public int compareTo(SamlSpMessage samlSpMessage) {
		long primaryKey = samlSpMessage.getPrimaryKey();

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

		if (!(obj instanceof SamlSpMessage)) {
			return false;
		}

		SamlSpMessage samlSpMessage = (SamlSpMessage)obj;

		long primaryKey = samlSpMessage.getPrimaryKey();

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
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public void resetOriginalValues() {
		SamlSpMessageModelImpl samlSpMessageModelImpl = this;

		samlSpMessageModelImpl._originalSamlIdpEntityId =
			samlSpMessageModelImpl._samlIdpEntityId;

		samlSpMessageModelImpl._originalSamlIdpResponseKey =
			samlSpMessageModelImpl._samlIdpResponseKey;

		samlSpMessageModelImpl._originalExpirationDate =
			samlSpMessageModelImpl._expirationDate;

		samlSpMessageModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SamlSpMessage> toCacheModel() {
		SamlSpMessageCacheModel samlSpMessageCacheModel =
			new SamlSpMessageCacheModel();

		samlSpMessageCacheModel.samlSpMessageId = getSamlSpMessageId();

		samlSpMessageCacheModel.companyId = getCompanyId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			samlSpMessageCacheModel.createDate = createDate.getTime();
		}
		else {
			samlSpMessageCacheModel.createDate = Long.MIN_VALUE;
		}

		samlSpMessageCacheModel.samlIdpEntityId = getSamlIdpEntityId();

		String samlIdpEntityId = samlSpMessageCacheModel.samlIdpEntityId;

		if ((samlIdpEntityId != null) && (samlIdpEntityId.length() == 0)) {
			samlSpMessageCacheModel.samlIdpEntityId = null;
		}

		samlSpMessageCacheModel.samlIdpResponseKey = getSamlIdpResponseKey();

		String samlIdpResponseKey = samlSpMessageCacheModel.samlIdpResponseKey;

		if ((samlIdpResponseKey != null) &&
			(samlIdpResponseKey.length() == 0)) {

			samlSpMessageCacheModel.samlIdpResponseKey = null;
		}

		Date expirationDate = getExpirationDate();

		if (expirationDate != null) {
			samlSpMessageCacheModel.expirationDate = expirationDate.getTime();
		}
		else {
			samlSpMessageCacheModel.expirationDate = Long.MIN_VALUE;
		}

		return samlSpMessageCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<SamlSpMessage, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<SamlSpMessage, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SamlSpMessage, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((SamlSpMessage)this));
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
		Map<String, Function<SamlSpMessage, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<SamlSpMessage, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SamlSpMessage, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((SamlSpMessage)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, SamlSpMessage>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _samlSpMessageId;
	private long _companyId;
	private Date _createDate;
	private String _samlIdpEntityId;
	private String _originalSamlIdpEntityId;
	private String _samlIdpResponseKey;
	private String _originalSamlIdpResponseKey;
	private Date _expirationDate;
	private Date _originalExpirationDate;
	private long _columnBitmask;
	private SamlSpMessage _escapedModel;

}