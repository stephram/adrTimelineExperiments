module stepheng.timelinecursors {
    requires javafx.controls;
    requires javafx.fxml;


    opens stepheng.timelinecursors to javafx.fxml;
    exports stepheng.timelinecursors;
}