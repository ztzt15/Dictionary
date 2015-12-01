package com.zt.dictionary.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yeepay.utils.http.VolleyError;
import com.zt.dictionary.R;
import com.zt.dictionary.adapter.ListViewAdapter;
import com.zt.dictionary.model.db.Word;
import com.zt.dictionary.model.entity.SelectResult;
import com.zt.dictionary.util.netUtil.YeePayBaseRequest;
import com.zt.dictionary.util.netUtil.YeePayNetRequestCallBack;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private String mainTranslate;
    private List<String> smartTranslateList;
    private EditText textView;
    private MyHandler myHandler;
    private Message msg;
    private Button savaWord;
    private boolean status = false;
    private StringBuffer stringBuffer;
    private Button reciteButton;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = (Button) findViewById(R.id.select_button);
        editText = (EditText) findViewById(R.id.key_word);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        textView = (EditText) findViewById(R.id.resultShow);
        savaWord = (Button) findViewById(R.id.savaWord);
        reciteButton = (Button) findViewById(R.id.recite_word);
        myHandler = new MyHandler();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                status = false;
                stringBuffer = new StringBuffer();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        msg = myHandler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("word", " ");
                        msg.setData(bundle);
                        myHandler.sendMessage(msg);
                    }
                }).start();
                YeePayBaseRequest yeePayBaseRequest = YeePayBaseRequest.getInstance();
                yeePayBaseRequest.getTranslate(editText.getText().toString(), new YeePayNetRequestCallBack() {
                    @Override
                    public void onResponse(Object obj) {
                        String jsonStr = obj.toString().trim();
                        SelectResult selectResult = JSON.parseObject(jsonStr, SelectResult.class);
                        if(selectResult.getSmartResult()!=null){
                            mainTranslate = selectResult.getTranslateResult().get(0).get(0).getTgt();
                            stringBuffer.append(mainTranslate+"\n");
                            smartTranslateList = selectResult.getSmartResult().getEntries();
                            if(smartTranslateList!=null){
                                smartTranslateList.get(0);
                                for (String str:smartTranslateList){
                                    stringBuffer.append(str+"\n");
                                }
                                status = true;
                            }
                        }else{
                            stringBuffer.append("查无此词");
                        }

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                msg = myHandler.obtainMessage();
                                Bundle bundle = new Bundle();
                                bundle.putString("word", stringBuffer.toString());
                                msg.setData(bundle);
                                myHandler.sendMessage(msg);
                            }
                        }).start();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
            }
        });


        savaWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status){
                    Word word = new Word();
                    word.setWord(editText.getText().toString());
                    word.setMainTranslate(mainTranslate);
                    word.setDetailTranslate(textView.getText().toString());
                    Date date=new Date();
                    word.setSaveTime(date);
                    word.save();
                }
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        reciteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Word> allWords = DataSupport.order("saveTime desc").find(Word.class);
                listView.setAdapter(new ListViewAdapter(MainActivity.this,allWords));
            }
        });



    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText(msg.getData().get("word").toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
