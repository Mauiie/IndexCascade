package com.mauiie.indexcascade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mauiie.indexcascade.utils.WhatSign2JsonUtils;
import com.mauiie.indexcascadeview.IndexCascadeView;
import com.mauiie.indexcascadeview.bean.IndexCascadeBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IndexCascadeView indexCascadeView;
    private HashMap<String, List<IndexCascadeBean>> seriesMaps;
    private IndexCascadeBean relatedBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indexCascadeView = (IndexCascadeView) findViewById(R.id.indexCascadeView);
        initView();
    }

    private void initView() {
        byte[] buffer = null;
        try {
            InputStream is = getAssets().open("demodata.txt");
            if (null != is) {
                int size = is.available();
                buffer = new byte[size];
                is.read(buffer);
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null != buffer) {
            String data = new String(buffer);
            List<IndexCascadeBean> signsDatas = WhatSign2JsonUtils.getSignsDatas(data);
            List<IndexCascadeBean> minorList = new ArrayList<>();
            seriesMaps = WhatSign2JsonUtils.getSeriesMaps();
            if (null != signsDatas && signsDatas.size() > 1) {
                relatedBean = signsDatas.get(1);
                relatedBean.isSelected = true;
                if (null != relatedBean.id) {
                    minorList = seriesMaps.get(relatedBean.id);
                }
            }
            indexCascadeView.setData("品类", "品牌", signsDatas, minorList);
        }

        indexCascadeView.setOnItemSelectListener(new IndexCascadeView.OnItemSelectListener() {
            @Override
            public void onPrimaryItemSelect(int position, IndexCascadeBean bean) {
                relatedBean = bean;
                if (null != seriesMaps) {
                    List<IndexCascadeBean> list = seriesMaps.get(bean.id);
                    for (IndexCascadeBean cascadeBean : list) {
                        if (cascadeBean.isSelected) {
                            cascadeBean.isSelected = false;
                        }
                    }
                    indexCascadeView.setRightData(list);
                }
            }

            @Override
            public void onMinorItemSelect(int position, IndexCascadeBean bean) {
                Toast.makeText(MainActivity.this, "name: " + bean.name, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
