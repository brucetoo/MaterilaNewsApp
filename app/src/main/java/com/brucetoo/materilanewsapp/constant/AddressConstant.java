package com.brucetoo.materilanewsapp.constant;

/**
 * Created by brucetoo
 * On 4/21/15.
 */
public class AddressConstant {

    public static final String HOST = "http://c.m.163.com/";
    public static final String END_URL = "-20.html"; //json数据URL
    public static final String END_DETAIL_URL = "/full.html";//网页详情URL
    // 头条
    public static final String TopUrl = HOST + "nc/article/headline/";
    public static final String TopId = "T1348647909107";
    //eg: TopUrl+ TopId + /+index(0,20,40...)+END_URL;  0,20,40表示每页（20个）的数据

    // 新闻详情基URL
    public static final String NewsDetail = HOST + "nc/article/";
    //eg: NewsDetail + id + END_DETAIL_URL;

    //新闻数据的基URL
    public static final String NewsBaseUrl = HOST + "nc/article/list/";
    //eg: NewsBaseUrl + id (FootId...) + / +index(0,20,40...) + END_URL;

    // 足球
    public static final String FootId = "T1399700447917";
    // 娱乐
    public static final String YuLeId = "T1348648517839";
    // 体育
    public static final String TiYuId = "T1348649079062";
    // 财经
    public static final String CaiJingId = "T1348648756099";
    // 科技
    public static final String KeJiId = "T1348649580692";
    // 电影
    public static final String DianYingId = "T1348648650048";
    // 汽车
    public static final String QiChiId = "T1348654060988";
    // 笑话
    public static final String XiaoHuaId = "T1350383429665";
    // 笑话
    public static final String YouXiId = "T1348654151579";
    // 时尚
    public static final String ShiShangId = "T1348650593803";
    // 情感
    public static final String QingGanId = "T1348650839000";
    // 精选
    public static final String JingXuanId = "T1370583240249";
    // 电台
    public static final String DianTaiId = "T1379038288239";
    // nba
    public static final String NBAId = "T1348649145984";
    // 数码
    public static final String ShuMaId = "T1348649776727";
    // 数码
    public static final String YiDongId = "T1351233117091";
    // 彩票
    public static final String CaiPiaoId = "T1356600029035";
    // 教育
    public static final String JiaoYuId = "T1348654225495";
    // 论坛
    public static final String LunTanId = "T1349837670307";
    // 旅游
    public static final String LvYouId = "T1348654204705";
    // 手机
    public static final String ShouJiId = "T1348649654285";
    // 博客
    public static final String BoKeId = "T1349837698345";
    // 社会
    public static final String SheHuiId = "T1348648037603";
    // 家居
    public static final String JiaJuId = "T1348654105308";
    // 暴雪游戏
    public static final String BaoXueId = "T1397016069906";
    // 亲子
    public static final String QinZiId = "T1397116135282";
    // CBA
    public static final String CBAId = "T1348649475931";
    // 消息
    public static final String MsgId = "T1371543208049";

    // 北京
    public static final String Local = HOST + "nc/article/local/";

    public static final String BeiJingId = "5YyX5Lqs";
    // 军事
    public static final String JunShiId = "T1348648141035";
    // 房产
    public static final String FangChan = HOST + "nc/article/house/";
    // 房产id
    public static final String FangChanId = "5YyX5Lqs";
    // 图集
    public static final String TuJi = HOST + "photo/api/morelist/0096/4GJ60096/";// 42358.json
    // 图集end
    public static final String TuJiEnd = ".json";
    // 热点42577
    public static final String TuPianReDian = HOST + "photo/api/morelist/0096/54GI0096/";
    // 独家42010
    public static final String TuPianDuJia = HOST + "photo/api/morelist/0096/54GJ0096/";
    // 明星 42599.json
    public static final String TuPianMingXing = HOST + "photo/api/morelist/0096/54GK0096/";
    // 明星 42262.json
    public static final String TuPianTiTan = HOST + "photo/api/morelist/0096/54GM0096/";
    // 美图 39683.json
    public static final String TuPianMeiTu = HOST + "photo/api/morelist/0096/54GN0096/";

    // 视频 http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html
    public static final String Video = HOST + "nc/video/list/";
    public static final String VideoCenter = "/n/";
    public static final String videoEndUrl = "-10.html";
    // 热点视频
    public static final String VideoReDianId = "V9LG4B3A0";
    // 娱乐视频
    public static final String VideoYuLeId = "V9LG4CHOR";
    // 搞笑视频
    public static final String VideoGaoXiaoId = "V9LG4E6VR";
    // 精品视频
    public static final String VideoJingPinId = "00850FRB";
}
