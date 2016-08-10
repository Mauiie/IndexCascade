package com.mauiie.indexcascadeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mauiie.indexcascadeview.adapter.IndexCascadeViewAdapter;
import com.mauiie.indexcascadeview.bean.IndexCascadeBean;

import java.util.List;

/**
 * Created by TaiJL on 2016/2/15.
 * 索引级联View
 */
public class IndexCascadeView extends FrameLayout {
    private Context mContext;
    private ListView mRecyclerView_st, mRecyclerView_nd;
    private SlideBar mSlideBar;
    private RelativeLayout index_tip;
    private TextView tv_index, tv_st, tv_nd;
    private List<IndexCascadeBean> leftDatas, rightDatas;

    private IndexCascadeViewAdapter leftAdapter, rightAdapter;

    private OnItemSelectListener mOnItemSelectListener;

    public IndexCascadeView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public IndexCascadeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public IndexCascadeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.indexcascade_layout, this, true);

        mRecyclerView_st = (ListView) findViewById(R.id.recyclerview_st);
        mRecyclerView_nd = (ListView) findViewById(R.id.recyclerview_nd);
        tv_st = (TextView) findViewById(R.id.tv_st);
        tv_nd = (TextView) findViewById(R.id.tv_nd);

        mRecyclerView_st.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IndexCascadeBean cascadeBean = leftDatas.get(position);
                if (cascadeBean.isKeywords) {
                    return;
                }

                cascadeBean.isSelected = true;
                for (int i = 0; i < IndexCascadeView.this.leftDatas.size(); i++) {
                    if (i != position) {
                        IndexCascadeView.this.leftDatas.get(i).isSelected = false;
                    }
                }
                leftAdapter.setDataSource(IndexCascadeView.this.leftDatas);
                leftAdapter.notifyDataSetChanged();
                //TODO 向外通知
                if (null != mOnItemSelectListener) {
                    mOnItemSelectListener.onPrimaryItemSelect(position, cascadeBean);
                }
            }
        });

        mRecyclerView_nd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IndexCascadeBean cascadeBean = rightDatas.get(position);
                cascadeBean.isSelected = true;
                for (int i = 0; i < rightDatas.size(); i++) {
                    if (i != position) {
                        rightDatas.get(i).isSelected = false;
                    }
                }
                rightAdapter.setDataSource(IndexCascadeView.this.rightDatas);
                rightAdapter.notifyDataSetChanged();
                //TODO 向外通知
                if (null != mOnItemSelectListener) {
                    mOnItemSelectListener.onMinorItemSelect(position, cascadeBean);
                }
            }
        });


        mSlideBar = (SlideBar) findViewById(R.id.index_area);
        index_tip = (RelativeLayout) findViewById(R.id.index_tip);
        tv_index = (TextView) index_tip.findViewById(R.id.tv_index);
        initSlideBar();
    }

    public void setData(String titleLeft, String titleRight, List<IndexCascadeBean> leftBeans, List<IndexCascadeBean> rightBeans) {
        tv_st.setText(titleLeft);
        tv_nd.setText(titleRight);

        this.leftDatas = leftBeans;
        leftAdapter = new IndexCascadeViewAdapter(mContext, leftBeans, IndexCascadeViewAdapter.TYPE_LEFT);
        mRecyclerView_st.setAdapter(leftAdapter);

        setRightData(rightBeans);
    }

    public void setRightData(List<IndexCascadeBean> rightBeans) {
        this.rightDatas = rightBeans;
        rightAdapter = new IndexCascadeViewAdapter(mContext, rightBeans, IndexCascadeViewAdapter.TYPE_RIGHT);
        mRecyclerView_nd.setAdapter(rightAdapter);
    }


    private void initSlideBar() {
        mSlideBar.setOnTouchLetterChangeListenner(new SlideBar.OnTouchLetterChangeListenner() {
            @Override
            public void onTouchLetterChange(boolean isTouched, String s) {
                if (isTouched) {
                    index_tip.setVisibility(VISIBLE);
                    tv_index.setText(s);
                    onTouchIndex(s);
                } else {
                    index_tip.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            index_tip.setVisibility(GONE);
                        }
                    }, 100);
                }
            }
        });
    }

    private void onTouchIndex(String index) {
        if (null != leftDatas && leftDatas.size() > 0) {
            for (IndexCascadeBean bean : leftDatas) {
                if (bean.name.equals(index)) {
                    int position = leftDatas.indexOf(bean);
                    mRecyclerView_st.setSelection(position);
                    break;
                }
            }
        }
    }


    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.mOnItemSelectListener = onItemSelectListener;
    }

    public interface OnItemSelectListener {
        /**
         * 主列Item被选中
         */
        void onPrimaryItemSelect(int position, IndexCascadeBean bean);

        /**
         * 次列Item被选中
         */
        void onMinorItemSelect(int position, IndexCascadeBean bean);
    }
}