import Bean.Task;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        String pattern = "^添加事务\\s+(\\d+)\\s+(.*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(rawMsg);

        if(m.find()) {
            String time = m.group(1);
            Date date = new Date();
            if(time.length() == 4) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                time = sdf.format(date) + time;
            }
            if(time.length() == 8) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                time = sdf.format(date) + time;
            }
            if(time.length() == 12) {
//                PluginData.INSTANCE.getTasks().add(String.valueOf(groupId) + " " + String.valueOf(menberId) + " " + time + " " + m.group(2));
                TaskPlugin.INSTANCE.tasks.getTasks().add(new Task(groupId, menberId, TaskType.Temp.getIndex(), Long.valueOf(time), m.group(2)));
                return true;
            }
        }
        return false;
    }
    public boolean removeTask(Long groupId, Long menberId, String rawMsg) {
        String pattern = "^删除事务\\s+(\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(rawMsg);
        String time = m.group(1);
        if(m.find()) {
            List<String> subList = new ArrayList<>();
            String pattern2 = "(\\d+)\\s(\\d+)\\s(\\d{12})\\s(.*)";
//            for(String s : PluginData.INSTANCE.getTasks()) {
//                Pattern p2 = Pattern.compile(pattern2);
//                Matcher m2 =p2.matcher(s);
//                if(m2.find() && m2.group(3) == time) {
//                    subList.add(s);
//                }
//            }
        }
        return false;
    }
    enum TaskType{
        Monthly(0),
        Weekly(1),
        Daily(2),
        Temp(3);

        public int index;
        TaskType(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
