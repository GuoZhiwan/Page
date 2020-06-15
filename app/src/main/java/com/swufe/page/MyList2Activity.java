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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import static android.content.ContentValues.TAG;

public class MyList2Activity extends ListActivity implements AdapterView.OnItemLongClickListener,Runnable {
    private static final String TAG = "MyList2Activity";
    String str1;
    String val;
    ListAdapter adapter;
    ArrayList fwords=new ArrayList();
    ArrayList fmeans=new ArrayList();
    Handler handler;
    fgWordManager fgmanager=new fgWordManager(this);;
    List<String> retlist= new ArrayList<String>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread t = new Thread(this);
        t.start();
        handler=new Handler(){
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    List<String> list2=(List<String>)msg.obj;
                    adapter=new ArrayAdapter<String>(MyList2Activity.this,android.R.layout.simple_expandable_list_item_1,list2);
                    setListAdapter(adapter);

                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemLongClickListener(this);
    }
    public void run(){
        List<WordItem> wordList = new ArrayList<WordItem>();
        SharedPreferences sp2 = getSharedPreferences("mylist2", Activity.MODE_PRIVATE);
        int n = sp2.getInt("fwordsint", 0);
        Log.i(TAG, "onCreate: 传过来的：" + n);
            for(int i=0;i<n;i++) {
                str1=sp2.getString("fwords"+i, null);
                val=sp2.getString("fmeans"+i, null);
                if(wordList.contains(str1)) {
                    Log.i(TAG, "run: 有这个词");
                }else{
                    wordList.add(new WordItem(str1, val));
                }
            }
            fgmanager.addAll(wordList);
            fgmanager.deleteAll();
        Log.i(TAG, "run: 添加所有记录");
        if (fgmanager.listall()==null) {
            String data[] = {"目前没有可复习的单词哦~"};
            final ListAdapter adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
            setListAdapter(adapter1);
        }

        for(WordItem item:fgmanager.listall()){
            retlist.add(item.getCurWord()+"-->"+item.getCurMean());
        }

            Message msg = handler.obtainMessage(5);
            msg.obj = retlist;
            handler.sendMessage(msg);
    }
    @Override








    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按列表项position="+position);
        //删除操作

        //构造对话框进行确认操作
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick: 对话框处理");
            }
        })

                .setNegativeButton("否",null);
        builder.create().show();
        Toast.makeText(this, "数据删除成功", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onItemLongClick: size"+retlist.size());
        return true;
    }
}
