package com.example.Kurs.models;


import com.example.Kurs.controllers.MainController;

import java.util.ArrayList;
import java.util.HashMap;
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

    public Buffer(int size){
        this.buffer = new ArrayList<>(size);
        this.size = size;
        sourceCountOfRefusals = new HashMap<>();
        for(int i = 0; i < MainController.inputStream.getInfoFromSources().size(); i++) {
            sourceCountOfRefusals.put(i, 0);
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
            //запомним, кол-во отказов для каждого из источников
            sourceCountOfRefusals.put(removableApp.getAppIndex().keySet().stream().findFirst().get(),
                    sourceCountOfRefusals.get(removableApp.getAppIndex().keySet().stream().findFirst().get()) + 1);
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
    public Application getApp(){
        Application application = null;
        int minSourceIndex = Integer.MAX_VALUE;
        double minCreateTime = Double.MAX_VALUE;
        for(Application app : buffer) {
            if(app.getAppIndex().keySet().stream().findFirst().get() <= minSourceIndex) {
                if(minSourceIndex == app.getAppIndex().keySet().stream().findFirst().get()) {
                    if(app.getAppIndex().values().stream().findFirst().get() < minCreateTime) {
                        minCreateTime = app.getAppIndex().values().stream().findFirst().get();
                        application = app;
                    }
                } else {
                    minSourceIndex = app.getAppIndex().keySet().stream().findFirst().get();
                    minCreateTime = app.getAppIndex().values().stream().findFirst().get();
                    application = app;
                }

            }
        }
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
