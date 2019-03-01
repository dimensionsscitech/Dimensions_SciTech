package com.wd.common.netWork;

import android.util.Log;

import com.wd.common.utils.ContextUtils;
import com.wd.common.utils.CustomIntercept;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author : FangShiKang
 * @date : 2019/02/27.
 * email : fangshikang@outlook.com
 * desc :   Retrofit网络请求的工具类
 */
public class RetrofitUtil {

    private static final String TAG = "RetrofitUtil";

    private static RetrofitUtil instance;
    private ObservedApis mObservedApis;
    private final String BaseUrl = "https://mobile.bwstudent.com/techApi/";

    /*public static synchronized RetrofitUtil getInstance() throws KeyManagementException, NoSuchAlgorithmException {
        if (instance == null) {
            instance = new RetrofitUtil();
        }
        return instance;
    }*/

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                instance = new RetrofitUtil();
            }
        }
        return instance;
    }

    private RetrofitUtil() {
        SSLContext sc = null;
        TrustManager tm = null;
        try {
            sc = SSLContext.getInstance("TLS");
            //信任证书管理,这个是由我们自己生成的,信任我们自己的服务器证书
            tm = new MyTrustManager(MyTrustManager.readCert(MyTrustManager.readRaw(ContextUtils.getApplication())));
            sc.init(null, new TrustManager[]{
                    tm
            }, null);
        } catch (Exception e) {

        }

        //拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient mClient = new OkHttpClient.Builder()
                //读取超时
                .readTimeout(
                        10, TimeUnit.SECONDS)
                //连接超时
                .connectTimeout(10, TimeUnit.SECONDS)
                //写超时
                .writeTimeout(10, TimeUnit.SECONDS)
                //添加拦截器
                .addInterceptor(interceptor)//添加自定义拦截器,header头参注入
                .sslSocketFactory(sc.getSocketFactory(), (X509TrustManager) tm)
                .hostnameVerifier(hostnameVerifier)
                .build();

        //Retrofit的创建
        Retrofit mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BaseUrl)
                .client(mClient)
                .build();
        mObservedApis = mRetrofit.create(ObservedApis.class);
    }

    //主机地址验证
    final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return hostname.equals("mobile.bwstudent.com");
        }
    };

    //get请求
    public void get(String url, ICallBack callBack) {
        mObservedApis.get(url)
                //后执行在哪个线程
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
    }


    //post请求
    public void post(String url, Map<String, String> params, Map<String, String> headerMap, ICallBack callBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        mObservedApis.post(url, params, headerMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
    }

    //delete请求
    public void delete(String url, ICallBack callBack) {
        mObservedApis.delete(url)
                //后执行在哪个线程
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));

    }

    //put请求
    public void put(String url, Map<String, String> params, ICallBack callBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        mObservedApis.put(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
    }

    //图片上传
    public void postImage(String url, File file, ICallBack callBack) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        mObservedApis.postImage(url, filePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));

    }

    private Observer getObserver(final ICallBack callBack) {
        //Rxjava
        Observer observer = new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (callBack != null) {
                    callBack.failureData(e.getMessage());
                    Log.i(TAG, e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    if (callBack != null) {
                        callBack.successData(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.failureData(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }


    public interface ICallBack {
        void successData(String result);

        void failureData(String error);
    }

}
