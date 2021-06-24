package app.lonzh.netlibrary

import android.app.Application
import app.lonzh.netlibrary.constant.NetCacheConstant
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * @Description:    RxHttp 初始化工具类
 * @Author:         Lisper
 * @CreateDate:     2020/6/23 9:52 AM
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/6/23 9:52 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class RxHttpManager {
    companion object{
        fun init(context: Application){
            val file = File(context.externalCacheDir, NetCacheConstant.NET_CACHE_PATH)
            val ss = HttpsUtils.getSslSocketFactory()
            val okHttpClient = OkHttpClient.Builder()
                .cookieJar(CookieStore(file))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(ss.sSLSocketFactory, ss.trustManager)
                .hostnameVerifier { _, _ -> true }
                .build()
            RxHttpPlugins.init(okHttpClient)
                .setDebug(BuildConfig.DEBUG)
                .setCache(file, 10*1024*1024, CacheMode.ONLY_NETWORK)
        }
    }
}