# Androidのライフサイクル
アプリで何かの処理を発火したいときに、いくつかの手法があります。例を挙げるとすると、例えば以下の通りです。
1. タップイベントなど、イベントをきっかけにする。
2. ActivityやFragmentの生成・破壊のタイミングをきっかけにする。
3. 他のアプリや別のプロセスからの呼び出しをきっかけにする。

特によく使われるのが、1と2です。
1章で1については触れたので、ここで2について触れます。

この章が終われば、『Viewが作成できる』『Viewの要素に変更を加えられる』ようになります。

## ライフサイクル
ActivityやFragmentには状態があり、その状態が変化した際にライフサイクルのコールバックが呼ばれます。
データを渡したり、表示する要素の初期値を代入したりするのに、そのライフサイクルのコールバックメソッドを使うのですが、
そのためには要素に『どういった状態が存在するのか』と『どんな』コールバックメソッドが『いつ』呼ばれるのかを把握しておく必要があります。

この辺りは公式のソースが非常にわかりやすいので、図だけ転載した上でURLを記載しておきます。

> https://developer.android.com/topic/libraries/architecture/lifecycle?hl=ja#lc

![コンポーネントの状態](https://developer.android.com/images/topic/libraries/architecture/lifecycle-states.svg?hl=ja)

## Activityのライフサイクル
> https://developer.android.com/guide/components/activities/activity-lifecycle?hl=ja

![Activityのライフサイクル](https://developer.android.com/guide/components/images/activity_lifecycle.png?hl=ja)

## :computer: ハンズオン
画面共有しながら、ライフサイクルとコールバックを見てみる。

## Fragmentのライフサイクル
> https://developer.android.com/guide/fragments/lifecycle?hl=ja

![Fragmentのライフサイクル](https://developer.android.com/images/guide/fragments/fragment-view-lifecycle.png?hl=ja)

