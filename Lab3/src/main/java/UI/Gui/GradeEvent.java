package UI.Gui;

import domain.Grade;

public class GradeEvent implements Event {
    public Grade getOldData() {
        return oldData;
    }

    public void setOldData(Grade oldData) {
        this.oldData = oldData;
    }

    public Grade getData() {
        return data;
    }

    public void setData(Grade data) {
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public void setType(ChangeEventType type) {
        this.type = type;
    }

    public GradeEvent(Grade oldData, Grade data, ChangeEventType type) {
        this.oldData = oldData;
        this.data = data;
        this.type = type;
    }

    private Grade oldData;
    private Grade data;
    private ChangeEventType type;


}
