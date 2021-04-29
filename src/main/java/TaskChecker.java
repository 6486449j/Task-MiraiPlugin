import Bean.Task;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.Bot;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class TaskChecker implements Runnable{
    private Bot bot;

    public TaskChecker(Bot bot) {
        this.bot = bot;
    }
    @Override
    public void run() {
        TaskPlugin.INSTANCE.logger.info("检查事务");

        List<Task> tasks = TaskPlugin.INSTANCE.tasks.getTasks();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");

        Collection<Task> removed = new ArrayList<>();
        for(Task task : tasks) {
            if(Long.valueOf(sdf.format(date)) >= task.getTime()) {
                if(task.getGroupId() == 0) {
                    bot.getFriend(task.getMenberId()).sendMessage(task.getTaskContent());
                } else {
                    bot.getGroup(task.getGroupId()).sendMessage(task.getTaskContent());
                }

                removed.add(task);
            }
        }

        tasks.removeAll(removed);

        try {
            FileOutputStream fos = new FileOutputStream(TaskPlugin.INSTANCE.configFile);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            String jsonString = JSONObject.toJSONString(tasks);

            bw.write(jsonString);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
