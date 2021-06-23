package app.lonzh.lisper.utils

import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/8 3:18 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/8 3:18 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
object MMKVUtil {
    fun getInstance(str: String?) : MMKV?{
        if(str == null){
            return MMKV.defaultMMKV()
        }
        return MMKV.mmkvWithID(str)
    }

    fun <P : Parcelable > setObject(value : P){
        getInstance(null)?.encode("user", value)
    }

    fun <T : Parcelable> getObject(key: String, clazz: Class<T>): T?{
        return getInstance(null)?.decodeParcelable(key, clazz)
    }

    fun clearUser(){
        getInstance(null)?.clearAll()
    }
}