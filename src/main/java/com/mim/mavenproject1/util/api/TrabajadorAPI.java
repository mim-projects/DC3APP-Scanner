package com.mim.mavenproject1.util.api;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.mim.models.Trabajador;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
/*import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;*/

public interface TrabajadorAPI {
    public class Factory {
        private static TrabajadorAPI service;

        public static TrabajadorAPI getInstance(int planta) throws KeyManagementException, NoSuchAlgorithmException {
            
          final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

               /*  final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .build();
                 
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    //.baseUrl(instance.getBASE_URL())
                    .baseUrl(WorkServer.getBaseUrl(planta))
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            service = retrofit.create(TrabajadorAPI.class);
            return service;*/
            // } else {
            // return service;
            //}
            return null;
        }
    }

    /*@GET("com.mim.entities.trabajador/credencialCaseta/{codigo}")
    public Call<Trabajador> findByCode(@Path("codigo") String codigo);

    @GET("com.mim.entities.trabajador/empresa/{id}")
    public Call<List<Trabajador>> findByCompany(@Path("id") int codigo);

    @GET("com.mim.entities.trabajador/proximos/dc3/{id}")
    public Call<List<Trabajador>> findCriticsCertificates(@Path("id") int codigo);*/
}
