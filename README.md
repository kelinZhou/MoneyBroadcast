# MoneyBroadcast
基于Kotlin和SoundPool实现Android端**一行代码**完成不只是金额播报功能。[![](https://jitpack.io/v/kelinZhou/MoneyBroadcast.svg)](https://jitpack.io/#kelinZhou/MoneyBroadcast)

* * *

## 说明
本库支持的最大位数为“亿”，小数最大支持4位，也就是说播报的金额不能超过**999999999.9999**。超过了**999999999.9999**将只会播报开头部分，如果你没有自定义开头部分的语音则只会播放'收款成功'，如果你自定了开头部分的语音则只会播放你自定义的开头语音。
**通过自定义前缀和单位可实现任何有关于数字的播报。例如：今日客流量为3000人次、同比上涨百分之180、昨夜降雨量为18.88毫米、您今日共步行28030步等等。**

## 更新

### 2.0.2
1.新增支持asset目录下的声音文件。
2.优化调用接口。

### 2.0.1
1.变更内部播放逻辑，采用生产者、消费者的模式配合队列实现语音播报。 
2.支持批量播放。
3.播放器改用MediaPlayer实现。
4.暂时不支持asset目录下的声音文件。

### 1.1.0 增加stop功能，用于停止播放。调用方法如下:
```kotlin
moneyBroadcaster.stop()
```

### 1.0.1 修复播放整百、整千等金额是会多报一个零的Bug。

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
MoneyBroadcaster.create(applicationContext).play(57392.23)
```

#### 使用自定义声音：

```kotlin
MoneyBroadcaster.create(applicationContext) {
    return@with when (it) {
        VoiceWhatPrefix -> {
            AssetVoice("sound/tts_success.mp3") //收款成功使用asset目录下的自定义声音资源
        }
        VoiceWhatUnit -> {
            RawVoice(R.raw.yuan) //金额单位使用raw目录下的自定义声音资源
        }
        else -> null  //返回null表示使用MoneyBroadcast提供的默认声音。
    }
}.play(57392.23)
```

```AssetVoice```为asset目下的声音资源，构造方法参数如下：

 * res : String             asset目录下资源的文件名称，如果资源放在子文件夹下，还要包含文件夹名称，例如：```sound/tts_success.mp3```。

```RawVoice```为raw目录下的声音资源，构造方法参数如下：

 * res : Int   @RawRes	  raw目录下的资源ID，例如：```R.raw.yuan```。

除了上面两种声音资源外还有一个类```NullVoice```是用来处理不需要播放声音的情况的，例如不需要播放**收款成功**的提示，则可以判断```what==VoiceWhatSuccess```时返回```NullVoice()```，当MoneyBroadcast检测到```NullVoice```后会不播放任何声音。

#### 不播报收款成功：

```kotlin
MoneyBroadcaster.create(applicationContext).play(
  	//利用VoiceWhatNull禁用'收款成功'的提示音。
    AmountPlayInfo(etAmount.text.toString().toDouble(), VoiceWhatNull)
)
```