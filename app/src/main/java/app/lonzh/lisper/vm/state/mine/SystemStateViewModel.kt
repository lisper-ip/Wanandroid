package app.lonzh.lisper.vm.state.mine

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/28 5:00 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/28 5:00 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SystemStateViewModel : ViewModel() {

    val cache = ObservableField<String>("0.00K")

    val version = ObservableField<String>()

    val author = ObservableField<String>("lisper")

    val webUrl = ObservableField<String>("https://github.com/lisper-ip/lisper")
}