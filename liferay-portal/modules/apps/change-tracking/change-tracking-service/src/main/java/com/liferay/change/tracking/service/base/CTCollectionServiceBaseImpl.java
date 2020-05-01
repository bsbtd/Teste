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

package com.liferay.change.tracking.service.base;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.persistence.CTCollectionFinder;
import com.liferay.change.tracking.service.persistence.CTCollectionPersistence;
import com.liferay.change.tracking.service.persistence.CTEntryFinder;
import com.liferay.change.tracking.service.persistence.CTEntryPersistence;
import com.liferay.change.tracking.service.persistence.CTMessagePersistence;
import com.liferay.change.tracking.service.persistence.CTPreferencesPersistence;
import com.liferay.change.tracking.service.persistence.CTProcessFinder;
import com.liferay.change.tracking.service.persistence.CTProcessPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the ct collection remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.change.tracking.service.impl.CTCollectionServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.change.tracking.service.impl.CTCollectionServiceImpl
 * @generated
 */
public abstract class CTCollectionServiceBaseImpl
	extends BaseServiceImpl
	implements AopService, CTCollectionService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CTCollectionService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.change.tracking.service.CTCollectionServiceUtil</code>.
	 */
	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CTCollectionService.class, IdentifiableOSGiService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		ctCollectionService = (CTCollectionService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CTCollectionService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CTCollection.class;
	}

	protected String getModelClassName() {
		return CTCollection.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ctCollectionPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Reference
	protected com.liferay.change.tracking.service.CTCollectionLocalService
		ctCollectionLocalService;

	protected CTCollectionService ctCollectionService;

	@Reference
	protected CTCollectionPersistence ctCollectionPersistence;

	@Reference
	protected CTCollectionFinder ctCollectionFinder;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected CTEntryPersistence ctEntryPersistence;

	@Reference
	protected CTEntryFinder ctEntryFinder;

	@Reference
	protected CTMessagePersistence ctMessagePersistence;

	@Reference
	protected CTPreferencesPersistence ctPreferencesPersistence;

	@Reference
	protected CTProcessPersistence ctProcessPersistence;

	@Reference
	protected CTProcessFinder ctProcessFinder;

}