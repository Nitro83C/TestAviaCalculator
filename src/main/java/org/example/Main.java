package org.example;
import java.io.IOException;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        //String  fileFolder = "C:/Users/guram/IdeaProjects/TestAviaCalculator/tickets.json";
        String  fileFolder = "src/main/resources/tickets.json";
        JsonParser jsonParser = new JsonParser();
        jsonParser.fileToListOfPojos(fileFolder);
        Map<String, Long> map =  jsonParser.getMap();
        map.forEach((k, v) -> System.out.println("Авиаперевозчик: " + k + ", Минимальное время полета в минутах: " + v));
        System.out.println("Разница между средней и медианной ценной равна " + jsonParser.getDifPrice());
    }
}