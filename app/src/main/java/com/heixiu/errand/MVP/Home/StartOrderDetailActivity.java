package com.heixiu.errand.MVP.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heixiu.errand.R;
import com.heixiu.errand.net.OrderInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartOrderDetailActivity extends AppCompatActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.receiveName)
    TextView receiveName;
    @BindView(R.id.publishOrderNum)
    TextView publishOrderNum;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.message)
    ImageView message;
    @BindView(R.id.call)
    ImageView call;
    @BindView(R.id.orderNo)
    TextView orderNo;
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.end)
    TextView end;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.add_money)
    TextView addMoney;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.recipientsame)
    TextView recipientsame;
    @BindView(R.id.recipientsNum)
    TextView recipientsNum;
    @BindView(R.id.courierNum)
    TextView courierNum;
    @BindView(R.id.diverLayout)
    LinearLayout diverLayout;
    @BindView(R.id.take_order)
    ImageView takeOrder;
    @BindView(R.id.startMap)
    ImageView startMap;
    private OrderInfo orderInfo;

    public static void startSelf(Context context, OrderInfo orderInfo) {
        Intent intent = new Intent(context, StartOrderDetailActivity.class);
        intent.putExtra("data", orderInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_order_detail);
        ButterKnife.bind(this);

        orderInfo = (OrderInfo) getIntent().getSerializableExtra("data");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderNo.setText("订单编号：" + orderInfo.getOrderNum());
        start.setText(orderInfo.getSendAddress());
        end.setText(orderInfo.getReceiveAddress());
        time.setText(orderInfo.getSendTime());
        addMoney.setText("加价" + orderInfo.getAddPrice());
        tips.setText(orderInfo.getDescription());
        recipientsame.setText("收件人姓名：" + orderInfo.getReceiveName());
        recipientsNum.setText("收件人电话：" + orderInfo.getReceiveNum());
        courierNum.setText("快递员电话：" + orderInfo.getCourierNum());

        takeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取到物品
            }
        });

        startMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderMapActivity.startSelf(StartOrderDetailActivity.this,orderInfo);
            }
        });
    }
}
