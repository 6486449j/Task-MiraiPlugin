import java.text.SimpleDateFormat;

public class Utils {
    public static boolean timeFormater(String raw) {
        //开始检查时间格式
        Long month = Long.valueOf(raw.substring(4, 5));
        if(month < 0 || month > 12) return false;

        Long day = Long.valueOf(raw.substring(6, 7));
        if(day > 0) {
            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month ==10 || month == 12) {
                if(day > 31) return false;
            } else if(month == 4 || month == 6 || month ==9 || month == 11) {
                if(day > 30) return false;
            } else {
                Long year = Long.valueOf(raw.substring(0, 3));
                if(year % 400 == 0 || year % 4 == 0 && year % 100 != 0) {
                    if (day > 29) return false;
                } else {
                    if(day > 28) return false;
                }
            }
        } else {
            return false;
        }

        Long hour = Long.valueOf(raw.substring(8, 9));
        if(hour < 0 || hour > 23) return false;

        Long minute = Long.valueOf(raw.substring(10, 11));
        if(minute < 0 || minute > 59) return false;

        return true;
    }
}
