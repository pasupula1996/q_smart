package qualtech_q_smart_home_automation_2018.q_smart.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Constants {


    public static String WIDGET_ID = "HOME_SCREEN_WIDGET_ID_EVENT";
    public static String WIDGET_BUTTON = "a_smart_light_one";
    public static String WIDGET_TV_BUTTON = "a_smart_TV_one";
    public static String WIDGET_FAN_BUTTON = "a_smart_FAN_one";
    public static String WIDGET_LIGHT_BUTTON = "a_smart_light_TWO";

    public static String WIDGET_RECEIVER = "Q-SMART_WIDGET_PROVIDER_CODE";

    public static String START_NOTIFICATION_SERVICE = "Q-SMART_NOTIFICATION_SERVICE";
    public static String LIGHT1_NOTIFICATION_SERVICE = "Q-SMART_NOTIFICATION_SERVICE_LIGHT_ONE";
    public static String LIGHT2_NOTIFICATION_SERVICE = "Q-SMART_NOTIFICATION_SERVICE_LIGHT_TWO";
    public static String FAN1_NOTIFICATION_SERVICE = "Q-SMART_NOTIFICATION_SERVICE_FAN_ONE";
    public static String TV1_NOTIFICATION_SERVICE = "Q-SMART_NOTIFICATION_SERVICE_TV_ONE";



    public static String BitmapToString(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
        byte[] b = out.toByteArray();
        //String bmpString = Base64.encodeToString(b,Base64.DEFAULT);
        return Base64.encodeToString(b,Base64.DEFAULT);
    }
    public static Bitmap StringToBitmap(String array){
        byte[] b =Base64.decode(array,Base64.DEFAULT);
        //Bitmap bmp = BitmapFactory.decodeByteArray(b,0,b.length);
        return BitmapFactory.decodeByteArray(b,0,b.length);
    }
}
