package com.zxl.base.client;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.zxl.base.ActivityManager;
import com.zxl.base.utils.BuildConfig;
import com.zxl.base.utils.LogUtils;
import com.zxl.base.utils.PreferenceUtils;
import com.zxl.base.utils.ToastUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Description
 * Created by zxl on 2017/5/27 下午3:51.
 * Email:444288256@qq.com
 */
public abstract class CacheSubscriber extends BaseSubscriber {
    private static final String TAG = "CacheSubscriber";
    private String mCacheKey;

    public CacheSubscriber(Context context, boolean isShowDialog, String cacheKey) {
        mContext = context;
        mIsShowDialog = isShowDialog;
        mCacheKey = cacheKey;
    }

    public CacheSubscriber(Context context, String cacheKey) {
        this(context, true, cacheKey);
    }

    public CacheSubscriber(String cacheKey, boolean isShowDialog) {
        mContext = ActivityManager.getInstance().currentActivity();
        mIsShowDialog = isShowDialog;
        mCacheKey = cacheKey;
    }

    public CacheSubscriber(String cacheKey) {
        this(cacheKey, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String result = responseBody.string();
            if (BuildConfig.IS_CACHE_JSON_DATA) {
                PreferenceUtils.getInstance().put(mCacheKey, result);
            }
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
        if (BuildConfig.IS_CACHE_JSON_DATA) {
            String cache = (String) PreferenceUtils.getInstance().get(mCacheKey,"");
            if (TextUtils.isEmpty(cache)) {
                //表示网络异常，并且没有缓存数据。缓存具体在RetrofitClient类中实现
                ToastUtil.showToast("网络异常，并且没有获取到缓存数据！");
                LogUtils.error(TAG, "网络异常，并且没有获取到缓存数据！");
                return;
            }
            doOnNext(cache);
        } else {
            ToastUtil.showToast("网络异常");
        }
        if (t != null) {
            t.printStackTrace();
        }
        LogUtils.error(TAG, "onError" + (TextUtils.isEmpty(t.getMessage()) ? "" : t.getMessage()));
    }
}
