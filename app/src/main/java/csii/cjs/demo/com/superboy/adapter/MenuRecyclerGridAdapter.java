package csii.cjs.demo.com.superboy.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import csii.cjs.demo.com.superboy.R;
import csii.cjs.demo.com.superboy.adapter.holder.MenuRecyclerGridHolder;
import csii.cjs.demo.com.superboy.entity.MenuItem;
import csii.cjs.demo.com.superboy.recyclerview.BaseSimpleRecyclerAdapter;
import csii.cjs.demo.com.superboy.tools.DrawableKit;
import csii.cjs.demo.com.superboy.tools.ImageKit;

/**
 * 描述:编辑页面主体元素的子元素适配器
 * <p>
 * 作者:陈俊森
 * 创建时间:2017年11月03日 17:37
 * 邮箱:chenjunsen@outlook.com
 *
 * @version 1.0
 */
public class MenuRecyclerGridAdapter extends BaseSimpleRecyclerAdapter<MenuRecyclerGridHolder, MenuItem> {
    public MenuRecyclerGridAdapter(List<MenuItem> recyclerItems) {
        super(recyclerItems);
    }

    @Override
    public MenuRecyclerGridHolder createRecyclerViewHolder(ViewGroup parent, int viewType) {
        return new MenuRecyclerGridHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_grid, parent, false));
    }

    @Override
    public void bindViewHolder(MenuRecyclerGridHolder holder, MenuItem item) {
        holder.iv_icon.setImageResource(ImageKit.getMipMapImageSrcIdWithReflectByName(item.getIcon()));
        holder.tv_name.setText(item.getName());
        Drawable d=holder.iv_icon.getDrawable();
        if(d!=null){
            DrawableKit.removeDrawableTintColor(d);
        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerItems == null ? 0 : mRecyclerItems.size();
    }
}
