package csii.cjs.demo.com.superboy.base.dialog;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import csii.cjs.demo.com.superboy.R;
import csii.cjs.demo.com.superboy.base.ContextUtil;
import csii.cjs.demo.com.superboy.base.FingerPrintHelper;
import csii.cjs.demo.com.superboy.tools.ColorKit;
import csii.cjs.demo.com.superboy.tools.DrawableKit;


/**
 * 描述:
 * <p>
 * 作者:陈俊森
 * 创建时间:2017年11月08日 10:20
 * 邮箱:chenjunsen@outlook.com
 *
 * @version 1.0
 */
public class FingerPrintDialog extends AbstractDialogFragment implements View.OnClickListener{
    private Button btn_cancel;
    private ImageView iv_back;
    private ImageView iv_icon_finger;
    private TextView tv_title;
    private TextView tv_desc;
    private ProgressBar mProgressBar;

    private boolean enableVibrator=true;//是否开启震动
    private Vibrator mVibrator;

    private CancellationSignal mCancellationSignal;//指纹识别取消控制器

    private static final int COLOR_ERROR= R.color.colorAccent;
    private static final int COLOR_WARN=R.color.orange;
    private static final int COLOR_SUCCESS=R.color.green;

    private boolean isSuccess;//指纹识别是否成功

    private OnDismissListener mOnDismissListener;

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mVibrator= (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    }

    public static FingerPrintDialog showDialog(FragmentActivity activity,boolean cancelable, String tag,OnDismissListener onDismissListener){
        FingerPrintDialog dialog=new FingerPrintDialog();
        dialog.setCancelable(cancelable);
        dialog.setOnDismissListener(onDismissListener);
        dialog.show(activity.getSupportFragmentManager(),tag);
        dialog.startFingerPrint();
        return dialog;
    }

    public static FingerPrintDialog showDialog(FragmentActivity activity,OnDismissListener onDismissListener){
        return showDialog(activity,true,"",onDismissListener);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_finger_print;
    }

    @Override
    protected void bindView(View convertView, Bundle savedInstanceState) {
        btn_cancel=findViewByIdAuto(R.id.btn_cancel);
        iv_back=findViewByIdAuto(R.id.iv_back);
        tv_title=findViewByIdAuto(R.id.tv_title);
        tv_desc=findViewByIdAuto(R.id.tv_desc);
        iv_icon_finger=findViewByIdAuto(R.id.iv_icon_finger);
        mProgressBar=findViewByIdAuto(R.id.progress);
    }

    @Override
    protected void bindEvents(View convertView, Bundle savedInstanceState) {
        Bundle data=getArguments();
        iv_back.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        setStyle(getString(R.string.finger_print_default),STYLE_DEFAULT);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_cancel){
            dismissAllowingStateLoss();
        }else if(v==iv_back){
            dismissAllowingStateLoss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(mCancellationSignal!=null){
            mCancellationSignal.cancel();
            mCancellationSignal=null;
        }
        if(mOnDismissListener!=null){
            mOnDismissListener.onDismiss(isSuccess);
        }
        super.onDismiss(dialog);
    }

    @Override
    protected int setWindowWidth() {
        return (int) (super.setWindowWidth()*0.8);
    }

    private void startFingerPrint(){
        mCancellationSignal=new CancellationSignal();
        FingerPrintHelper.doFingerPrint(ContextUtil.getContext(), mCancellationSignal,new FingerprintManagerCompat.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                super.onAuthenticationError(errMsgId, errString);
                setStyle(errString.toString(),STYLE_ERROR);
                startVibrate();
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                super.onAuthenticationHelp(helpMsgId, helpString);
                setStyle(helpString.toString(),STYLE_WARN);
                startVibrate();
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                setStyle(getString(R.string.finger_print_success),STYLE_SUCCESS);
                startVibrate();
                isSuccess=true;
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissAllowingStateLoss();
                    }
                },1000);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                setStyle(getString(R.string.finger_print_failed),STYLE_ERROR);
                startVibrate();
            }
        });
    }

    public void startVibrate(){
        if(enableVibrator && mVibrator!=null){
            mVibrator.vibrate(200);
        }
    }

    private static final int STYLE_DEFAULT=0;
    private static final int STYLE_SUCCESS=1;
    private static final int STYLE_ERROR=-1;
    private static final int STYLE_WARN=2;

    private void setStyle(String msg,int style){
        tv_desc.setText(msg);
        mProgressBar.setVisibility(View.VISIBLE);
        switch (style){
            case STYLE_ERROR:
                tv_desc.setTextColor(ColorKit.getColorSrc(COLOR_ERROR));
                DrawableKit.setDrawableTintColor(iv_icon_finger.getDrawable(),COLOR_ERROR);
                break;
            case STYLE_WARN:
                tv_desc.setTextColor(ColorKit.getColorSrc(COLOR_WARN));
                DrawableKit.removeDrawableTintColor(iv_icon_finger.getDrawable());
                break;
            case STYLE_SUCCESS:
                tv_desc.setTextColor(ColorKit.getColorSrc(COLOR_SUCCESS));
                DrawableKit.setDrawableTintColor(iv_icon_finger.getDrawable(),COLOR_SUCCESS);
                mProgressBar.setVisibility(View.GONE);
                break;
            default:
                tv_desc.setTextColor(ColorKit.getColorSrc(R.color.white_gray));
                DrawableKit.removeDrawableTintColor(iv_icon_finger.getDrawable());
                break;

        }
    }

    /**
     * 描述:对话框消失的时候的监听器
     *
     * <br>作者: 陈俊森
     * <br>创建时间: 2017/11/8 0008 15:26
     * <br>邮箱: chenjunsen@outlook.com
     * @version 1.0
     */
    public interface OnDismissListener{
        /**
         * 对话框消失的时候
         * @param isSuccess 验证是否成功
         */
        void onDismiss(boolean isSuccess);
    }
}
