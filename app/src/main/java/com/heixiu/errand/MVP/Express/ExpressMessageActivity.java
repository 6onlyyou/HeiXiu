package com.heixiu.errand.MVP.Express;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


        ((TextView) findViewById(R.id.package_code)).setText("快递单号");
        ((TextView) findViewById(R.id.package_company)).setText("配送公司");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.package_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PackageMessageAdapter adapter = new PackageMessageAdapter(new ArrayList<PackageInformationBean.TracesBean>());
        recyclerView.setAdapter(adapter);
        List<PackageInformationBean.TracesBean> traces = data.getTraces();
        Collections.reverse(traces);
        adapter.setNewData(traces);
    }
}
