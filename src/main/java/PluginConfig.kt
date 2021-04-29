import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object PluginConfig : AutoSavePluginConfig("test_config") {
    val admin by value<Long>(0);
    val groups : List<Long> by value();
    val dataPath : String by value();
}