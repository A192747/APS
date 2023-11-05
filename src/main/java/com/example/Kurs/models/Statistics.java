package com.example.Kurs.models;

import com.example.Kurs.controllers.MainController;
import com.example.Kurs.models.dispatchers.DispatcherInput;
import com.example.Kurs.models.dispatchers.DispatcherOutput;
import com.example.Kurs.models.streams.InputStream;
import com.example.Kurs.models.streams.OutputStream;

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

    public static void getSystemStatus(){
        System.out.println("------------------NEXT STEP----------------");
        System.out.println("SYSTEM TIME: " + MainController.systemTime);
        System.out.println("SOURCE");
        System.out.println((dispInput.readApp() != null && MainController.visibleApp) ? dispInput.readApp() : null);
        System.out.println("BUFFER");
        System.out.println(buffer.getBufferApps());
        System.out.println("DEVISES");
        System.out.println(outputStream.getDevices());
    }
    public static void getCountOfAppFromSources() {
        System.out.println(inputStream.getInfoFromSources());
    }
    public static void getProbabilityRefusalAverage() {
        System.out.println(buffer.getCountOfRefusals() / countApp);
    }
    public static void getProbabilityRefusalForSources() {
        System.out.println(buffer.getProbabilityOfRefusalsForSources());
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
