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

package com.liferay.segments.experiment.web.internal.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.experiment.web.internal.constants.SegmentsExperimentWebKeys;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = DynamicInclude.class)
public class SegmentsExperimentAnalyticsTopHeadJSPDynamicInclude
	extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!SegmentsExperimentUtil.isAnalyticsSynced(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId())) {

			return;
		}

		long[] segmentsExperienceIds = GetterUtil.getLongValues(
			httpServletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS));

		httpServletRequest.setAttribute(
			SegmentsExperimentWebKeys.
				SEGMENTS_EXPERIMENT_SEGMENTS_EXPERIENCE_KEY,
			_getSegmentsExperienceKey(segmentsExperienceIds));

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/top_head.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.segments.experiment.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private String _getSegmentsExperienceKey(long[] segmentsExperienceIds) {
		if (segmentsExperienceIds.length > 0) {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					segmentsExperienceIds[0]);

			if (segmentsExperience != null) {
				return segmentsExperience.getSegmentsExperienceKey();
			}
		}

		return SegmentsExperienceConstants.KEY_DEFAULT;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentAnalyticsTopHeadJSPDynamicInclude.class);

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}