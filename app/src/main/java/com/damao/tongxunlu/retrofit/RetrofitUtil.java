package com.damao.tongxunlu.retrofit;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import com.damao.tongxunlu.BaseApplication;
import com.damao.tongxunlu.util.ConstanceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author chenlong
 *         <p>
 *         init all of the components used to access the net interface
 */
public class RetrofitUtil {
    private static final String TAG = "contract_Retrofit";

    private static APIService service = getRetrofit().create(APIService.class);
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    public static APIService getService() {
        return service;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            AssetManager assetManager = BaseApplication.getAppContext().getAssets();
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(9, TimeUnit.SECONDS)
                    .build();
//            OkHttpClient client = null;
//            try {
//                client = getOkHttpClient(BaseApplication.getInstance(),null);
//                        assetManager.open("1_down.jinrongweb.net_bundle.crt"),
//                        assetManager.open("1_jinrongweb.com_bundle.crt"),
//                        assetManager.open("ServerCertificate.cer"));
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl(ConstanceUtil.API_HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
        return retrofit;
    }

    /**
     * 获取oKHttpClient
     * certificates 证书信息 没有就传null
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient(Application appContext, InputStream... certificates) {
        if (okHttpClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Log.i(TAG, message));
//            File sdcache = appContext.getExternalCacheDir();
            int cacheSize = 10 * 1024 * 1024;
//            assert sdcache != null;
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(interceptor);
            if (certificates != null) {
                try {
                    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    keyStore.load(null);
                    int index = 0;
                    for (InputStream certificate : certificates) {
                        String certificateAlias = Integer.toString(index++);
                        keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                        try {
                            if (certificate != null)
                                certificate.close();
                        } catch (IOException ignored) {
                        }
                    }
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init(keyStore);
                    sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
                    builder.sslSocketFactory(sslContext.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    /**
     * 获取SSLSocketFactory
     *
     * @param certificates 证书流文件
     * @return
     */
    private static SSLSocketFactory getSSLSocketFactory(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
