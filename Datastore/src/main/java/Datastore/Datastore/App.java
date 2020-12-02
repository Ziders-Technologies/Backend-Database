package Datastore.Datastore;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class App
{
    public static void main( String[] args ) {
        FileClass fileClass = FileCreate.fileCreate();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println();
        Scanner sc= new Scanner(System.in);
        int op =10;
        while(op!=5){
            System.out.println("Select operation");
            System.out.println("1. Write Data");
            System.out.println("2. Read Single Data");
            System.out.println("3. Read All data");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            System.out.print("Enter operation number: ");
            op = Integer.parseInt(sc.nextLine());
            switch (op){
                case 1:
                    writeOp(fileClass);
                    break;
                case 2:
                    System.out.println("Enter key to be shown");
                    String key = sc.nextLine();
                    System.out.println("\n");
                    boolean readable= true;
                    JSONObject jsonObject = checkExisting(readOp(fileClass),key);
                    JSONObject nestedJsonObj = (JSONObject) jsonObject.get(key);
                    int ttl = Integer.parseInt(nestedJsonObj.get("ttl").toString());
                    System.out.println();
                    String addedDateStr = nestedJsonObj.get("addedTime").toString();
                    try {
                        Date addedDate = simpleDateFormat.parse(addedDateStr);
                        Date validDate = simpleDateFormat.parse(addedDateStr);
                        validDate.setSeconds(addedDate.getSeconds()+ttl);


                        Date date1 = new Date();
                        String currentTimeStr;
                        currentTimeStr = simpleDateFormat.format(date);
                        Date currentTime = simpleDateFormat.parse(currentTimeStr);

                        if(validDate.compareTo(currentTime) > 0){
                            System.out.println(jsonObject);

                        }
                        else{
                            System.out.println("The element has expired");
                            deleteExpired(fileClass,jsonObject);
                        }
                        System.out.println("\n");
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = readOp(fileClass);
                    System.out.println("\n");
                    for (int z=0;z<jsonArray.size();z++){
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1 = (JSONObject) jsonArray.get(z);
                        System.out.println(jsonObject1);
                    }
                    System.out.println("\n");
                    break;
                case 4:
                    deleteOp(fileClass);
                    break;
                case 5:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Invalid operation");
            }
        }

    }

    private static void deleteExpired(FileClass fileClass,JSONObject jsonObject){
        File file = fileClass.getFile();
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            JSONArray jsonArray = readOp(fileClass);
            if(jsonObject!=null){
                jsonArray.remove(jsonObject);
                System.out.println("Deleted the given element");
            }
            else {
                System.out.println("Element not found");
            }
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write("");
            printWriter.close();
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void deleteOp(FileClass fileClass) {
        File file = fileClass.getFile();
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            JSONArray jsonArray = readOp(fileClass);
            Scanner scanner = new Scanner(System.in);
            String key = scanner.nextLine();
            JSONObject jsonObject = checkExisting(jsonArray,key);
            if(jsonObject!=null){
                jsonArray.remove(jsonObject);
                System.out.println("Deleted the given element");
            }
            else {
                System.out.println("Element not found");
            }
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write("");
            printWriter.close();
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeOp(FileClass fileClass) {
        File file =  fileClass.getFile();
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            Scanner scanner = new Scanner(System.in);
            Data data = new Data();
            JSONArray jsonArray = readOp(fileClass);
            System.out.println("\n");
            System.out.println("Enter Object Key");
            data.setId(scanner.nextLine());
            if(data.getId().length()<33){
                JSONObject jsonObjectTemp = checkExisting(jsonArray,data.getId());
                if(jsonObjectTemp != null){
                    System.out.println("Element Already Exists");
                }
                else{
                    System.out.println("Enter Element Key");
                    data.setKey(scanner.nextLine());
                    System.out.println("Enter Element Value");
                    data.setValue(scanner.nextLine());
                    System.out.println("Enter TTL Value(in seconds)");
                    data.setTtl(scanner.nextLine());
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    data.setAddedTime(simpleDateFormat.format(date));
                    JSONObject jsonObject = new JSONObject(),nestedJsonObject = new JSONObject();
                    nestedJsonObject.put(data.getKey(),data.getValue());
                    nestedJsonObject.put("ttl",data.getTtl());
                    nestedJsonObject.put("addedTime",data.getAddedTime());
                    jsonObject.put(data.getId(),nestedJsonObject);
                    jsonArray.add(jsonObject);
                    PrintWriter printWriter = new PrintWriter(file);
                    printWriter.write("");
                    printWriter.close();
                    fileWriter.write(jsonArray.toJSONString());
                    fileWriter.flush();
                }
            }
            else {
                System.out.println("Enter Object Key less than 32 characters");
                fileWriter.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized static JSONArray readOp(FileClass fileClass) {
        File file = fileClass.getFile();
        JSONArray jsonArray = new JSONArray();
        try {
            FileReader fileReader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            try {
                Object obj = jsonParser.parse(fileReader);
                jsonArray = (JSONArray) obj;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                System.out.println("Empty File");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONObject checkExisting(JSONArray jsonArray,String key){
        for (int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject = (JSONObject) jsonArray.get(i);
            if(jsonObject.get(key) != null){
                return jsonObject;
            }
        }
        return null;
    }
}
