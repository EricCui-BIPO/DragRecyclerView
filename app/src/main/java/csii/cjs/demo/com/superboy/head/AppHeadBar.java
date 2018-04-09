package csii.cjs.demo.com.superboy.head;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import csii.cjs.demo.com.superboy.R;
import csii.cjs.demo.com.superboy.tools.ColorKit;

/**
 * 描述:App的标题栏
 * <p>
 * 作者:陈俊森
 * 创建时间:2017年10月20日 17:23
 * 邮箱:chenjunsen@outlook.com
 *
 * @version 1.0
 */
public class AppHeadBar extends FrameLayout {
    private String mTitle;
    private int mTitleSize;
    private int mTitleColorSrc;
    private String mRightBtnText;
    private int mRightBtnTextSize;
    private int mRightBtnTextColor;
    private boolean showRightBtn;
    private boolean can_back;

    private ImageView iv_back;
    private TextView tv_title;
    private Button btn_right;

    private OnBackClickListener mOnBackClickListener;
    private OnRightButtonClickListener mOnRightButtonClickListener;

    public AppHeadBar(Context context) {
        super(context);
        inflateView(context, null, 0, 0);
    }

    public AppHeadBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateView(context, attrs, 0, 0);
    }

    public AppHeadBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public AppHeadBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void inflateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AppHeadBar);
        mTitle=array.getString(R.styleable.AppHeadBar_title);
        mTitleColorSrc=array.getColor(R.styleable.AppHeadBar_title_color, ColorKit.getColorSrc(getContext(),R.color.white));
        mTitleSize=array.getDimensionPixelOffset(R.styleable.AppHeadBar_title_size, sp2px(getContext(),18));
        can_back=array.getBoolean(R.styleable.AppHeadBar_can_back,true);
        showRightBtn=array.getBoolean(R.styleable.AppHeadBar_show_right_btn,false);
        mRightBtnText=array.getString(R.styleable.AppHeadBar_right_btn_text);
        mRightBtnTextSize=array.getDimensionPixelOffset(R.styleable.AppHeadBar_right_btn_text_size, sp2px(getContext(),14));
        mRightBtnTextColor=array.getColor(R.styleable.AppHeadBar_right_btn_text_color, ColorKit.getColorSrc(getContext(),R.color.white));
        array.recycle();

        View headBar = LayoutInflater.from(context).inflate(R.layout.head_app, this, true);
        iv_back= (ImageView) headBar.findViewById(R.id.back);
        tv_title= (TextView) headBar.findViewById(R.id.title);
        iv_back.setVisibility(can_back?View.VISIBLE:View.GONE);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnBackClickListener!=null){
                    mOnBackClickListener.onBackClick(v);
                }else{
                    if(getContext() instanceof Activity){
                        ((Activity)getContext()).finish();
                    }
                }
            }
        });
        tv_title.setText(mTitle);
        tv_title.setTextColor(mTitleColorSrc);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);

        btn_right= (Button) headBar.findViewById(R.id.btn_right);
        btn_right.setVisibility(showRightBtn?View.VISIBLE:View.GONE);
        btn_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnRightButtonClickListener!=null){
                    mOnRightButtonClickListener.onRightClick(v);
                }
            }
        });
        btn_right.setText(mRightBtnText);
        btn_right.setTextSize(TypedValue.COMPLEX_UNIT_PX,mRightBtnTextSize);
        btn_right.setTextColor(mRightBtnTextColor);
    }

    public String getTitle() {
        return mTitle;
    }

    /**
     * 设置标题文字
     * @param title
     */
    public void setTitle(String title) {
        mTitle = title;
        if(tv_title!=null){
            tv_title.setText(mTitle);
        }
    }

    public int getTitleSize() {
        return mTitleSize;
    }

    /**
     * 设置标题文字大小
     * @param titleSize
     */
    public void setTitleSize(int titleSize) {
        mTitleSize = titleSize;
        if(tv_title!=null){
            tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        }
    }

    public int getTitleColorSrc() {
        return mTitleColorSrc;
    }

    /**
     * 设置标题文字颜色
     * @param titleColorSrc
     */
    public void setTitleColorSrc(int titleColorSrc) {
        mTitleColorSrc = titleColorSrc;
        if(tv_title!=null){
            tv_title.setTextColor(mTitleColorSrc);
        }
    }

    /**
     * 是否有返回键
     * @return
     */
    public boolean isCan_back() {
        return can_back;
    }

    /**
     * 设置是否有返回键
     * @param can_back
     */
    public void setCan_back(boolean can_back) {
        this.can_back = can_back;
        if(iv_back!=null){
            iv_back.setVisibility(can_back?View.VISIBLE:View.GONE);
        }
    }

    /**
     * 获取右侧按钮的显示文字
     * @return
     */
    public String getRightBtnText() {
        return mRightBtnText;
    }

    public void setRightBtnText(String rightBtnText) {
        mRightBtnText = rightBtnText;
        if(btn_right!=null){
            btn_right.setText(mRightBtnText);
        }
    }

    /**
     * 获取右侧按钮的文字大小
     * @return
     */
    public int getRightBtnTextSize() {
        return mRightBtnTextSize;
    }

    /**
     * 设置右侧按钮的文字大小
     * @param rightBtnTextSize
     */
    public void setRightBtnTextSize(int rightBtnTextSize) {
        mRightBtnTextSize = rightBtnTextSize;
        if(btn_right!=null){
            btn_right.setTextSize(TypedValue.COMPLEX_UNIT_PX,mRightBtnTextSize);
        }
    }

    /**
     * 获取右侧文字的颜色
     * @return
     */
    public int getRightBtnTextColor() {
        return mRightBtnTextColor;
    }

    /**
     * 设置右侧文字的颜色
     * @param rightBtnTextColor
     */
    public void setRightBtnTextColor(int rightBtnTextColor) {
        mRightBtnTextColor = rightBtnTextColor;
        if(btn_right!=null){
            btn_right.setTextColor(mRightBtnTextColor);
        }
    }

    /**
     * 是否显示右侧按钮
     * @return
     */
    public boolean isShowRightBtn() {
        return showRightBtn;
    }

    /**
     * 设置是否显示右侧按钮
     * @param showRightBtn
     */
    public void setShowRightBtn(boolean showRightBtn) {
        this.showRightBtn = showRightBtn;
        if(btn_right!=null){
            btn_right.setVisibility(showRightBtn?View.VISIBLE:View.GONE);
        }
    }

    /**
     * 设置返回键监听器
     * @param onBackClickListener
     */
    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        mOnBackClickListener = onBackClickListener;
    }

    /**
     * 设置右侧按钮点击监听器
     * @param onRightButtonClickListener
     */
    public void setOnRightButtonClickListener(OnRightButtonClickListener onRightButtonClickListener) {
        mOnRightButtonClickListener = onRightButtonClickListener;
    }

    /**
     * 描述:标题栏返回键监听器
     *
     * <br>作者: 陈俊森
     * <br>创建时间: 2017/10/23 0023 9:09
     * <br>邮箱: chenjunsen@outlook.com
     * @version 1.0
     */
    public interface OnBackClickListener{
        void onBackClick(View v);
    }

    /**
     * 描述:标题栏右侧按钮单击监听器
     *
     * <br>作者: 陈俊森
     * <br>创建时间: 2017/10/30 0030 14:51
     * <br>邮箱: chenjunsen@outlook.com
     * @version 1.0
     */
    public interface OnRightButtonClickListener{
        void onRightClick(View v);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
