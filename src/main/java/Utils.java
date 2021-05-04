import java.text.SimpleDateFormat;

public class Utils {
    public static boolean timeFormater(String raw) {
        //开始检查时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            sdf.format(raw);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
