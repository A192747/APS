package com.example.Kurs.models;

import com.example.Kurs.controllers.MainController;
import com.example.Kurs.models.dispatchers.DispatcherInput;
import com.example.Kurs.models.dispatchers.DispatcherOutput;
import com.example.Kurs.models.streams.InputStream;
import com.example.Kurs.models.streams.OutputStream;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.util.Map;

public class Statistics {
    private static int countApp = MainController.countOfApps;
    private static int countSource = MainController.countOfSources;
    private static int countDevice = MainController.countOfDevices;
    private static int bufferSize = MainController.bufferSize;
    private static Buffer buffer = MainController.buffer;
    private static DispatcherInput dispInput = MainController.dispatcherInput;
    private static DispatcherOutput dispOutput = MainController.dispatcherOutput;
    private static InputStream inputStream = MainController.inputStream;
    private static OutputStream outputStream = MainController.outputStream;

    public Statistics(int countApp, int countSource, int countDevice, int bufferSize) {

    }

    public static void getSystemStatus(TextArea out){
        StringBuilder str = new StringBuilder();
        str.append("------------------NEXT STEP----------------\n");
        str.append("SYSTEM TIME: " + MainController.systemTime + "\n");
        str.append("SOURCE" + "\n");
        str.append(((dispInput.readApp() != null && MainController.visibleApp) ? dispInput.readApp() : null) + "\n");
        str.append("BUFFER" + "\n");
        if(!buffer.isEmpty()) {
            for (Application app : buffer.getBufferApps()) {
                str.append(app + "\n");
            }
        } else {
            str.append(buffer.getBufferApps() + "\n");
        }
        str.append("DEVISES" + "\n");
        str.append(outputStream.getDevices() + "\n");

        if(out != null)
            out.appendText(str.toString());
        else
            System.out.println(str);
    }
    public static Map<Integer, Integer> getCountOfAppFromSources() {
        return inputStream.getInfoFromSources();
    }
    public static void getProbabilityRefusalAverage() {
        System.out.println(buffer.getCountOfRefusals() / countApp);
    }
    public static Map<Integer, Double> getProbabilityRefusalForSources() {
        return buffer.getProbabilityOfRefusalsForSources();
    }
    public static void getAverageTimeOfApp() {
        //Это время прибывания заявки в системе или на приборе?
        System.out.println();
    }
    public static void getAverageTimeOfWait() {

    }
    public static void getCountOfRefusalsForSources() {
        System.out.println(buffer.getCountOfRefusalsForSources());
    }
    public static void getAverageTimeOfWork() {
        System.out.println(MainController.outputStream.getWorkTimeForSources());
    }
    public static void getVariance() {

    }

    public static void getDeviceUse() {

    }

    
}
