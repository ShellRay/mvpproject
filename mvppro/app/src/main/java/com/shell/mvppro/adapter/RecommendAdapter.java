package com.shell.mvppro.adapter;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shell.mvppro.R;
import com.shell.mvppro.bean.BiliAppIndex;
import com.shell.mvppro.uitls.DensityUtil;
import com.shell.mvppro.uitls.ImageUtil;
import com.shell.mvppro.uitls.ScreenUtil;
import com.shell.mvppro.uitls.StringUtil;

import java.util.zip.Inflater;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author ShellRay
 * Created  on 2020/3/23.
 * @description
 */
public class RecommendAdapter extends DAdapter<BiliAppIndex, DViewHolder>{

    @Override
    public DViewHolder newViewHolder(ViewGroup viewGroup, int type) {
         View itemView = inflate(viewGroup,R.layout.item_recommend_index_item);
        return new RecommendIndexItemHolder(itemView);
    }

    static class RecommendIndexItemHolder extends DViewHolder<BiliAppIndex> {

        @BindView(R.id.cover_iv)
        SimpleDraweeView ivCover;
        @BindView(R.id.play_tv)
        TextView tvPlay;
        @BindView(R.id.reply_tv)
        TextView tvReply;
        @BindView(R.id.duration_tv)
        TextView tvDuration;
        @BindView(R.id.title_tv)
        TextView tvTitle;
        @BindView(R.id.t_name_tv)
        TextView tvTName;
        @BindView(R.id.login_cover_fl)
        FrameLayout flLoginCover;
        @BindView(R.id.login_cover_iv)
        ImageView ivLoginCover;

        private RecommendIndexItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(BiliAppIndex item, int position) {
            Context context = ivCover.getContext();
            int width = ScreenUtil.getScreenWidth(context) / 2 - DensityUtil.dip2px(context, 14);
            int height = context.getResources().getDimensionPixelSize(R.dimen.recommend_cover_height);
            ImageUtil.load(ivCover, item.getCover(), width, height);
            tvPlay.setText(StringUtil.numberToWord(item.getPlay()));
            tvReply.setText(StringUtil.numberToWord(item.getReply()));
            tvDuration.setText(StringUtil.secToTime(item.getDuration()));
            tvTitle.setText(item.getTitle());
            tvTName.setText(item.getTname());
        }
    }


}
