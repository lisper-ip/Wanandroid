package app.lonzh.lisper.vm.state.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import app.lonzh.lisper.utils.MMKVUtil

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/29 11:06 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/29 11:06 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SearchStateViewModel : ViewModel() {
    val keyword = ObservableField("")

    val hasHistory = ObservableField(false)

    val history = ObservableField<List<String>>()

    init {
        val histories = mutableListOf<String>()
        val result = MMKVUtil.getSetObject(MMKVUtil.HISTORY)
        result?.map {
            histories.add(it)
        }
        hasHistory.set(histories.isNotEmpty())
        history.set(histories)
    }

    val hasHot = ObservableField(false)
}