package com.example.lr_4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Определение основного класса приложения, который наследует от Application
public class HelloApplication extends Application {

    // Переопределение метода start, который запускается при старте приложения
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загружаем FXML файл для UI, используя путь к файлу
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lr_4/hello-view.fxml"));
        // Загружаем корневой элемент из FXML файла
        Parent root = loader.load();
        // Устанавливаем заголовок окна приложения
        primaryStage.setTitle("DOCX Viewer");
        // Создаем сцену с корневым элементом и устанавливаем ее в окно приложения
        primaryStage.setScene(new Scene(root));
        // Показываем окно приложения
        primaryStage.show();
    }
    // Метод main, который запускает приложение
    public static void main(String[] args) {
        launch();
    }
}
