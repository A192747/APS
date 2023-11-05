package com.example.Kurs.models.streams;

import com.example.Kurs.controllers.MainController;
import com.example.Kurs.models.Application;
import com.example.Kurs.models.StatusApp;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
    WARNING:
    1) зачем нужен метод getInfoFromDevices()?
    EDITED:
    1) удалил переменную private boolean devicesOverflow, так как мы и так определяем значение в функции
    2) добавил переменную для отслеживания указателя на последний прибор private int lastDeviceIndex;
*/
public class OutputStream {
    //для Пети
    private int lastDeviceIndex;
    //
    private int countDevice;
    private Map<Integer, Double> sourceWorkTime;
    private static final double lambda = MainController.lambda;
    private ArrayList<Device> devices;

    public OutputStream(int countDevice) {
        this.countDevice = countDevice;
        this.devices = new ArrayList<>(countDevice);
        for (int i = 0; i < countDevice; i++) {
            devices.add(new Device(i));
        }
        this.lastDeviceIndex = 0;
        sourceWorkTime = new HashMap<>();
        for(int i = 0; i < MainController.inputStream.getInfoFromSources().size(); i++) {
            sourceWorkTime.put(i, 0.0);
        }
    }
    public boolean isDevicesOverflow() {
        for(Device device : devices) {
            if(device.isDeviceFree())
                return false;
        }
        return true;
    }

    public void putApp(Application app) {
        if(app != null) {
            for (int i = lastDeviceIndex; i < countDevice + lastDeviceIndex; i++) {
                Device device = devices.get(i % countDevice);
                if (device.isDeviceFree()) {
                    lastDeviceIndex = device.getIndex();
                    device.putApp(app);
                    sourceWorkTime.put(app.getAppIndex().keySet().stream().findFirst().get(),
                            sourceWorkTime.get(app.getAppIndex().keySet().stream().findFirst().get()) +
                                    device.getTimeEnd() - device.getTimeStart());
                    app.setStatusApp(StatusApp.PUT_INTO_DEVICE);
                    return;
                }
            }
        }
    }
    public Map<Integer, Double> getWorkTimeForSources() {
        //NEED TEST
        Map<Integer, Integer> allSources = MainController.inputStream.getInfoFromSources();
        for(int i = 0 ; i < allSources.size(); i++) {
            sourceWorkTime.put(i, sourceWorkTime.get(i)/ allSources.get(i));
        }
        return sourceWorkTime;
    }
    private Device getDeviceWithLowestTime() {
        Double minTime = getLowestTime();
        for(Device device : devices) {
            if(device.getTimeEnd() == minTime) {
                return device;
            }
        }
        return null;
    }
    public void deleteAppFromDevice() {
        if(!isDevicesFinished()) {
            Device device = getDeviceWithLowestTime();
            device.deleteAppFromDevice();
        }
    }
    public double getLowestTime() {
        double minTime = Double.MAX_VALUE;
        for(Device device : devices) {
            if(device.getTimeEnd() > 0.0 && minTime > device.getTimeEnd()){
                minTime = device.getTimeEnd();
            }
        }
        return minTime;
    }


    public ArrayList<Device> getDevices(){
        return devices;
    }
    public boolean isDevicesFinished() {
        for(Device device : devices) {
            if(!device.isDeviceFree())
                return false;
        }
        return true;
    }
//    public Map<Integer, Integer> getInfoFromDevices() {
//        Map<Integer, Integer> map = new HashMap<>()
//        for(Device device : devices) {
//
//        }
//    }
}
