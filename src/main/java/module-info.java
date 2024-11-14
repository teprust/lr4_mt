module com.example.lr_4 {
    requires javafx.fxml;
    requires org.apache.tika.core;
    requires javafx.web;
    requires org.apache.poi.ooxml;


    opens com.example.lr_4 to javafx.fxml;
    exports com.example.lr_4;
}