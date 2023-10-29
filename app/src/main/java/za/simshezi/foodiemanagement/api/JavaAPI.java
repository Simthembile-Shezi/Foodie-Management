package za.simshezi.foodiemanagement.api;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JavaAPI {
    public static String getTime(Timestamp timestamp) {
        if(timestamp != null) {
            Date date = timestamp.toDate();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return sdf.format(date);
        }else {
            return null;
        }
    }

    public static String getDate(Timestamp timestamp) {
        if(timestamp != null) {
            Date date = timestamp.toDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return sdf.format(date);
        }else {
            return null;
        }
    }

    public static Timestamp getTimestamp(String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = sdf.parse(timestamp);
            assert date != null;
            return new Timestamp(date);
        } catch (Exception e) {
            return null;
        }
    }
}
