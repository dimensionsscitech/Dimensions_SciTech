package com.wd.common.netWork;

import android.content.Context;

import com.wd.common.R;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * @author : FangShiKang
 * @date : 2019/02/27.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyTrustManager implements X509TrustManager {
    X509Certificate cert;

    MyTrustManager(X509Certificate cert) {
        this.cert = cert;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType){

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // 确认服务器端证书和代码中 hard code 的 CRT 证书相同。
        if (chain[0].equals(this.cert)) {
            return;
        }
        throw new CertificateException("checkServerTrusted No trusted server cert found!");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    /**
     * 根据字符串读取出证书
     *
     * @param cer
     * @return
     */
    public static X509Certificate readCert(String cer) {
        if (cer == null || cer.trim().isEmpty()) {
            return null;
        }

        InputStream caInput = new ByteArrayInputStream(cer.getBytes());
        X509Certificate cert = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            cert = (X509Certificate) cf.generateCertificate(caInput);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (caInput != null) {
                    caInput.close();
                }
            } catch (Throwable ex) {
            }
        }
        return cert;
    }

    public static String readRaw(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.server);
        StringBuffer response = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s;
        try {
            while ((s = br.readLine()) != null) {
                response.append(s);
                response.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response.toString();
    }
}

