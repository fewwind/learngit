package com.example.fewwind.keymap;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameListAdapter.OnItemClickLitener {

    private List<GameAppListBean> mAppDatas = new ArrayList<>();
    private List<GameAppListBean> mDatas = new ArrayList<>();
    int titlePos = 0;
    private String mCategory;
    private String mIntentPakName;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

                test();
            }
        });

        mDatas.add(new GameAppListBean(GameListAdapter.TYPE_TITLE));
        for (int i = 0; i < 4; i++) {
            //String appName, String packName, Drawable icon, int type, boolean isConfiged, boolean switchState
            mDatas.add(
                new InstalledAppBean("", "pack", null,
                    GameListAdapter.TYPE_LIST
                    , false, KeyMapService.KeyMappingInfosEntry.STATE_OFF));
        }
        title = new GameAppListBean(GameListAdapter.TYPE_TITLE);
        mDatas.add(title);
        for (int i = 0; i < 44; i++) {
            //String appName, String packName, Drawable icon, int type, boolean isConfiged, boolean switchState
            mDatas.add(new RecommonedAppBean("推广", "pack", GameListAdapter.TYPE_GRID));
        }
        titlePos = mDatas.indexOf(title);

        RecyclerView mRvListView = (RecyclerView) findViewById(R.id.id_main_list);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        adapter = new GameListAdapter(mDatas, this);
        adapter.setOnItemClickLitener(this);
        mRvListView.setLayoutManager(manager);
        mRvListView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRvListView.setAdapter(adapter);
        //mRvListView.addItemDecoration(new SpaceItemDecoration(40));

        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addDataScheme("package");
        mReceiver = new AppInstallReceiver();
        registerReceiver(mReceiver, filter);

        dialog();
        InputMethodService ser = new InputMethodService();
        startActivity(new Intent(this, SecondActivity.class));
        Logger.w("Main--onCreate");

        showNotify(10);
        showNotify(100);
    }


    private void test() {
        fab.post(new Runnable() {
            @Override public void run() {
                int height = getWindow().getDecorView().getHeight();
                DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
                int heightS = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;
                Toast.makeText(MainActivity.this,
                    "宽度=" + width + "&高度&" + heightS + "**总高度**" + height, Toast.LENGTH_LONG)
                    .show();
                Logger.d("【屏幕--" + heightS + "--decorview--" + height +
                    fab.getContext().getPackageName());
            }
        });

    }


    AppInstallReceiver mReceiver;

    GameAppListBean title;
    GameListAdapter adapter;


    private void dialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(
            R.string.dialog_remove_title)
            .setNegativeButton(
                R.string.dialog_cancle, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {

                    }
                })
            .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.RIGHT | Gravity.TOP);
        lp.y = Resources.getSystem().getDisplayMetrics().heightPixels / 4;
        lp.x = 100;
        //window.setBackgroundDrawableResource(R.drawable.popup);
        window.setAttributes(lp);
        //alertDialog.show();

    }


    @Override public void OnItemOpenClick(InstalledAppBean bean, int pos) {
        Log.d("yang", bean.toString());
        dialog();
    }


    @Override public void OnItemSwitchClick(InstalledAppBean bean, int pos) {
        Log.d("yang", pos + "---" + bean.toString());
    }


    @Override public void OnItemRemoveClick(InstalledAppBean bean, int pos) {
        mDatas.remove(pos);
        Log.d("yang", "****" + pos);
        //adapter.notifyDataSetChanged();
        //adapter.notifyItemRemoved(pos);
        adapter.notifyItemChanged(pos);
        //adapter.notifyItemRangeChanged(pos,1);
    }


    @Override public void OnItemInstallClick(RecommonedAppBean bean, int pos) {
        Log.w("yang", bean.toString());
        Util.launchApp(getApplicationContext());
    }


    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;


        public SpaceItemDecoration(int space) {
            this.space = space;
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            int pos = parent.getChildLayoutPosition(view);
            if (titlePos >= pos) {
                outRect.left = 0;
            } else {
                if ((pos - titlePos + 1) % 2 == 0) {
                    outRect.left = 0;
                }
            }
        }
    }


    /**
     * 监听应用程序卸载
     *
     * @author tangyouke
     */
    public class AppInstallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 应用安装或者卸载, 刷新应用列表.
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                String pkgName = intent.getData().getSchemeSpecificPart();
                Logger.e("卸载--" + pkgName);
            } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                String pkgName = intent.getData().getSchemeSpecificPart();
                Logger.w("安装--" + pkgName);
            }
        }
    }

    public String getFanShe(){
        return "反射成功";
    }
    @Override protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);

        try {
            unregisterReceiver(testReceiver);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("解绑异常--" + e.toString());
        }

    }


    BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SecondActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showNotify(int id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
            this)
            .setSmallIcon(
                R.drawable.about_icon)
            //.setColor(Color.RED)
            .setContentTitle("title")
            .setPriority(
                NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentText("内容没动按摩大门面对面佛奥泡沫片； ")
            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.about_icon));
            //.setContentIntent(
            //    pendingIntent);
        Notification notification = builder.build();
        notification.defaults = Notification.FLAG_AUTO_CANCEL
            | Notification.DEFAULT_SOUND;
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id,
            notification);

    }

}

