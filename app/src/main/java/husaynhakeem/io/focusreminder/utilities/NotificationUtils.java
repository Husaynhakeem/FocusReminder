package husaynhakeem.io.focusreminder.utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;

import husaynhakeem.io.focusreminder.MainActivity;
import husaynhakeem.io.focusreminder.R;

import static android.app.PendingIntent.getActivity;

/**
 * Created by husaynhakeem on 6/24/17.
 */

public class NotificationUtils {

    private static final int FOCUS_REMINDER_NOTIFICATION_ID = 100;

    private static final int DEFAULT_FOCUS_REMINDER_PENDING_INTENT_ID = 1000;
    private static final int FOCUSED_FOCUS_REMINDER_PENDING_INTENT_ID = 1001;
    private static final int NOT_FOCUSED_FOCUS_REMINDER_PENDING_INTENT_ID = 1002;

    public static final String ACTION_USER_IS_FOCUSED = "action-user-is-focused";
    public static final String ACTION_USER_IS_NOT_FOCUSED = "action-user-is-not-focused";


    public static void dismissAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }


    public static void remindUserToFocus(Context context) {

        if (!TimeUtils.isWithinReminderTimeInterval())
            return;

        dismissAllNotifications(context);
        displayReminderNotification(context);
    }


    private static void displayReminderNotification(Context context) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_body)))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(contentIntent(context))
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .addAction(userIsNotFocusedAction(context))
                .addAction(userIsFocusedAction(context));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(FOCUS_REMINDER_NOTIFICATION_ID, notification.build());
    }


    private static PendingIntent contentIntent(Context context) {
        return getActivity(
                context,
                DEFAULT_FOCUS_REMINDER_PENDING_INTENT_ID,
                new Intent(context, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


    private static Action userIsFocusedAction(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(ACTION_USER_IS_FOCUSED);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                FOCUSED_FOCUS_REMINDER_PENDING_INTENT_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        return new Action(
                R.drawable.ic_focused,
                context.getString(R.string.notification_action_focused),
                pendingIntent
        );
    }


    private static Action userIsNotFocusedAction(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(ACTION_USER_IS_NOT_FOCUSED);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                NOT_FOCUSED_FOCUS_REMINDER_PENDING_INTENT_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        return new Action(
                R.drawable.ic_not_focused,
                context.getString(R.string.notification_action_not_focused),
                pendingIntent
        );
    }
}
