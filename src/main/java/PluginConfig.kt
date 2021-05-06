import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object PluginConfig : AutoSavePluginConfig("test_config") {
    val admin by value<Long>(0);
    val groups : List<Long> by value();
    val addTaskCmd : String by value("添加事务")
    val rmTaskCmd : String by value("删除事务")
    val lsTaskCmd : String by value("列出事务")
    val helpCmd : String by value("帮助")
}