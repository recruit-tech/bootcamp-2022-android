# クラスの種類
クラスと一概に言っても、クラスの名前を冠するものが次のようにいくつか存在します。
1. クラス
2. オープンクラス
3. データクラス
4. 列挙クラス
5. 抽象クラス 
6. シールクラス
7. オブジェクト(クラスの名は冠していない)

などです。それぞれ特長があって状況に応じて利用分けされていくので見ておきましょう。頻出度に差はありますが、いずれも重要です。

## クラス
一番基本のクラスですが、説明は省略します。
ここでは、クラスの定義の仕方とプロパティの初期化などに焦点を当てます。まずはサンプルのクラスを下に示します。
```kotlin
class User(
    _name: String,
    val username: String = "John Doe",
    private val gender: Gender = Gender.NOANSWER,
    private val birthDate: LocalDate
) {
    var name = _name
        get() = field.capitalize()
        private set(value) {
            field.lowercase()
        }
    
    val age = getAgeFromBirthDate(birthDate)

    private fun getAgeFromBirthDate(birthDate: LocalDate) : Int {
        // ageを計算する
        return age
    }
}

enum class Gender {
    NONE,
    MALE,
    FEMALE
}
```
### クラスのメンバ
クラスでは、プロパティや関数を内部で持つことができます。オブジェクトとしての実体はインスタンスを生成したタイミングでできるので、Non-nullのプロパティについては、基本的にインスタンスを呼び出した段階で代入が完了している必要があります。

Kotlinでは、プライマリコンストラクタ内で可視性の設定も含めた変数宣言をすることができます。例のように、デフォルトの引数を設定することもできます。nameプロパティのようにprivateなsetterを定義したい場合などは、クラス内で利用できる変数宣言をあえてせずに値を取得し、加工した上でクラス内で利用する形に値を加工するような使い方もできます。

プロパティageはクラス内の関数を呼び出していますが、これはインスタンスが生成されるタイミングで実行されます。したがって、ここに出ているNon-Nullの値については全てクラスが生成したタイミングで代入が完了されていることになります。

### セカンダリコンストラクタ
```kotlin
class User(
    _name: String,
    val username: String = "John Doe",
    private val gender: Gender = Gender.NOANSWER,
    private val birthDate: LocalDate
) {
    constructor(birthDate: LocalDate) {
        //ここに第二のコンストラクタを定義することができる。
    }

    var name = _name
        get() = field.capitalize()
        private set(value) {
            field.lowercase()
        }
    
    val age = getAgeFromBirthDate(birthDate)

    private fun getAgeFromBirthDate(birthDate: LocalDate) : Int {
        // ageを計算する
        return age
    }
}

enum class Gender {
    NONE,
    MALE,
    FEMALE
}
```
Javaではよく複数のコンストラクタを用意したりしますが、Kotlinでも可能です。実際に逆コンパイルしてみてもJavaでのセカンダリコンストラクタと等価な挙動をしています。


### 遅延初期化
先ほど、`基本的にインスタンスを呼び出した段階で代入が完了している必要があります。`と多少思わせぶりな書き方をしましたが、例外があります。例えば、次の場合を考えてみます。
```kotlin
class User(
    _name: String,
    val username: String = "John Doe",
    private val gender: Gender = Gender.NOANSWER,
    private val birthDate: LocalDate
) {
    var name = _name
        get() = field.capitalize()
        private set(value) {
            field.lowercase()
        }
    
    val age = getAgeFromBirthDate(birthDate)
    
    // 新しく追加したプロパティ
    val userData = getUserDataFromFile(username)

    private fun getAgeFromBirthDate(birthDate: LocalDate) : Int {
        return age
    }

    private fun getUserDataFromFile(username: String) : String {
        /*
            ファイルからデータを読み込む処理
            多分時間かかる
        */
        return output
    }
}
```
このとき、インスタンスを生成するのに時間がかかります。このファイルを使うタイミングがインスタンス生成直後ではなかったとしても、全体の処理をロックしかねません。
こういった場合はできれば初期化を遅らせたいわけです。この方法は二通り存在しています。

#### lateinit
```kotlin
class User(
    _name: String,
    val username: String = "John Doe",
    private val gender: Gender = Gender.NOANSWER,
    private val birthDate: LocalDate
) {
    var name = _name
        get() = field.capitalize()
        private set(value) {
            field.lowercase()
        }
    
    val age = getAgeFromBirthDate(birthDate)
    
    // 遅延初期化を明示する。
    lateinit var userData: String

    private fun getAgeFromBirthDate(birthDate: LocalDate): Int {
        return age
    }

    // ここでは外部から呼び出せるようにpublicに変更。
    fun getUserDataFromFile(): String {
        /*
            ファイルからデータを読み込む処理
            多分時間かかる
        */
        this.userData = output
    }
}
```
`lateinit`をつけることで、後で初期化することを許容されます。ただし、実際にこのプロパティを使う前には初期化されている必要があります。忘れていると例外が発生し、クラッシュの原因になるので要注意です。

#### by lazy
```kotlin
class User(
    _name: String,
    val username: String = "John Doe",
    private val gender: Gender = Gender.NOANSWER,
    private val birthDate: LocalDate
) {
    var name = _name
        get() = field.capitalize()
        private set(value) {
            field.lowercase()
        }
    
    val age = getAgeFromBirthDate(birthDate)
    
    // by lazyにする
    val userData by lazy { getUserDataFromFile(username) }

    private fun getAgeFromBirthDate(birthDate: LocalDate) : Int {
        return age
    }

    private fun getUserDataFromFile(username: String) : String {
        /*
            ファイルからデータを読み込む処理
            多分時間かかる
        */
        return output
    }
}
```
`by lazy`キーワードをつけることで、userDataが使用されるタイミングで初めて初期化をすることになります。なので、インスタンス生成時にはプロパティは登録されていませんが、安全に後から呼び出すことができるようになります。


## オープンクラス
Kotlinでは、クラスの継承がデフォルトではできません。
そのクラスが明示的に継承可能であることを示す必要があります。
その方法のうちの一つが`open`キーワードです。
`open`キーワードを付与したクラスは継承可能になります。あくまで継承可能というだけなので、後述する抽象クラスのように、継承が必須というわけではありません。オープンクラスで定義されたクラスのインスタンスを生成することが可能です。

## データクラス
値の保持するだけのクラスを使いたいときがあります。例えば、APIから返却されたデータでどのようなデータが含まれるか事前にわかっているものや、DBに与えたりDBから返却されたりするデータセットのことです。その場合は、下のような書き方でデータクラスを定義することができます。
```kotlin
data class(val name: String, val age: Int)
```
データクラスの良さは、データクラスで定義をすると、`equals()`や`copy()`などという関数を追加の実装なく利用できるようになるところにあります。

## 列挙クラス
先ほどクラスの例に挙げたような列挙クラス(enum)についてです。
```kotlin
enum class UserLanguage {
    ENGLISH,
    SPANISH,
    OTHERS
}
```
状態として有限のパターンしか持たないことがわかっている場合、または意図的に有限にしたい場合などに列挙クラスは有効です。ここでは、Genderを３種類に設定していますが、
こうすることによって、when節を明確に切り分けることができます。
```kotlin
fun main() {
    val userLanguage: UserLanguage = ENGLISH
    when(userLanguage) {
        ENGLISH -> print("Please wait a moment.")
        SPANISH -> print("Por favor, espere un momento.")
        OTHERS -> print("Not supported languages.")
    }
}
```
のように、列挙されたもの以外のデータが存在しないので、`else`が不要になります。

## 抽象クラス
抽象クラスはそれ自体ではインスタンスを生成することができないクラスです。
抽象クラスは中に抽象関数を持たせることができます。インターフェイスと類似点があるので、詳細は後述します。


## オブジェクト
オブジェクトはシングルトンです。
クラスのインスタンスは新しく作成するたびに、別の実体が生成されています。
だからこそ、
```kotlin
val taro = Human("Taro", 20)
val hanako = Human("Hanako", 22)
```
のように、複数の実体を作ることができるのですが、場合によっては実体が分かれてほしくない時があります。そういうときに使用されるのが『シングルトン』です。Kotlinでは`object`キーワードでクラス宣言をすると、シングルトンとして認識されます。クラス実体そのものをシングルトンにすることもあるのですが、ここでは`companion object`について説明をします。

### companion object
クラスの中で定数を持ちたいときなど、クラスに紐づいたシングルトンのデータを持ちたいときに有効な方法です。テストクラスの中でのみ使われる関数などもよくcompanion objectで閉じたりしています。
```kotlin
class Car {
    companion object {
        val discription = "Car is a vehicle"
    }
}
```
アクセスには少し注意が必要です。
```kotlin
val carInstance = Car()
print(carInstance.discription) // NG
print(Car.discription) // OK
```

### companion objectの使い道
先のアクセス例からもわかるように、インスタンスに対して紐づいている値ではないので、いくつCarのインスタンスを生成しても、discriptionの向き先は変わらないということになります。
したがって、特定の機能を持った関数をまとめたいという要望がある場合や、定数をまとめて保持しておきたいという意図がある場合についてはcompanion objectの利用を検討するべきでしょう。


> 練習問題
> https://pl.kotl.in/3DP_amtKu?theme=darcula


# Appendix
## シールクラス
Kotlinは列挙型に似た`sealed class`が存在します。`enum`が値の列挙であったのに対して、`sealed class`は具体的なクラスの列挙と言えます。少し例を見てみます。
```kotlin
sealed class VisibleCassette

class AdCassette(
    private val adId: Long
): VisibleCassette()

class SearchButtonCassette(
    private val searchMethod: SearchMethod
): VisibleCassette()

class ResultItemCassette(
    private val userItem: UserInformation
): VisibleCassette()

/** 以下Usage */
fun getItem(position: Int): View {
    val visibleCassette: VisibleCassette = getVisibleCassette(positon)
    when(visibleCassette) {
        is AdCassette -> visibleCassette.getProperAds()
        is SearchButtonCassette -> visibleCassette.getProperButton()
        is ResultItemCassette -> visibleCassette.getItemView()
    }
}
```
上記の例のように、`sealed class`にもどんなオブジェクトを持っているかを明示的にする機能があります。また、`sealed class`とこの継承は同一ファイル内からでしかできないため、検索性にも優れています。`enum`が値しか持てなかったことにより制限されていたことがありましたが、`sealed class`ではより高機能なことができると言えます。
