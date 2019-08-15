package com.example.android_hw2_part1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class edit_infor extends AppCompatActivity {

    String id;
    TextView name,height,weight,gender,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infor);

        try {
            Bundle bundle =getIntent().getExtras();

            id = bundle.getString("id") ;

            name = (TextView)findViewById(R.id.name);
            height = (TextView)findViewById(R.id.height);
            weight = (TextView)findViewById(R.id.weight);
            gender = (TextView)findViewById(R.id.gender);
            age = (TextView)findViewById(R.id.age);


            String result = DBConnector.select(id);
            JSONArray jsonArray = new JSONArray(result);

            List<String> list = new ArrayList<String>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                name.setText(jsonObject.getString("name"));
                height.setText(jsonObject.getString("height"));
                weight.setText(jsonObject.getString("weight"));
                age.setText(jsonObject.getString("age"));
                gender.setText(jsonObject.getString("gender"));

            }
        }
        catch (Exception e) {
            Log.e("log_tag", e.toString());
        }

        Button go_click = (Button)findViewById(R.id.go_click);
        go_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_name = name.getText().toString();
                String new_height = height.getText().toString();
                String new_weight = weight.getText().toString();
                String new_age = age.getText().toString();
                String new_gender = gender.getText().toString();

                float fh = Float.parseFloat(new_height);
                float fw = Float.parseFloat(new_weight);
                float fa = Float.parseFloat(new_age);

                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);

                String bmi = nf.format(fw / (fh * fh / 10000)).toString();
                String bmr = "";

                if (new_gender.equals("Boy")) {
                    bmr = nf.format(66 + (13.7 * fw) + (5 * fh) - (6.8 * fa)).toString();
                } else if (new_gender.equals("Girl")) {
                    bmr = nf.format(655 + (9.6 * fw) + (1.8 * fh) - (4.7 * fa)).toString();
                }

                String result = DBConnector.Update(new_name, new_height, new_weight, new_age, new_gender, bmi, bmr, id);
            }
        });
    }
}
