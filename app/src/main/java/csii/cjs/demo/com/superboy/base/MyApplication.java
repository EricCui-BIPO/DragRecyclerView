package csii.cjs.demo.com.superboy.base;

import android.app.Application;

import csii.cjs.demo.com.superboy.MenuHelper;

/**
 * 描述:
 * <p>
 * 作者:陈俊森
 * 创建时间:2017年11月03日 14:19
 * 邮箱:chenjunsen@outlook.com
 *
 * @version 1.0
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtil.init(getApplicationContext());
        if(!MenuHelper.hasEverInit()){
            MenuHelper.init();
        }
    }
}
