package app.lonzh.commonlibrary.event

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 6:39 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 6:39 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class SingleLiveEvent<T> : MediatorLiveData<T>() {
    companion object{
        val TAG = "SingleLiveEvent"
        val mPending = AtomicBoolean(false)
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if(hasActiveObservers()){
            Log.w(
                TAG,
                "Multiple observers registered but only one will be notified of changes."
            )
        }
        super.observe(owner, {
            if(mPending.compareAndSet(true, false)){
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(value: T) {
        mPending.set(true)
        super.setValue(value)
    }

    open fun call(){
        postValue(null)
    }
}