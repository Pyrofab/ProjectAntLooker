package fr.antproject.antlookerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.opencv.core.Mat;

public class AntLookerApp extends AppCompatActivity {

    static {
        System.loadLibrary("opencv_java3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mat m = new Mat();
        setContentView(R.layout.activity_ant_looker_app);
    }
}
