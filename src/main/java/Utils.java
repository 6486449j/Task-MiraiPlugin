import java.text.SimpleDateFormat;

public class Utils {
    public static boolean timeFormater(String raw) {
        TaskPlugin.INSTANCE.logger.info(raw);
        //开始检查时间格式
        Long month = Long.valueOf(String.valueOf(raw.charAt(4))) == 0 ? Long.valueOf(String.valueOf(raw.charAt(5))) : Long.valueOf(raw.substring(4, 5));
        if(month < 0 || month > 12) {
            TaskPlugin.INSTANCE.logger.info("month");
            return false;
        }

        Long day = Long.valueOf(String.valueOf(raw.charAt(6))) == 0 ? Long.valueOf(String.valueOf(raw.charAt(7))) : Long.valueOf(raw.substring(6, 7));
//        TaskPlugin.INSTANCE.logger.info(day.toString());
//        TaskPlugin.INSTANCE.logger.info(String.valueOf(raw.charAt(6)));
//        TaskPlugin.INSTANCE.logger.info(String.valueOf(raw.charAt(7)));
        if(day > 0) {
            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month ==10 || month == 12) {
                if(day > 31) {
                    TaskPlugin.INSTANCE.logger.info("day 31");
                    return false;
                }
            } else if(month == 4 || month == 6 || month ==9 || month == 11) {
                if(day > 30) {
                    TaskPlugin.INSTANCE.logger.info("day 30");
                    return false;
                }
            } else {
                Long year = Long.valueOf(raw.substring(0, 3));
                if(year % 400 == 0 || year % 4 == 0 && year % 100 != 0) {
                    if (day > 29) {
                        TaskPlugin.INSTANCE.logger.info("day 29");
                        return false;
                    }
                } else {
                    if(day > 28) {
                        TaskPlugin.INSTANCE.logger.info("day 28");
                        return false;
                    }
                }
            }
        } else {
            TaskPlugin.INSTANCE.logger.info("day");
            return false;
        }

        Long hour = Long.valueOf(String.valueOf(raw.charAt(8))) == 0 ? Long.valueOf(String.valueOf(raw.charAt(9))) : Long.valueOf(raw.substring(8, 9));
        if(hour < 0 || hour > 23) {
            TaskPlugin.INSTANCE.logger.info("hour");
            return false;
        }

        Long minute = Long.valueOf(String.valueOf(raw.charAt(10))) == 0 ? Long.valueOf(String.valueOf(raw.charAt(11))) : Long.valueOf(raw.substring(10, 11));
        if(minute < 0 || minute > 59) {
            TaskPlugin.INSTANCE.logger.info("hour");
            return false;
        }

        return true;
    }
}
