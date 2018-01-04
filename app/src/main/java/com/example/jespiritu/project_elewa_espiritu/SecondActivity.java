package com.example.jespiritu.project_elewa_espiritu;

import android.app.Activity;
import android.os.Bundle;

public class SecondActivity extends Activity {

    private static int CLARKSON_DEP_TIME_INTERVAL = 6;
    private static int PORTCREDIT_DEP_TIME_INTERVAL = 15;

    private static int CLARKSON_ARR_TIME_INTERVAL = 6;
    private static int OAKVILLE_ARR_TIME_INTERVAL = 14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
