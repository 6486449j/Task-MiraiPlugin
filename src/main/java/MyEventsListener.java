import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;

public class MyEventsListener extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }
    @EventHandler
    public void groupMsgEvent(@NotNull GroupMessageEvent event) throws Exception {
        if(event.getGroup().getId() == 782515841L) {
//            String msg = event.getMessage().toString();
            String msg = event.getMessage().contentToString();
            event.getSubject().sendMessage("发送了消息" + msg);
        }
    }
    @EventHandler
    public void friendMsgEvent(@NotNull FriendMessageEvent event) throws Exception {
        if(event.getFriend().getId() == 1966063360L) {
//            String msg = event.getMessage().toString();
            String msg = event.getMessage().contentToString();
            event.getSubject().sendMessage("发送了消息" + msg);
        }
    }
}
