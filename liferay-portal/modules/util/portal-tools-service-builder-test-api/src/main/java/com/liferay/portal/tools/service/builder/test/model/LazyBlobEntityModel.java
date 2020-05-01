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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;

import java.sql.Blob;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the LazyBlobEntity service. Represents a row in the &quot;LazyBlobEntity&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.tools.service.builder.test.model.impl.LazyBlobEntityModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.tools.service.builder.test.model.impl.LazyBlobEntityImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntity
 * @generated
 */
@ProviderType
public interface LazyBlobEntityModel extends BaseModel<LazyBlobEntity> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a lazy blob entity model instance should use the {@link LazyBlobEntity} interface instead.
	 */

	/**
	 * Returns the primary key of this lazy blob entity.
	 *
	 * @return the primary key of this lazy blob entity
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this lazy blob entity.
	 *
	 * @param primaryKey the primary key of this lazy blob entity
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this lazy blob entity.
	 *
	 * @return the uuid of this lazy blob entity
	 */
	@AutoEscape
	public String getUuid();

	/**
	 * Sets the uuid of this lazy blob entity.
	 *
	 * @param uuid the uuid of this lazy blob entity
	 */
	public void setUuid(String uuid);

	/**
	 * Returns the lazy blob entity ID of this lazy blob entity.
	 *
	 * @return the lazy blob entity ID of this lazy blob entity
	 */
	public long getLazyBlobEntityId();

	/**
	 * Sets the lazy blob entity ID of this lazy blob entity.
	 *
	 * @param lazyBlobEntityId the lazy blob entity ID of this lazy blob entity
	 */
	public void setLazyBlobEntityId(long lazyBlobEntityId);

	/**
	 * Returns the group ID of this lazy blob entity.
	 *
	 * @return the group ID of this lazy blob entity
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this lazy blob entity.
	 *
	 * @param groupId the group ID of this lazy blob entity
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the blob1 of this lazy blob entity.
	 *
	 * @return the blob1 of this lazy blob entity
	 */
	public Blob getBlob1();

	/**
	 * Sets the blob1 of this lazy blob entity.
	 *
	 * @param blob1 the blob1 of this lazy blob entity
	 */
	public void setBlob1(Blob blob1);

	/**
	 * Returns the blob2 of this lazy blob entity.
	 *
	 * @return the blob2 of this lazy blob entity
	 */
	public Blob getBlob2();

	/**
	 * Sets the blob2 of this lazy blob entity.
	 *
	 * @param blob2 the blob2 of this lazy blob entity
	 */
	public void setBlob2(Blob blob2);

}