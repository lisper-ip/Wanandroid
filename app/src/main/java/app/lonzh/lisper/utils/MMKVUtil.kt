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
    const val USER = "user"

    const val HISTORY = "history"

    fun getInstance(str: String?) : MMKV?{
        if(str == null){
            return MMKV.defaultMMKV()
        }
        return MMKV.mmkvWithID(str)
    }

    fun <P : Parcelable > setObject(key: String, value : P){
        getInstance(null)?.encode(key, value)
    }

    fun <T : Parcelable> getObject(key: String, clazz: Class<T>): T?{
        return getInstance(null)?.decodeParcelable(key, clazz)
    }

    fun getSetObject(key: String): MutableSet<String>?{
       return getInstance(null)?.getStringSet(key, mutableSetOf())
    }

    fun setSetObject(key: String, sets: MutableSet<String>?){
        getInstance(null)?.encode(key, sets)
    }

    fun clearUser(){
        getInstance(null)?.clearAll()
    }
}