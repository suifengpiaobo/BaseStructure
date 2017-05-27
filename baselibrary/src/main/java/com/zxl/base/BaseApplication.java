package com.zxl.base;

import android.support.multidex.MultiDexApplication;

import com.liulishuo.filedownloader.FileDownloader;
import com.zxl.base.utils.ContextUtils;

/**
 * Description
 * Created by zxl on 2017/5/27 下午3:11.
 * Email:444288256@qq.com
 */
public abstract class BaseApplication extends MultiDexApplication {

    public abstract void initConfig();
    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.getInstance().setContext(this.getApplicationContext());
        initConfig();
        FileDownloader.init(getApplicationContext());
    }
}
