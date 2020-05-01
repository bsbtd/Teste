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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for SystemEvent. This utility wraps
 * <code>com.liferay.portal.service.impl.SystemEventLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SystemEventLocalService
 * @generated
 */
public class SystemEventLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.SystemEventLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.SystemEvent addSystemEvent(
			long userId, long groupId, String className, long classPK,
			String classUuid, String referrerClassName, int type,
			String extraData)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSystemEvent(
			userId, groupId, className, classPK, classUuid, referrerClassName,
			type, extraData);
	}

	public static com.liferay.portal.kernel.model.SystemEvent addSystemEvent(
			long companyId, String className, long classPK, String classUuid,
			String referrerClassName, int type, String extraData)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSystemEvent(
			companyId, className, classPK, classUuid, referrerClassName, type,
			extraData);
	}

	/**
	 * Adds the system event to the database. Also notifies the appropriate model listeners.
	 *
	 * @param systemEvent the system event
	 * @return the system event that was added
	 */
	public static com.liferay.portal.kernel.model.SystemEvent addSystemEvent(
		com.liferay.portal.kernel.model.SystemEvent systemEvent) {

		return getService().addSystemEvent(systemEvent);
	}

	public static void checkSystemEvents()
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkSystemEvents();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new system event with the primary key. Does not add the system event to the database.
	 *
	 * @param systemEventId the primary key for the new system event
	 * @return the new system event
	 */
	public static com.liferay.portal.kernel.model.SystemEvent createSystemEvent(
		long systemEventId) {

		return getService().createSystemEvent(systemEventId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the system event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event that was removed
	 * @throws PortalException if a system event with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.SystemEvent deleteSystemEvent(
			long systemEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSystemEvent(systemEventId);
	}

	/**
	 * Deletes the system event from the database. Also notifies the appropriate model listeners.
	 *
	 * @param systemEvent the system event
	 * @return the system event that was removed
	 */
	public static com.liferay.portal.kernel.model.SystemEvent deleteSystemEvent(
		com.liferay.portal.kernel.model.SystemEvent systemEvent) {

		return getService().deleteSystemEvent(systemEvent);
	}

	public static void deleteSystemEvents(long groupId) {
		getService().deleteSystemEvents(groupId);
	}

	public static void deleteSystemEvents(
		long groupId, long systemEventSetKey) {

		getService().deleteSystemEvents(groupId, systemEventSetKey);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.kernel.model.SystemEvent fetchSystemEvent(
		long systemEventId) {

		return getService().fetchSystemEvent(systemEventId);
	}

	public static com.liferay.portal.kernel.model.SystemEvent fetchSystemEvent(
		long groupId, long classNameId, long classPK, int type) {

		return getService().fetchSystemEvent(
			groupId, classNameId, classPK, type);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the system event with the primary key.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event
	 * @throws PortalException if a system event with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.SystemEvent getSystemEvent(
			long systemEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSystemEvent(systemEventId);
	}

	/**
	 * Returns a range of all the system events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of system events
	 */
	public static java.util.List<com.liferay.portal.kernel.model.SystemEvent>
		getSystemEvents(int start, int end) {

		return getService().getSystemEvents(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.SystemEvent>
		getSystemEvents(long groupId, long classNameId, long classPK) {

		return getService().getSystemEvents(groupId, classNameId, classPK);
	}

	public static java.util.List<com.liferay.portal.kernel.model.SystemEvent>
		getSystemEvents(
			long groupId, long classNameId, long classPK, int type) {

		return getService().getSystemEvents(
			groupId, classNameId, classPK, type);
	}

	/**
	 * Returns the number of system events.
	 *
	 * @return the number of system events
	 */
	public static int getSystemEventsCount() {
		return getService().getSystemEventsCount();
	}

	/**
	 * Updates the system event in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param systemEvent the system event
	 * @return the system event that was updated
	 */
	public static com.liferay.portal.kernel.model.SystemEvent updateSystemEvent(
		com.liferay.portal.kernel.model.SystemEvent systemEvent) {

		return getService().updateSystemEvent(systemEvent);
	}

	public static boolean validateGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().validateGroup(groupId);
	}

	public static SystemEventLocalService getService() {
		if (_service == null) {
			_service = (SystemEventLocalService)PortalBeanLocatorUtil.locate(
				SystemEventLocalService.class.getName());
		}

		return _service;
	}

	private static SystemEventLocalService _service;

}