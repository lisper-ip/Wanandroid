package app.lonzh.baselibrary.manage.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager

/**
 *
 * @Description:    网络工具类
 * @Author:         Lisper
 * @CreateDate:     2020/6/23 9:44 AM
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/6/23 9:44 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
object NetHelper {

    private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        val mConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return mConnectivityManager.activeNetworkInfo
    }

    /**
     * 判断是否有网络链接
     */
    fun isNetWorkAvailable(context: Context): Boolean {
        val netWork =
            getActiveNetworkInfo(
                context
            )
        if (netWork != null) {
            return netWork.isAvailable
        }
        return false
    }

    fun isNetWorkConnected(context: Context): Boolean {
        val netWork =
            getActiveNetworkInfo(
                context
            )
        if (netWork != null) {
            return netWork.isConnected
        }
        return false
    }

    /**
     * 获取网络类型
     */
    fun getNetWorkType(context: Context?): NetworkType {
        var networkType = NetworkType.NETWORK_NO
        context?.let {
            val networkInfo =
                getActiveNetworkInfo(
                    context
                )
            networkInfo?.let {
                if (it.isAvailable) {
                    when (it.type) {
                        ConnectivityManager.TYPE_WIFI -> networkType = NetworkType.NETWORK_WIFI
                        ConnectivityManager.TYPE_MOBILE -> {
                            when (it.subtype) {
                                TelephonyManager.NETWORK_TYPE_TD_SCDMA,
                                TelephonyManager.NETWORK_TYPE_EVDO_A,
                                TelephonyManager.NETWORK_TYPE_UMTS,
                                TelephonyManager.NETWORK_TYPE_EVDO_0,
                                TelephonyManager.NETWORK_TYPE_HSDPA,
                                TelephonyManager.NETWORK_TYPE_HSUPA,
                                TelephonyManager.NETWORK_TYPE_HSPA,
                                TelephonyManager.NETWORK_TYPE_EVDO_B,
                                TelephonyManager.NETWORK_TYPE_EHRPD,
                                TelephonyManager.NETWORK_TYPE_HSPAP -> networkType =
                                    NetworkType.NETWORK_3G
                                TelephonyManager.NETWORK_TYPE_LTE,
                                TelephonyManager.NETWORK_TYPE_IWLAN ->
                                    networkType = NetworkType.NETWORK_4G
                                TelephonyManager.NETWORK_TYPE_GSM,
                                TelephonyManager.NETWORK_TYPE_GPRS,
                                TelephonyManager.NETWORK_TYPE_CDMA,
                                TelephonyManager.NETWORK_TYPE_EDGE,
                                TelephonyManager.NETWORK_TYPE_1xRTT,
                                TelephonyManager.NETWORK_TYPE_IDEN ->
                                    networkType = NetworkType.NETWORK_2G
                                else ->{
                                    val subName = it.subtypeName
                                    networkType = if (subName.equals("WCDMA", ignoreCase = true)
                                        || subName.equals("TD-SCDMA", ignoreCase = true)
                                        || subName.equals("CDMA2000", ignoreCase = true)){
                                        NetworkType.NETWORK_3G
                                    }else{
                                        NetworkType.NETWORK_UNKNOWN
                                    }
                                }
                            }
                        }
                        else -> {
                            networkType = NetworkType.NETWORK_UNKNOWN
                        }
                    }
                }
            }
        }
        return networkType
    }
}