package com.example.martins.tsiapplication.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class TaskDO {

    private String name;
    private String details;
    private Date toDoDate;
    private String id;
    private String label;
    private String url;

    public TaskDO() {
    }

    public TaskDO(String id, String name, String details, String url) {
        this.name = name;
        this.details = details;
        this.url = url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getToDoDate() {
        return toDoDate;
    }

    public void setToDoDate(Date toDoDate) {
        this.toDoDate = toDoDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDO taskDO = (TaskDO) o;

        if (name != null ? !name.equals(taskDO.name) : taskDO.name != null) return false;
        if (details != null ? !details.equals(taskDO.details) : taskDO.details != null)
            return false;
        if (toDoDate != null ? !toDoDate.equals(taskDO.toDoDate) : taskDO.toDoDate != null)
            return false;
        return id != null ? id.equals(taskDO.id) : taskDO.id == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (toDoDate != null ? toDoDate.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskDO{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", toDoDate=" + toDoDate +
                ", id=" + id +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        final HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("details", details);
        result.put("url", url);
        result.put("label", label);

        return result;
    }
}
