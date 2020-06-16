package com.swufe.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import static android.content.ContentValues.TAG;

public class MyList2Activity extends ListActivity implements Runnable {
    private static final String TAG = "MyList2Activity";
    String str1;
    String val;
    private List<HashMap<String,String>> listItems;//存放文字、图片等信息
    private SimpleAdapter listItemAdapter;//适配器
    Handler handler;
    fgWordManager fgmanager=new fgWordManager(this);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        Thread t = new Thread(this);
        t.start();
        handler=new Handler(){
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    listItems=(List<HashMap<String,String>>)msg.obj;
                    listItemAdapter=new SimpleAdapter(MyList2Activity.this,listItems,
                            R.layout.list_item,
                            new String[]{"ItemWord","ItemMean"},
                            new int[]{R.id.itemWord,R.id.itemMean}
                    );
                    setListAdapter(listItemAdapter);

                }
                super.handleMessage(msg);
            }
        };
    }
    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemWord", "word= " + i);
            map.put("ItemMean", "mean" + i);
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter=new SimpleAdapter(this,listItems,
                R.layout.list_item,
                new String[]{"ItemWord","ItemMean"},
                new int[]{R.id.itemWord,R.id.itemMean}
        );

    }
    public void run() {
        List<HashMap<String, String>> retlist = new ArrayList<HashMap<String, String>>();
        List<WordItem> wordList = new ArrayList<WordItem>();
        SharedPreferences sp2 = getSharedPreferences("mylist2", Activity.MODE_PRIVATE);
        int n = sp2.getInt("fwordsint", 0);
        Log.i(TAG, "onCreate: 传过来的：" + n);
        for (int i = 0; i < n; i++) {
            str1 = sp2.getString("fwords" + i, null);
            val = sp2.getString("fmeans" + i, null);
            wordList.add(new WordItem(str1, val));
        }
        fgmanager.deleteAll();
        fgmanager.addAll(wordList);
        Log.i(TAG, "run: 添加所有记录");
        for (WordItem item : fgmanager.listall()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemWord", item.getCurWord());
            map.put("ItemMean", item.getCurMean());
            retlist.add(map);
        }

        Message msg = handler.obtainMessage(5);
        msg.obj = retlist;
        handler.sendMessage(msg);
    }
}
