package com.danny.filmfan.utlis;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.util.Log;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ClientSSLSocketFactory {

    public static SSLSocketFactory getSocketFactory(Context context) {
        try {
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{tm}, new SecureRandom());

            SSLSocketFactory ssf = SSLCertificateSocketFactory.getDefault(10000, new SSLSessionCache(context));
            return ssf;
        } catch (Exception ex) {
            Log.e("ssl", "Error during the getSocketFactory");
            ex.printStackTrace();
            return null;
        }
    }
}