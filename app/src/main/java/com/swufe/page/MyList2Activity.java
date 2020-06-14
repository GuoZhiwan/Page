package com.swufe.page;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyList2Activity extends ListActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MyList2Activity";
    String str1;
    String val;
    WordManager manager1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> retlist = new ArrayList<String>();
        List<WordItem> wordList = new ArrayList<WordItem>();

        SharedPreferences sp2=getSharedPreferences("mylist2", Activity.MODE_PRIVATE);
        int n=sp2.getInt("fwordsint",0);
        Log.i(TAG, "onCreate: 传过来的："+n);
        if(n==0){
            String data[]={"目前没有可复习的单词哦~"};
            final ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
            setListAdapter(adapter);


        }else {
            for (int i = 0; i < n; i++) {
                    str1 = sp2.getString("fwords",null);
                    val = sp2.getString("fmeans",null);
                    retlist.add(str1 + "==>" + val);
                    wordList.add(new WordItem(str1, val));

            }
            manager1 = new WordManager(MyList2Activity.this);
            manager1.addAll(wordList);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按列表项position="+position);
        //删除操作

        //构造对话框进行确认操作
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick: 对话框处理");
                manager1.delete(new WordItem(str1, val));
                Log.i(TAG, "onClick: 删除的一项是"+str1+val);
            }
        })
                .setNegativeButton("否",null);
        builder.create().show();

    }
}
