package com.heixiu.errand.MVP.Contentt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.heixiu.errand.R;
import com.heixiu.errand.adapter.PackageTypeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackageTypeActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.package_type_Rv)
    RecyclerView packageTypeRv;
    @BindView(R.id.receiveName)
    EditText receiveName;
    @BindView(R.id.receiveNum)
    EditText receiveNum;
    @BindView(R.id.deliverName)
    EditText deliverName;
    @BindView(R.id.description)
    EditText description;
    private PackageTypeAdapter adapter;

    public static void startSelf(Context context) {
        Intent intent = new Intent(context, PackageTypeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_type);
        ButterKnife.bind(this);
        packageTypeRv.setLayoutManager(new GridLayoutManager(this,4));
        adapter = new PackageTypeAdapter(this);
        packageTypeRv.setAdapter(adapter);
    }
}
