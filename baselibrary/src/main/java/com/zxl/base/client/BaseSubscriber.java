package com.zxl.base.client;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.zxl.base.ActivityManager;
import com.zxl.base.utils.LogUtils;
import com.zxl.base.utils.ToastUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Description 订阅者的基类，封装对话框等操作
 * Created by zxl on 2017/5/27 下午3:36.
 * Email:444288256@qq.com
 */
public abstract class BaseSubscriber implements Subscriber<ResponseBody> {
    private static final String TAG = "BaseSubscriber";
    //加载对话框
    protected ProgressDialog mLoadingDialog;
    //是否显示加载对话框
    protected boolean mIsShowDialog;
    protected Context mContext;
    protected String mLoadText;


    public BaseSubscriber(Context context, boolean isShowDialog) {
        this.mContext = context;
        mIsShowDialog = isShowDialog;
    }

    public BaseSubscriber(Context context) {
        this(context, true);
    }

    public BaseSubscriber(boolean isShowDialog) {
        mContext = ActivityManager.getInstance().currentActivity();
        mIsShowDialog = isShowDialog;
    }

    public BaseSubscriber() {
        this(true);
    }

    public BaseSubscriber(String loadText){
        this(true);
        mLoadText = loadText;
    }

    protected void showDialog() {
        if (mLoadingDialog == null) {
            if (TextUtils.isEmpty(mLoadText)){
                mLoadText = "正在努力加载中...";
            }
            mLoadingDialog = ProgressDialog.show(mContext, null, mLoadText,
                    true, true);
        }
        mLoadingDialog.show();
    }

    protected void disMissDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSubscribe(Subscription s) {
        //示对话框
        if (mIsShowDialog) {
            showDialog();
        }
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String result = responseBody.string();
            //执行具体的解析数据逻辑
            doOnNext(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        //显示错误
        if (mIsShowDialog) {
            disMissDialog();
        }

        ToastUtil.showLongToast("网络异常");

        if (t != null) {
            t.printStackTrace();
        }
        LogUtils.error(TAG, "onError" + (TextUtils.isEmpty(t.getMessage()) ? "" : t.getMessage()));
    }

    @Override
    public void onComplete() {
        //消失对话框
        if (mIsShowDialog) {
            disMissDialog();
        }
    }

    //子类去实现具体的解析逻辑
    public abstract void doOnNext(String result);
}
