# インターフェース
## 「何をするか」を指定する
interfaceは『何が実装されているか』を指定することができる宣言です。
これのありがたいことは、interfaceを用いて引数の指定をすると、「もらったインスタンスは何ができるかはわかる」ので、もらった側の実装を進めることができて、渡す側としては「指定したことができるインスタンス」を渡すことだけを保証すればいいので、違ったものを渡すことができるようになるのです。

### interfaceの使い道
interfaceは色々と使い道があるのですが、テストを書きたいというときに特に重宝するので、今回はテストに関しての説明にします。
まずは下の図で、元々は状況Aだったとしましょう。あるクラスからはHogeClassのインスタンスを呼び出し、なんらかの処理をしています。このとき、あるクラスに対してテストを書こうとすると、HogeClassのインスタンスが必要になります。
もちろん、HogeClassのインスタンスが簡単に準備できればそれでいいのですが、実際はこのHogeClassはDBへのアクセスを行なっていたとします。そうすると、あるクラスのテストのためにわざわざHogeClassのインスタンスを作り、DBへのアクセスをしないといけなくなります。
そこで、一枚インターフェースを挟むことで、あるクラスは振る舞いを変えずに、テストをするときのために外からHogeTestClassのインスタンスを差し込むことができるようになります。

## 抽象クラスとinterfaceの違い
abstract classとinterfaceは非常に似ているように思えます。
Kotlinのinterfaceはデフォルトの実装も持つことができます。

Kotlinの公式ページでは、次のように差分の紹介がされています。

> Interfaces in Kotlin can contain declarations of abstract methods, as well as method implementations. What makes them different from abstract classes is that interfaces cannot store a state. They can have properties, but these need to be abstract or provide accessor implementations.
> 参考 https://kotlinlang.org/docs/interfaces.html

違いを簡単にまとめると、次のようになります。
### abstract classを使う
```kotlin
abstract class ASample(
    val input: String = "default" // コンストラクタを持てる。
) {
    var sampleInt = 123 // 状態を持てる
    open var sampleOpenVar = "abc"
    // val notAllowed: String  これは許されない。abstractで定義するか、initializeされている必要がある。
    abstract val allowed: Int // abstractなら定義可能。
}

class Sample: ASample() {
    override var sampleOpenVar = "from Sample Class"
    override val allowed = 123 // 必ずclassの方で実装が必要
}
```
抽象クラスは状態を持て、コンストラクタも持つことができます。
従って、似た処理をクラスたちの基底クラスとして実装して、ボイラープレートを減らすような用途には向いていると言えるでしょう。

### interfaceを使う
```kotlin
interface ISample { // コンストラクタは持てない。
    var sampleInt: Int // abstract classと違って、このままの宣言が可能
    // val notAllowed = "Not Allowed" 状態を持つことができない。
    val sampleString: String
        get() = "This is Allowed." // getter/setterの利用は可能。
}
```
interfaceは状態を持てません。
用途としては抽象クラスのような継承のための利用ではなく、クラス間の結合を疎にするための役割を果たすためにあると言えるでしょう。


### コード例
```kotlin
class A( // クラスのプライマリコンストラクタとデフォルト引数を用いると便利
    private val hoge: HogeInterface = HogeClass()
) {
    fun do() {
        hoge.doSomething()
    }
}

interface HogeInterface { // あくまでどんなメソッドが存在するかの羅列
    fun doSomething()
}

// テストコードでは、HogeTestClassを作っておく。
class HogeTestClass : HogeInterface {
    fun doSomething() {
        // nothing.
    }
}
```

使い慣れるためにも、練習問題に取り組んでみよう。

> 練習問題
> https://pl.kotl.in/x7Mu70OyH
