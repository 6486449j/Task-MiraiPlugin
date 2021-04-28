import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object PluginData : AutoSavePluginData("test_data") {
    //数据格式：对象：（时间：事务）
    //时间格式：yyyyMMddhhmm
    val tasks : List<String> by value();
}