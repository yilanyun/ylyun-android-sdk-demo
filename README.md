# 一览Android UI SDK接入文档


ReleaseNote

| 版本 | 时间 | 修改内容 |
| --- | --- | --- |
| 1.0.1 | 2018年11月15日 | 第一次创建  V1.0.0 |
| 1.0.3 | 2019年2月28日 | 增加feed广告 |
| 1.0.4 | 2019年3月15日 | 1、增加Feed流自定义样式和回调<br />2、增加小视频自定义样式和回调<br />3、优化webview自动播放视频 |
| 1.0.5 | 2019年4月10日 | 1、降级glide至3.6<br />2、增加feed列表scrollToTop()方法，支持列表滚动到顶部 |
| 1.0.6 | 2019年4月15日 | 1、解决小视频不可见播放的问题<br />2、优化小视频播放，节省流量<br />3、优化FeedConfig，用户可以自定义跳转 |
| 1.0.7 | 2019年4月24日 | 1、增加快手瀑布流样式小视频<br />2、优化小视频播放页<br />3、通过videoId打开播放页<br />4、原生播放页支持Feed流样式 |



<a name="0bc94f51"></a>
## 一、SDK 概要
UISDK为移动应用提供内容分发功能，为客户提供较为简洁的API接口，方便第三方应用快速的集成并实现内容分发功能。<br />UISDK提供的功能如下：

- 绘制频道Feed流列表
- 绘制小视频列表
- 绘制播放页
<a name="8b6bfda7"></a>
## 二、SDK 类
一览Data SDK主要提供了以下类：<br />基础类：

- YLUIInit：整个UI SDK的主入口，单例，主要提供初始化，配置用户信息。
- MediaInfo：视频的model类
- MediaDetail:视频详情的model类

页面：

- ChannelFragment 频道导航栏 + feed流页面
- FeedFragment feed流页面
- LittleVideoFragment 小视频页面
- KSLittleVideoFragment 类快手样式小视频页面
- VideoActivity 播放页面Activity
- VideoFragment 正常样式的播放页Fragment
- VideoFeedFragment Feed流样式的播放页Fragment

<a name="fffec4b8"></a>
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
implementation 'com.yilan.sdk:ui:x.x.x'//修改为具体的sdk版本
implementation 'com.squareup.okhttp3:okhttp:3.11.0'
implementation 'com.google.code.gson:gson:2.8.5'
implementation 'com.github.bumptech.glide:glide:4.8.0'
implementation 'com.android.support:recyclerview-v7:28.0.0'
implementation ('com.aliyun.ams:alicloud-android-httpdns:1.2.3@aar') {
        transitive true
    }
```

<a name="d7f4b06a"></a>
### 2、示例demo工程接入

YilanSDkDemo是接入一览SDK的示例工程，它可以使用AndroidStudio来打开。

demo地址：<br />[https://github.com/yilanyun/ylyun-android-sdk-demo](https://github.com/yilanyun/ylyun-android-sdk-demo)

当您使用AndroidStudio开发环境时（这也是我们推荐的方式，请采用AndroidStudio 3.0 以上），通过import project的方式导入示例工程，在弹出的向导中按照AndroidStudio默认的选项，一直点next即可导入工程，正确编译运行。

<a name="203b7c77"></a>
## 三、接入代码

<a name="d5b061ad"></a>
### 3.1 一览sdk 初始化

<a name="7fdaf727"></a>
#### 3.1.1 代码示例

```
YLUIInit.getInstance()
  .setApplication(this)
  .setAccessKey("")//设置accesskey
  .setAccessToken("")//设置token
  .setUid("uid")//设置登录用户id
  .build();
```

<a name="37f247f2"></a>
#### 3.1.2 方法说明

- com.yilan.sdk.ui.**YLUIInit**(与DataSDK初始化入口不同！！！)

**YLUIInit** 是一览ui sdk的唯一入口。

| 方法名 | 方法说明 | 是否为必须参数 |
| --- | --- | --- |
| setApplication(Application context) | 设置Context | 是 |
| setAccessToken(String token) | 设置token，由一览提供 | 是，注册后台获取 |
| setAccessKey(String key) | 设置key，由一览提供 | 是，注册后台获取 |
| setUid(String uid) | 设置对接方的登陆用户id | 否,未登录填"0" |
| init() | 初始一览sdk |  |


<a name="3965076a"></a>
### 3.2 渲染Feed流短视频页面

<a name="5c23fc8c"></a>
#### 3.2.1 Feed流默认配置（带频道导航栏）
```
ChannelFragment fragment = new ChannelFragment();
FragmentManager manager = getSupportFragmentManager();
manager.beginTransaction().replace(R.id.content, fragment).commitAllowingStateLoss();
```
<a name="d41d8cd9"></a>
### 
<a name="5f0e9447"></a>
#### 3.2.2 单频道Feed流页面（不带频道导航栏）
接入方可以使用自己的导航栏，内容是FeedFragment。

```
Channel channel = new Channel();
channel.setId(String id);//设置频道id
Fragment fragment = FeedFragment.newInstance(channel);
```

<a name="1814747a"></a>
#### 3.2.3 FeedConfig （Feed流配置）
需在ChannelFragment (FeedFragment) **初始化之前**调用。

```
FeedConfig.getInstance()
  .setViewHolder(new TestFeedViewHolder())//自定义样式
  .setOnItemClickListener(new FeedConfig.OnClickListener() {//点击回调
  @Override
  public boolean onClick(MediaInfo info) {
    Log.e(TAG, "点击了 " + info);
    return false;
  }
  });
```


方法说明：

| 方法名 | 方法说明 |
| --- | --- |
| setViewHolder | 设置Item样式，必须继承MediaViewHolder,具体见Demo，否则服无法播放视频。 |
| setOnItemClickListener | 设置点击的item回调。<br />return true:不跳转，接入方负责跳转;return false,使用默认跳转 |



<a name="5b8e123f"></a>
### 3.3 小视频页面（类抖音样式）
<a name="3d98b8de"></a>
#### 3.3.1 小视频初始化
```
LittleVideoFragment fragment = LittleVideoFragment.newInstance();
manager.beginTransaction().replace(R.id.content, channelFragment).commitAllowingStateLoss();
      
```

<a name="ce6d5449"></a>
#### 3.3.2 LittleVideoConfig （小视频其他配置）
需在LittleVideoFragment 初始化之前调用。
```
LittleVideoConfig.getInstance()
    .setViewHolder(new TestLittleVideoViewHolder())//自定义样式
    .setPlayerCallback(new UserCallback() {//设置播放状态回调
    @Override
    public void event(int type, PlayData data, int playerHash) {
    switch (type) {
        case Constant.STATE_PREPARED:
        case Constant.STATE_ERROR:
        case Constant.STATE_PLAYING:
        case Constant.STATE_COMPLETE:
        case Constant.STATE_PAUSED:
        Log.e(TAG, "播放器状态" + type);
    break;
    }
    }
    });
```

方法说明：

| 方法名 | 方法说明 |
| --- | --- |
| setViewHolder | 设置Item样式，必须继承LittleVideoViewHolder，且设置点击回调，具体见demo，否则重复播放视频。 |
| setPlayerCallback | 设置点击的item播放状态 |

<a name="ac687a5b"></a>
#### 3.3.2 Fragment嵌套小视频页面

如果Fragment内嵌LittleVideoFragment，需要手动回调以下Fragment声明周期函数。直接在Activity里面使用则不需要。否则会造成异常情况，如不可见播放等问题。

LittleVideoFragment方法说明。

| onPause() |  |
| --- | --- |
| onResume() |  |
| onHiddenChange(boolean hidden) |  |
| setUserHint(boolean) |  |

LittleVideoFragment其他方法说明

| refresh() | 刷新列表 |
| --- | --- |


<a name="6lO80"></a>
### 3.4 小视频页面（快手样式）

<a name="Bsgyu"></a>
#### 3.4.1 小视频初始化

```
KSLittleVideoFragment littleVideoFragment = KSLittleVideoFragment.newInstance();
manager.beginTransaction().replace(R.id.short_content, littleVideoFragment)
        .commitAllowingStateLoss();
```


KSLittleVideoFragment 其他方法说明

| refresh() | 刷新列表 |
| --- | --- |


<a name="Amnn2"></a>
### 3.5 Natvie原生播放页
原生播放页支持2种样式；正常的播放页为固定播放器+相关视频 和 Feed流样式播放页。进入播放器均自动播放。

<a name="InsYx"></a>
#### 3.4.1 Native Feed流点击item进入播放页
<br />如从Feed页面点击Item默认进入H5播放页，可以通过以下配置进入原生播放页。

```
FeedConfig.getInstance().setPlayerStyle(int style);
```

方法说明：

| 方法名 | 方法说明 |
| --- | --- |
| setPlayerStyle（int style） | public static final int _STYLE_WEB _= 0;//feed流跳转到web<br />public static final int _STYLE_FEED_PLAY _= 1;//feed流当前播放<br />public static final int _STYLE_NATIVE _= 2;//native播放页<br />public static final int _STYLE_NATIVE_FEED _= 3;//native feed流样式播放页 |


同时设置导入Fragment 的 Activity的manefest配置文件中的属性为：
```
android:configChanges="orientation|keyboardHidden|screenSize"
```

<a name="Lk2ZJ"></a>
#### 3.4.2 直接拉起播放页

- 方式1 直接拉起播放页面，使用VideoId主动拉起。

```
VideoActivity.start(Context context,String videoId);
```

- 方式2。因为播放页内部使用Fragment，可以直接初始化2种Fragment。这种方式更加灵活。

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
```

<a name="Jqb1B"></a>
## 四、常见问题
<a name="eb8b55ef"></a>
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

<a name="e7475c27"></a>
### 4.2 冲突解决
1、gson冲突

取消依赖Gson

2、阿里云Httpdns冲突。<br />[https://helpcdn.aliyun.com/knowledge_detail/66886.html](https://helpcdn.aliyun.com/knowledge_detail/66886.html)<br />[https://help.aliyun.com/knowledge_detail/59152.html](https://help.aliyun.com/knowledge_detail/59152.html)

```
//implementation ('com.aliyun.ams:alicloud-android-httpdns:1.2.3@aar') {
//        transitive true
//    }
```

3、Android集成时，可以通过`exclude`关闭其他产品依赖，示例如下所示：

```
compile ('com.xxx:xxx.xxx:1.0.1') {
exclude (module: 'alicloud-android-utdid')
}
```






