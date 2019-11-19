# 通用Android UI SDK 文档

注意：Demo 需要先去一览官网申请key和token。

官网接入指南地址：http://doc.yilan.tv/feed/guideline/usage/

# 通用Android UI SDK 文档

ReleaseNote

| 版本    | 时间           | 修改内容                                                     |
| ------- | -------------- | ------------------------------------------------------------ |
| 1.0.1   | 2018年11月15日 | 第一次创建  V1.0.0                                           |
| 1.0.3   | 2019年2月28日  | 增加feed广告                                                 |
| 1.0.4   | 2019年3月15日  | 1、增加Feed流自定义样式和回调<br />2、增加小视频自定义样式和回调<br />3、优化webview自动播放视频 |
| 1.0.5   | 2019年4月10日  | 1、1.0.5降级glide至3.6，其他不变<br />2、增加feed列表scrollToTop()方法，支持列表滚动到顶部 |
| 1.0.6   | 2019年4月15日  | 1、解决小视频不可见播放的问题<br />2、优化小视频播放，节省流量<br />3、优化FeedConfig，用户可以自定义跳转 |
| 1.0.7   | 2019年4月24日  | 1、增加快手瀑布流样式小视频<br />2、优化小视频播放页<br />3、通过videoId打开播放页<br />4、原生播放页支持Feed流样式 |
| 1.0.8   | 2019年5月15日  | 网络请求优化，提升稳定性                                     |
| 1.0.9   | 2019年5月30日  | 1、增加网络请求稳定性<br />2、增加用户相关接口<br />3、小视频支持自动播放下一集<br />4、短视频播放完成后，追加2个相关视频。 |
| 1.0.10  | 2019年6月14日  | ugc接口增加sz                                                |
| 1.1.0   | 2019年7月1日   | 支持第三方联盟广告                                           |
| 1.2.0.1 | 2019年8月2日   | 广告支持deeplink和下载                                       |
| 1.3.0.2 | 2019年8月12日  | 1、 播放器支持httpdns <br/>2、评论添加开关                   |
| 1.3.2.1 | 2019年8月22日  | 1、视频播放页相关广告支持deeplink<br/>2、修改部分机型不能弹出安装提示<br/>3、增加小视频添加cp头像和点赞数<br/>4、修改小视频重播不显示封面问题 |
| 1.3.4.1 | 2019年8月29日  | 1、升级穿山甲依赖至 2.3.0.4版本<br>2、小视频广告增加播放回调，增加本地保存点赞结果 <br>3、小视频相关问题修复 |
| 1.3.6.1 | 2019年9月6日   | 1、播放页banner广告支持多种尺寸<br>2、feed列表在当前页播放时支持获取播放器状态<br>3、其他样式调整 |
| 1.3.8.1 | 2019年9月16日  | 1、小视频支持分享回调<br>2、优化播放页样式                   |
| 1.4.0.1 | 2019年9月23日  | 1、增加小视频cp页面 <br> 2、增加视频边下边播功能 <br> 3、修复小视频不登录时的点赞效果和点赞数 |
| 1.5.0.1 | 2019年10月14日 | 1、短视频页面支持多种样式广告<br>2、feed流支持视频广告       |
| 1.6.2.1 | 2019年11月1日  | 1、增加多个图片加载框架，去除对glide的依赖<br>2、修改部分机型稳定性 |
| 1.7.2.1 | 2019年11月19日 | 1、增加feed流当前页播放的回调<br>2、修复了一些线上的崩溃     |



## 一、SDK 概要

UISDK为移动应用提供内容分发功能，为客户提供较为简洁的API接口，方便第三方应用快速的集成并实现内容分发功能。

UISDK提供的功能如下：

- 绘制频道Feed流列表
- 绘制小视频列表
- 绘制播放页

## 二、SDK 类

一览Data SDK主要提供了以下类：

基础类：

- YLUIInit：整个UI SDK的主入口，单例，主要提供初始化，配置用户信息。
- MediaInfo：视频的model类
- MediaDetail:视频详情的model类
- YLUser：登陆用户类

页面：

- ChannelFragment 频道导航栏 + feed流页面
- FeedFragment feed流页面
- LittleVideoFragment 小视频页面
- KSLittleVideoFragment 类快手样式小视频页面
- LittleVideoActivity 小视频详情页（通过点击快手样式列表进入，或插入视频流点击进入）
- VideoActivity 播放页面Activity
- VideoFragment 正常样式的播放页Fragment
- VideoFeedFragment Feed流样式的播放页Fragment

### 2.1. 添加SDK到工程中

- 推荐方式 gradle依赖

在App.gradle中加入maven仓库地址

```
allprojects {
    repositories {
        google()
        jcenter()
        //添加一览 maven地址
        maven {
            url 'http://nexus.1lan.tv/repository/maven-releases/'
        }
        //添加阿里云maven地址
        maven {
            url 'http://maven.aliyun.com/nexus/content/repositories/releases/'
        }
    }
}
```

在使用的module中加入依赖，具体sdk版本根据发布的文档为准

```
implementation "com.yilan.sdk:ui:${SDK_VERSION}"//修改为具体的sdk版本
implementation "com.yilan.sdk:ad:${SDK_VERSION}"//修改为具体的sdk版本,支持广点通、百度广告
implementation 'com.squareup.okhttp3:okhttp:3.11.0'
implementation 'com.google.code.gson:gson:2.8.5'
implementation 'com.android.support:recyclerview-v7:28.0.0'
implementation ('com.aliyun.ams:alicloud-android-httpdns:1.2.3@aar') {
        transitive true
    }
    //支持ijkplayer进行播放，非必选，建议添加
    implementation 'tv.danmaku.ijk.media:ijkplayer-java:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-armv7a:0.8.8'
```

### 2、示例demo工程接入

YilanSDkDemo是接入一览SDK的示例工程，它可以使用AndroidStudio来打开。

demo地址：<br />[https://github.com/yilanyun/ylyun-android-sdk-demo](https://github.com/yilanyun/ylyun-android-sdk-demo)

当您使用AndroidStudio开发环境时（这也是我们推荐的方式，请采用AndroidStudio 3.0 以上），通过import project的方式导入示例工程，在弹出的向导中按照AndroidStudio默认的选项，一直点next即可导入工程，正确编译运行。

## 三、接入代码

### 3.1 一览sdk 初始化

#### 3.1.1 代码示例

```
YLUIInit.getInstance()
  .setApplication(this)
  .setAccessKey("")//设置accesskey
  .setAccessToken("")//设置token
  .build();
```

#### 3.1.2 方法说明

- com.yilan.sdk.ui.**YLUIInit**(与DataSDK初始化入口不同！！！)

**YLUIInit** 是一览ui sdk的唯一入口。

| 方法名                              | 方法说明              | 是否为必须参数   |
| ----------------------------------- | --------------------- | ---------------- |
| setApplication(Application context) | 设置Context           | 是               |
| setAccessToken(String token)        | 设置token，由一览提供 | 是，注册后台获取 |
| setAccessKey(String key)            | 设置key，由一览提供   | 是，注册后台获   |
| init()                              | 初始一览sdk           |                  |

### 3.2 短视频Feed流页面

#### 3.2.1 Feed流默认配置（带频道导航栏）

```
ChannelFragment fragment = new ChannelFragment();
FragmentManager manager = getSupportFragmentManager();
manager.beginTransaction().replace(R.id.content, fragment).commitAllowingStateLoss();
```

#### 3.2.2 单频道Feed流页面（不带频道导航栏）

接入方可以使用自己的导航栏，内容是FeedFragment。

```
Channel channel = new Channel();
channel.setId(String id);//设置频道id
Fragment fragment = FeedFragment.newInstance(channel);
```

#### 3.2.3 FeedConfig （Feed流配置）

需在ChannelFragment (FeedFragment) **初始化之前**调用。

```java
FeedConfig.getInstance()
                .setUserCallback(new UserCallback() {
                    @Override
                    public boolean event(int type, //播放状态
                                         PlayData data, //播放的数据，可能为null
                                         int playerHash//哪个播放器ß) {
                        switch (type) {
                            case Constant.STATE_PREPARED:
                                break;
                            case Constant.STATE_ERROR:
                                break;
                            case Constant.STATE_PLAYING:
                                break;
                            case Constant.STATE_COMPLETE:
                                break;
                            case Constant.STATE_PAUSED:
                                break;
                        }
                        Log.e("player state", type + " ");
                        return false;
                    }
                })
                .setShareCallBack(new ShareCallback() {
                    @Override
                    public void onShare(MediaInfo mediaInfo) {
                        Log.e("share", mediaInfo.getVideo_id());
                    }
                }).setOnItemClickListener(new FeedConfig.OnClickListener() {
            @Override
            public boolean onClick(Context context, MediaInfo info) {
                Log.e("click ", "点击了 " + info);
                return false;
            }//点击回调
        });
```

方法说明：

| 方法名                                           | 方法说明                                                     |
| ------------------------------------------------ | ------------------------------------------------------------ |
| setViewHolder（MediaViewHolder mediaViewHolder） | 设置Item样式，必须继承MediaViewHolder,具体见Demo，否则服无法播放视频。 |
| setOnItemClickListener                           | 设置点击的item回调。<br />return true:不跳转，接入方负责跳转;return false,使用默认跳转 |
| setPlayerStyle(int playerStyle)                  | 设置feed流打开样式，目前支持一下4种：<br />FeedConfig._STYLE_NATIVE;//_**_默认_**_为Native播放页_<br />FeedConfig._STYLE_FEED_PLAY;//Feed流当前页播放_<br />FeedConfig._STYLE_NATIVE_FEED;//播放页Feed流_<br />FeedConfig._STYLE_WEB;//Webview打开_ |
| setUserCallBack(UserCallback callback)           | 只有在feed流当前页播放模式下才有效。UserCallback可以获取当前视频播放器的各种状态。<br>目前支持的状态有： |
| setShareCallBack(ShareCallback shareCallBack)    | 设置feed流分享回调，使用share_url字段的链接进行分享          |

### 3.2.4 PlayerConfig 播放页配置

需在VideoFragment (FeedFragment) **初始化之前**调用。

```
PlayerConfig.getInstance()
						.setShareCallback(new ShareCallback())
						.setCommentType(PlayerConfig.SHOW_COMMENT_ALL)；
```



方法说明：

| 方法名           | 方法说明                                                     |
| ---------------- | ------------------------------------------------------------ |
| setCommentType   | 设置播放页评论显示类，目前支持一下3种：<br />PlayerConfig.SHOW_COMMENT_ALL;//显示评论列表，并支持评论_<br />PlayerConfig.SHOW_COMMENT_LIST;//只显示评论列表，不支持评论_<br/>PlayerConfig.DISMISS_COMMENT//隐藏评论列表 |
| setShareCallback | 设置播放页分享回调，使用share_url字段的链接进行分享          |

### 

### 3.3 小视频页面（类抖音样式）

#### 3.3.1 小视频初始化

举例：

```
LittleVideoFragment fragment = LittleVideoFragment.newInstance();
//        littleVideoFragment.
//                setUserCallBack(new UserCallback() {
//                    @Override
//                    public boolean event(int type, PlayData data, int playerHash) {
//                        switch (type) {
//                            case Constant.STATE_PREPARED:
//															break;
//                            case Constant.STATE_ERROR:
//															break;
//                            case Constant.STATE_PLAYING:
//															break;
//                            case Constant.STATE_COMPLETE:
//															break;
//                            case Constant.STATE_PAUSED:
//                                break;
//                        }
//                        return false;
//                    }
//                });
manager.beginTransaction().replace(R.id.content, channelFragment).commitAllowingStateLoss();
      
```

小视频常见方法：

| 方法名                                                       | 方法说明                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| playNext()                                                   | 播放下一集                                                   |
| onPause()、<br />onResume（）、onHiddenChange(boolean hidden)、<br />setUserHint(boolean) | 如果在**Fragment内嵌LittleVideoFragment**，需要手动回调以下Fragment声明周期函数。直接在Activity里面使用则不需要。否则会造成不可见播放等异常情况。 |
| setUserCallBack(UserCallback callback )                      | 设置点击的item播放状态,callback返回true标识用户已经处理了event，返回false，标识使用播放器内部逻辑处理event。 |
| setShareCallBack(ShareCallback shareCallBack)                | 设置分享回调，设置后分享按钮会显示出来，接入方实现Callback方法，使用share_url字段的链接进行分享 |



#### 3.3.2 LittleVideoConfig （小视频其他配置）

需在LittleVideoFragment 初始化之前调用。

```
LittleVideoConfig.getInstance()
    .setViewHolder(new TestLittleVideoViewHolder())//自定义样式
    .setAdVideoCallback(new AdVideoCallback())//设置视频广告回调
    ;

```

方法说明：

| 方法名             | 方法说明                                                     |
| ------------------ | ------------------------------------------------------------ |
| setViewHolder      | 设置小视频列表Item**样式**，必须继承LittleVideoViewHolder，且设置点击回调，具体见demo，否则重复播放视频。 |
| setAdVideoCallback | 设置（穿山甲）视频广告播放回调，AdVideoCallback              |

AdVideoCallback为（穿山甲）视频广告的播放状态回调，接入方根据广告状态进行逻辑处理。

```
package com.yilan.sdk.ui.configs;

import com.yilan.sdk.ui.ad.entity.AdEntity;

public interface AdVideoCallback {
    /**
     * 视频广告已加载
     * @param entity
     */
    void onVideoLoad(AdEntity entity);

    /**
     * 
     * @param errorCode
     * @param entity
     */
    void onVideoError(int errorCode, AdEntity entity);

    /**
     * 视频广告开始播放
     * @param entity
     */
    void onVideoAdStartPlay(AdEntity entity);

    /**
     * 视频广告暂停播放
     * @param entity
     */
    void onVideoAdPaused(AdEntity entity);

    /**
     * 视频广告继续播放
     * @param entity
     */
    void onVideoAdContinuePlay(AdEntity entity);
}

```



### 3.4 小视频页面（快手样式）

#### 3.4.1 小视频初始化

```
KSLittleVideoFragment littleVideoFragment = KSLittleVideoFragment.newInstance();
manager.beginTransaction().replace(R.id.short_content, littleVideoFragment)
        .commitAllowingStateLoss();

```

### 3.5 Natvie原生播放页

原生播放页支持以下2种样式，进入播放器均自动播放。

- 正常的播放页，固定播放器+相关视频。
- Feed流样式播放页。

#### 3.5.1 直接拉起播放页

使用VideoId主动拉起。

```
VideoActivity.start(Context context,String videoId);

```

#### 3.5.2 嵌套播放页Fragment

因为播放页内部使用Fragment，可以直接初始化Fragment。这种方式更加灵活。<br />导入Fragment 的 Activity的manefest配置文件中的属性为：

```
android:configChanges="orientation|keyboardHidden|screenSize"

```

1、正常播放页，VideoFragment。

```
BaseVideoFragment videoFragment = VideoFragment.newInstance();
Bundle bundle = new Bundle();
if (info != null) {
	bundle.putSerializable(Constants.DATA, info);
}
videoFragment.setArguments(bundle);
getSupportFragmentManager().beginTransaction()
        .replace(R.id.layout_root, videoFragment).commitAllowingStateLoss();

```

2、Feed流样式的播放页，VideoFeedFragment。

```
BaseVideoFragment videoFragment = VideoFeedFragment.newInstance();
//其他参数同上

```

3、设置播放状态监听

```
public void setUserCallBack(UserCallback userCallBack) {
        mUserCallback = userCallBack;
    }

```

举例：

```
videoFragment.setUserCallBack(new UserCallback() {
            @Override
            public boolean event(int type, PlayData data, int playerHash) {
                switch (type) {
                    case Constant.STATE_PREPARED:
                    case Constant.STATE_ERROR:
                    case Constant.STATE_PLAYING:
                    case Constant.STATE_COMPLETE:
                    case Constant.STATE_PAUSED:
                        Log.e("player ", "播放器状态" + type);
                        break;
                }
                return false;
            }
        });

```

### 3.6 局部信息流

为了方便接入方在各个位置插入视频内容，我们提供了局部信息流概念。接入方可以在feed流内随意接入视频Item，点击后进入播放页进行播放。

封装好的样式举例:（只显示第1条数据）

```
new FeedExpress().show(viewGroup,"10175", new CustomListener() {
           @Override
           public void onShow(View view, MediaInfo info) {}
           
           @Override
           public void onClick(View view, MediaInfo info) {}
           
           @Override
           public void onError(int hashCode, Throwable e) {}
           
           @Override
           public void noData(int hashCode) {}
           
           @Override
           public void onSuccess(int hashCode, List<MediaInfo> mediaInfos) {
           		//接入方可以根据返回的数据mediaInfos，来自定义样式
           }
       });

```

获取数据自定义样式举例:

```
new FeedExpress().show(null,null,0,3, new CustomListener() {
           @Override
           public void onShow(View view, MediaInfo info) {}
           
           @Override
           public void onClick(View view, MediaInfo info) {}
           
           @Override
           public void onError(int hashCode, Throwable e) {}
           
           @Override
           public void noData(int hashCode) {}
           
           @Override
           public void onSuccess(int hashCode, List<MediaInfo> mediaInfos) {
           		//接入方可以根据返回的数据mediaInfos，来自定义样式
           }
       });

```



FeedExpress 方法说明：

| 方法名                                                       | 方法说明                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| show(ViewGroup rootView, String channelId, CustomListener customListener) | rootView：item的父容器<br />channelId：请求的频道<br />customListener：参数回调。<br />rootView和customListener2者必须填一个。 |
| show(ViewGroup rootView, String channelId, int type, final int num, CustomListener customListener) | rootView：item的父容器<br />channelId：请求的频道<br />type:请求类型，默认 0 短视频; 1：小视频<br />num：请求个数，建议1-2，最多4个。<br />customListener：参数回调。<br />rootView和customListener2者必须填一个。 |

**如使用MediaInfo数据自定义样式，需要先添加展示上报，点击时进行跳转。**

```
//展示上报
YLReport.instance().reportVideoShow(mMediaInfo);
//小视频页面
LittleVideoActivity.start(Context context, ArrayList<MediaInfo> list);
//短视频页面
VideoActivity.start(Context context, MediaInfo info);


```

<a name="ev3Pf"></a>

### 3.7 社交模块

社区功能主要包括评论、点赞、分享等，未登录用户只能进行浏览，不能进行参与。如发表评论、删除评论等等。

#### 3.7.1 用户登陆、退出

```
YLUser.getInstance().login(nick, avatar, phone, userId);

```

YLUser为单例使用，常用的方法如下。

| 方法名                                                       | 方法说明                                                     | 备注                            |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------- |
| void login(String nickname, String avatar, String phone, String userId) | nickname：用户昵称。必传。<br />avatar：用户头像。<br />phone：用户手机号。必传<br />userId：用户id。必传 | 调用时机：在APP启动和用户登陆后 |
| void logout()                                                | 退出登录                                                     | 调用时机：在用户主动退出登录后  |
| String getToken()                                            | 获取用户token                                                | 在登陆后获取                    |

### 3.8 支持联盟广告

目前sdk已经支持大部分联盟广告， 如接入方已经接入了联盟广告，可以将广告位appid和广告位id告知一览广告人员，在一览后台进行统一下发后，SDK端进行渲染显示。目前sdk支持的广告汇总如下：

| 联盟名称\广告位  | feed流     | 小视频 | 播放页相关推荐 |
| ---------------- | ---------- | ------ | -------------- |
| 直投（一览下发） | 图片、视频 | 图片   | 图片、视频     |
| 穿山甲           | 图片       | 视频   | 图片           |
| 广点通           | 图片       | 不支持 | 图片           |
| 百度             | 图片       | 不支持 | 图片           |

接入步骤如下：

1）添加联盟的jar或aar。（默认已经支持）

2）修改Manifest的文件，增加联盟需要支持的provider等。

3）告知一览广告同学响应广告位的联盟广告应用id和广告位id。

具体可以参考联盟广告开发文档。

```
<!-- 穿山甲 -->
        <provider
            android:name="com.bytedance.sdk.openadsdk.TTFileProvider"
            android:authorities="${applicationId}.TTFileProvider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>

        <provider
            android:name="com.bytedance.sdk.openadsdk.multipro.TTMultiProvider"
            android:authorities="${applicationId}.TTMultiProvider"
            android:exported="false" />

 <!-- 广点通 -->
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>

        <!-- 声明 SDK 所需要的组件 -->
        <service
            android:name="com.qq.e.comm.DownloadService"
            android:exported="false" />
        <!-- 请开发者注意字母的大小写，ADActivity，而不是 AdActivity -->
        <activity
            android:name="com.qq.e.ads.ADActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenSize" />
        <activity
            android:name="com.qq.e.ads.PortraitADActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenSize"
            android:screenOrientation="portrait" />
        <activity
            android:name="com.qq.e.ads.LandscapeADActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenSize"
            android:screenOrientation="landscape" />

<!--baidu -->
        <activity
            android:name="com.baidu.mobads.AppActivity"
            android:configChanges="keyboard|keyboardHidden|orientation"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
        <provider
            android:name="com.baidu.mobads.openad.FileProvider"
            android:authorities="${packageName}.bd.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>

```

## 四、常见问题

### 4.1.混淆

若您的App开启了混淆，请为我们的SDK添加下述混淆规则

```
-keep class com.yilan.sdk.**{
    *;
}
-dontwarn javax.annotation.**
-dontwarn sun.misc.Unsafe
-dontwarn org.conscrypt.*
-dontwarn okio.**

###阿里云混淆
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

```

### 4.2 冲突解决

1、gson冲突

取消依赖Gson

2、阿里云Httpdns冲突。<br />[https://helpcdn.aliyun.com/knowledge_detail/66886.html](https://helpcdn.aliyun.com/knowledge_detail/66886.html)

[https://help.aliyun.com/knowledge_detail/59152.html](https://help.aliyun.com/knowledge_detail/59152.html)

```
//implementation ('com.aliyun.ams:alicloud-android-httpdns:1.2.3@aar') {
//        transitive true
//    }
如支付宝和httpdns冲突
implementation('com.aliyun.ams:alicloud-android-httpdns:1.2.3', {
        exclude group: 'com.ta.utdid2'
        exclude group: 'com.ut.device'
    })

```

3、Android集成时，可以通过`exclude`关闭其他产品依赖，示例如下所示：

```
compile ('com.xxx:xxx.xxx:1.0.1') {
exclude (module: 'alicloud-android-utdid')
}


```

