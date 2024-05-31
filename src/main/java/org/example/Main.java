package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.print.attribute.standard.JobName;
import javax.swing.plaf.metal.OceanTheme;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class Main {

    private static String empty = "notNull";


    public static void main(String[] args) {
        System.out.println("Добро пожаловать в задачник! " + "\n");
        inputCommand();


    }

    public static void addTask(JSONArray jsonArray, String task, String name) {
        JSONObject taskObj = new JSONObject();
        taskObj.put("task", task);
        taskObj.put("name", name);
        empty = "notNull";

        jsonArray.add(taskObj);
    }


    public static void printTask() {







            if (empty == null) {
                System.out.println("Файл пуст, поставьте новую задачу - /task");
                System.out.println();
                inputCommand();
                return;
            } else {
                try {

                    JSONParser jsonParser = new JSONParser();
                    FileReader reader = new FileReader("test.json");
                    Object object = jsonParser.parse(reader);
                    JSONArray tasklist = (JSONArray) object;

                for (Object taskObj : tasklist) {
                    JSONObject task = (JSONObject) taskObj;
                    String taskName = (String) task.get("task");
                    String name = (String) task.get("name");
                    System.out.println("|###################################|");
                    System.out.println("|Задача: " + taskName + ", Имя: " + name + "|");
                    System.out.println("|###################################|" + "\n");
                }
            } catch(FileNotFoundException e){
                throw new RuntimeException("Файл не найден", e);
            } catch(IOException e){
                throw new RuntimeException("Ошибка ввода-вывода", e);
            } catch(ParseException e){
                throw new RuntimeException("Ошибка парсинга JSON", e);
            }
                inputCommand();
        }

    }

    public static void taskLogic(String task, String nameInput) {

        Scanner in = new Scanner(System.in);
        empty = "notNull";

        try {
            // Загружаем существующий JSON массив из файла
            JSONArray jsonArray;
            try {
                Object obj = new JSONParser().parse(new FileReader("test.json"));
                jsonArray = (JSONArray) obj;
            } catch (FileNotFoundException e) {
                // Если файла нет, создаем новый JSON массив
                jsonArray = new JSONArray();
            } catch (ParseException e) {
                // Если возникла ошибка парсинга, создаем новый JSON массив
                jsonArray = new JSONArray();
            }

            // Добавляем новую задачу

            addTask(jsonArray, task, nameInput);

            // Записываем обновленный JSON массив обратно в файл
            try (FileWriter file = new FileWriter("test.json")) {
                file.write(jsonArray.toJSONString());
                file.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        inputCommand();


    }

    public static void deleteTask() {
        try {
            File file = new File("test.json");
            file.delete();
            file.createNewFile();

            empty = null;


            inputCommand();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public static void helpComm() {
        System.out.println("/task \t - \t  Даёт вам возможность писать задачи  ");
        System.out.println("/view \t - \t  Просмотр всех ваших задач ");
        System.out.println("/delete \t - \t Удаление задачи");

        inputCommand();


    }

    public static void inputCommand() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите команду:  ");
        String command = in.nextLine();

        switch (command) {
            case "/task":
                System.out.print("\n" + "Введите задачу:");
                String task = in.nextLine();

                System.out.print("\n" + "Введите имя:");
                String nameInput = in.nextLine();

                taskLogic(task, nameInput);
                break;
            case "/help":
                helpComm();
                break;
            case "/view":
                printTask();
                break;
            case "/delete":
                deleteTask();
                break;

            default:
                System.out.println("Вы ввели неправильную команду ");
            
                helpComm();
                break;


        }
    }


}
