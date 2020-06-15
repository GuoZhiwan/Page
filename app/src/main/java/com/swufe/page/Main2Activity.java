package com.swufe.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Main2Activity extends AppCompatActivity {
    TextView showword;
    TextView showmean;
    ArrayList words;
    ArrayList means;
    ArrayList fwords=new ArrayList();
    ArrayList fmeans=new ArrayList();;
    int count;
    public final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        showword = findViewById(R.id.main2_show1);
        showmean = findViewById(R.id.main_show2);
        Intent config = getIntent();
        words = config.getParcelableArrayListExtra("CURWORDS");
        means = config.getParcelableArrayListExtra("CURMEANS");
        Log.i(TAG, "onCreate: words传过来的：" + words);
        Toast.makeText(this, "请开始今天的学习吧~", Toast.LENGTH_SHORT).show();
        SharedPreferences sp = getSharedPreferences("main2", Activity.MODE_PRIVATE);
        count = sp.getInt("count", count);
        if(words!=null) {
            showword.setText(words.get(count).toString());
        }
        Button btn1 = findViewById(R.id.btn_mean);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("main2", Activity.MODE_PRIVATE);
                count = sp.getInt("count", count);
                // TODO Auto-generated method stub
                if (count < words.size()) {
                    showmean.setText(means.get(count).toString());
                } else
                    showmean.setText("恭喜你，全部单词已经背完~");
            }
        });
        Button btn2 = findViewById(R.id.btn_next);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("main2", Activity.MODE_PRIVATE);
                count = sp.getInt("count", count);
                count++;
                if (count < words.size()) {
                    showword.setText(words.get(count).toString());
                    showmean.setText("   ");
                } else {
                    showword.setText("无单词");
                }
                SharedPreferences sp1 = getSharedPreferences("main2", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp1.edit();
                editor.putInt("count", count);
                editor.apply();
            }
        });
        Button btn3 = findViewById(R.id.btn_forget);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("main2", Activity.MODE_PRIVATE);
                count = sp.getInt("count", count);
                Log.i(TAG, "onClick: count"+count);
                if(words!=null&&means!=null) {

                        fwords.add(words.get(count));
                        fmeans.add(means.get(count));
                        Log.i(TAG, "onClick: fwords" + fwords);
                        SharedPreferences sp2=getSharedPreferences("mylist2",Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp2.edit();
                        editor.putInt("fwordsint",fwords.size());
                        for(int i=0;i<fwords.size();i++){
                            editor.putString("fwords"+i,fwords.get(i).toString());
                            editor.putString("fmeans"+i,fmeans.get(i).toString());
                        }
                        editor.commit();
                    Log.i(TAG, "onClick: length"+fwords.size());
                    }
                }
        });
    }
}
