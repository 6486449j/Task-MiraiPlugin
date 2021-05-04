package Bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class Task /*implements Serializable*/ {
    private Long groupId;
    private Long menberId;
    private int type;
    private String time;
    private String taskContent;

    public Task(Long groupId, Long menberId, int type, String time, String taskContent) {
        this.groupId = groupId;
        this.menberId = menberId;
        this.type = type;
        this.time = time;
        this.taskContent = taskContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMenberId() {
        return menberId;
    }

    public void setMenberId(Long menberId) {
        this.menberId = menberId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

}
