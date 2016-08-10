package com.mauiie.indexcascadeview.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mauiie.indexcascadeview.R;
import com.mauiie.indexcascadeview.bean.IndexCascadeBean;

import java.util.List;

/**
 * Created by TaiJL on 2016/2/16.
 */
public class IndexCascadeViewAdapter extends BaseAdapter {
    public final static int TYPE_LEFT = 0;
    public final static int TYPE_RIGHT = 1;

    private Context mContext;
    private List<IndexCascadeBean> dataSource;
    private int type = -1;

    public IndexCascadeViewAdapter(Context context, List<IndexCascadeBean> datas, int type) {
        this.mContext = context;
        this.dataSource = datas;
        this.type = type;
    }

    public void setDataSource(List<IndexCascadeBean> datas) {
        this.dataSource = datas;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.indexcascade_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindView(holder, position);
        return convertView;
    }

    private void bindView(ViewHolder holder, int position) {
        if (null != dataSource && dataSource.size() > 0) {
            IndexCascadeBean bean = dataSource.get(position);
            holder.view.setText(bean.name);
            if (bean.isKeywords) {
                holder.view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            } else {
                holder.view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            }

            if (bean.isSelected) {
                if (type == TYPE_LEFT) {
                    holder.bg_box.setBackgroundColor(ContextCompat.getColor(holder.bg_box.getContext(), R.color.listview_bgcolor_st));
                } else if (type == TYPE_RIGHT) {
                    holder.bg_box.setBackgroundColor(ContextCompat.getColor(holder.bg_box.getContext(), R.color.listview_bgcolor_nd));
                    // TODO: 2016/2/16 添加选择符号
                    holder.select_icon.setVisibility(View.VISIBLE);
                }
                holder.view.setTextColor(ContextCompat.getColor(holder.view.getContext(), R.color.text_item_select_color));
            } else {
                if (type == TYPE_LEFT) {
                    holder.bg_box.setBackgroundColor(ContextCompat.getColor(holder.bg_box.getContext(), R.color.listview_bgcolor_st));
                } else if (type == TYPE_RIGHT) {
                    holder.bg_box.setBackgroundColor(ContextCompat.getColor(holder.bg_box.getContext(), R.color.listview_bgcolor_nd));
                    // TODO: 2016/2/16 添加选择符号
                    holder.select_icon.setVisibility(View.INVISIBLE);
                }
                holder.view.setTextColor(ContextCompat.getColor(holder.view.getContext(), R.color.text_title_color));
            }
        }

    }

    class ViewHolder {
        public TextView view;
        public LinearLayout bg_box;
        public ImageView select_icon;

        public ViewHolder(View rootView) {
            view = (TextView) rootView.findViewById(R.id.text_area);
            bg_box = (LinearLayout) rootView.findViewById(R.id.bg_box);
            select_icon = (ImageView) rootView.findViewById(R.id.select_icon);
        }
    }


}
