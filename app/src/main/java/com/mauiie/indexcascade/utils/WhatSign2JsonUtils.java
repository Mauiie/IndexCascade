package com.mauiie.indexcascade.utils;

import com.mauiie.indexcascadeview.bean.IndexCascadeBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TaiJL on 2016/2/17.
 */
public class WhatSign2JsonUtils {
    private static final List<String> INDEXS = new ArrayList<>();
    private static final HashMap<String, List<IndexCascadeBean>> seriesMap = new HashMap<>();

    public static List<IndexCascadeBean> getSignsDatas(String originalData) {
        initializedIndexData();
        List<IndexCascadeBean> primaryList = new ArrayList<IndexCascadeBean>();

        if (null != originalData) {
            try {
                JSONObject allData = new JSONObject(originalData);
                if (null != allData && allData.has("data")) {
                    JSONArray array = allData.getJSONArray("data");
                    if (null != array) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject beanJson = (JSONObject) array.get(i);
                            if (null != beanJson) {
                                String pid = beanJson.optString("id");
                                String pname = beanJson.optString("classname");
                                String keywords = beanJson.optString("firstword", "");
                                if (!INDEXS.contains(keywords)) {
                                    keywords = "#";
                                }
                                if (!hasKeyWords(primaryList, keywords)) {
                                    IndexCascadeBean keywordsbean = new IndexCascadeBean(pid, keywords, keywords);
                                    keywordsbean.keywords = keywords;
                                    keywordsbean.isKeywords = true;
                                    primaryList.add(keywordsbean);
                                } else {

                                }
                                IndexCascadeBean bean = new IndexCascadeBean(pid, pname, keywords);
                                primaryList.add(bean);

                                String cid = beanJson.optString("cid", "");
                                String cname = beanJson.optString("cname", "");
                                String[] cids = null;
                                if (null != cid && cid.length() > 1) {
                                    cids = cid.split("\\^");
                                }
                                String[] cnames = null;
                                if (null != cname && cname.length() > 1) {
                                    cnames = cname.split("\\^");
                                }


                                List<IndexCascadeBean> minorList = new ArrayList<>();
                                if (null != cids) {
                                    for (int j = 0; j < cids.length; j++) {
                                        IndexCascadeBean minorBean = new IndexCascadeBean();
                                        minorBean.id = cids[j];
                                        minorBean.name = cnames[j];
                                        minorBean.keywords = keywords;
                                        minorList.add(minorBean);
                                    }
                                }
                                seriesMap.put(pid, minorList);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!primaryList.isEmpty()) {
            Collections.sort(primaryList);
        }
        return primaryList;
    }

    public static HashMap<String, List<IndexCascadeBean>> getSeriesMaps() {
        return seriesMap;
    }

    private static void initializedIndexData() {
        INDEXS.add("A");
        INDEXS.add("B");
        INDEXS.add("C");
        INDEXS.add("D");
        INDEXS.add("E");
        INDEXS.add("F");
        INDEXS.add("G");
        INDEXS.add("H");
        INDEXS.add("I");
        INDEXS.add("J");
        INDEXS.add("K");
        INDEXS.add("L");
        INDEXS.add("M");
        INDEXS.add("N");
        INDEXS.add("O");
        INDEXS.add("P");
        INDEXS.add("Q");
        INDEXS.add("R");
        INDEXS.add("S");
        INDEXS.add("T");
        INDEXS.add("U");
        INDEXS.add("V");
        INDEXS.add("W");
        INDEXS.add("X");
        INDEXS.add("Y");
        INDEXS.add("Z");
        INDEXS.add("#");
    }

    private static boolean hasKeyWords(List<IndexCascadeBean> list, String keywords) {
        if (null != list && !list.isEmpty()) {
            for (IndexCascadeBean bean : list) {
                if (keywords.equals(bean.keywords)) {
                    return true;
                }
            }
        }
        return false;
    }
}
