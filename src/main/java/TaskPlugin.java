import Bean.Task;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JavaPluginScheduler;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public final class TaskPlugin extends JavaPlugin {
    public static final TaskPlugin INSTANCE = new TaskPlugin();

    public MiraiLogger logger = getLogger();

    private List<TaskChecker> checkers = new ArrayList<>();

    private TaskPlugin() {
        super(new JvmPluginDescriptionBuilder("com.v6486449j.task-plugin", "1.0.0")
        .author("6486449j")
        .info("一个管理事务的mirai-console插件")
        .build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {

    }

    @Override
    public void onEnable() {
        logger.info("TaskPlugin加载");

        TaskPlugin.INSTANCE.reloadPluginConfig(PluginConfig.INSTANCE);
//        logger.info("加载配置");
        TaskPlugin.INSTANCE.reloadPluginData(PluginData.INSTANCE);
//        logger.info("加载数据");

        INSTANCE.getScheduler().delayed(2000, () -> {
            for(Bot bot : Bot.getInstances()) {
                bot.getEventChannel().registerListenerHost(new MyEventsListener());
                logger.info(String.valueOf(bot.getId()) + "注册监听器");
                //checkers.add(new TaskChecker(bot));
            }
            for(TaskChecker ch : checkers) {
                //INSTANCE.getScheduler().repeating(10000, ch);
            }
            Task task = new Task();
            task.setMenberId(1966063360L);
            task.setGroupId(123456789L);
            task.setTime(202104211300L);
            task.setTaskContent("测试内容");
            task.setType("group");
            try {
                File path = new File("./config/task_plugin/");
                File file = new File(path, "json_test.json");
                if(!path.exists()) {
                    path.mkdirs();
                }
                if(!file.exists()) {
                    file.createNewFile();
                }
                FileReader fr = new FileReader(file);
//                fr.read();
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDisable() {

    }
}
