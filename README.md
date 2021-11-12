# MoneyBroadcast
基于Kotlin和SoundPool实现Android端**一行代码**完成金额播报功能。[![](https://jitpack.io/v/kelinZhou/MoneyBroadcast.svg)](https://jitpack.io/#kelinZhou/MoneyBroadcast)

* * *

## 说明
本库支持的最大位数为“亿”，小数最大支持4位，也就是说播报的金额不能超过**999999999.9999**。超过了**999999999.9999**将只会播报开头部分，如果你没有自定义开头部分的语音则只会播放'收款成功'，如果你自定了开头部分的语音则只会播放你自定义的开头语音。

## 下载

###### 第一步：添加 JitPack 仓库到你项目根目录的 gradle 文件中。
```groovy
allprojects {
    repositories {
        //... 省略N行代码
        maven { url 'https://jitpack.io' }
    }
}
```
###### 第二步：添加这个依赖。
```groovy
dependencies {
    implementation 'com.github.kelinZhou:MoneyBroadcast:${Last Version Here!}'
}
```

## 使用

#### 最简单的用法：
```kotlin
MoneyBroadcaster.with(applicationContext).play(57392.23)
```

#### 使用自定义声音：

```kotlin
MoneyBroadcaster.with(applicationContext) {
    return@with when (it) {
        VoiceWhatSuccess -> {
            AssetVoice("sound/tts_success", 1500) //收款成功使用asset目录下的自定义声音资源
        }
        VoiceWhatUnit -> {
            RawVoice(R.raw.yuan, 1500) //金额单位使用raw目录下的自定义声音资源
        }
        else -> null  //返回null表示使用MoneyBroadcast提供的默认声音。
    }
}.play(57392.23)
```

```AssetVoice```为asset目下的声音资源，构造方法参数如下：

 * res : String             asset目录下资源的文件名称，如果资源放在子文件夹下，还要包含文件夹名称，例如：```sound/tts_success```。
 * duration : Long      声音资源的播放时长，该参数必须要是当前声音资源的真实播放时长，否则可能出现声音重叠或间隔过长的问题。

```RawVoice```为raw目录下的声音资源，构造方法参数如下：

 * res : Int   @RawRes	  raw目录下的资源ID，例如：```R.raw.yuan```。
 * duration : Long      声音资源的播放时长，该参数必须要是当前声音资源的真实播放时长，否则可能出现声音重叠或间隔过长的问题。

除了上面两种声音资源外还有一个类```NullVoice```是用来处理不需要播放声音的情况的，例如不需要播放**收款成功**的提示，则可以判断```what==VoiceWhatSuccess```时返回```NullVoice()```，当MoneyBroadcast检测到```NullVoice```后会不播放任何声音。

#### 不播报收款成功：

```kotlin
MoneyBroadcaster.with(applicationContext).play(
  	//利用VoiceWhatNull禁用'收款成功'的提示音。
    AmountPlayInfo(etAmount.text.toString().toDouble(), VoiceWhatNull)
)
```