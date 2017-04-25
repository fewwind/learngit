package com.example.fewwind.keymap;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fewwind.keymap.test.KeyMapActionBean;
import com.example.fewwind.keymap.util.ReflactUtil;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBtnAdd;
    private ImageView mBtnCancle;
    private ImageView mBtnSave;
    private TextView mTvAdd;
    private TextView mTvCancle;
    private TextView mTvSave;
    List<KeyMapActionBean> mDatas = new ArrayList<>();
    private Subscription mSubtion;


    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_window);
        mBtnAdd = (ImageView) findViewById(R.id.add);
        mBtnCancle = (ImageView) findViewById(R.id.cancel);
        mBtnSave = (ImageView) findViewById(R.id.save);
        mTvAdd = (TextView) findViewById(R.id.tv_add);
        mTvCancle = (TextView) findViewById(R.id.tv_cancle);
        mTvSave = (TextView) findViewById(R.id.tv_save);

        mBtnAdd.setOnClickListener(this);
        mBtnCancle.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        mTvCancle.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
        mContext = this;
        getPopupWindow();
        initActionData();
        Logger.v("onconCreat=="+ System.currentTimeMillis());
        mBtnAdd.setImageResource(R.drawable.alpha);
        mBtnAdd.setOnHoverListener(new View.OnHoverListener() {
            @Override public boolean onHover(View v, MotionEvent event) {
                return false;
            }
        });

        mSubtion =Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override public void call(Long aLong) {
                //Logger.v("interval=="+aLong);
             }
        });

    }


    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.d("onconfigchange=="+newConfig.toString());
    }

    private void getScreen() {

        int width =getResources().getDisplayMetrics().widthPixels;
        int height =getResources().getDisplayMetrics().heightPixels;
        //int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        //int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        Logger.d(width+"----"+height);
        //mNavigationBarHeight = mContext.getResources().getDimensionPixelSize(
        //    com.android.internal.R.dimen.navigation_bar_height_phoenix);
        Observable.just(1,2,3).take(5).take(2, TimeUnit.SECONDS).subscribe(new Action1<Integer>() {
            @Override public void call(Integer integer) {
                Logger.d("take--"+integer);
            }
        });
    }


    PopupWindow popupWindow;


    private void initActionData() {
        mDatas.add(
            new KeyMapActionBean(getString(R.string.tv_frontsight), getString(R.string.tv_frontsight_content),
                R.drawable.sight_selector,1));
        mDatas.add(
            new KeyMapActionBean(getString(R.string.tv_fire), getString(R.string.tv_fire_content),
                R.drawable.fire_selector,1));
        mDatas.add(
            new KeyMapActionBean(getString(R.string.tv_alpha), "",
                R.drawable.reset_selector,0));
        mDatas.add(
            new KeyMapActionBean(getString(R.string.tv_key), getString(R.string.tv_key_content),
                R.drawable.key_selector,1));
        mDatas.add(
            new KeyMapActionBean(getString(R.string.tv_compass),
                getString(R.string.tv_compass_content), R.drawable.control_direction_selector,1));
        mDatas.add(
            new KeyMapActionBean(getString(R.string.tv_reset), getString(R.string.tv_reset_content),
                R.drawable.reset_selector,1));

        mDatas.add(
            new KeyMapActionBean(getString(R.string.tv_stop), getString(R.string.tv_stop_content),
                R.drawable.stop_selector,1));

        Object getFanShe1 = ReflactUtil.invokeMethod(new MainActivity(), "getFanShe");
        Object getFanShe2 = ReflactUtil.invokeMethod2(MainActivity.class, "getFanShe");
        Object getFanShe3 = ReflactUtil.invokeMethod3("com.example.fewwind.keymap.MainActivity", "getFanShe");
        Logger.w(getFanShe1.toString());
    }


    /**
     * 创建PopupWindow
     */
    protected void initPopupWindow() {
        final View popipWindow_view = getLayoutInflater().inflate(R.layout.pop_down, null, false);
        popupWindow = new PopupWindow(popipWindow_view, 500, 800, true);
        //设置动画效果
        //popupWindow.setAnimationStyle(R.style.AnimationFade);
        //点击其他地方消失
        //popipWindow_view.setOnTouchListener(new View.OnTouchListener() {
        //    @Override
        //    public boolean onTouch(View v, MotionEvent event) {
        //        if (popipWindow_view != null && popipWindow_view.isShown()) {
        //
        //            popupWindow.dismiss();
        //            popupWindow = null;
        //        }
        //        return false;
        //    }
        //});
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override public void onDismiss() {
                changeAddState(false);
            }
        });

        ListView mListView = (ListView) popipWindow_view.findViewById(R.id.pop_list);
        /*mListView.setAdapter(new CommonAdapter<KeyMapActionBean>(SecondActivity.this, mDatas,
            R.layout.item_keymap_ation) {
            @Override public void convert(ViewHolder holder, KeyMapActionBean item) {
                holder.setBackgroundRes(R.id.action_iv_key, item.getResId());
                holder.setText(R.id.action_tv_title, item.getTitle());
                holder.setText(R.id.action_tv_content, item.getContent());

                holder.setOnClickListener(R.id.action_iv_key, new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Logger.e("nbuttom");

                    }
                });
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Logger.e("convertion");
                    }
                });
            }
        });*/
        mListView.setAdapter(new PopAdapter());
    }

    private int mProgress = 10;


    private class PopAdapter extends BaseAdapter {
        public PopAdapter() {
        }

        @Override public int getCount() {
            return mDatas.size();
        }


        @Override public KeyMapActionBean getItem(int position) {
            return mDatas.get(position);
        }


        @Override public long getItemId(int position) {
            return position;
        }


        @Override public int getItemViewType(int position) {
            return mDatas.get(position).getType();
        }


        @Override public View getView(int position, View convertView, ViewGroup parent) {

            KeyMapActionBean bean = mDatas.get(position);
            final TextView title;
            TextView content;
            ImageView icon;
            SeekBar seekBar;
            if (getItemViewType(position)==1){
                if (convertView==null){
                    convertView  = LayoutInflater.from(mContext).inflate(R.layout.item_keymap_ation,null);
                }
                title = (TextView) convertView.findViewById(R.id.action_tv_title);
                content = (TextView) convertView.findViewById(R.id.action_tv_content);
                icon = (ImageView) convertView.findViewById(R.id.action_iv_key);

                title.setText(bean.getTitle());
                content.setText(bean.getContent());
                icon.setImageResource(bean.getResId());
            } else if (getItemViewType(position)==0){
                if (convertView==null){
                    convertView  = LayoutInflater.from(mContext).inflate(R.layout.item_keymap_ation_seekbar,null);
                }
                title = (TextView) convertView.findViewById(R.id.action_tv_title);
                seekBar = (SeekBar) convertView.findViewById(R.id.action_sb);
                icon = (ImageView) convertView.findViewById(R.id.action_iv_key);
                title.setText(bean.getTitle());
                icon.setImageResource(bean.getResId());

                seekBar.setProgress(mProgress);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mProgress = progress;
                    }

                    @Override public void onStartTrackingTouch(SeekBar seekBar) {
                        Logger.i("开始触摸");
                        title.setText("kaishi ");
                    }


                    @Override public void onStopTrackingTouch(SeekBar seekBar) {
                        Logger.w("停止触摸s");
                        title.setText("over ");
                    }
                });
            }
            return convertView;
        }
    }


    private void state() {
        InputMethodManager imm = (InputMethodManager) getSystemService(
            Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        boolean acceptingText = imm.isAcceptingText();

    }

    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private void changeAddState(boolean focusState) {
        if (focusState) {
            mBtnAdd.setBackgroundResource(R.drawable.function_on_focus);
            mTvAdd.setTextColor(Color.parseColor("#80cbc4"));
        } else {
            mBtnAdd.setBackgroundResource(R.drawable.add_selector);
            mTvAdd.setTextColor(Color.GRAY);
        }

    }


    private boolean isPad() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        // 屏幕宽度
        float screenWidth = display.getWidth();
        // 屏幕高度
        float screenHeight = display.getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于6尺寸则为Pad
        if (screenInches >= 6.0) {
            return true;
        }
        return false;
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Logger.d("add");
                Logger.e("开关" + isPad());
                popupWindow.showAsDropDown(mBtnAdd, -200, 50);
                changeAddState(true);
                break;
            case R.id.cancel:
                getScreen();
                Map<String,String > map = new HashMap<>();
                map.put("haha","haha");
                String ah = map.remove("ah");
                String  haha = map.remove("haha");
                Logger.d(ah+"--cancle--"+haha);
                mSubtion.unsubscribe();
                break;
            case R.id.save:
                boolean connected = Util.isNetworkConnected(getApplication());
                Logger.d("save" + connected);
                //launchApp(v.getContext(),pakName);
                launchApp2(v.getContext(),pakName);

                state();

                break;
            case R.id.tv_add:
                mBtnAdd.performClick();
                break;
            case R.id.tv_cancle:
                mBtnCancle.performClick();
                break;
            case R.id.tv_save:
                mBtnSave.performClick();
                break;
        }
    }


    String pakName = "com.android.settings";
    public  void launchApp(Context context, String pkgName) {
        try {
            //Intent intent = context.getPackageManager()
            //    .getLaunchIntentForPackage(pkgName);
            Intent intent = new Intent();
            //intent.putExtra("category","AppStore");"com.tencent.android.qqdownloader"
            intent.putExtra("pakName", pkgName);
            //Settings.StorageSettingsActivity
            intent.setComponent(new ComponentName(pkgName,"com.android.settings.StorageSettingsActivity"));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(),
                Toast.LENGTH_SHORT).show();
        }
    }

    public  void launchApp2(Context context, String pkgName) {
        try {
            Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(pkgName);
            intent.putExtra("pakName", pkgName);

            Intent intent2 =  new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
            startActivity(intent2);
            //context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(),
                Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 获取PopipWinsow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
        } else {
            initPopupWindow();
        }

    }


}
