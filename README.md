# 仅仅天气
Material Design + RxJava + Retrofit + Glide + Otto

## 前言
写这款小小小的天气应用，只是为了学习、练手，但由于我目前的开发姿势不高，投入了很多的课余时间，希望能有喜欢它的人(๑•̀ㅂ•́)و✧

当然，如果有任何意见或问题，欢迎提 Issue，或者通过微博、邮件联系我。
## 简介
仅仅天气，顾名思义，只有天气，功能相当单一，以至于不会让你在不合适的地方看到广告(逃...

首页的设计，参考了[就看天气](https://github.com/xcc3641/SeeWeather) -- 很赞的项目，适合新手学习

#### 暂且说是特性吧
- 当天以及未来几天的天气数据
- 长按 可拖拽天气卡片，并改变其位置，而排在第一位的城市，将会在首页进行展示
- 左右滑动 可删除天气卡片

#### TODO
- ~~不同的天气状况，可自动替换相应的首页背景图~~
- ~~初级定位功能~~
- 完善定位功能
- ...

#### 已知的 BUG
- ~~在 Drawer『打开再关闭』之后，如果此时的 CollapsingToolbar 刚好是折叠着的，那就会出现『下拉无法伸展 CollapsingToolbar，而只能触发刷新』的情况，可通过左右滑动 ViewPager 来暂时解决这个问题~~

#### 下载
- [fir.im](http://fir.im/onlyweather)
- [豌豆荚](http://www.wandoujia.com/apps/me.codpoe.onlyweather)


#### 版本更新
V 1.2.6
- 增加 定位功能
目前的定位功能还在初步阶段
- 增加 应用崩溃信息的上传

V 1.2.5
- 增加 星座卡片
默认关闭，可在设置中开启。
- 增加 通知栏天气
默认开启，可在设置中关闭。建议将此应用设为白名单，以至于通知栏天气不会失效。
- 优化 交互动画，改善体验

V 1.2.1
- 修复一个重大 BUG
此 BUG 曾导致：添加国外城市后，应用崩溃。
- 增加老黄历
默认关闭，可在设置中开启。

V 1.1.0
- 更新首页背景图
不同的天气状况，会自动更换相应的背景图。
- 增加截屏并分享的功能
现在可以通过点击首页的『飞机』按钮，或右上角的分享菜单，来截取首页的天气，并且分享给小伙伴们。

V 1.0.1
- 修复 添加城市的 BUG

V 1.0.0
- First version

## 截图
![](http://7xttuv.com2.z0.glb.qiniucdn.com/github%2Fonly-weather%2Freadme%2Fsceenshot_4.jpg)

## 感谢开源
此项目用到的开源库:
- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Retrofit](https://github.com/square/retrofit)
- [Glide](https://github.com/bumptech/glide)
- [Otto](https://github.com/square/otto)
- [AndroidViewAnimations](https://github.com/daimajia/AndroidViewAnimations)
- [recyclerview-animators](https://github.com/wasabeef/recyclerview-animators)

## 特别鸣谢
- [写代码的猴子](https://github.com/laobie)
- [Hugo Xie](https://github.com/xcc3641)

## 关于我
Android 新手

博客也是新建的，暂时没什么内容，以后会多起来吧，谁知道呢╮(╯▽╰)╭
- Blog: [Codpoe's blog](http://www.codpoe.me/)
- Weibo: [@Codpoe](http://weibo.com/u/2757541610)
- E-mail: codpoe.me@gmail.com

