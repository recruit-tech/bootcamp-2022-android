## 問題
- 一覧画面だけだと味気ない。より具体的な情報が知りたい
- そのユーザーの情報をより詳しく知るためのページを作成しよう。

## 要件
1. 詳細用の新しいFragmentを作成してください。
2. Fragmentでは、`https://api.github.com/users/{username}`の情報を表示します。下記の情報を表示するようにしてください。
  - login (※usernameのことです)
  - avater_url → アバターの画像に変換して表示してください
  - type
  - blog
  - hireable
  - public_repos
  - public_gists
  - created_at
3. 一覧のカセットをタップすると詳細のFragmentに飛ぶようにしてください。

## ヒント
1. ResultsFragment(とその周り)が参考になります。
2. Retrofitの使い方が参考になります。
  - まずは`GithubAccessService`,`GithubAccessRepository`を見てみましょう。
  - https://square.github.io/retrofit/
  - https://developer.android.com/courses/pathways/android-basics-kotlin-unit-4-pathway-2

## 学び
- 画面一枚を自分で作れるようになる。
- 遷移の方法を学ぶ。
- Retrofitの使い方と通信結果をViewに反映させる方法を学ぶ。

## 実際の案件
画面遷移や、通信関連は頻度は高くないものの、重要度の高い案件として登場します。
たとえば、新しくレコメンドを表出したいという案件で通信周りが必要になったり、キャンペーンを実施するので、そのために新しいページを追加したりみたいなことがありました。