import net.mamoe.mirai.console.plugin.description.PluginDependency;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.console.util.SemVersion;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TaskPlugin extends JavaPlugin {
    public static final TaskPlugin INSTANCE = new TaskPlugin();
    private TaskPlugin() {
        super(new JvmPluginDescriptionBuilder("com.v6486449j.task-plugin", "1.0.0")
        .author("6486449j")
        .info("A task plugin for mirai-console.")
        .build());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
