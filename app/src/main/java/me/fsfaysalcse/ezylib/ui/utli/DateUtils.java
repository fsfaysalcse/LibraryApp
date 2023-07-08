package me.fsfaysalcse.ezylib.ui.utli;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getCurrentDate() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }

    public static String getDateInFuture(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date futureDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(futureDate);
    }

    public static void main(String[] args) {
        String currentDate = getCurrentDate();
        System.out.println("Current Date: " + currentDate);

        String futureDate = getDateInFuture(7);
        System.out.println("Seven Days Later: " + futureDate);
    }
}
