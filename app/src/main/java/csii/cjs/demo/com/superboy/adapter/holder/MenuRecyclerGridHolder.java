package csii.cjs.demo.com.superboy.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import csii.cjs.demo.com.superboy.R;

/**
 * 描述:编辑页面的主体item的holder
 * <p>
 * 作者:陈俊森
 * 创建时间:2017年11月03日 17:14
 * 邮箱:chenjunsen@outlook.com
 *
 * @version 1.0
 */
public class MenuRecyclerGridHolder extends RecyclerView.ViewHolder {
    public TextView tv_name;
    public ImageView iv_icon;

    public MenuRecyclerGridHolder(View itemView) {
        super(itemView);
        tv_name= (TextView) itemView.findViewById(R.id.desc);
        iv_icon= (ImageView) itemView.findViewById(R.id.pic);
    }
}
