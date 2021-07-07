package com.yinp.proapp.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hjq.permissions.XXPermissions;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * 不创建，则系统自动创建，有且仅有一个（可以看作单例模式的一个类）
 * 生命周期是整个程序的生命周期
 * 可以存储全局变量，此处应该注意内存泄露
 * 再其他类可以使用getApplication（）来得到实例
 * 图片资源初始化，WebView的预加载，推送服务的注册等等，注意不要执行耗时操作
 */
public class BaseApplication extends Application {
    public Context mAppContext;
    public static final String TAG = "yinp";
    public static final String TYPE_DEBUG = "type_debug";
    public static final String TYPE_RELEASE = "type_release";
    public static final String TYPE = TYPE_DEBUG;

    /**
     * 推送服务
     */
    public static final String APP_ID = "2882303761519857366";
    public static final String APP_KEY = "5871985762366";

    /**
     * 此处耗时操作将影响程序响应的速度
     */
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        registerActivityLifecycleCallbacks(lifecycleCallbacks);
        mAppContext = this;
//        initXUtils();
//        initPath();
        initQQ();
        initBmob();
        initXXPermissions();
        initBugly();
        MMKV.initialize(this);
//        initMIPush();
    }

    private void initXXPermissions() {
        // 当前项目是否已经适配了分区存储的特性
        XXPermissions.setScopedStorage(true);
    }

    //    /**
//     * 小米推送
//     */
//    private void initMIPush(){
//        //初始化push推送服务
//        if(shouldInit()) {
//            MiPushClient.registerPush(this, APP_ID, APP_KEY);
//        }
//        //打开Log
//        LoggerInterface newLogger = new LoggerInterface() {
//
//            @Override
//            public void setTag(String tag) {
//                // ignore
//            }
//
//            @Override
//            public void log(String content, Throwable t) {
//                Log.d(TAG, content, t);
//            }
//
//            @Override
//            public void log(String content) {
//                Log.d(TAG, content);
//            }
//        };
//        Logger.setLogger(this, newLogger);
//    }
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getApplicationInfo().processName;
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    private void initBugly() {
        // 获取当前包名
        String packageName = mAppContext.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mAppContext);
        strategy.setBuglyLogUpload(processName == null || processName.equals(packageName));
        if (TYPE.equals(TYPE_DEBUG)) {
            CrashReport.initCrashReport(getApplicationContext(), "84648843c8", true, strategy);
        } else {
            CrashReport.initCrashReport(getApplicationContext(), "84648843c8", false, strategy);
        }
    }

    private void initBmob() {
        //第一：默认初始化
        Bmob.initialize(this, "cca3c828341fc092aaca3bc378ef00bc");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }

    /**
     * 初始化xutils
     */
//    private void initXUtils() {
//        x.Ext.init(this);
//        if (TYPE.equals(TYPE_DEBUG)) {
//            x.Ext.setDebug(BuildConfig.DEBUG);
//        }
//    }
    private void initQQ() {
    }

    /**
     * 初始化存储路径
     */
    private void initPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
            return;
        }
//        /**
//         * /storage/emulated/0/Android/data/com.example.goodluck/files/images
//         */
//        File file = new File(getExternalFilesDir(Constant.IMAGE_PATH).getAbsolutePath());
//        if (!file.exists()) {
//            file.mkdirs();
//        }

//        // 初始化文件夹  外部文件夹
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            File dir = new File(mContext.getExternalFilesDir(null), Constant.APP_SD_PATH);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//        } else {
//            File dir = new File(Environment.getExternalStorageDirectory(), Constant.APP_SD_PATH);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//        }
//        File dir = new File(Environment.getExternalStorageDirectory(), Constant.APP_SD_PATH);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 很有可能不会调用,模拟器会调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 重写此方法可以监听APP一些配置信息的改变事件（如屏幕旋转等）
     *
     * @param newConfig 配置android:configChanges属性相应的配置属性，会使Activity在配置改变时候不会重启，只会执行onConfigurationChanged()方法
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 重写此方法可以监听Android系统整体内存较低时候的事件。按我的理解就是
     * ，当APP处于前台时，但是所有后台程序都被kill光了，但是还是内存不足时，系统就会调用这个方法告诉APP，兄弟轮到你了。
     * 我们可以在这个方法里面释放一些不重要的资源，来保证到时候内存足够而让APP进程不被系统杀掉，或者提醒用户清一下垃圾，
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 这个方法是一个比较难理解的方法，Trim意思是修剪，按我的理解，用这个方法打个比方：
     * 　　从前有个伟大的妈妈叫Android系统，她有一群子女叫APP，她含辛茹苦地养着这群熊孩子。当系统老妈发现她的工资（内存）不够下个月的开销的时候，就会回调这个方法，告诉她的APP子女，我现在工资不够了，你们赶紧少吃少用点，不然我就要根据你们的重要性高低来一个一个地“清理门户”了。
     * 　　这里有传入一个int类型的参数level，它告诉APP们内存不足的严重性（越高越严重）。假如这时候系统内存不足，运行着前台和后台一共几个APP，这些不同的APP会收到系统老妈不同的“劝告信息”：
     * <p>
     * TRIM_MEMORY_RUNNING_MODERATE：数值为5，这个APP是系统老妈的“掌上明珠”（前台APP），老妈让APP注意一下：不要大手大脚（释放不用的内存），我的工资（内存）不够养你了，不过就算再不够，只是把你其他不争气兄弟姐妹（杀掉后台APP）清出家门，你注意一下吧。
     * TRIM_MEMORY_RUNNING_LOW：数值10，这个APP是系统老妈的“掌上明珠”（前台APP），老妈语重心长地对APP说：孩子，我的工资（内存）实在不够了，你能不能拿点压岁钱出来帮补一下（释放不用的内存），不行的话就要把你的很多兄弟姐妹（杀掉后台APP）送走了。
     * TRIM_MEMORY_RUNNING_CRITICAL：数值15，这个APP是系统老妈的“掌上明珠”（前台APP），老妈严重警告APP：臭小子，你的兄弟姐妹（杀掉后台APP）都快走光了，你还不给我多省点钱（要求释放内存），你还真的想把你的兄弟全赶走啊，当时候就剩你一个，说不定你都自身难保啦（执行onLowMemory()方法）。
     * TRIM_MEMORY_UI_HIDDEN：数值20，老妈告诉这个APP：你个熊孩子，闯了祸（用户把APP从前台切换到后台），我要收回你的零用钱（UI资源）。
     * TRIM_MEMORY_BACKGROUND ：数值40，这些APP是老妈收养的（后台APP），老妈在吃完晚饭后留下了他，对他说：孩子啊，现在家里经济不好（内存不足），你就少花点吧，这个月的零用钱不发了吧（要求释放资源），不然的话我们家可能养不下你和你后面的那帮兄弟姐妹了（杀掉后台APP）。
     * TRIM_MEMORY_MODERATE ：数值60，这些APP是老妈收养的（后台APP），老妈偷偷地跟APP说：孩子啊，你们花费太多了，老妈的工资养不下你们了（内存不足），你们用少点吧（要求释放内存），不然等我把你后面那几个兄弟赶出去之后就轮到你了（已进入LRU缓存列表的中间位置，如果后面的APP进程资源都被回收的话，下一个就是轮到它了）。
     * <p>
     * TRIM_MEMORY_COMPLETE ：数值80，这些APP是老妈充话费送的（后台APP），老妈狠狠地对他说：臭小子，没看到都快揭不开锅了（内存不足）吗？赶紧把你的私房钱拿出来（要求释放资源），不然你们就准备滚出这个家门吧（已处于LRU缓存列表的后面位置，APP随时都有被回收的风险）。
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

    }

    /**
     * 监听所有activity的生命周期，可以做一些触发操作
     */
    public ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {

        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }
    };
}
