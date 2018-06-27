package com.heixiu.errand.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heixiu.errand.bean.CityBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by YuanGang on 2018/6/27.
 */

public class CityUtils {
    public static List<CityBean> getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type listType = new TypeToken<List<CityBean>>() {
        }.getType();
        //这里的json是字符串类型 = jsonArray.toString();
        List<CityBean> list = new Gson().fromJson(stringBuilder.toString(), listType);

        return list;
    }
}
