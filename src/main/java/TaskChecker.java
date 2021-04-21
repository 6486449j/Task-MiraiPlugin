import jdk.tools.jlink.plugin.Plugin;
import net.mamoe.mirai.Bot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskChecker implements Runnable{
    private Bot bot;

    public TaskChecker(Bot bot) {
        this.bot = bot;
    }
    @Override
    public void run() {
        TaskPlugin.INSTANCE.logger.info("检查事务");

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Long date = Long.valueOf(sdf.format(d));

        TaskPlugin.INSTANCE.logger.info(date.toString());

        String pattern = "(\\d+)\\s(\\d+)\\s(\\d{12})\\s(.*)";

        List<String> list = new ArrayList<>();

//        for(String s : PluginData.INSTANCE.getTasks()) {
//            Pattern p = Pattern.compile(pattern);
//            Matcher m = p.matcher(s);
//
//            TaskPlugin.INSTANCE.logger.info("正则测试");
//
//            if(m.find() && date >= Long.valueOf(m.group(3))) {
//                if(Long.valueOf(m.group(1)) != 0) {
//                    bot.getGroup(Long.valueOf(m.group(1))).sendMessage("事务" + m.group(4));
//                } else {
//                    bot.getFriend(Long.valueOf(m.group(2))).sendMessage("事务" + m.group(4));
//                }
//                list.add(s);
//            }
//        }

        for(String s : list) {
            PluginData.INSTANCE.getTasks().remove(s);
        }
    }
}
