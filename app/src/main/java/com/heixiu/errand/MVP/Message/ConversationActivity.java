package com.heixiu.errand.MVP.Message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        ImageView message_back = (ImageView)findViewById(R.id.message_back);
        TextView message_name = (TextView)findViewById(R.id.message_name);
        message_name.setText(sName);
        message_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        setTitle("与" + sName + "聊天中");
    }
}