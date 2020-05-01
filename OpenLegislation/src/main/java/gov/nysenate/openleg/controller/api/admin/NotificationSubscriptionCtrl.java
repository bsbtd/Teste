package gov.nysenate.openleg.controller.api.admin;

import gov.nysenate.openleg.client.response.base.BaseResponse;
import gov.nysenate.openleg.client.response.base.ListViewResponse;
import gov.nysenate.openleg.client.response.base.SimpleResponse;
import gov.nysenate.openleg.client.response.base.ViewObjectResponse;
import gov.nysenate.openleg.client.view.notification.NotificationSubscriptionView;
import gov.nysenate.openleg.controller.api.base.BaseCtrl;
import gov.nysenate.openleg.controller.api.base.InvalidRequestParamEx;
import gov.nysenate.openleg.dao.base.LimitOffset;
import gov.nysenate.openleg.model.notification.*;
import gov.nysenate.openleg.service.notification.subscription.NotificationSubscriptionDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequestMapping(value = BaseCtrl.BASE_ADMIN_API_PATH + "/notifications", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
public class NotificationSubscriptionCtrl extends BaseCtrl
{
    @Autowired
    private NotificationSubscriptionDataService subscriptionDataService;

    /**
     * Notification Subscription API
     * -----------------------------
     *
     * Register for notification tracking (GET) /api/3/admin/notifications/subscribe
     * Request Parameters:  type (string) - The Notification Type to subscribe for.
     *                      target (string) - The medium through which the notification will be sent.
     *                      address (string) - The address for the specified target medium.
     *
     */
    @RequiresPermissions("admin:notification-subscribe")
    @RequestMapping(value = "/subscribe")
    public BaseResponse subscribeToNotification(@RequestParam String type,
                                                @RequestParam String target,
                                                @RequestParam String address) {
        NotificationSubscription subscription = buildSubscriptionFromParams(type, target, address);
        subscriptionDataService.updateSubscription(subscription);
        return new ViewObjectResponse<>(new NotificationSubscriptionView(subscription));
    }


    /**
     * Notification Unsubscribe API
     * ----------------------------
     *
     * Unsubscribe from a notification (GET) /api/3/admin/notifications/unsubscribe
     * @deprecated
     * @see #unsubscribeFromNotification(int)
     */
    @RequiresPermissions("admin:notification-subscribe")
    @RequestMapping(value = "/unsubscribe")
    @Deprecated
    public BaseResponse unsubscribeFromNotification(@RequestParam String type,
                                                    @RequestParam String target,
                                                    @RequestParam String address) {
        String user = (String) SecurityUtils.getSubject().getPrincipal();
        NotificationType notificationType = getEnumParameter("type", type, NotificationType.class);
        NotificationMedium notificationMedium = getNotificationTargetFromString(target);
        subscriptionDataService.getSubscriptions(user).stream()
                .filter(sub -> sub.getNotificationType() == notificationType &&
                        sub.getMedium() == notificationMedium &&
                        StringUtils.equals(sub.getTargetAddress(), address))
                .map(NotificationSubscription::getId)
                .forEach(subscriptionDataService::removeSubscription);
        return new SimpleResponse(true, "unsubscribe successful", "unsubscribe-success");
    }

    /**
     * Notification Unsubscribe API
     * ----------------------------
     *
     * Unsubscribe from a notification
     *
     * (DELETE) /api/3/admin/notifications/unsubscribe/{id}
     *
     * @param subscriptionId int - subscription id
     * @return {@link SimpleResponse}
     */
    @RequiresPermissions("admin:notification-subscribe")
    @RequestMapping(value = "/unsubscribe/{subscriptionId}", method = DELETE)
    public SimpleResponse unsubscribeFromNotification(@PathVariable int subscriptionId) {
        ensureOwned(subscriptionId);
        subscriptionDataService.removeSubscription(subscriptionId);
        return new SimpleResponse(true, "unsubscribe successful", "unsubscribe-success");
    }

    /**
     * Notification Subscription Listing API
     * -------------------------------------
     *
     * Lists the logged in admin's notification subscriptions (GET) /api/3/admin/notifications/subscriptions
     *
     * Request Parameters: limit, offset (int) - Paginate.
     *
     * Expected Output: List of NotificationSubscriptionView
     */
    @RequiresPermissions("admin:notification-subscribe")
    @RequestMapping(value = "/subscriptions")
    public BaseResponse viewSubscriptions(WebRequest request) {
        String user = (String) SecurityUtils.getSubject().getPrincipal();
        LimitOffset limOff = getLimitOffset(request, 0);
        return ListViewResponse.of(subscriptionDataService.getSubscriptions(user).stream()
                               .map(NotificationSubscriptionView::new).collect(Collectors.toList()),
                0, limOff);
    }

    /** --- Internal --- */

    private NotificationSubscription buildSubscriptionFromParams(String type, String target, String address) {
        String user = (String) SecurityUtils.getSubject().getPrincipal();
        NotificationType notificationType = getEnumParameter("type", type, NotificationType.class);
        NotificationMedium notificationMedium = getNotificationTargetFromString(target);
        return InstantNotificationSubscription.builder()
                .setUserName(user)
                .setNotificationType(notificationType)
                .setMedium(notificationMedium)
                .setTargetAddress(address)
                .setDetail(true)
                .setActive(true)
                .build();
    }

    private NotificationMedium getNotificationTargetFromString(String text) {
        try {
            return NotificationMedium.getValue(text);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestParamEx(text, "target", "String",
                    NotificationMedium.getAllNotificationMedia().stream()
                            .map(NotificationMedium::toString)
                            .reduce("", (a, b) -> a + "|" + b));
        }
    }

    /**
     * Ensures that the authenticated user owns the subscription with the given id.
     *
     * @param subscriptionId int
     */
    private void ensureOwned(int subscriptionId) {
        String user = (String) SecurityUtils.getSubject().getPrincipal();
        Set<Integer> subscriptionIds = subscriptionDataService.getSubscriptions(user).stream()
                .map(NotificationSubscription::getId)
                .collect(Collectors.toSet());
        if (!subscriptionIds.contains(subscriptionId)) {
            throw new AuthorizationException("The authenticated user is not authorized to modify notification subscription #" + subscriptionId);
        }
    }
}
