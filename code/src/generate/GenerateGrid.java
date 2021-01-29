package generate;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import model.CalendarModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class GenerateGrid {

    private static ToggleButton trenteEtUnBtn = null;
    private static List<ToggleButton> februaryList = new ArrayList<>();

    public static GridPane generateDayGrid(GridPane gp, Double size, CalendarModel cm){
        int day;
        int currentDate = LocalDate.now().getDayOfMonth();
        ToggleButton btn;
        ToggleGroup group = new ToggleGroup();
        for (int i=0;i<=4;i++){
            for (int j=1;j<=7;j++){
                day = j+i*7;
                btn = new ToggleButton(String.valueOf(day));
                btn.setMinSize(size,size);
                gp.add(btn,j,i);
                btn.setToggleGroup(group);
                if(day==currentDate){
                    btn.setSelected(true);
                }
                btn.setOnAction(actionEvent -> {
                    if(!((ToggleButton)actionEvent.getSource()).getText().equals("")){
                        cm.setSelectedDay(parseInt(((ToggleButton)actionEvent.getSource()).getText()));
                    }
                });
                if(day>28){
                    februaryList.add(btn);
                }
                if(day==31){
                    btn.setVisible(false);
                    trenteEtUnBtn = btn;
                    break;
                }
            }
        }
        cm.getSelectedYear().addListener((observableValue, number, t1) -> {
            if(cm.getSelectedMonth().get()==2) {
                februaryFix(t1.intValue());
            }
        });
        cm.getSelectedMonth().addListener((observableValue, number, t1) -> {
            if (observableValue.getValue().intValue() == 2) {
                februaryFix(cm.getSelectedYear().get());
            } else {
                for (ToggleButton tgl : februaryList) {
                    tgl.setVisible(true);
                }
                trenteEtUnBtn.setVisible(observableValue.getValue().intValue() % 2 != 1);
            }
        });
        return gp;
    }
    private static void februaryFix(Integer currentYear){
        for(ToggleButton tgl:februaryList){
            tgl.setVisible(false);
        }
        if((currentYear-2020)%4==0){
            februaryList.get(0).setVisible(true);
        }
    }
}
