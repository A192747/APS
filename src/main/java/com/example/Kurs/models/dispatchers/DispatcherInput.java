package com.example.Kurs.models.dispatchers;

import com.example.Kurs.controllers.MainController;
import com.example.Kurs.models.Application;
import com.example.Kurs.models.Buffer;
import com.example.Kurs.models.streams.InputStream;
import com.example.Kurs.models.StatusApp;

/*
    EDITED
    1) убрал передаваемый аргумент putApp(Application app),
        так как DispatcherInput должен сам выбирать заявку из InputStream,
        а не получать её откуда-то, чтобы положить в буфер
    2) изменил название функции putApp на getAndPutInBuffer
    3) убрал private Application app, так как нам незачем хранить app в Dispatcher-е
    4) добавил конструктор DispatcherInput для инита
*/
public class DispatcherInput {
    private Buffer buffer;
    private InputStream stream;
    private int countOfRefusals = 0;

    private Application application = null;

    public DispatcherInput(InputStream input, Buffer buffer) {
        this.stream = input;
        this.buffer = buffer;
    }
    public void getApp() {
        application = stream.getApp();
    }
    public Application readApp(){
        return application;
    }

    public void putApp() {
        buffer.putApp(application);
        MainController.systemTime = application.getCreateTime();
        application.setStatusApp(StatusApp.PUT_INTO_BUFFER);
        application = null;
    }
    public int getCountOfRefusals() {
        return countOfRefusals;
    }
}
