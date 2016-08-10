package com.mauiie.indexcascadeview.bean;

/**
 * Created by TaiJL on 2016/2/16.
 */
public class IndexCascadeBean implements Comparable<IndexCascadeBean> {
    public IndexCascadeBean() {
    }

    public IndexCascadeBean(String id, String name, String keywords) {
        this.id = id;
        this.name = name;
        this.keywords = keywords;
    }

    public String id;
    public String name;
    public String keywords;
    public boolean isSelected;
    public boolean isKeywords;

    @Override
    public int compareTo(IndexCascadeBean another) {
        if (this.keywords.equals("#") && another.keywords.equals("#")) {
            return 0;
        } else if (!(this.keywords.equals("#")) && another.keywords.equals("#")) {
            return -1;
        } else if (!(another.keywords.equals("#")) && this.keywords.equals("#")) {
            return 1;
        }
        return this.keywords.compareTo(another.keywords);
    }

    @Override
    public String toString() {
        return "Csb_IndexCascadeBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", keywords='" + keywords + '\'' +
                ", isSelected=" + isSelected +
                ", isKeywords=" + isKeywords +
                '}';
    }
}
