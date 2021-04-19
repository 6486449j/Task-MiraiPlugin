import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JavaPluginScheduler;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

public final class TaskPlugin extends JavaPlugin {
    public static final TaskPlugin INSTANCE = new TaskPlugin();

    private MiraiLogger logger = getLogger();

    private TaskPlugin() {
        super(new JvmPluginDescriptionBuilder("com.v6486449j.task-plugin", "1.0.0")
        .author("6486449j")
        .info("A task plugin for mirai-console.")
        .build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {

    }

    @Override
    public void onEnable() {
        logger.info("TaskPlugin加载");

        TaskPlugin.INSTANCE.reloadPluginConfig(PluginConfig.INSTANCE);
        logger.info("加载配置");

        INSTANCE.getScheduler().delayed(2000, () -> {
            for(Bot bot : Bot.getInstances()) {
                bot.getEventChannel().registerListenerHost(new MyEventsListener());
                logger.info(String.valueOf(bot.getId()) + "注册群监听器");
            }
        });
    }

    @Override
    public void onDisable() {
    }
}
