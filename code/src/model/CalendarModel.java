package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.metier.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class CalendarModel {

    private LocalDate date;
        public LocalDate getDate() {
            return date;
        }
        public void setDate(LocalDate date) {
            this.date = date;
        }

    private StringProperty title = new SimpleStringProperty();
        public StringProperty titleProperty(){return title;}
        public StringProperty getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title.set(title);
        }

    private StringProperty description = new SimpleStringProperty();
        public StringProperty descriptionProperty(){return description;}
        public StringProperty getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description.set(description);
        }

    private StringProperty currentDateLabel = new SimpleStringProperty();
        public StringProperty currentDateLabelProperty(){return currentDateLabel;}
        public StringProperty getCurrentDateLabel() {
            return currentDateLabel;
        }
        public void setCurrentDateLabel(String currentDateLabel) {
            this.currentDateLabel.set(currentDateLabel);
        }
    //Note: Il faudrait stocker une enum avec chaque langue afin de laisser le choix à l'utilisateur
    public final ObservableList<String> monthOptions =
            FXCollections.observableArrayList(
                    "Janvier",
                    "Février",
                    "Mars",
                    "Avril",
                    "Mai",
                    "Juin",
                    "Juillet",
                    "Août",
                    "Septembre",
                    "Octobre",
                    "Novembre",
                    "Décembre"
            );
        public ListProperty<String> month = new SimpleListProperty(monthOptions);
        public ObservableList<String> getMonth() {
            return month.get();
        }
        public ListProperty<String> monthProperty() {
            return month;
        }
        public void setMonth(ObservableList<String> month) {this.month.set(month);}

    private IntegerProperty selectedYear = new SimpleIntegerProperty(LocalDate.now().getYear());
        public IntegerProperty selectedYearProperty(){return selectedYear;}
        public IntegerProperty getSelectedYear() {
            return selectedYear;
        }
        public void setSelectedYear(Integer selectedYear) {
            this.selectedYear.set(selectedYear);
            updateDate();
        }

    private IntegerProperty selectedMonth = new SimpleIntegerProperty(LocalDate.now().getMonthValue());
        public IntegerProperty selectedMonthProperty(){return selectedMonth;}
        public IntegerProperty getSelectedMonth() {
            return selectedMonth;
        }
        public void setSelectedMonth(String selectedMonth) {
            this.selectedMonth.set(monthOptions.indexOf(selectedMonth)+1);
            updateDate();
        }

    private IntegerProperty selectedDay = new SimpleIntegerProperty(LocalDate.now().getDayOfMonth());
        public IntegerProperty selectedDayProperty(){return selectedDay;}
        public IntegerProperty getSelectedDay() {
            return selectedDay;
        }
        public void setSelectedDay(Integer selectedDay) {
            this.selectedDay.set(selectedDay);
            updateDate();
        }
    //Liste des années visibles dans la combobox (année de la plus ancienne tâche + 5ans)
    private ObservableList<String> theYears = FXCollections.observableArrayList();
        public ListProperty<String> years = new SimpleListProperty(theYears);
            public ObservableList<String> getYears() {
                return years.get();
            }
            public ListProperty<String> yearsProperty() {
                return years;
            }
        public void setYears(ObservableList<String> years) {this.years.set(years);}

    //Fonction qui permet d'update la propriété date
    //Note: Il faudrait faire une classe qui se bind sur les trois propriétés en même temps afin de faire une seule
    //propriété qui change lorsque l'une de ses trois propriétés change + compute avec une mise en forme comme ci dessous
    private void updateDate(){
        LocalDate date = LocalDate.of(selectedYearProperty().getValue(),selectedMonthProperty().getValue(),selectedDayProperty().getValue());
        setDate(date);
        String day = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - EEEE", Locale.FRENCH)).toUpperCase();
        setCurrentDateLabel(day);
    }

    public Task getTask(){
        String ttl = title.getValue();
        if(ttl.equals("")){
            return null;
        }
        return new Task(ttl,description.getValue(),getDate());
    }

    public CalendarModel(Integer firstYear){
        for (int i=firstYear;i<LocalDate.now().getYear()+5;i++){
            years.add(String.valueOf(i));
        }
        selectedYearProperty().set(firstYear);
        updateDate();
    }
}
