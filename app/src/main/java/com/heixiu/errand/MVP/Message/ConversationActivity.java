package com.heixiu.errand.MVP.Message;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fushuaige.common.utils.ToastUtils;
import com.heixiu.errand.R;
import com.heixiu.errand.utils.SPUtil;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;


/**
 * Created by fuyuancong on 2018/3/8.
 */

public class ConversationActivity extends AppCompatActivity implements RongIM.ConversationClickListener  {
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

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        if(!userInfo.getUserId().equals(SPUtil.getString("userid"))) {
            Intent intent = new Intent(ConversationActivity.this, OtherPersonalPageActivity.class);
            intent.putExtra("friendid", userInfo.getUserId());
            ConversationActivity.this.startActivity(intent);
        }else{
            Intent intent = new Intent(ConversationActivity.this, PersonalPageActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s, Message message) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }
}