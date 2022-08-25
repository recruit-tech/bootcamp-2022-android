# Nullとのお付き合い
KotlinはNull-Safetyな言語なので、明示的にNullを許容しない限りNullが発生することはありません。

## Nullを許容する書き方
```kotlin
// Stringの代わりにString?を使う
var allowNullVariable: String? = null
```

## Null安全だからNullとのお付き合いは大変
まずは次のコードを見てください。
```kotlin
val input: String? = "input value."
var check: String = input
```
Nullableで定義したinputをNon-nullの型には代入できません。(逆は可能です)
そこで、一番簡単にこのエラーを解消する方法が次です。
```kotlin
val input: String? = "input value."
var check: String = input!! //強制unwrap
```
このやり方は非推奨です。絶対に使ってはいけないというわけではないですが、使わないといけない実装はなるべく避けるべきです。また、おそらく強制アンラップをチームルールとして禁止しているところもあるんじゃないでしょうか。

## Nullableとうまく付き合う。
`any`を使って確かめてみます。
any関数はNon-nullのString型に対して実装しているので、次のコードもエラーになります。
```kotlin
val input: String? = "input value."
print(input.any())
```
これに対処する方法を幾つか列挙していきます。

### 方法1 if文でnull check
```kotlin
val input: String? = "input value."
if (input != null) print(input.any()) else print("input is null.")
```

### 方法2 セーフコール演算子
```kotlin
val input: String? = "input value."
print(input?.any())
```
`?`をつけると、inputがnullでない場合は`any()`を呼び出し、inputがnullであれば`null`がprintされます。

### 方法3 セーフコールとletを使う
```kotlin
val input: String? = "input value."
input?.let { // input!!に相当する変数が暗黙的にitに格納されている。
    print(it.any()) // itはこの時点でNon-nullなのでここで怒られない。
} // input がnullだった場合は何も出力されない。
```

### 方法４ エルビス演算子
```kotlin
val input: String? = "input value."
val inputNotNull = input ?: "Null Value"
```

## Nullableとの付き合いはどれがベスト？
Kotlinとしては、方法3を使うことが多いです。(`let`のような標準関数は後ほど扱います。)
方法1はやはり冗長だし、方法2はnullの場合何もされずにこの行がスキップされるので、関数内だとまたnullが返されることにつながります。

ところで、一つ方法1で疑問に思ったことはありませんか？
2行目の`input.any()`はなぜ許されているんでしょうか。
Kotlinはスマートキャストの機能があるので、直前にnullチェックがされたと言うことで、
この部分でinputがnullになる可能性はないので、inputをこの段階でNon-nullと認識しています。
> 参考: https://kotlinlang.org/docs/null-safety.html#checking-for-null-in-conditions

> 練習問題
> https://pl.kotl.in/GfSV9lXDX?theme=darcula
