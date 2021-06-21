package com.app.namllahuser.data.model;

public class UserFirebaseJaveModel {
    private long complete_at;
    private long duration;
    private boolean is_working;
    private long order_id;
    private String type;

    UserFirebaseJaveModel(){

    }

    public long getComplete_at() {
        return complete_at;
    }

    public void setComplete_at(long complete_at) {
        this.complete_at = complete_at;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isIs_working() {
        return is_working;
    }

    public void setIs_working(boolean is_working) {
        this.is_working = is_working;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
