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

package com.liferay.portal.tools.service.builder.test.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity;
import com.liferay.portal.tools.service.builder.test.service.EagerBlobEntityLocalService;
import com.liferay.portal.tools.service.builder.test.service.persistence.EagerBlobEntityPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the eager blob entity local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.tools.service.builder.test.service.impl.EagerBlobEntityLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.tools.service.builder.test.service.impl.EagerBlobEntityLocalServiceImpl
 * @generated
 */
public abstract class EagerBlobEntityLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements EagerBlobEntityLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>EagerBlobEntityLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.tools.service.builder.test.service.EagerBlobEntityLocalServiceUtil</code>.
	 */

	/**
	 * Adds the eager blob entity to the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntity the eager blob entity
	 * @return the eager blob entity that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public EagerBlobEntity addEagerBlobEntity(EagerBlobEntity eagerBlobEntity) {
		eagerBlobEntity.setNew(true);

		return eagerBlobEntityPersistence.update(eagerBlobEntity);
	}

	/**
	 * Creates a new eager blob entity with the primary key. Does not add the eager blob entity to the database.
	 *
	 * @param eagerBlobEntityId the primary key for the new eager blob entity
	 * @return the new eager blob entity
	 */
	@Override
	@Transactional(enabled = false)
	public EagerBlobEntity createEagerBlobEntity(long eagerBlobEntityId) {
		return eagerBlobEntityPersistence.create(eagerBlobEntityId);
	}

	/**
	 * Deletes the eager blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity that was removed
	 * @throws PortalException if a eager blob entity with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public EagerBlobEntity deleteEagerBlobEntity(long eagerBlobEntityId)
		throws PortalException {

		return eagerBlobEntityPersistence.remove(eagerBlobEntityId);
	}

	/**
	 * Deletes the eager blob entity from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntity the eager blob entity
	 * @return the eager blob entity that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public EagerBlobEntity deleteEagerBlobEntity(
		EagerBlobEntity eagerBlobEntity) {

		return eagerBlobEntityPersistence.remove(eagerBlobEntity);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			EagerBlobEntity.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return eagerBlobEntityPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntityModelImpl</code>.
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

		return eagerBlobEntityPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntityModelImpl</code>.
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

		return eagerBlobEntityPersistence.findWithDynamicQuery(
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
		return eagerBlobEntityPersistence.countWithDynamicQuery(dynamicQuery);
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

		return eagerBlobEntityPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public EagerBlobEntity fetchEagerBlobEntity(long eagerBlobEntityId) {
		return eagerBlobEntityPersistence.fetchByPrimaryKey(eagerBlobEntityId);
	}

	/**
	 * Returns the eager blob entity matching the UUID and group.
	 *
	 * @param uuid the eager blob entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	@Override
	public EagerBlobEntity fetchEagerBlobEntityByUuidAndGroupId(
		String uuid, long groupId) {

		return eagerBlobEntityPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the eager blob entity with the primary key.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity
	 * @throws PortalException if a eager blob entity with the primary key could not be found
	 */
	@Override
	public EagerBlobEntity getEagerBlobEntity(long eagerBlobEntityId)
		throws PortalException {

		return eagerBlobEntityPersistence.findByPrimaryKey(eagerBlobEntityId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(eagerBlobEntityLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(EagerBlobEntity.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("eagerBlobEntityId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			eagerBlobEntityLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(EagerBlobEntity.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"eagerBlobEntityId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(eagerBlobEntityLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(EagerBlobEntity.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("eagerBlobEntityId");
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return eagerBlobEntityPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return eagerBlobEntityLocalService.deleteEagerBlobEntity(
			(EagerBlobEntity)persistedModel);
	}

	public BasePersistence<EagerBlobEntity> getBasePersistence() {
		return eagerBlobEntityPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return eagerBlobEntityPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the eager blob entity matching the UUID and group.
	 *
	 * @param uuid the eager blob entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching eager blob entity
	 * @throws PortalException if a matching eager blob entity could not be found
	 */
	@Override
	public EagerBlobEntity getEagerBlobEntityByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return eagerBlobEntityPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @return the range of eager blob entities
	 */
	@Override
	public List<EagerBlobEntity> getEagerBlobEntities(int start, int end) {
		return eagerBlobEntityPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of eager blob entities.
	 *
	 * @return the number of eager blob entities
	 */
	@Override
	public int getEagerBlobEntitiesCount() {
		return eagerBlobEntityPersistence.countAll();
	}

	/**
	 * Updates the eager blob entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntity the eager blob entity
	 * @return the eager blob entity that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public EagerBlobEntity updateEagerBlobEntity(
		EagerBlobEntity eagerBlobEntity) {

		return eagerBlobEntityPersistence.update(eagerBlobEntity);
	}

	/**
	 * Returns the eager blob entity local service.
	 *
	 * @return the eager blob entity local service
	 */
	public EagerBlobEntityLocalService getEagerBlobEntityLocalService() {
		return eagerBlobEntityLocalService;
	}

	/**
	 * Sets the eager blob entity local service.
	 *
	 * @param eagerBlobEntityLocalService the eager blob entity local service
	 */
	public void setEagerBlobEntityLocalService(
		EagerBlobEntityLocalService eagerBlobEntityLocalService) {

		this.eagerBlobEntityLocalService = eagerBlobEntityLocalService;
	}

	/**
	 * Returns the eager blob entity persistence.
	 *
	 * @return the eager blob entity persistence
	 */
	public EagerBlobEntityPersistence getEagerBlobEntityPersistence() {
		return eagerBlobEntityPersistence;
	}

	/**
	 * Sets the eager blob entity persistence.
	 *
	 * @param eagerBlobEntityPersistence the eager blob entity persistence
	 */
	public void setEagerBlobEntityPersistence(
		EagerBlobEntityPersistence eagerBlobEntityPersistence) {

		this.eagerBlobEntityPersistence = eagerBlobEntityPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity",
			eagerBlobEntityLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return EagerBlobEntityLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return EagerBlobEntity.class;
	}

	protected String getModelClassName() {
		return EagerBlobEntity.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = eagerBlobEntityPersistence.getDataSource();

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

	@BeanReference(type = EagerBlobEntityLocalService.class)
	protected EagerBlobEntityLocalService eagerBlobEntityLocalService;

	@BeanReference(type = EagerBlobEntityPersistence.class)
	protected EagerBlobEntityPersistence eagerBlobEntityPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}