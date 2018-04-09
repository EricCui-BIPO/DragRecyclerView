package csii.cjs.demo.com.superboy.entity;

import java.io.Serializable;

import csii.cjs.demo.com.superboy.recyclerview.BaseRecyclerItem;

/**
 * 描述:列表子item实体类
 * <p>
 * 作者:cjs
 * 创建时间:2017年11月03日 11:51
 * 邮箱:chenjunsen@outlook.com
 *
 * @version 1.0
 */
public class MenuItem implements BaseRecyclerItem,Serializable{

    /**
     * name : 匕首
     * icon : ic_cold_1
     * desc : 传说的匕首,杀人于无形
     * group : cold_weapon
     */

    private String name;//名字
    private String icon;//图标
    private String desc;//item的描述
    private String group;//item所属组

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private int viewType;
    private int itemId;

    @Override
    public int getViewType() {
        return viewType;
    }

    @Override
    public long getItemId() {
        return itemId;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", desc='" + desc + '\'' +
                ", group='" + group + '\'' +
                ", viewType=" + viewType +
                ", itemId=" + itemId +
                '}';
    }
}
