package org.example;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        Scanner in = new Scanner(System.in);

        System.out.print("Введите свою задачу: ");
        String task = in.nextLine();
        System.out.print("\n" + "Введите имя: ");
        String nameInput = in.nextLine();


        try {
           Object obj = new JSONParser().parse(new FileReader("test.json"));
            JSONObject jo = (JSONObject) obj;

            jo.put("task",task);
            jo.put("name", nameInput);



            String Task = (String) jo.get("task");
            String name = (String) jo.get("name");


            System.out.println("Задача: " + Task);

            System.out.println("От: " + name);


            try (FileWriter file = new FileWriter("test.json")) {

                file.write(((JSONObject) obj).toJSONString());
                file.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }
}