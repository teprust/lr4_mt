package com.example.lr_4;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;
import java.io.*;



public class DocumentProperty {

    // Путь к docx-файлу, передается из Controller
    private final String path;

    // Конструктор для инициализации пути к файлу
    public DocumentProperty(String path) {
        this.path = path;
    }

    // Метод для получения содержимого docx-файла в HTML формате
    public String getHTML() throws IOException {
        // Создание StringBuilder для формирования HTML
        StringBuilder htmlContent = new StringBuilder();
        // Добавление начальных тегов HTML
        htmlContent.append("<html><body>");

        // Открытие docx-файла для чтения
        try (FileInputStream fis = new FileInputStream(path);
             // Чтение файла через Apache POI
             XWPFDocument document = new XWPFDocument(fis)) {
            // Перебор всех параграфов в документе
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                // Открывающий тег параграфа в HTML
                htmlContent.append("<p>");

                // Перебор всех "ранов" (частей текста) в текущем параграфе
                for (XWPFRun run : paragraph.getRuns()) {
                    // Получение текста из текущего рана
                    String text = run.text();
                    // Проверка на непустой текст
                    if (text != null && !text.isEmpty()) {
                        // Замена \n на <br> для HTML
                        htmlContent.append(text.replaceAll("\n", "<br>"));
                    }
                }
                // Закрывающий тег параграфа
                htmlContent.append("</p>");
            }
        }
        // Закрывающие теги HTML
        htmlContent.append("</body></html>");
        // Возвращение HTML-контента как строки
        return htmlContent.toString();
    }

    // Метод для поиска и выделения текста в документе
    public void highlite(String str, String str_way) {
        // Путь к исходному файлу для чтения
        String inputFilePath = str_way;
        // Путь к выходному файлу для записи
        String outputFilePath = "D:\\Институт\\Магистратура\\3 семестр\\Мультимедиа технологии\\ЛР4\\high_d.docx";
        // Ключевое слово для поиска и выделения
        String searchTerm = str;

        // Чтение и запись файла через FileInputStream и FileOutputStream
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             // Открытие docx-файла для редактирования
             XWPFDocument document = new XWPFDocument(fis);
             // Открытие потока записи
             FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            // Перебор параграфов в документе
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                // Перебор ранов в параграфе
                for (int i = 0; i < paragraph.getRuns().size(); i++) {
                    // Получение текущего рана
                    XWPFRun run = paragraph.getRuns().get(i);
                    // Извлечение текста из текущего рана
                    String text = run.text();
                    // Проверка на наличие вхождений слова для выделения
                    int index = text.indexOf(searchTerm);
                    // Проверка наличия вхождений искомого слова
                    if (index != -1) {
                        // Удаляем текущий ран, поскольку будем его заменять
                        paragraph.removeRun(i);
                        // Инициализация начальной позиции для поиска всех подвхождений
                        int lastIndex = 0;
                        // Цикл для обработки всех вхождений в ране
                        while (index != -1) {
                            // Если до найденного вхождения есть текст
                            if (index > lastIndex) {
                                // Создание нового пред-рана (до вхождения)
                                XWPFRun beforeRun = paragraph.insertNewRun(i++);
                                // Запись текста до вхождения
                                beforeRun.setText(text.substring(lastIndex, index));
                            }

                            // Создание рана для выделенного текста
                            XWPFRun highlightRun = paragraph.insertNewRun(i++);
                            // Установка текста для выделения
                            highlightRun.setText(searchTerm);
                            // Установка желтого цвета
                            highlightRun.getCTR().addNewRPr().addNewHighlight().setVal(STHighlightColor.YELLOW);

                            // Обновление индекса для поиска следующего вхождения
                            // Позиция после текущего вхождения
                            lastIndex = index + searchTerm.length();
                            // Поиск следующего вхождения
                            index = text.indexOf(searchTerm, lastIndex);
                        }

                        // Добавление оставшегося текста после последнего вхождения
                        // Проверка на наличие текста после последнего вхождения
                        if (lastIndex < text.length()) {
                            // Создание нового рана для оставшегося текста
                            XWPFRun afterRun = paragraph.insertNewRun(i);
                            // Установка оставшегося текста
                            afterRun.setText(text.substring(lastIndex));
                        }
                    }
                }
            }

            // Записываем изменения в новый файл
            document.write(fos);

        } catch (IOException e) {
            // Вывод стека ошибок, если возникло исключение ввода-вывода
            e.printStackTrace();
        }
    }

}
