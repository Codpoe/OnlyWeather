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
- 不同的天气状况，可自动替换相应的首页背景图
- 定位功能
- 目前最低只适配到 4.4，而且在 4.4 上的体验不佳，后续会努力做得更好
- ...

#### 已知的 BUG
- 在 Drawer『打开再关闭』之后，如果此时的 CollapsingToolbar 刚好是折叠着的，那就会出现『下拉无法伸展 CollapsingToolbar，而只能触发刷新』的情况，可通过左右滑动 ViewPager 来暂时解决这个问题

#### 版本更新
V 1.0.1
- 修复 添加城市的 BUG

V 1.0.0
- First version

## 截图
![](http://7xttuv.com2.z0.glb.qiniucdn.com/github%2Fonly-weather%2Freadme%2Fsceenshot_4.jpg)

## 感谢开源
此项目用到的开源库:
- [Rxjava](https://github.com/ReactiveX/RxJava)
- [Rxandroid](https://github.com/ReactiveX/RxAndroid)
- [Retrofit2](https://github.com/square/retrofit)
- [Glide](https://github.com/bumptech/glide)
- [Otto](https://github.com/square/otto)

## 特别鸣谢
- [写代码的猴子](https://github.com/laobie)
- 就看天气的作者 -- [Hugo Xie](https://github.com/xcc3641)

## 关于我
Android 新手

博客也是新建的，暂时没什么内容，以后会多起来吧，谁知道呢╮(╯▽╰)╭
- Blog: [Codpoe's blog](http://www.codpoe.me/)
- Weibo: [@Codpoe](http://weibo.com/u/2757541610)
- E-mail: codpoe.me@gmail.com

