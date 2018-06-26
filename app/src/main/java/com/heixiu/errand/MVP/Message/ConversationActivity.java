package com.heixiu.errand.MVP.Message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.heixiu.errand.R;

import io.rong.imkit.RongIM;


/**
 * Created by fuyuancong on 2018/3/8.
 */

public class ConversationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        String sName = getIntent().getData().getQueryParameter("title");//获取昵称
        setTitle("与" + sName + "聊天中");
    }
}