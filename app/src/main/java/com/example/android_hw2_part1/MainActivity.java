package com.example.android_hw2_part1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private TextView text_name;
    private TextView text_height;
    private TextView text_weight;
    private TextView text_age;


    boolean boy_state = false;
    boolean girl_state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listBtn = (Button)findViewById(R.id.list_click);
        listBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , Android_count_bmi_bmr.class);
                startActivity(intent);
            }
        });

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        text_name = findViewById(R.id.name);
        text_height = findViewById(R.id.height);
        text_weight = findViewById(R.id.weight);
        text_age = findViewById(R.id.age);


        final RadioButton boy = (RadioButton)findViewById(R.id.boy);
        final RadioButton girl = (RadioButton)findViewById(R.id.girl);


        RadioGroup rg =(RadioGroup)findViewById(R.id.gender);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == boy.getId()){
                    boy_state = true;
                    girl_state = false;
                }
                else if(checkedId == girl.getId()){
                    girl_state = true;
                    boy_state =false;
                }
            }
        });

        Button countPageBtn = (Button)findViewById(R.id.go_click);

        countPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Android_count_bmi_bmr.class);  // 換頁

                String name = text_name.getText().toString();
                String height = text_height.getText().toString();
                String weight = text_weight.getText().toString();
                String age = text_age.getText().toString();
                String gender = "";

                if(boy_state == true)
                    gender = boy.getText().toString();
                else if(girl_state == true)
                    gender =  girl.getText().toString();


                float fh = Float.parseFloat(height);
                float fw = Float.parseFloat(weight);
                float fa = Float.parseFloat(age);

                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);

                String bmi = nf.format( fw/(fh*fh/10000)  ) .toString() ;
                String bmr = "";

                if(  gender.equals("Boy") ){
                    bmr = nf.format(  66+(13.7*fw)+(5*fh)-(6.8*fa) ).toString() ;
                }
                else if (gender.equals("Girl") ){
                    bmr = nf.format(  655+(9.6*fw)+(1.8*fh)-(4.7*fa) ).toString() ;
                }

                String result= DBConnector.executeUpdate(name, height, weight, age, gender, bmi, bmr);
                startActivity(intent);

            }
        });

    }
}
