package com.example.Kurs.models;

import com.example.Kurs.controllers.MainController;

import java.util.Map;

/*
    EDITED
    1) добавил метод public Map<Integer, Integer> getAppIndex()
    2) добавил метод public void setStatusApp(StatusApp status)
    3) изменил Timestamp на double, для того, чтобы не нужно было физически ждать времени, когда обработается заявка
    4) добавил в конструкторе принимаемую переменную double createTime
*/
public class Application implements Comparable<Application>{
    private Map<Integer, Integer> appIndex;
    private double createTime;
    private StatusApp status;

    public Application (Map<Integer, Integer> appIndex, double createTime) {
        this.appIndex = appIndex;
        this.createTime = createTime;
        this.status = StatusApp.APP_GENERATION;
    }

    public Map<Integer, Integer> getAppIndex(){
        return appIndex;
    }
    public void setStatusApp(StatusApp status) {
        StatusApp prevStat = this.status;
        this.status = status;
//        if(prevStat != status)
//            Statistics.getSystemStatus();
    }
    public double getCreateTime() {
        return createTime;
    }
    public StatusApp getStatusApp() {
        return status;
    }

    @Override
    public String toString() {
        return "Application{" +
                "appIndex=" + appIndex +
                ", createTime=" + createTime +
                ", status=" + status +
                '}' + "\n";
    }

    @Override
    public int compareTo(Application o) {
        return Double.compare(this.createTime, o.getCreateTime());
    }
}
