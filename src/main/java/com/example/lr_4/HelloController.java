package com.example.lr_4;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class HelloController {

    // Аннотированные переменные для связывания с элементами интерфейса в FXML
    // Редактор HTML, в который будет загружаться контент из текстового документа
    @FXML
    private HTMLEditor htmlEditor;
    // Элемент меню для открытия файла
    @FXML
    private MenuItem openMenuItem;

    //Текстовое поле для ввода строки, которую нужно выделить в документе
    @FXML
    private TextField txtField;

    //Текстовое поле для пути к текстовому документу
    @FXML
    private TextField txtField_way;

    // Элемент меню для выделения ключевого слова в текстовом документе
    @FXML
    private MenuItem videlyator;

    // Метод для обработки открытия файла через элемент меню
    @FXML
    private void handleOpenMenuItemAction() {
        // Создание окна выбора файла
        FileChooser fileChooser = new FileChooser();
        // Устанавливаем заголовок окна выбора файла
        fileChooser.setTitle("Open DOCX File");
        // Добавляем фильтр для отображения только документов с расширением DOCX
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Documents", "*.docx"));
        // Открываем диалог и получаем выбранный файл
        File file = fileChooser.showOpenDialog(openMenuItem.getParentPopup().getScene().getWindow());

        // Если файл был выбран
        if (file != null) {
            try {
                // Создаем объект DocumentProperty с путем к выбранному файлу
                DocumentProperty document = new DocumentProperty(file.getPath());
                // Получаем HTML-версию содержимого документа
                String htmlContent = document.getHTML();
                // Вставляем полученный HTML-код в редактор HTMLEditor
                htmlEditor.setHtmlText(htmlContent);
            } catch (IOException e) {
                // Если произошла ошибка при загрузке файла, выводим стек ошибки
                e.printStackTrace();
            }
        }
    }

    // Метод для обработки действия по выделению текста в документе
    @FXML
    private void videlyatorClick(){
        // Создаем объект DocumentProperty с пустым путем
        DocumentProperty document = new DocumentProperty("");
        // Вызываем метод highlite, передавая введенные в поля данные о ключевом слове и пути до файла
        document.highlite(txtField.getText(), txtField_way.getText());

    }
}
