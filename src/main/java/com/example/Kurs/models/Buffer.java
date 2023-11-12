package com.example.Kurs.models;


import com.example.Kurs.controllers.MainController;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    EDITED
        1) убрал метод public void removeApp(Application app){
 */
public class Buffer {
    private ArrayList<Application> buffer;
    private int size = 0;
    private int occupiedSize = 0;
    private boolean bufferOverflow = false;
    private int countOfRefusals = 0;

    private Map<Integer, Integer> sourceCountOfRefusals;
    private Map<Integer, Double> sourcesTimeInBuffer;
    private Map<Integer, List<Double>> timeForSources;

    public Buffer(int size){
        this.buffer = new ArrayList<>(size);
        this.size = size;
        sourceCountOfRefusals = new HashMap<>();
        sourcesTimeInBuffer = new HashMap<>();
        timeForSources = new HashMap<>();
        for(int i = 0; i < MainController.inputStream.getInfoFromSources().size(); i++) {
            sourceCountOfRefusals.put(i, 0);
            sourcesTimeInBuffer.put(i, 0.0);
            timeForSources.put(i, null);
        }
    }
    public int getOccupiedSize() {
        return occupiedSize;
    }
    public boolean isBufferOverflow() {
        return bufferOverflow;
    }
    public ArrayList<Application> getBufferApps() {
        return buffer;
    }
    public void putApp(Application app) {
        if(isBufferOverflow()) {
            Application removableApp = buffer.get(buffer.size() - 1);
            removableApp.setStatusApp(StatusApp.REFUSAL_OF_APP);

            //запомним временные значения для каждого из источников для расчета дисперсии
            putTime(removableApp);

            int appSourceIndex = removableApp.getAppIndex().keySet().stream().findFirst().get();
            sourcesTimeInBuffer.put(appSourceIndex,
                    sourcesTimeInBuffer.get(appSourceIndex) + MainController.systemTime - removableApp.getCreateTime());

            //запомним, кол-во отказов для каждого из источников
            sourceCountOfRefusals.put(removableApp.getAppIndex().keySet().stream().findFirst().get(),
                    sourceCountOfRefusals.get(appSourceIndex) + 1);

            buffer.remove(removableApp);
            countOfRefusals++;
            occupiedSize--;
        }
        buffer.add(app);
        occupiedSize++;
        bufferOverflow = this.size == occupiedSize;
    }
    public Map<Integer, Integer> getCountOfRefusalsForSources() {
        //NEED TEST
        return sourceCountOfRefusals;
    }

    public Map<Integer, Double> getProbabilityOfRefusalsForSources() {
        Map<Integer, Double> result = new HashMap<>();
        for(int i = 0; i < sourceCountOfRefusals.size(); i++) {
            result.put(i, ( (double) sourceCountOfRefusals.get(i) / MainController.inputStream.getInfoFromSources().get(i)));
        }
        return result;
    }
    public Map<Integer, Double> getSourcesTimeInBuffer(){
        Map<Integer, Double> result = new HashMap<>();
        for(int i = 0; i < sourcesTimeInBuffer.size(); i++) {
            result.put(i, ( (double) sourcesTimeInBuffer.get(i) / MainController.inputStream.getInfoFromSources().get(i)));
        }
        return result;
    }
    public Map<Integer, Double> getDispersion() {
        Map<Integer, Double> result = new HashMap<>();
        for(int i = 0; i < sourcesTimeInBuffer.size(); i++) {
            if(timeForSources.get(i) != null) {
                double averageTime = timeForSources.get(i).stream().reduce(0.0, Double::sum) / timeForSources.get(i).size();
                result.put(i, (1.0f / timeForSources.get(i).size()) * timeForSources.get(i)
                        .stream().map(x -> Math.pow(x - averageTime, 2)).reduce(0.0, Double::sum));
            } else {
                result.put(i, null);
            }
        }
        return result;
    }

    private void putTime(Application application) {
        int appSourceIndex = application.getAppIndex().keySet().stream().findFirst().get();
        List<Double> list = timeForSources.get(appSourceIndex);
        if(list == null)
            list = new ArrayList<>();
        list.add(MainController.systemTime - application.getCreateTime() );
        timeForSources.put(appSourceIndex, list);
    }
    public Application getApp(){
        Application application = null;
        int minSourceIndex = Integer.MAX_VALUE;
        double minCreateTime = Double.MAX_VALUE;
        int appSourceIndex;
        int appIndex;
        for(Application app : buffer) {

            appSourceIndex = app.getAppIndex().keySet().stream().findFirst().get();
            appIndex = app.getAppIndex().values().stream().findFirst().get();

            if(appSourceIndex <= minSourceIndex) {
                if(minSourceIndex == appSourceIndex) {
                    if(appIndex < minCreateTime) {
                        minCreateTime = appIndex;
                        application = app;
                    }
                } else {
                    minSourceIndex = appSourceIndex;
                    minCreateTime = appIndex;
                    application = app;
                }

            }

        }


        appSourceIndex = application.getAppIndex().keySet().stream().findFirst().get();
        sourcesTimeInBuffer.put(appSourceIndex,
                sourcesTimeInBuffer.get(appSourceIndex) + MainController.systemTime - application.getCreateTime());

        //запомним временные значения для каждого из источников для расчета дисперсии
        putTime(application);

        Application result = application;
        buffer.remove(application);
        occupiedSize--;
        bufferOverflow = this.size == occupiedSize;
        return result;
    }
    public boolean isEmpty(){
        return buffer.size() == 0;
    }
    public int getCountOfRefusals() {
        return countOfRefusals;
    }
}
