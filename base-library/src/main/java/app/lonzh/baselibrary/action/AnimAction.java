package app.lonzh.baselibrary.action;

import app.lonzh.baselibrary.R;

/**
 * @ProjectName: lisper
 * @Description: 描述
 * @Author: Lisper
 * @CreateDate: 5/18/21 11:26 AM
 * @UpdateUser: Lisper：
 * @UpdateDate: 5/18/21 11:26 AM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface AnimAction {
    /** 默认动画效果 */
    int ANIM_DEFAULT = -1;

    /** 没有动画效果 */
    int ANIM_EMPTY = 0;

    /** 缩放动画 */
    int ANIM_SCALE = R.style.ScaleAnimStyle;

    /** IOS 动画 */
    int ANIM_IOS = R.style.IOSAnimStyle;

    /** 吐司动画 */
    int ANIM_TOAST = android.R.style.Animation_Toast;

    /** 顶部弹出动画 */
    int ANIM_TOP = R.style.TopAnimStyle;

    /** 底部弹出动画 */
    int ANIM_BOTTOM = R.style.BottomAnimStyle;

    /** 左边弹出动画 */
    int ANIM_LEFT = R.style.LeftAnimStyle;

    /** 右边弹出动画 */
    int ANIM_RIGHT = R.style.RightAnimStyle;
}
