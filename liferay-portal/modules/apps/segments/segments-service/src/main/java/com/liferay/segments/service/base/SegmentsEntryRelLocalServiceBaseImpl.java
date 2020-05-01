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

package com.liferay.segments.service.base;

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
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryRelLocalService;
import com.liferay.segments.service.persistence.SegmentsEntryPersistence;
import com.liferay.segments.service.persistence.SegmentsEntryRelPersistence;
import com.liferay.segments.service.persistence.SegmentsEntryRolePersistence;
import com.liferay.segments.service.persistence.SegmentsExperiencePersistence;
import com.liferay.segments.service.persistence.SegmentsExperimentFinder;
import com.liferay.segments.service.persistence.SegmentsExperimentPersistence;
import com.liferay.segments.service.persistence.SegmentsExperimentRelPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the segments entry rel local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.segments.service.impl.SegmentsEntryRelLocalServiceImpl}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see com.liferay.segments.service.impl.SegmentsEntryRelLocalServiceImpl
 * @generated
 */
public abstract class SegmentsEntryRelLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, IdentifiableOSGiService,
			   SegmentsEntryRelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>SegmentsEntryRelLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.segments.service.SegmentsEntryRelLocalServiceUtil</code>.
	 */

	/**
	 * Adds the segments entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRel the segments entry rel
	 * @return the segments entry rel that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SegmentsEntryRel addSegmentsEntryRel(
		SegmentsEntryRel segmentsEntryRel) {

		segmentsEntryRel.setNew(true);

		return segmentsEntryRelPersistence.update(segmentsEntryRel);
	}

	/**
	 * Creates a new segments entry rel with the primary key. Does not add the segments entry rel to the database.
	 *
	 * @param segmentsEntryRelId the primary key for the new segments entry rel
	 * @return the new segments entry rel
	 */
	@Override
	@Transactional(enabled = false)
	public SegmentsEntryRel createSegmentsEntryRel(long segmentsEntryRelId) {
		return segmentsEntryRelPersistence.create(segmentsEntryRelId);
	}

	/**
	 * Deletes the segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel that was removed
	 * @throws PortalException if a segments entry rel with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SegmentsEntryRel deleteSegmentsEntryRel(long segmentsEntryRelId)
		throws PortalException {

		return segmentsEntryRelPersistence.remove(segmentsEntryRelId);
	}

	/**
	 * Deletes the segments entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRel the segments entry rel
	 * @return the segments entry rel that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SegmentsEntryRel deleteSegmentsEntryRel(
		SegmentsEntryRel segmentsEntryRel) {

		return segmentsEntryRelPersistence.remove(segmentsEntryRel);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			SegmentsEntryRel.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return segmentsEntryRelPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsEntryRelModelImpl</code>.
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

		return segmentsEntryRelPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsEntryRelModelImpl</code>.
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

		return segmentsEntryRelPersistence.findWithDynamicQuery(
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
		return segmentsEntryRelPersistence.countWithDynamicQuery(dynamicQuery);
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

		return segmentsEntryRelPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public SegmentsEntryRel fetchSegmentsEntryRel(long segmentsEntryRelId) {
		return segmentsEntryRelPersistence.fetchByPrimaryKey(
			segmentsEntryRelId);
	}

	/**
	 * Returns the segments entry rel with the primary key.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel
	 * @throws PortalException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel getSegmentsEntryRel(long segmentsEntryRelId)
		throws PortalException {

		return segmentsEntryRelPersistence.findByPrimaryKey(segmentsEntryRelId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			segmentsEntryRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SegmentsEntryRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("segmentsEntryRelId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			segmentsEntryRelLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(SegmentsEntryRel.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"segmentsEntryRelId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			segmentsEntryRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SegmentsEntryRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("segmentsEntryRelId");
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return segmentsEntryRelPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return segmentsEntryRelLocalService.deleteSegmentsEntryRel(
			(SegmentsEntryRel)persistedModel);
	}

	public BasePersistence<SegmentsEntryRel> getBasePersistence() {
		return segmentsEntryRelPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return segmentsEntryRelPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> getSegmentsEntryRels(int start, int end) {
		return segmentsEntryRelPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of segments entry rels.
	 *
	 * @return the number of segments entry rels
	 */
	@Override
	public int getSegmentsEntryRelsCount() {
		return segmentsEntryRelPersistence.countAll();
	}

	/**
	 * Updates the segments entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRel the segments entry rel
	 * @return the segments entry rel that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SegmentsEntryRel updateSegmentsEntryRel(
		SegmentsEntryRel segmentsEntryRel) {

		return segmentsEntryRelPersistence.update(segmentsEntryRel);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			SegmentsEntryRelLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		segmentsEntryRelLocalService = (SegmentsEntryRelLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SegmentsEntryRelLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return SegmentsEntryRel.class;
	}

	protected String getModelClassName() {
		return SegmentsEntryRel.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = segmentsEntryRelPersistence.getDataSource();

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
	protected SegmentsEntryPersistence segmentsEntryPersistence;

	protected SegmentsEntryRelLocalService segmentsEntryRelLocalService;

	@Reference
	protected SegmentsEntryRelPersistence segmentsEntryRelPersistence;

	@Reference
	protected SegmentsEntryRolePersistence segmentsEntryRolePersistence;

	@Reference
	protected SegmentsExperiencePersistence segmentsExperiencePersistence;

	@Reference
	protected SegmentsExperimentPersistence segmentsExperimentPersistence;

	@Reference
	protected SegmentsExperimentFinder segmentsExperimentFinder;

	@Reference
	protected SegmentsExperimentRelPersistence segmentsExperimentRelPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

}