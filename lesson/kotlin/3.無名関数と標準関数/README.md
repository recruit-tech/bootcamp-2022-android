# 無名関数とラムダ式
まずは次のコードを見てみる。
```kotlin
var hasChars = "sample".any()
```
ここで、`any()`はStringの型に定義されている標準の関数で、文字が含まれていれば`true`を返す。
では、`any()`はどんな実装を持っているのか、見にいってみよう。`any()`にカーソルをあてて`⌘ + B`してみよう。

### any()の実装を見てみる
```kotlin
public fun CharSequence.any(): Boolean {
    return !isEmpty()
}

public inline fun CharSequence.any(predicate: (Char) -> Boolean): Boolean {
    for (element in this) if (predicate(element)) return true
    return false
}
```
このように、anyには二つの関数が紐づいていることがわかります。
先ほど実行したのは上の方なんですが、下側の関数も気になりますよね？ということで触ってみましょう。

## 無名関数
```kotlin
var hasCharA = "sample".any{ letter ->
    letter == 'a'
}
```
ポイントは、この関数`any()`の引数が`(Char) -> Boolean`になっているところにあります。
関数そのものが`any()`の引数になっているわけです。`any()`の実装を見ていると、引数`predicate`に`element`、つまりStringの一文字一文字を代入していき、`true`が返ってこれば`any()`も`true`を返すという実装になっています。この引数に与えている、`{letter -> //hoge}`こそが無名関数です。

先程の実装にコメントを加えると、
```kotlin
var hasCharA = "sample".any{ letter /** (Char)に該当。 **/ ->
    letter == 'a' // returnは書かなくて良い。この条件式の結果が返り値で期待されているBoolean
}
```
こうなっているというわけです。
コールバックの実装や、こういった標準関数を利用する際によく使われます。

まずは少し遊んでみましょう。

> 練習問題
> https://pl.kotl.in/3trtL-5hu?theme=darcula

# 標準関数
より**Kotlinっぽい**部分に足を踏み入れていきましょう。さきほど少し`let`を扱いましたが、他にもいくつかあるので触っていきましょう。
まず、この標準関数と呼んでいるのは、ラムダ(無名関数)で指定した処理を実行するための汎用的な関数です。
実装自体は`Standard.kt`にあるので、興味がある方はこちらをご覧ください。ジェネリクスあたりについて少し学んだ後に見てみると面白いかもしれません。
ここでは、`apply`,`let`について触れることにします。

## `apply`について
`apply`はよく使われる標準関数です。リストの学習と前後しますが、サンプルコードは次のようなものです。
```kotlin
var list = mutableListOf<String>().apply {
    add("apple")
    add("grape")
    add("orange")
}
```
この`mutableListOf<String>()`は`MutableList`という型のインスタンスに当たりますが、これをレシーバと呼びます。applyはこのレシーバに対して元の型が持っている関数を呼び出すことができ、実質的にレシーバの初期化を簡単・簡潔にしてくれる標準関数です。applyはあれこれ処理した後のレシーバそのものを返します。

## `let`について
`let`もよく使われる標準関数です。サンプルコードは次のような感じです。
```kotlin
var numOfString = "sample".let { it.count() }
```
String型のインスタンスである"sample"というレシーバに対して、そのレシーバ自体をラムダ内部の引数にします。加工後の値が出力になるので、`apply`とは異なり、レシーバ自体に変更はありません。numOfStringは整数型になります。
nullの章でお話しした通り、セーフコール演算子との相性がよく、nullに対する扱いの定義の際によく用いられています。

## そのほかの汎用的な標準関数について
そのほかにも、`also`や`run`、`with`などの関数が定義されていますが、これらの違いは返す値がレシーバ自身かそうではないか、自身を受けっとっているか、引数としているかの違いです。例に出していた`apply`と`let`を見てみます。
```kotlin
public inline fun <T> T.apply(block: T.() -> Unit): T
public inline fun <T, R> T.let(block: (T) -> R): R
```
どちらの関数の引数でもある`block`を確認していきます。applyは`T`を引数としていません。なので、アクセスするには自分自身を示す`this`になります。一方で`let`は`T`が引数です。なので、明示的に変数名を指定しない場合は`it`でアクセスできます。
また、返り値が自分自身の`apply`とblockの返り値`R`の`let`で違いがありますね。

> 練習問題
> https://play.kotlinlang.org/embed?short=vReICb3G8&theme=darcula