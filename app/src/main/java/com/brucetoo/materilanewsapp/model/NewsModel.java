package com.brucetoo.materilanewsapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bruce Too
 * On 4/22/15.
 * At 16:41
 * 抽取需要的新闻实体类
 */
public class NewsModel implements Serializable{

    public List<NewsId> T1348647909107;
    public static class NewsId{
        //抽取公共的
        public String title; //标题
        public String ptime; //发布时间
        public String imgsrc; //图片地址
        public int replyCount; // 跟贴数
        //图片
        public List<ExtraImage> imgextra; //图片集 共三张
        public String docid; //图片html
        //新闻
        public String digest; // 摘要
        public String source; // 来源
        public String url; //新闻html
    }

    public static class ExtraImage{
        public String imgsrc;
    }

//    public int recode;
//    public NewsList data;
//    public static class NewsList{
//        public String more;
//        public ArrayList<TopNews> topnews;
//        public ArrayList<News> news;
//        public String countcommenturl;
//    }
//
//    public static class TopNews{
//        public String id;
//        public String title;
//        public String topimage;
//        public String url;
//        public String pubdate;
//        public boolean comment;
//        public String commenturl;
//        public String commentlist;
//        public int commentcount;
//        public String type;
//    }
//
//    public static class News{
//        public String id;
//        public String title;
//        public String url;
//        public String listimage;
//        public String pubdate;
//        public int commentcount;
//        public boolean comment;
//        public String commenturl;
//        public String commentlist;
//        public boolean isRead;
//    }
}
