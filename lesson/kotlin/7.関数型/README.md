# 関数型
Kotlinは関数型プログラミングを使えるようになっています。
実際に、コレクションについて関数型の書き方をしてシンプルにまとめるというところがソースコードの中でも散見されていますし、可読性が実際に向上しています。

## map関数
例えば、リストに対して、map関数を使うことで、その中身に対して操作をした新しいコレクションを返すことができます。
```kotlin
val list = listOf(2,3,5,7,11,13) // 2,3,5,7,11,13
val listSquare = list.map{ it * it } // 4,9,25,49,121,169
```
map関数を少しみにいくと、
```kotlin
public inline fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R>
```
となっていて、Iterableに対して使える関数で、返す型はtransformの内部で生成された型に対するListになっていますね。

## filter関数
次はfilter関数です。この関数は、コレクションの内部に対して、与えたpredicate関数にマッチするものだけを抜き出してくれます。
```kotlin
val list = listOf(2,3,5,7,11,13) // 2,3,5,7,11,13
val listUnderSix = list.filter{ it < 6 } //2,3,5
```
こちらも少しどういった型なのかみにいくと、
```kotlin
public inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T>
```
となっており、与えたpredicateの関数でtrueになるものだけを抜き出した新しいListが生成されることになっています。

> 練習問題
> https://pl.kotl.in/klqa3zwE3?theme=darcula

