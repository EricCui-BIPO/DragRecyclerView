package csii.cjs.demo.com.superboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import csii.cjs.demo.com.superboy.adapter.MenuHeaderRecyclerGridAdapter;
import csii.cjs.demo.com.superboy.adapter.MenuRecyclerListAdapter;
import csii.cjs.demo.com.superboy.adapter.MenuRecyclerListHeaderWrapper;
import csii.cjs.demo.com.superboy.base.Common;
import csii.cjs.demo.com.superboy.entity.EditItem;
import csii.cjs.demo.com.superboy.entity.MenuItem;
import csii.cjs.demo.com.superboy.recyclerview.OnRecyclerItemClickListener;

public class EditActivity extends AppCompatActivity implements MenuHeaderRecyclerGridAdapter.OnDeleteClickListener{

    private RecyclerView mRecyclerView;
    private List<MenuItem> mFavList;
    private List<MenuItem> mColdList;
    private List<MenuItem> mModernList;
    private List<MenuItem> mMiscList;
    private List<MenuItem> mPersonList;
    private List<MenuItem> mEqtList;

    private List<EditItem> mEditList;

    private MenuRecyclerListAdapter mListAdapter;
    private MenuRecyclerListHeaderWrapper mListHeaderWrapper;

    private boolean hasChangedListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        initEvents();
    }

    private void initView() {
        mRecyclerView= (RecyclerView) findViewById(R.id.edit);
    }

    private void initEvents() {
        mFavList=MenuHelper.getPreferFavoriteList();
        mColdList=MenuHelper.getPreferColdWeaponList();
        mModernList=MenuHelper.getPreferModernWeaponList();
        mMiscList=MenuHelper.getPreferMiscList();
        mPersonList=MenuHelper.getPreferPersonList();
        mEqtList=MenuHelper.getPreferEquipmentList();

        mEditList=new ArrayList<>();
        mEditList.add(new EditItem(MenuHelper.GROUP_COLD_WEAPON,getString(R.string.cold_weapon),mColdList));
        mEditList.add(new EditItem(MenuHelper.GROUP_MODERN_WEAPON,getString(R.string.modern_weapon),mModernList));
        mEditList.add(new EditItem(MenuHelper.GROUP_MISC,getString(R.string.misc),mMiscList));
        mEditList.add(new EditItem(MenuHelper.GROUP_EQUIPMENT,getString(R.string.equipment),mEqtList));
        mEditList.add(new EditItem(MenuHelper.GROUP_PERSON,getString(R.string.person),mPersonList));

        mListAdapter=new MenuRecyclerListAdapter(mEditList);
        mListAdapter.setChildItemClickListener(new ListChildItemClickListener());
        mListHeaderWrapper=new MenuRecyclerListHeaderWrapper(mListAdapter);
        mListHeaderWrapper.setOnChildItemClickListener(new HeaderChildItemClickListener());
        mListHeaderWrapper.setOnDeleteClickListener(this);
        mListHeaderWrapper.addHeader(new EditItem(MenuHelper.GROUP_FAVORITE,getString(R.string.favorite),mFavList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(mListHeaderWrapper);
    }

    @Override
    protected void onDestroy() {
        mListHeaderWrapper.releaseDragManager();
        if(mListHeaderWrapper.isHasDragChanged() || hasChangedListData){
            sendBroadcast(new Intent(Common.Notification.NOTIFY_REFRESH_MAIN_LIST_DATA));
        }
        super.onDestroy();
    }

    @Override
    public void onDeleteClick(View v, MenuItem item,int position) {
        Toast.makeText(EditActivity.this,"从最爱里面移除"+item.getName(),Toast.LENGTH_SHORT).show();
        MenuHelper.deletePreferFavoriteItem(item);
        MenuHelper.addItem(item.getGroup(),item);
        notifyFavDataRemoved(item);
    }

    private class HeaderChildItemClickListener implements OnRecyclerItemClickListener<MenuItem>{

        @Override
        public void onItemClick(View v, MenuItem item, int position, int segment) {
            Toast.makeText(EditActivity.this,item.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private class ListChildItemClickListener implements OnRecyclerItemClickListener<MenuItem>{

        @Override
        public void onItemClick(View v, MenuItem item, int position, int segment) {
            Toast.makeText(EditActivity.this,"往最爱里面添加"+item.getName(),Toast.LENGTH_SHORT).show();
            MenuHelper.addPreferFavoriteItem(item);
            MenuHelper.deleteItem(item.getGroup(),item);
            notifyFavDataAdded(item);
        }
    }

    private void notifyFavDataAdded(MenuItem item){
        mListHeaderWrapper.notifyChildDataAdded(item);
        mListAdapter.notifyChildDataRemoved(item.getGroup(),item);
        /*mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mListHeaderWrapper.notifyDataSetChanged();
            }
        });*/
        hasChangedListData=true;
    }

    private void notifyFavDataRemoved(MenuItem item){
        mListHeaderWrapper.notifyChildDataRemoved(item);
        mListAdapter.notifyChildDataAdded(item.getGroup(),item);
        /*mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mListHeaderWrapper.notifyDataSetChanged();
            }
        });*/
        hasChangedListData=true;
    }
}
