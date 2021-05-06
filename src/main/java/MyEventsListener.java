import Bean.Task;
import com.alibaba.fastjson.JSONObject;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyEventsListener extends SimpleListenerHost {
    private boolean removeTaskFlag = false;
    private List<Task> removingTaskList = null;
    private int removeListLenght = -1;
    private Long removingTaskMenber = 0L;

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }

    // 群组事件监听
    @EventHandler
    public void groupMsgEvent(@NotNull GroupMessageEvent event) throws Exception {
        if(PluginConfig.INSTANCE.getGroups().contains(event.getGroup().getId())) {
            boolean[] addTaskResult = testAddTask(event.getGroup().getId(), event.getSender().getId(), event.getMessage().contentToString());

            if(addTaskResult[0]) {
                if(addTaskResult[1]) {
                    event.getSubject().sendMessage(JSONObject.toJSONString(TaskPlugin.INSTANCE.tasks));
                } else {
                    event.getSubject().sendMessage("时间格式错误，请检查命令");
                }
            }

            RemoveTaskResult removeResult = testRemoveTask(event.getGroup().getId(), event.getSender().getId(), event.getMessage().contentToString());

            if(removeResult.isStatus()) {
                if(removeResult.getStr() == "") {
                    event.getSubject().sendMessage("删除事务成功");
                } else {
                    event.getSubject().sendMessage(removeResult.getStr());
                }
            }
        }
    }

    // 私聊事件监听
    @EventHandler
    public void friendMsgEvent(@NotNull FriendMessageEvent event) throws Exception {
        if(event.getFriend().getId() == PluginConfig.INSTANCE.getAdmin()) {
            boolean[] addTaskResult = testAddTask(0L, event.getSubject().getId(), event.getMessage().contentToString());
            if(addTaskResult[0]) {
                if(addTaskResult[1]) {
                    event.getSubject().sendMessage(JSONObject.toJSONString(TaskPlugin.INSTANCE.tasks));
                } else {
                    event.getSubject().sendMessage("时间格式错误，请检查命令");
                }
            }
        }
    }

    // 测试添加事务命令
    public boolean[] testAddTask(Long groupId, Long menberId, String rawMsg) {
        boolean[] result = new boolean[] { false, true };

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
                if(Utils.timeFormater(time)) {
                    TaskPlugin.INSTANCE.tasks.getTasks().add(new Task(groupId, menberId, TaskType.Temp.getIndex(), time, m.group(2)));
                    result[0] = true;
                    TaskPlugin.INSTANCE.logger.info("时间检查通过");
                } else {
                    result[0] = true;
                    result[1] = false;
                    TaskPlugin.INSTANCE.logger.info("时间检查未通过");
                }
            }
        }

        return result;
    }

    // 测试删除事务命令
    public RemoveTaskResult testRemoveTask(Long groupId, Long menberId, String rawMsg) {
        RemoveTaskResult result = new RemoveTaskResult(false, "");
        if(removeTaskFlag && removingTaskMenber == menberId) {
            String pattern = "\\s+(\\d+)";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(rawMsg);
            if(m.find()) {
                int removingIndex = Integer.valueOf(m.group(1));
                TaskPlugin.INSTANCE.tasks.getTasks().remove(removingTaskList.get(removingIndex));
                result.setStatus(true);
                return result;
            }
        }

        String pattern = "^删除事务\\s+(\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(rawMsg);
        String time;

        if(m.find()) {
            time = m.group(1);

            Date date = new Date();

            if(time.length() == 4) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                time = sdf.format(date) + time;
            }

            if(time.length() == 8) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                time = sdf.format(date) + time;
            }

            List<Task> subList = new ArrayList();
            for(Task task : TaskPlugin.INSTANCE.tasks.getTasks()) {
                if(task.getGroupId() == groupId && task.getMenberId() == menberId && task.getTime() == time) {
                    subList.add(task);
                }
            }

            if(subList.size() >= 1) {
                removeTaskFlag = true;
                if(subList.size() == 1) {
                    TaskPlugin.INSTANCE.tasks.getTasks().remove(subList.get(0));
                    result.setStatus(true);
                    return result;
                } else {
                    removingTaskList = subList;
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for(Task t : subList) {
                        i++;
                        sb.append("您在该时间段有如下事务，请输入序号以删除：\n");
                        if(i < 10) sb.append("["); else sb.append("[ ");
                        sb.append(i);
                        sb.append("]");
                        sb.append(t.getTaskContent());
                        sb.append("\n");
                    }
                    removeListLenght = i;

                    result.setStr(sb.toString());
                    return result;
                }
            }
        }
        return result;
    }

    enum TaskType {
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
    class RemoveTaskResult {
        boolean status;
        String str;

        public RemoveTaskResult(boolean status, String str) {
            this.status = status;
            this.str = str;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }
}
