package UI.Gui;

import domain.Homework;

public class HomeworkEvent implements Event {
    public Homework getOldData() {
        return oldData;
    }

    public void setOldData(Homework oldData) {
        this.oldData = oldData;
    }

    public Homework getData() {
        return data;
    }

    public void setData(Homework data) {
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public void setType(ChangeEventType type) {
        this.type = type;
    }

    public HomeworkEvent(Homework oldData, Homework data, ChangeEventType type) {
        this.oldData = oldData;
        this.data = data;
        this.type = type;
    }

    private Homework oldData;
    private Homework data;
    private ChangeEventType type;


}
