package com.example.a84640.clockingin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 软件主程序入口
 * @author jixiang
 * @data 2019.2.27
 */
public class MainActivity extends AppCompatActivity {

    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        myButton=(Button)findViewById(R.id.button1);
        //
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Toast.makeText(getApplication(),"hahah",Toast.LENGTH_LONG).show();
            }
        });

    }

}
