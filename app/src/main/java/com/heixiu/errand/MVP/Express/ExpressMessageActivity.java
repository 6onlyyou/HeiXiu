package com.heixiu.errand.MVP.Express;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.heixiu.errand.R;
import com.heixiu.errand.adapter.PackageMessageAdapter;
import com.heixiu.errand.bean.PackageInformationBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpressMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_message);

        PackageInformationBean data = (PackageInformationBean) getIntent().getSerializableExtra("data");

        ((TextView) findViewById(R.id.package_code)).setText("快递单号：" + data.getLogisticCode());
        if ("2".equals(data.getState())) {
            ((TextView) findViewById(R.id.package_state)).setText("订单状态：在途中");
        } else if ("3".equals(data.getState())) {
            ((TextView) findViewById(R.id.package_state)).setText("订单状态：已签收");
        } else if ("4".equals(data.getState())) {
            ((TextView) findViewById(R.id.package_state)).setText("订单状态：问题件");
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView) findViewById(R.id.package_company)).setText("配送公司：" + getIntent().getStringExtra("name"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.package_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PackageMessageAdapter adapter = new PackageMessageAdapter(new ArrayList<PackageInformationBean.TracesBean>());
        recyclerView.setAdapter(adapter);

        List<PackageInformationBean.TracesBean> traces = data.getTraces();
        Collections.reverse(traces);
        adapter.setNewData(traces);
    }
}
