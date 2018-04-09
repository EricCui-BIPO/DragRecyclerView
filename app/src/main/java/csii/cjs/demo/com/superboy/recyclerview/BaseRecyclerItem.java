package csii.cjs.demo.com.superboy.recyclerview;

/**
 * 描述:通用RecyclerView适配器的条目
 * <p>
 * 作者:cjs
 * 创建时间:2017年10月17日 18:20
 * 邮箱:chenjunsen@outlook.com
 *
 * @version 1.0
 */
public interface BaseRecyclerItem {
    /**
     * 条目的视图类型
     * @return
     */
    int getViewType();

    /**
     * 条目类型的Id<br>
     *     一旦使用的条目数据列表有插入的可能，一定要给条目设置一个静态ID，否则会出现数据混乱。
     *      在{@link android.support.v7.widget.RecyclerView.Adapter#setHasStableIds(boolean)}设为true,
     *      在{@link android.support.v7.widget.RecyclerView.Adapter#getItemId(int)}设为这个getItemId
     * @return
     */
    long getItemId();
}
