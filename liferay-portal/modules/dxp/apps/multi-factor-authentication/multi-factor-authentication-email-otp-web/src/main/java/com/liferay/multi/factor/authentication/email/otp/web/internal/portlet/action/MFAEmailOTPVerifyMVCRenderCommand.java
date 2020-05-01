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

package com.liferay.multi.factor.authentication.email.otp.web.internal.portlet.action;

import com.liferay.multi.factor.authentication.email.otp.web.internal.checker.MFAEmailOTPChecker;
import com.liferay.multi.factor.authentication.email.otp.web.internal.constants.MFAEmailOTPPortletKeys;
import com.liferay.multi.factor.authentication.email.otp.web.internal.constants.MFAEmailOTPWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"javax.portlet.name=" + MFAEmailOTPPortletKeys.MFA_EMAIL_OTP_VERIFY_PORTLET,
		"mvc.command.name=/mfa_email_otp_verify/verify"
	},
	service = MVCRenderCommand.class
)
public class MFAEmailOTPVerifyMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long mfaEmailOTPUserId = _getMFAEmailOTPUserId(renderRequest);

		if (mfaEmailOTPUserId == 0) {
			SessionErrors.add(renderRequest, "sessionExpired");

			return "/error.jsp";
		}

		renderRequest.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_CHECKER, _mfaEmailOTPChecker);
		renderRequest.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_USER_ID, mfaEmailOTPUserId);

		return "/mfa_email_otp_verify/verify.jsp";
	}

	private long _getMFAEmailOTPUserId(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isSignedIn()) {
			return themeDisplay.getUserId();
		}

		HttpServletRequest httpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(portletRequest));

		HttpSession httpSession = httpServletRequest.getSession();

		return GetterUtil.getLong(
			httpSession.getAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP_USER_ID));
	}

	@Reference
	private MFAEmailOTPChecker _mfaEmailOTPChecker;

	@Reference
	private Portal _portal;

}