package model.metier;

import javafx.beans.property.SimpleStringProperty;

public class TimeZoneHourSet {
    private String timeZone;
    private SimpleStringProperty hourProperty;

    public String getTimeZone() {
        return timeZone;
    }

    public String getHourProperty() {
        return hourProperty.get();
    }

    public SimpleStringProperty hourPropertyProperty() {
        return hourProperty;
    }

    public TimeZoneHourSet(String timeZone, SimpleStringProperty hourProperty) {
        this.timeZone = timeZone;
        this.hourProperty = hourProperty;
    }
}
