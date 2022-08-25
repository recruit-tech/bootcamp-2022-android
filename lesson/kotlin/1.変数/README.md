# 変数、条件文・条件式、関数

## 変数
```kotlin
var sample: String = "Sample"
```
| 部分     | 何してる | 備考                                             |
| :------- | :------- | :----------------------------------------------- |
| var      | 宣言     | varはmutable変数, immutableはval (後述)          |
| sample   | 変数名   | 一部予約語はあるので気を付ける                   |
| String   | 型宣言   | 型推論が効くので"基本的には"書かないことが多い。 |
| "Sample" | 初期値   | 型指定している場合は他の型を入力すると怒られる。 |

### mutableとimmutable
#### mutable
```kotlin
var mutableVariable = "This is Mutable."
// OK
mutableVariable = "Change Values."
```

#### immutable
```kotlin
val immutableVariable = "This is Immutable."
// NG
immutableVariable = "Cannot Change Values." // Error Here.
```

### 静的型付け・型安全
Kotlinは型安全の静的型付け言語です。
値やオブジェクトなどの宣言の際に、型が決定しています。
ソースコードのコンパイルの際に解析され、型について検証が行われます。
実際には、IDEで適宜リンターが指摘をしてくれるので、コンパイル時よりも早く型の間違いに気づくことはできますが、いずれにせよコンパイルされたあと、実行時に型に関するエラーは起きないということが担保されているというメリットがあります。

### 型推論
静的型付け言語である一方で、
```kotlin
var mutableVariable = "This is Mutable."
```
こういった書き方が許されている(推奨されている)のがKotlinです。
これは、静的解析機が自動で代入される値を元に、`mutableVariable`の型を推論して決定しているから実現しています。

# 条件文・条件式
## 条件文
if文やwhen文で処理の切り分けができます。
### if文
```kotlin
if (a > 5) {
    print("")
} else {
    print("")
}
```

### when文
```kotlin
when {
    tmp > 0 -> print("$tmp is greater than 0")
    tmp == 0 -> print("$tmp is zero")
    else -> print("$tmp is negative value.")
}

when(num) {
    // numの値によって挙動を変える
    0 -> print("zero.")
    1..100 -> print("1 ~ 100")
    else -> print("other value.")
}
```

## 条件式
if式やwhen式で値の代入ができます。
### if式
```kotlin
val isAdult = if (age >= 18) true else false
```

### when式
```kotlin
val grade = when(point) {
    0..50 -> "不可"
    51..60 -> "可"
    61..80 -> "良"
    81..90 -> "優"
    91..100 -> "秀"
    else -> "Error"
}
```

> 練習
> https://pl.kotl.in/eVtHnqkio?theme=darcula


# 関数
```kotlin
private fun addOne(before: Int): Int {
    return before + 1
}
```
| 部分          | 何してる     | 備考                                       |
| :------------ | :----------- | :----------------------------------------- |
| private       | 可視性修飾子 | 詳細は後述                                 |
| fun           | 関数宣言     |                                            |
| addOne        | 関数名       |                                            |
| (before: Int) | 引数         | スコープ内ではbeforeで引数にアクセスする。 |
| Int           | 返り値型     | 整数型を返す宣言                           |

---
## 可視性修飾子(参考)
| 修飾子    | 範囲                                               |
| :-------- | :------------------------------------------------- |
| private   | 定義されたスコープ内でのみ呼び出せる               |
| protected | 定義されたスコープとそのサブクラスのみ呼び出し可能 |
| internal  | 定義されたモジュール内で呼び出し可能               |
| public    | デフォルト。どこからでも呼び出せる。               |

## 関数内での変数定義
```kotlin
private fun addTwo(before: Int): Int {
    val two = 2 // この関数内のみがスコープ。外からは見えない。
    return before + two
}
```
## デフォルト引数
```kotlin
private fun addValues( 
    a: Int, 
    b: Int = 1 // 外部からbに値を書き込むこともできるが、何も書かないとbは1として計算される
): Int { // この関数は引数に"1つ"または"2つ"の整数値があればOK.
    return a + b
}
```

## 返り値がない関数
次の関数を考えてみよう。
```kotlin
private fun doSomething(arg: String) {
    print(arg)
}
```
これはJavaではこのように書かれていたものとほぼ同じ
```java
private void doSomething(String arg) {
    System.out.println(arg)
}
```
実は、Kotlinでは返り値の型を省略すると、Javaのように`void`ではなく`Unit`という型が返ることになっています。試してみましょう。

> 練習問題
> https://pl.kotl.in/XBMpUAvId?theme=darcula