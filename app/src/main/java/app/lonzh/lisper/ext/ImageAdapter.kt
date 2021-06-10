package app.lonzh.lisper.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/10 3:29 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/10 3:29 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
@BindingAdapter("imageUrl")
fun imageUrl(view: ImageView, url: String){
    view.load(url)
}