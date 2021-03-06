package com.alenbeyond.sujin.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alenbeyond.sujin.R;
import com.alenbeyond.sujin.utils.NetUtils;
import com.alenbeyond.sujin.utils.UiUtils;

/**
 * Created by AlenBeyond on 2016/5/23.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        initWidget();
        setListener();
        initData();
    }

    public <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    public void setStatusTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 初始化ToolBar
     *
     * @param title
     * @param isBack
     */
    public void initToolBar(String title, boolean isBack) {
        Toolbar toolbar = findView(R.id.toolbar);
        toolbar.setTitle("");
        TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        ImageButton btnBack = (ImageButton) findViewById(R.id.btn_back);
        tvTitle.setText(title);
        setSupportActionBar(toolbar);
        if (isBack) {
            btnBack.setVisibility(View.VISIBLE);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (listener != null) {
                    listener.onClickBackListener();
                } else {
                    finish();//默认关闭当前Activity
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private OnClickBackListener listener;

    public interface OnClickBackListener {
        void onClickBackListener();
    }

    /**
     * 设置返回按钮的事件监听
     *
     * @param listener
     */
    public void setOnClickBackListener(OnClickBackListener listener) {
        this.listener = listener;
    }


    /**
     * 获取Intent数据
     */
    protected void getIntentData() {

    }

    /**
     * 初始化组件
     **/
    public abstract void initWidget();

    /**
     * 设置监听事件
     **/
    public void setListener() {

    }

    /**
     * 初始化数据
     **/
    public void initData() {
        if (NetUtils.isOnline(this)) {
            loadData();
        } else {
            Snackbar.make(UiUtils.getRootView(this), "请检查网络", Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData();
                }
            }).show();
        }
    }

    /**
     * 加载数据
     */
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * 控件的点击事件处理
     *
     * @param v
     */
    public void widgetClick(View v) {
    }
}
