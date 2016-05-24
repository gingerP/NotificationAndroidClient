package vinni.com.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import vinni.com.notification.handlers.Calling;

/**
 * Created by vinni on 5/23/16.
 */
public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO extract number
        Calling.sendNotification("Something!!");
    }
}
