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

package com.liferay.multi.factor.authentication.email.otp.web.internal.system.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Arthur Chan
 */
@ExtendedObjectClassDefinition(
	category = "multi-factor-authentication",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.multi.factor.authentication.email.otp.web.internal.system.configuration.MFAEmailOTPSystemConfiguration",
	localization = "content/Language",
	name = "mfa-email-otp-system-configuration-name"
)
public interface MFAEmailOTPSystemConfiguration {

	@Meta.AD(
		deflt = "false", description = "disable-globally-description",
		name = "disable-globally", required = false
	)
	public boolean disableGlobally();

}