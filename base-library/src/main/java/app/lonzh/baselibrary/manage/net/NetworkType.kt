package app.lonzh.baselibrary.manage.net

/**
 *
 * @Description:    定义网络枚举类
 * @Author:         Lisper
 * @CreateDate:     2020/6/23 9:45 AM
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/6/23 9:45 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
enum class NetworkType(var desc: String) {
    NETWORK_WIFI("当前网络wifi"),
    NETWORK_4G("当前网络4G"),
    NETWORK_3G("当前网络3G"),
    NETWORK_2G("当前网络2G"),
    NETWORK_UNKNOWN("未知网络"),
    NETWORK_NO("当前无网络")
}