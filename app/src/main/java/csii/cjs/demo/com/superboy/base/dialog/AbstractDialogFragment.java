package csii.cjs.demo.com.superboy.base.dialog;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import csii.cjs.demo.com.superboy.R;


/**
 * 描述:
 * <p>
 * 作者:陈俊森
 * 创建时间:2017年11月08日 10:22
 * 邮箱:chenjunsen@outlook.com
 *
 * @version 1.0
 */
public abstract class AbstractDialogFragment extends DialogFragment {
    protected View convertView;
    @LayoutRes
    protected abstract int setLayoutId();
    protected abstract void bindView(View convertView,Bundle savedInstanceState);
    protected abstract void bindEvents(View convertView,Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        convertView=inflater.inflate(setLayoutId(),null);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view,savedInstanceState);
        bindEvents(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        setWindowAttrs(getDialog().getWindow());
    }

    /**
     * 自带强转功能的findViewById
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View>T findViewByIdAuto(@IdRes int id){
        View v= convertView.findViewById(id);
        return (T) v;
    }

    /**
     * 设置窗体弹出动画样式
     */
    protected int setAnimationStyle() {
        return -1;
    }

    /**
     * 设置当前窗体背景
     */
    protected int setBackground() {
        return R.color.transparent;
    }

    /**
     * 设置窗体的显示位置
     * @return
     */
    protected int setGravity() {
        return Gravity.CENTER;
    }

    /**
     * 设置蒙层透明度
     * @return
     */
    protected float setDimAmount(){
        return 0.4f;
    }

    /**
     * 设置窗体宽度，默认为整个屏幕宽度
     * @return
     */
    protected int setWindowWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 设置窗体高度，默认为包裹窗体原始高度
     * @return
     */
    protected int setWindowHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    /**
     * 设置窗体基于Gravity的垂直偏移量百分比，偏移的具体值为次百分比乘以窗体所在容器高度。(0.x小数的效果就很明显了)
     * 正值向下偏移，负值向上偏移。
     * @return
     */
    protected float setWindowVerticalOffsetPercent(){
        return 0;
    }

    /**
     * 设置窗体基于Gravity的水平偏移量百分比，偏移的具体值为次百分比乘以窗体所在容器宽度。(0.0x的效果就很明显了)
     * 正值向右偏移，负值向左偏移。
     * @return
     */
    protected float setWindowHorizontalOffsetPercent(){
        return 0;
    }

    /**
     * 设置窗体属性
     *
     * @param window 当前窗体
     */
    protected void setWindowAttrs(Window window) {
        if (-1 != setAnimationStyle()) {
            window.setWindowAnimations(setAnimationStyle());
        }
        window.setBackgroundDrawableResource(setBackground());
        WindowManager.LayoutParams lp=window.getAttributes();
        lp = window.getAttributes();
        lp.gravity = setGravity();
        lp.width=setWindowWidth();
        lp.height=setWindowHeight();
        lp.dimAmount=setDimAmount();
        lp.verticalMargin= setWindowVerticalOffsetPercent();
        lp.horizontalMargin= setWindowHorizontalOffsetPercent();
        getDialog().onWindowAttributesChanged(lp);
    }
}
