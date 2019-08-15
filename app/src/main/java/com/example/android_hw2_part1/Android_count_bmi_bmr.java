package com.example.android_hw2_part1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Android_count_bmi_bmr extends AppCompatActivity {

    private ListView mListView;
    String touch_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_count_bmi_bmr);

        update();

        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBConnector.delete(touch_id);
                clear(mListView);
                update();
            }
        });

        Button catchinfo = (Button)findViewById(R.id.catchinfo);
        catchinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();

            }
        });

        //編輯
        Button edit = (Button)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Android_count_bmi_bmr.this , edit_infor.class);  // 換頁
                Bundle bundle = new Bundle();
                bundle.putString("id", touch_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    //清空adapter
    public void clear(View v){
        ArrayAdapter adapter = (ArrayAdapter) mListView.getAdapter();
        int count = adapter.getCount();
        if (count > 0) {

            mListView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1));
        }

    }
    public void update(){
        try {

            String result = DBConnector.executeQuery("SELECT * FROM hw2");

            mListView = findViewById(R.id.list);

            JSONArray jsonArray = new JSONArray(result);

            List<String> list = new ArrayList<String>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String height = jsonObject.getString("height");
                String weight = jsonObject.getString("weight");
                String age = jsonObject.getString("age");
                String gender = jsonObject.getString("gender");
                String bmi = jsonObject.getString("bmi");
                String bmr = jsonObject.getString("bmr");
                list.add(" id: " + id +
                        "\n name:" + name +
                        "\n height:" + height +
                        "\n weight:" + weight +
                        "\n age:" + age +
                        "\n gender:" + gender +
                        "\n bmi:" + bmi +
                        "\n bmr:" + bmr);
            }

            ArrayAdapter adapter = new ArrayAdapter(Android_count_bmi_bmr.this, android.R.layout.simple_list_item_1, list);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    String list_read = mListView.getItemAtPosition(position).toString();
                    touch_id = list_read.substring(4, 7);

                    Toast.makeText(
                            Android_count_bmi_bmr.this,
                            " 選擇ID = " + touch_id,
                            Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.e("log_tag", e.toString());
        }
    }
}
