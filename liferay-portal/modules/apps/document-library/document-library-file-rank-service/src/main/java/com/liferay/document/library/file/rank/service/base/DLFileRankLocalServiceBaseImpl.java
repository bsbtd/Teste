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

package com.liferay.document.library.file.rank.service.base;

import com.liferay.document.library.file.rank.model.DLFileRank;
import com.liferay.document.library.file.rank.service.DLFileRankLocalService;
import com.liferay.document.library.file.rank.service.persistence.DLFileRankFinder;
import com.liferay.document.library.file.rank.service.persistence.DLFileRankPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the document library file rank local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.document.library.file.rank.service.impl.DLFileRankLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.document.library.file.rank.service.impl.DLFileRankLocalServiceImpl
 * @generated
 */
public abstract class DLFileRankLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, DLFileRankLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>DLFileRankLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.document.library.file.rank.service.DLFileRankLocalServiceUtil</code>.
	 */

	/**
	 * Adds the document library file rank to the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileRank the document library file rank
	 * @return the document library file rank that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFileRank addDLFileRank(DLFileRank dlFileRank) {
		dlFileRank.setNew(true);

		return dlFileRankPersistence.update(dlFileRank);
	}

	/**
	 * Creates a new document library file rank with the primary key. Does not add the document library file rank to the database.
	 *
	 * @param fileRankId the primary key for the new document library file rank
	 * @return the new document library file rank
	 */
	@Override
	@Transactional(enabled = false)
	public DLFileRank createDLFileRank(long fileRankId) {
		return dlFileRankPersistence.create(fileRankId);
	}

	/**
	 * Deletes the document library file rank with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileRankId the primary key of the document library file rank
	 * @return the document library file rank that was removed
	 * @throws PortalException if a document library file rank with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFileRank deleteDLFileRank(long fileRankId) throws PortalException {
		return dlFileRankPersistence.remove(fileRankId);
	}

	/**
	 * Deletes the document library file rank from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileRank the document library file rank
	 * @return the document library file rank that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFileRank deleteDLFileRank(DLFileRank dlFileRank) {
		return dlFileRankPersistence.remove(dlFileRank);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			DLFileRank.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return dlFileRankPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.file.rank.model.impl.DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return dlFileRankPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.file.rank.model.impl.DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return dlFileRankPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return dlFileRankPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return dlFileRankPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public DLFileRank fetchDLFileRank(long fileRankId) {
		return dlFileRankPersistence.fetchByPrimaryKey(fileRankId);
	}

	/**
	 * Returns the document library file rank with the primary key.
	 *
	 * @param fileRankId the primary key of the document library file rank
	 * @return the document library file rank
	 * @throws PortalException if a document library file rank with the primary key could not be found
	 */
	@Override
	public DLFileRank getDLFileRank(long fileRankId) throws PortalException {
		return dlFileRankPersistence.findByPrimaryKey(fileRankId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(dlFileRankLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DLFileRank.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("fileRankId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			dlFileRankLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(DLFileRank.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("fileRankId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(dlFileRankLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DLFileRank.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("fileRankId");
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return dlFileRankPersistence.create(((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return dlFileRankLocalService.deleteDLFileRank(
			(DLFileRank)persistedModel);
	}

	public BasePersistence<DLFileRank> getBasePersistence() {
		return dlFileRankPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return dlFileRankPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the document library file ranks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.file.rank.model.impl.DLFileRankModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file ranks
	 * @param end the upper bound of the range of document library file ranks (not inclusive)
	 * @return the range of document library file ranks
	 */
	@Override
	public List<DLFileRank> getDLFileRanks(int start, int end) {
		return dlFileRankPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of document library file ranks.
	 *
	 * @return the number of document library file ranks
	 */
	@Override
	public int getDLFileRanksCount() {
		return dlFileRankPersistence.countAll();
	}

	/**
	 * Updates the document library file rank in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileRank the document library file rank
	 * @return the document library file rank that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFileRank updateDLFileRank(DLFileRank dlFileRank) {
		return dlFileRankPersistence.update(dlFileRank);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			DLFileRankLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		dlFileRankLocalService = (DLFileRankLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DLFileRankLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DLFileRank.class;
	}

	protected String getModelClassName() {
		return DLFileRank.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = dlFileRankPersistence.getDataSource();

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

	protected DLFileRankLocalService dlFileRankLocalService;

	@Reference
	protected DLFileRankPersistence dlFileRankPersistence;

	@Reference
	protected DLFileRankFinder dlFileRankFinder;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.document.library.kernel.service.DLFolderLocalService
		dlFolderLocalService;

}