package com.heixiu.errand.MVP.Contentt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.heixiu.errand.R
import com.heixiu.errand.adapter.PackageTypeAdapter
import kotlinx.android.synthetic.main.activity_package_type.*

class PackageTypeActivity : AppCompatActivity() {


    private var adapter: PackageTypeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_type)
        package_type_Rv!!.layoutManager = GridLayoutManager(this, 4)
        adapter = PackageTypeAdapter(this)
        package_type_Rv!!.adapter = adapter
    }

    companion object {

        fun startSelf(context: Context) {
            val intent = Intent(context, PackageTypeActivity::class.java)
            context.startActivity(intent)
        }
    }
}
