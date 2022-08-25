# これまでに使っていない技術について
## LiveData
LiveData は監視可能なデータホルダー クラスです。
通常の監視とは異なり、LiveData はライフサイクルに応じた監視が可能です。
つまり、アクティビティ、フラグメント、サービスなどの他のアプリ コンポーネントのライフサイクルが考慮されます。
このため、LiveData はライフサイクルの状態がアクティブなアプリ コンポーネント オブザーバーのみを更新します。

> 参考 https://developer.android.com/topic/libraries/architecture/livedata?hl=ja#work_livedata

> 参考 [LiveDataを利用する利点](https://developer.android.com/topic/libraries/architecture/livedata?hl=ja#the_advantages_of_using_livedata)

### 簡単な使用例
@Fragment
```kotlin
viewModel.dataSet.observe(viewLifecycleOwner) {
    // doSomething
}
```

@ViewModel
```kotlin
// 通信を伴うものは、IO用のThreadに任せるためDispatchers.IOを利用する。
var dataSet: LiveData<List<String>> = liveData(Dispatchers.IO) {
        // 中断関数を用いる時は、emit()を使う。
        emit(repository.getData() ?: listOf())
    }
```

## RecyclerView
RecyclerViewはリスト要素を表示するためのViewコンポーネントです。
"Recycler"の由来は、"リサイクル"で、RecyclerViewの上に乗っているViewが画面外に出ても、それ自身を破棄せず再利用するというところからきています。

RecyclerViewは実際に描画するために、以下の実装・リソースを必要とします。
1. 表示するそれぞれのViewそのもの(カセット、リスト要素)とその要素を定義するViewHolder
2. 表示内容をViewHolderと紐付けるAdapter
3. 全体のレイアウトの方針を司るLayoutManager

実際の実装をみてみましょう。

@Fragment
```kotlin
recyclerView.adapter = SampleAdapter()
recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
```

@SampleAdapter
```kotlin
class SampleAdapter(
    private var dataSet: List<String>
) : RecyclerView.Adapter<SampleAdapter.ViewHolder>() {
    

    /**
     * ViewHolderクラス
     * ここでしか使わないので、inner classで指定している
    */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textview)
    }

    /**
    * Adapterクラスのメソッド
    *
    * RecyclerViewに表示させるViewをinflateしてViewHolderに入れる
    * override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
    *
    * ViewHolderに情報を紐づける
    * override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    *
    * RecyclerViewで表示する最大の要素数を指定する。
    * override fun getItemCount(): Int
    *
    */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item , viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView = dataSet[position]
    }

    override fun getItemCount() = dataSet.size
}
```

@res/layout/list_item.xml

// 省略

以上がSimpleなRecyclerViewの使い方です。
より詳しくは、以下の参考を見てみてください。

> 参考 https://developer.android.com/guide/topics/ui/layout/recyclerview?hl=ja

> 参考 https://developer.android.com/guide/topics/ui/layout/recyclerview-custom?hl=ja

> 参考 https://www.youtube.com/watch?v=zdQRIYOST64