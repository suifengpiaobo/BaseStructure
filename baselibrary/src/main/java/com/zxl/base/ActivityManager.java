package com.zxl.base;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Description activity管理类
 * Created by zxl on 2017/5/27 下午2:44.
 * Email:444288256@qq.com
 */
public class ActivityManager {
    private Stack<Activity> activityStack = new Stack<>();

    private static ActivityManager instance;

    public static ActivityManager getInstance(){
        if (instance == null){
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity){
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 清除指定Activity
     *
     * @param activityClass
     */
    public void clearActivity(Class<? extends Activity> activityClass) {
        for (Activity act : activityStack) {
            if (act.getClass().getName().equals(activityClass.getName())) {
                act.finish();
                activityStack.remove(act);
            }
        }
    }

    /**
     * 清除指定的多个activity
     *
     * @param activityClasses
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void clearActivities(Class... activityClasses) {
        Iterator<Activity> it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            for (Class<? extends Activity> oact : activityClasses) {
                if (activity.getClass().getName().equals(oact.getName())) {
                    activity.finish();
                    it.remove();
                    break;
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

}
