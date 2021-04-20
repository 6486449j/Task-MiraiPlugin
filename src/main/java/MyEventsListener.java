import jdk.tools.jlink.plugin.Plugin;
import kotlin.PublishedApi;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyEventsListener extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }

    @EventHandler
    public void groupMsgEvent(@NotNull GroupMessageEvent event) throws Exception {
        if(PluginConfig.INSTANCE.getGroups().contains(event.getGroup().getId())) {
            if(addTask(event.getGroup().getId(), event.getSender().getId(), event.getMessage().contentToString())) {
                event.getSubject().sendMessage(PluginData.INSTANCE.getTasks().toString());
            }
        }
    }

    @EventHandler
    public void friendMsgEvent(@NotNull FriendMessageEvent event) throws Exception {
        if(event.getFriend().getId() == PluginConfig.INSTANCE.getAdmin()) {
            if(addTask(0L, event.getSubject().getId(), event.getMessage().contentToString())) {
                event.getSubject().sendMessage(PluginData.INSTANCE.getTasks().toString());
            }
        }
    }

    public boolean addTask(Long groupId, Long menberId, String rawMsg) {
        String pattern = "^添加事务\\s+(\\d{12})\\s+(.*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(rawMsg);

        if(m.find()) {
//            subject.sendMessage("匹配！\n组数：" + m.groupCount() + "\n组0： " + m.group(0) + "\n组1：" + m.group(1) + "\n组2：" + m.group(2));
            PluginData.INSTANCE.getTasks().add(String.valueOf(groupId + " " + menberId) + " " + m.group(1) + " " + m.group(2));
//            subject.sendMessage(PluginData.INSTANCE.getTasks().toString());
        }
        return true;
    }
}
