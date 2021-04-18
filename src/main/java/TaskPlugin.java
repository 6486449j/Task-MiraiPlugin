import net.mamoe.mirai.console.plugin.description.PluginDependency;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription;
import net.mamoe.mirai.console.util.SemVersion;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TaskPlugin extends JavaPlugin {
    public static final TaskPlugin INSTANCE = new TaskPlugin();
    private TaskPlugin() {
        super(new JvmPluginDescription() {
            @NotNull
            @Override
            public String getId() {
                return "";
            }

            @NotNull
            @Override
            public String getName() {
                return "Task-MiraiPlugin";
            }

            @NotNull
            @Override
            public String getAuthor() {
                return "6486449j";
            }

            @NotNull
            @Override
            public SemVersion getVersion() {
                return new SemVersion(1, 0, 0, null, null);
            }

            @NotNull
            @Override
            public String getInfo() {
                return "A task plugin for mirai";
            }

            @NotNull
            @Override
            public Set<PluginDependency> getDependencies() {
                return null;
            }
        });
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
