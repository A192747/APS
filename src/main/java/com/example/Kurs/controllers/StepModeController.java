package com.example.Kurs.controllers;

import com.example.Kurs.models.Buffer;
import com.example.Kurs.models.Statistics;
import com.example.Kurs.models.StatusApp;
import com.example.Kurs.models.dispatchers.DispatcherInput;
import com.example.Kurs.models.dispatchers.DispatcherOutput;
import com.example.Kurs.models.streams.InputStream;
import com.example.Kurs.models.streams.OutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import javax.sound.midi.MidiChannel;
import java.util.ArrayList;
import java.util.List;


public class StepModeController {

    @FXML
    private Button finishButton;

    @FXML
    private Button nextStepButton;

    @FXML
    private TableView<?> table;


    @FXML
    void nextStep(ActionEvent event) {
        MainController.nextStep();
        Statistics.getSystemStatus();
    }

}
