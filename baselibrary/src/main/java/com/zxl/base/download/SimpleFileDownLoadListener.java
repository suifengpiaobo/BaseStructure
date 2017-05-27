package com.zxl.base.download;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.zxl.base.utils.LogUtils;

/**
 * Description 下载
 * Created by zxl on 2017/5/27 下午4:18.
 * Email:444288256@qq.com
 */
public abstract class SimpleFileDownLoadListener extends FileDownloadListener {
    private static final String TAG = "SimpleFileDownloadListener";

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
    }

    @Override
    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        int percent = (int) (((float) soFarBytes / totalBytes) * 100);
        updateProgress(percent);
    }

    protected void updateProgress(int percent) {

    }

    @Override
    protected void blockComplete(BaseDownloadTask task) {
    }

    @Override
    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
    }

    @Override
    protected abstract void completed(BaseDownloadTask task);

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        LogUtils.error(TAG, task.getFilename() + "下载失败:" + e.getMessage());
        e.printStackTrace();
    }

    @Override
    protected void warn(BaseDownloadTask task) {
    }
}
