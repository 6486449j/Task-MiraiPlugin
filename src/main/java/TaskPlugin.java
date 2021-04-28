import Bean.Task;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class TaskPlugin extends JavaPlugin {
    public static final TaskPlugin INSTANCE = new TaskPlugin();

    public MiraiLogger logger = getLogger();

    private List<TaskChecker> checkers = new ArrayList<>();

    private List<Task> tasks = new ArrayList<>();

    private TaskPlugin() {
        super(new JvmPluginDescriptionBuilder("com.v6486449j.task-plugin", "1.0.0")
        .author("6486449j")
        .info("一个管理事务的mirai-console插件")
        .build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        //加载数据
        try {
            File path = new File("./config/task_plugin/");
            File file = new File(path, "json_test.json");
            if(!path.exists()) {
                path.mkdirs();
            }
            if(!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String str = "";
            while((str = br.readLine()) != null) {}
            JSONObject jsonObject = JSONObject.parseObject(str);

            fis.close();
        } catch(Exception e) {
            e.printStackTrace();
        } /*finally {

        }*/
    }

    @Override
    public void onEnable() {
        logger.info("TaskPlugin加载");

        //加载配置
        TaskPlugin.INSTANCE.reloadPluginConfig(PluginConfig.INSTANCE);
        TaskPlugin.INSTANCE.reloadPluginData(PluginData.INSTANCE);

        INSTANCE.getScheduler().delayed(2000, () -> {
            //获取所有Bot实例
            for(Bot bot : Bot.getInstances()) {
                //注册监听器
                bot.getEventChannel().registerListenerHost(new MyEventsListener());

                //checkers.add(new TaskChecker(bot));
            }
            for(TaskChecker ch : checkers) {
                //INSTANCE.getScheduler().repeating(10000, ch);
            }
        });
    }

    @Override
    public void onDisable() {

    }
}
