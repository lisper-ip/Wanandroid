package app.lonzh.lisper.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.lisper.data.BannerBean
import coil.load
import com.youth.banner.adapter.BannerAdapter

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/21/21 11:24 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/21/21 11:24 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class HomeBannerAdapter(list: List<BannerBean>?) : BannerAdapter<BannerBean, HomeBannerAdapter.MainBannerViewHolder>(list) {

    inner class MainBannerViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    /**
     * 创建ViewHolder
     *
     * @return XViewHolder
     */
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): MainBannerViewHolder {
        val imageView = ImageView(parent?.context)
        imageView.run {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return MainBannerViewHolder(imageView)
    }

    /**
     * 绑定布局数据
     *
     * @param holder   XViewHolder
     * @param data     数据实体
     * @param position 当前位置
     * @param size     总数
     */
    override fun onBindView(
        holder: MainBannerViewHolder?,
        data: BannerBean?,
        position: Int,
        size: Int
    ) {
        holder?.imageView?.load(data?.imagePath)
    }
}