package csii.cjs.demo.com.superboy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import csii.cjs.demo.com.superboy.adapter.MenuRecyclerGridAdapter;
import csii.cjs.demo.com.superboy.base.Common;
import csii.cjs.demo.com.superboy.base.FingerPrintHelper;
import csii.cjs.demo.com.superboy.base.dialog.FingerPrintDialog;
import csii.cjs.demo.com.superboy.entity.MenuItem;
import csii.cjs.demo.com.superboy.recyclerview.OnRecyclerItemClickListener;

public class MainActivity extends AppCompatActivity implements OnRecyclerItemClickListener<MenuItem>{
    private RecyclerView mRecyclerView;
    private List<MenuItem> mFavList;
    private MenuRecyclerGridAdapter mAdapter;
    private RecyclerUpdateReceiver mRecyclerUpdateReceiver;

    static final int ID_ADD_ITEM=-1;//自定义添加条目的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FingerPrintHelper.isHardWareDetected(this) && FingerPrintHelper.hasEnrolledFingerPrint(this)){
            FingerPrintDialog.showDialog(this, false, "fingerPrint", new FingerPrintDialog.OnDismissListener() {
                @Override
                public void onDismiss(boolean isSuccess) {
                    if(isSuccess){
                        setContentView(R.layout.activity_main);
                        initView();
                        initEvents();
                    }else{
                        finish();
                        System.exit(0);
                    }
                }
            });
        }else{
            setContentView(R.layout.activity_main);
            initView();
            initEvents();
        }

    }

    private void initView() {
        mRecyclerView= (RecyclerView) findViewById(R.id.recycler_display);
    }

    private void initEvents() {
        initData();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mAdapter=new MenuRecyclerGridAdapter(mFavList);
        mAdapter.setOnRecyclerItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        //注册刷新数据的广播
        mRecyclerUpdateReceiver=new RecyclerUpdateReceiver();
        IntentFilter filter=new IntentFilter();
        filter.setPriority(1000);
        filter.addAction(Common.Notification.NOTIFY_REFRESH_MAIN_LIST_DATA);
        registerReceiver(mRecyclerUpdateReceiver,filter);
    }

    @Override
    public void onItemClick(View v, MenuItem item, int position, int segment) {
        if(item.getItemId()==ID_ADD_ITEM){
            Intent i=new Intent(this,EditActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(this,item.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    //初始化数据列表
    private void initData(){
        if(mFavList!=null){
            mFavList.clear();
        }else{
            mFavList=new ArrayList<>();
        }
        mFavList.addAll(MenuHelper.getPreferFavoriteList());
        MenuItem add=new MenuItem();
        add.setName("添加");
        add.setIcon("add");
        add.setItemId(ID_ADD_ITEM);
        mFavList.add(add);
    }

    /**
     * 用于执行刷新数据的广播接收器
     */
    private class RecyclerUpdateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        //注销刷新数据的广播
        if(mRecyclerUpdateReceiver!=null){
            unregisterReceiver(mRecyclerUpdateReceiver);
        }
        super.onDestroy();
    }
}
