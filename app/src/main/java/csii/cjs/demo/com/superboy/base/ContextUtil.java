package csii.cjs.demo.com.superboy.base;

import android.content.Context;

/**
 * 描述:上下文句柄获取工具类
 * <br>作者:Alienware
 * <br>创建时间:2017年08月16日 14:49
 * <br>邮箱:chenjunsen@outlook.com
 */
public class ContextUtil {
    private static Context context;

    private ContextUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        ContextUtil.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init ContextUtil int your application class firstly");
    }
}
