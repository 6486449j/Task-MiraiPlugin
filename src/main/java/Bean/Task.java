package Bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class Task /*implements Serializable*/ {
    private Long groupId;
    private Long menberId;
    private String type;
    private Long time;
    private String taskContent;

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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
