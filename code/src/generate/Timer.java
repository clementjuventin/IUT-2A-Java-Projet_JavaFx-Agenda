package generate;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import java.text.DateFormat;
import java.util.*;
import java.text.SimpleDateFormat;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.metier.TimeZoneHourSet;

public class Timer{
    public static final String pattern = "HH:mm:ss";
    //Note: Changer en enum
    public static final String[] timeZone = {"Europe/Paris","America/New_York","Asia/Tokyo"};

    public static ObservableList<TimeZoneHourSet> getTimerProperty(){
        HashMap<DateFormat, SimpleStringProperty> hm = new HashMap<>();
        ObservableList<TimeZoneHourSet> timeZoneHourSets = FXCollections.observableArrayList();

        for(String tz:timeZone){
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setTimeZone(TimeZone.getTimeZone(tz));
            SimpleStringProperty sp = new SimpleStringProperty(dateFormat.format(new Date()));
            hm.put(dateFormat, sp);
            timeZoneHourSets.add(new TimeZoneHourSet(tz, sp));
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                actionEvent -> {
                    Date update = new Date();
                    for (Map.Entry<DateFormat, SimpleStringProperty> dateFormatSimpleStringPropertyEntry : hm.entrySet()) {
                        dateFormatSimpleStringPropertyEntry.getValue().set(dateFormatSimpleStringPropertyEntry.getKey().format(update));
                    }
                }

        ), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return timeZoneHourSets;
    }
}