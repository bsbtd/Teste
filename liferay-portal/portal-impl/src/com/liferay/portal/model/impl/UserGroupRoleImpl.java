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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupRoleImpl extends UserGroupRoleBaseImpl {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserGroupRole)) {
			return false;
		}

		UserGroupRole userGroupRole = (UserGroupRole)obj;

		if ((getUserId() == userGroupRole.getUserId()) &&
			(getGroupId() == userGroupRole.getGroupId()) &&
			(getRoleId() == userGroupRole.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	public Group getGroup() throws PortalException {
		return GroupLocalServiceUtil.getGroup(getGroupId());
	}

	@Override
	public Role getRole() throws PortalException {
		return RoleLocalServiceUtil.getRole(getRoleId());
	}

	@Override
	public User getUser() throws PortalException {
		return UserLocalServiceUtil.getUser(getUserId());
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, getUserId());

		hash = HashUtil.hash(hash, getGroupId());

		return HashUtil.hash(hash, getRoleId());
	}

}