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

package com.liferay.change.tracking.internal.reference.portal;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.TeamTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.TeamPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class TeamTableReferenceDefinition
	implements TableReferenceDefinition<TeamTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<TeamTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			TeamTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				TeamTable.INSTANCE,
				TeamTable.INSTANCE.companyId.eq(CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				TeamTable.INSTANCE,
				TeamTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			TeamTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			TeamTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			TeamTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				TeamTable.INSTANCE,
				TeamTable.INSTANCE.groupId.eq(GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			TeamTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			TeamTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			TeamTable.INSTANCE.lastPublishDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				TeamTable.INSTANCE,
				TeamTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.eq(
						Team.class.getName())
				).and(
					ResourcePermissionTable.INSTANCE.primKeyId.eq(
						TeamTable.INSTANCE.teamId)
				)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				RoleTable.INSTANCE
			).innerJoinON(
				TeamTable.INSTANCE,
				TeamTable.INSTANCE.companyId.eq(
					RoleTable.INSTANCE.companyId
				).and(
					TeamTable.INSTANCE.teamId.eq(RoleTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					RoleTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Team.class.getName())
				)
			));
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _teamPersistence;
	}

	@Override
	public TeamTable getTable() {
		return TeamTable.INSTANCE;
	}

	@Reference
	private TeamPersistence _teamPersistence;

}