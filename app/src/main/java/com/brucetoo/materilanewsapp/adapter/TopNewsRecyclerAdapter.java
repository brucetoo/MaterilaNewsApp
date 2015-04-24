package com.brucetoo.materilanewsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.brucetoo.materilanewsapp.R;
import com.brucetoo.materilanewsapp.model.NewsModel;
import com.brucetoo.materilanewsapp.utils.DensityUtil;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Bruce Too
 * On 4/24/15.
 * At 00:18
 */
public class TopNewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Recycler item 类型
    private static final int VIEW_TYPE_PHOTO = 1;
    private static final int VIEW_TYPE_TEXT = 2;

    private List<NewsModel.NewsId> newsIds;
    private Context context;

    public TopNewsRecyclerAdapter(Context ctx){
        this.context = ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_PHOTO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_news_photo, parent, false);
            return new PhotoNewsViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_news_text, parent, false);
            return new TextViewViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        runEnterAnimation(holder.itemView);
        NewsModel.NewsId item = newsIds.get(position);
            if(getItemViewType(position) == VIEW_TYPE_PHOTO){
                PhotoNewsViewHolder photoNewsViewHolder = (PhotoNewsViewHolder) holder;
                photoNewsViewHolder.newsTitle1.setText(item.title);
                photoNewsViewHolder.newsComment1.setText(String.format(context.getResources().getString(R.string.text_reply_count),item.replyCount));
                photoNewsViewHolder.newsTime1.setText(item.ptime);
                Picasso.with(context)
                       .load(item.imgsrc)
                       .placeholder(R.drawable.img_loading)
                       .error(R.drawable.img_loading_fail)
                       .into(photoNewsViewHolder.newsPic1);
                Picasso.with(context)
                        .load(item.imgextra.get(0).imgsrc)
                        .error(R.drawable.img_loading_fail)
                        .placeholder(R.drawable.img_loading)
                        .into(photoNewsViewHolder.newsPic2);
                Picasso.with(context)
                        .load(item.imgextra.get(1).imgsrc)
                        .error(R.drawable.img_loading_fail)
                        .placeholder(R.drawable.img_loading)
                        .into(photoNewsViewHolder.newsPic3);
            }else if(getItemViewType(position) == VIEW_TYPE_TEXT){
                TextViewViewHolder textViewViewHolder = (TextViewViewHolder) holder;
                textViewViewHolder.newsTitle0.setText(item.title);
                textViewViewHolder.newsComment0.setText(String.format(context.getResources().getString(R.string.text_reply_count),item.replyCount));
                textViewViewHolder.newsDigest.setText(item.digest);
                textViewViewHolder.newsTime0.setText(item.ptime);
                Picasso.with(context)
                        .load(item.imgsrc)
                        .placeholder(R.drawable.img_loading)
                        .error(R.drawable.img_loading_fail)
                        .into(textViewViewHolder.newsPic0);
            }
    }

    @Override
    public int getItemCount() {
        return newsIds.size() > 0 ? newsIds.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if (newsIds.get(position).imgextra == null) {
            type = VIEW_TYPE_TEXT;
        } else {
            type = VIEW_TYPE_PHOTO;
        }
        return type;
    }

    /**
     * 三张图片的新闻item
     */
    class PhotoNewsViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_news_title_1)
        TextView newsTitle1;
        @InjectView(R.id.tv_news_pic_1)
        ImageView newsPic1;
        @InjectView(R.id.tv_news_pic_2)
        ImageView newsPic2;
        @InjectView(R.id.tv_news_pic_3)
        ImageView newsPic3;
        @InjectView(R.id.tv_news_comment_1)
        TextView newsComment1;
        @InjectView(R.id.tv_news_time_1)
        TextView newsTime1;

        public PhotoNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    /**
     * 普通新闻的item
     */
    class TextViewViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_news_title_0)
        TextView newsTitle0;
        @InjectView(R.id.tv_news_pic_0)
        ImageView newsPic0;
        @InjectView(R.id.tv_news_digest)
        TextView newsDigest;
        @InjectView(R.id.tv_news_comment_0)
        TextView newsComment0;
        @InjectView(R.id.tv_news_time_0)
        TextView newsTime0;

        public TextViewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }


    public List<NewsModel.NewsId> getNewsIds() {
        return newsIds;
    }

    public void setNewsIds(List<NewsModel.NewsId> newsIds) {
        this.newsIds = newsIds;
        this.notifyDataSetChanged();
    }

    /**
     * 增加新闻条目
     * @param news
     */
    public void addNews(List<NewsModel.NewsId> news){
        newsIds.addAll(news);
        this.notifyItemInserted(newsIds.size()-1);
    }

    /**
     * 每个Item进来的时候都执行动画
     * @param itemView
     */
    public void runEnterAnimation(View itemView){
        itemView.setTranslationY(DensityUtil.getScreenHeight(context));
        ObjectAnimator enterAnim = ObjectAnimator.ofFloat(itemView,"translationY",0);
        enterAnim.setDuration(700);
        enterAnim.setInterpolator(new DecelerateInterpolator(3));
        enterAnim.start();
    }
}
