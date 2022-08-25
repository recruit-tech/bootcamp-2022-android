# コレクション型
要素の集合体としてCollectionという型が存在します。
CollectionはIterableを継承しているので、for-loopなどが実装可能です。また、MutableとImmutableが型として明確に分かれています。
![コレクション](https://kotlinlang.org/docs/images/collections-diagram.png)

コレクションとしては、List,Set,MapをKotlinは持っていますが、上の図のように、Mapだけは継承しているルートが他の二つと異なります。

## List型
Listは指定された順序で要素を保存し、インデックスによるアクセスを提供します。

## Set型
Setは一意な要素を格納します。その順序は一般に不定です。2つの集合が同じ大きさであり、ある集合の各要素に対して、もう一方の集合に同じ要素がある場合、それらは等しいと言えます。

## Map型
`Map<K, V>`はCollectionインターフェイスを継承していませんが、Kotlinのコレクション型です。Mapはキーと値のペア（またはエントリ）を格納します。キーは一意である必要がありますが、異なるキーは同じ値でペアにすることができます。Mapインターフェースは、キーによる値へのアクセス、キーと値の検索など、特定の機能を提供します。

## MutableとImmutable
### List
```kotlin
val sampleList: List<String> = listOf("Apple", "Google")
```
このsampleListは読み出ししかできません。実際に下記のメソッドしか存在しません。
```kotlin
override val size: Int
override fun isEmpty(): Boolean
override fun contains(element: @UnsafeVariance E): Boolean
override fun iterator(): Iterator<E>
override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean
public operator fun get(index: Int): E
public fun indexOf(element: @UnsafeVariance E): Int
public fun lastIndexOf(element: @UnsafeVariance E): Int
public fun listIterator(): ListIterator<E>
public fun listIterator(index: Int): ListIterator<E>
public fun subList(fromIndex: Int, toIndex: Int): List<E>
```

### MutableList
可変なリストには、次のような定義をします。
```kotlin
val sampleMutableList: MutableList<String> = mutableListOf("Apple", "Google")
```
こちらには、List型が持っていたメソッド以外にも、
```kotlin
override fun add(element: E): Boolean
override fun remove(element: E): Boolean
```
などのリストが保持しているコレクションに対する追加や削除ができるようになっています。面白いのが、`sampleMutableList`自体は`val`で定義している通り不変な変数ですが、リストの中身は可変になっています。つまり、`sampleMutableList.add("Facebook")`は成功します。


### 繰り返し処理
コレクション型は全てIterableを継承しているので、次のような操作ができます。
#### for文を使う
```kotlin
for (item in sampleList) {
    print(item)
}
```
#### forEachを使う
```kotlin
sampleList.forEach { item ->
    print(item)
}
```

### Set
SetについてもListとほぼ同じです。
SetとMutableSetが存在します。

### Map
MapについてもMapとMutableMapが存在します。
継承元が違うため、List,Setと同様と片づけてしまうのは困難です。
しかし、MutableとImmutableについてはList, Setと同様だと考えてください。

> 練習問題
> https://pl.kotl.in/GJyPaRNA8?theme=darcula