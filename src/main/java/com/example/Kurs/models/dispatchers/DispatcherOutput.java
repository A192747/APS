package com.example.Kurs.models.dispatchers;

import com.example.Kurs.models.Application;
import com.example.Kurs.models.Buffer;
import com.example.Kurs.models.streams.OutputStream;


/*
    EDITED:
    1) убрал private Application app, так как нам незачем хранить app в Dispatcher-е
    2) добавил конструктор для DispatcherOutput
    3) убрал строчку private Device device, вместо неё написал private OutputStream stream
    4) переименовал функцию getApp() на getAndPutInDevice()
    5) сделал getAndPutInBuffer() типа void
 */
public class DispatcherOutput {
    private Buffer buffer;
    private OutputStream stream;
    public DispatcherOutput(OutputStream output, Buffer buffer) {
        this.buffer = buffer;
        this.stream = output;
    }
    public void getAndPutInDevice() {
        //логика для Пети--------
        if(!stream.isDevicesOverflow()) {
            Application application = buffer.getApp();
            stream.putApp(application);
        }
        //-----------------------
    }
}
