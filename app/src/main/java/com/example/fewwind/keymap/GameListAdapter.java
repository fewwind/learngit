package com.example.fewwind.keymap;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by fewwind on 17-3-3.
 */

public class GameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_LIST = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_GRID = 2;
    public static final int TYPE_FOOTER = 3;

    private List<GameAppListBean> listData;
    private LayoutInflater inflater;
    private Context mContext;

    private boolean isShow;

    public GameListAdapter(List<GameAppListBean> list_data, Context context) {
        this.listData = list_data;
        this.inflater = LayoutInflater.from(context);
        context.getResources().getString(R.string.action_settings);
        this.mContext = context;
    }


    public void setShow(boolean show) {
        isShow = show;
    }


    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder mViewHolder = null;
        if (viewType == TYPE_LIST) {
            view = inflater.inflate(R.layout.item_list_install, parent, false);
            mViewHolder = new lViewHolder(view);
        } else if (viewType == TYPE_TITLE) {
            view = inflater.inflate(R.layout.item_list_title, parent, false);
            mViewHolder = new tViewHolder(view);
        } else if (viewType == TYPE_GRID) {
            view = inflater.inflate(R.layout.item_grid_recommend, parent, false);
            mViewHolder = new gViewHolder(view);

        } else {
            view = inflater.inflate(R.layout.item_list_footer, parent, false);
            mViewHolder = new fViewHolder(view);
        }

        return mViewHolder;
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (position == listData.size()){
            final fViewHolder fHolder = (fViewHolder) holder;
            fHolder.itemView.setVisibility(isShow?View.VISIBLE:View.GONE);
            return;
        }
        GameAppListBean gameApp = listData.get(position);
        switch (getItemViewType(position)) {
            case TYPE_LIST:

                if (!(gameApp instanceof InstalledAppBean)) return;

                final InstalledAppBean installedApp = (InstalledAppBean) gameApp;
                final lViewHolder lHolder = (lViewHolder) holder;
                //Bitmap bitmap = BitmapFactory.decodeByteArray(installedApp.getAppIconData(), 0,
                //    installedApp.getAppIconData().length);
                String url = "http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg";

                //lHolder.ivIcon.setImageBitmap(installedApp.getAppIconData());

                Picasso.with(mContext).load(url).into(lHolder.ivIcon);
                lHolder.btnOpen.setText("*" + position);

                lHolder.btnOpen.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        if (mOnItemClickLitener != null) {
                            mOnItemClickLitener.OnItemOpenClick(installedApp, position);
                        }
                    }
                });
                lHolder.ivSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        if (mOnItemClickLitener != null) {
                            mOnItemClickLitener.OnItemSwitchClick(installedApp, position);
                        }
                    }
                });
                lHolder.ivRemove.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        if (mOnItemClickLitener != null) {
                            mOnItemClickLitener.OnItemRemoveClick(installedApp, position);
                        }
                    }
                });

                lHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {

                    }
                });
                break;

            case TYPE_TITLE:
                final tViewHolder tHolder = (tViewHolder) holder;
                boolean firstTitle = (position == 0);
                tHolder.tvLeft.setText(firstTitle ? "游戏列表" : "下列应用已为您预置按键");
                tHolder.tvMid.setText("开关");
                tHolder.tvRight.setText("移除");
                tHolder.tvMid.setVisibility(firstTitle ? View.VISIBLE : View.GONE);
                tHolder.tvRight.setVisibility(firstTitle ? View.VISIBLE : View.GONE);

                break;
            case TYPE_FOOTER:
                final fViewHolder fHolder = (fViewHolder) holder;


                break;
            case TYPE_GRID:
                if (!(gameApp instanceof RecommonedAppBean)) return;

                final RecommonedAppBean appBean = (RecommonedAppBean) gameApp;
                final gViewHolder gHolder = (gViewHolder) holder;
                gHolder.tvName.setText(gameApp.getAppName() + position);
                gHolder.btnInstall.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        if (mOnItemClickLitener != null) {
                            mOnItemClickLitener.OnItemInstallClick(appBean, position);
                        }

                    }
                });

                gHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        isShow =!isShow;
                        notifyDataSetChanged();
                    }
                });
                break;
        }

    }


    @Override public int getItemViewType(int position) {
        if (position == listData.size()) {

        } else {
            return listData.get(position).getType();
        }

        return TYPE_FOOTER;
    }


    @Override public int getItemCount() {
        return listData == null ? 0 : listData.size()+1;
    }


    @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == listData.size()) {
                        return gridLayoutManager.getSpanCount();
                    }else if (listData.get(position).getType() == TYPE_GRID) {
                        return 1;
                    } else {
                        return gridLayoutManager.getSpanCount();
                    }
                }
            });
        }
    }


    public interface OnItemClickLitener {
        void OnItemOpenClick(InstalledAppBean bean, int pos);
        void OnItemSwitchClick(InstalledAppBean bean, int pos);
        void OnItemRemoveClick(InstalledAppBean bean, int pos);
        void OnItemInstallClick(RecommonedAppBean bean, int pos);
    }


    private OnItemClickLitener mOnItemClickLitener;


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    private class gViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvName;
        Button btnInstall;


        public gViewHolder(View view) {
            super(view);
            ivIcon = (ImageView) view.findViewById(R.id.id_item_grid_icon);
            tvName = (TextView) view.findViewById(R.id.id_item_grid_name);
            btnInstall = (Button) view.findViewById(R.id.id_item_grid_install);
        }
    }


    private class lViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        ImageView ivSwitch;
        ImageView ivRemove;
        Button btnOpen;
        TextView tvHint;


        public lViewHolder(View view) {
            super(view);
            ivIcon = (ImageView) view.findViewById(R.id.id_item_list_icon);
            ivSwitch = (ImageView) view.findViewById(R.id.id_item_list_iv_switch);
            ivRemove = (ImageView) view.findViewById(R.id.id_item_list_iv_remove);
            btnOpen = (Button) view.findViewById(R.id.id_item_list_btn_open);
            tvHint = (TextView) view.findViewById(R.id.id_item_list_tv_hint);

        }
    }


    private class tViewHolder extends RecyclerView.ViewHolder {
        TextView tvLeft;
        TextView tvMid;
        TextView tvRight;


        public tViewHolder(View view) {
            super(view);
            tvLeft = (TextView) view.findViewById(R.id.id_item_title_tv_left);
            tvMid = (TextView) view.findViewById(R.id.id_item_title_tv_mid);
            tvRight = (TextView) view.findViewById(R.id.id_item_title_tv_right);
        }
    }
    private class fViewHolder extends RecyclerView.ViewHolder {
        public fViewHolder(View view) {
            super(view);
        }
    }
}
