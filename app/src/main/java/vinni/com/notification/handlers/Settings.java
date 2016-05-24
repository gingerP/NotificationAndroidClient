package vinni.com.notification.handlers;

/**
 * Created by vinni on 5/23/16.
 */
public class Settings {
    private static String address;
    public static void setIpAddress(String ipAddress) {
        address = ipAddress;
    }

    public static String getIpAddress() {
        return address;
    }

}
