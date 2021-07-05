package app.lonzh.lisper.data

import app.lonzh.lisper.R
import app.lonzh.lisper.ext.appContext

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/2 4:03 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/2 4:03 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ShareEntity(
    val icon: Int,
    val title: String
){
    companion object{
        fun getShareEntities() : MutableList<ShareEntity>{
           return mutableListOf<ShareEntity>().let {
               it.add(ShareEntity(R.drawable.ic_wx, appContext.resources.getString(R.string.share_wx)))
               it.add(ShareEntity(R.drawable.ic_wx_circle, appContext.resources.getString(R.string.wx_circle)))
               it.add(ShareEntity(R.drawable.ic_weibo, appContext.resources.getString(R.string.weibo)))
               it.add(ShareEntity(R.drawable.ic_qq, appContext.resources.getString(R.string.qq)))
               it.add(ShareEntity(R.drawable.ic_qq_zone, appContext.resources.getString(R.string.qq_zone)))
               it
            }
        }
    }
}