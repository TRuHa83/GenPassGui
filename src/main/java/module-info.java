module es.truhatechdev.genpassgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.truhatechdev.genpassgui to javafx.fxml;
    exports es.truhatechdev.genpassgui;
}