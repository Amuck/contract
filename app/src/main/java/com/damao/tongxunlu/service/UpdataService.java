package com.damao.tongxunlu.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.damao.tongxunlu.rxbus.RxBus;
import com.damao.tongxunlu.rxbus.RxConstants;

import java.io.File;
import java.util.Objects;

import static com.damao.tongxunlu.util.ConstanceUtil.APK_NAME;
import static com.damao.tongxunlu.util.ConstanceUtil.DOWNLOADPATH;
import static com.damao.tongxunlu.util.ConstanceUtil.DOWNLOAD_TITLE;
import static com.damao.tongxunlu.util.ConstanceUtil.UPDATA_APP;


/**
 * @author chenlong
 *
 * the service used to check if there is new version of this app, and download the new verion from url
 */
public class UpdataService extends Service {

    /**
     * 安卓系统下载类
     **/
    private DownloadManager manager;
    /**
     * 接收下载完的广播
     **/
    private DownloadCompleteReceiver receiver;
    private String url;

    /**
     * 初始化下载器
     **/
    private void initDownManager() {
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        //设置下载地址
        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);
        down.setAllowedOverRoaming(false);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        down.setMimeType(mimeString);
        // 下载时，通知栏显示途中
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // 显示下载界面
        down.setVisibleInDownloadsUi(true);
        // 设置下载后文件存放的位置
        down.setDestinationInExternalPublicDir(DOWNLOADPATH, APK_NAME);
        down.setTitle(DOWNLOAD_TITLE);
        // 将下载请求放入队列
        manager.enqueue(down);
        //注册下载广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        url = intent.getStringExtra(UPDATA_APP);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + DOWNLOADPATH + APK_NAME;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
//            showDownload();
            // 调用下载
            initDownManager();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "下载失败, 请重试.", Toast.LENGTH_SHORT).show();
            downlaodFailed();
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        if (receiver != null)
            // 注销下载广播
            unregisterReceiver(receiver);
        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            //判断是否下载完成的广播
            if (Objects.equals(intent.getAction(), DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                //获取下载的文件id
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (manager.getUriForDownloadedFile(downId) != null) {
                    //自动安装apk
                    installAPK(manager.getUriForDownloadedFile(downId), context);
                    //installAPK(context);
                } else {
                    Toast.makeText(context, "下载失败, 请重试.", Toast.LENGTH_SHORT).show();
                    downlaodFailed();
                }
                //停止服务并关闭广播
                UpdataService.this.stopSelf();
            }
        }

        private void installAPK(Uri apk, Context context) {
            downloadSuccess();

            if (Build.VERSION.SDK_INT < 23) {
                Intent intents = new Intent();
                intents.setAction("android.intent.action.VIEW");
                intents.addCategory("android.intent.category.DEFAULT");
                intents.setType("application/vnd.android.package-archive");
                intents.setData(apk);
                intents.setDataAndType(apk, "application/vnd.android.package-archive");
                intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intents);
            } else {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DOWNLOADPATH + APK_NAME);
                if (file.exists()) {
                    openFile(file, context);
                }
            }
        }

        private void installAPK(Context context) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DOWNLOADPATH + APK_NAME);
            if (file.exists()) {
                openFile(file, context);
            } else {
                Toast.makeText(context, "下载失败, 请重试.", Toast.LENGTH_SHORT).show();
                downlaodFailed();
            }
        }
    }


    public void openFile(File file, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        String type = getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        try {
            context.startActivity(intent);
        } catch (Exception var5) {
            var5.printStackTrace();
            Toast.makeText(context, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
        }

    }

    public String getMIMEType(File var0) {
        String var1;
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

    private   void downlaodFailed() {
        RxBus.getDefault().postWithCode(RxConstants.UPDATE_APP, RxConstants.UPDATE_APP_FAILED);
    }

    private void downloadSuccess() {
        RxBus.getDefault().postWithCode(RxConstants.UPDATE_APP, RxConstants.UPDATE_APP_SUCCESS);
    }
}
