# AndroidManifestについて
> 参考 https://developer.android.com/guide/topics/manifest/manifest-intro?hl=ja

すべてのアプリプロジェクトでは、プロジェクトソースセットのルートに AndroidManifest.xmlファイルを（このままの名前で）配置する必要があります。 マニフェストファイルは、アプリに関する重要な情報を Androidビルドツール、Androidオペレーティングシステム、Google Play に対して説明するものです。

特に、マニフェスト ファイルでは次の情報を宣言する必要があります。

- アプリのパッケージ名。通常は、コードの名前空間と一致します。 Android ビルドツールは、プロジェクトをビルドするときに、これを使用してコードエンティティの場所を特定します。 アプリをパッケージ化する際に、ビルドツールはこの値を Gradleビルドファイル内のアプリケーションID に置き換えます。これが、システムとGoogle Playで一意のアプリ識別子として使用されます。 
- アプリのコンポーネント（すべてのアクティビティ、サービス、ブロードキャスト レシーバ、コンテンツプロバイダ）。 各コンポーネントでは、Kotlinクラスや Javaクラスの名前など、基本的なプロパティを定義する必要があります。 また、処理できる端末設定や、コンポーネントの開始方法を記述したインテント フィルタなど、機能を宣言することもできます。 
- システムや他のアプリの保護された部分にアクセスするためにアプリが必要とするパーミッション。 また、他のアプリがこのアプリのコンテンツにアクセスする場合に必要なパーミッションも宣言します。 

AndroidManifestファイルは下のような構成になっています。

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.myapplication">
    
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MyApplication">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

今回扱うアプリケーションはエンハンス体験で扱うものも含めてActivityが一つだけです。ですので、<application></application>の中に含まれるActivityも一つですが、アプリケーションの機能によっては複数のActivityになることがあります。その際は、この中に並列して示すことで、複数のActivityが同一のアプリケーションであることをビルドツールやOSに伝えることができます。

<activity></activity>の中にはintent-filterがあります。
Intentとは異なるコンポーネント(Activityなどのこと)間で実行時バインディングを付与するオブジェクトです。Intent自体は非常に使い道が多いオブジェクトです。例えば、Activityを起動するときや、他のActivityを呼び出すときなどに使われます。

ここで書かれているintent-filterは
- 通常起動された場合に`MainActivity`が立ち上がること
- Launcherから起動ができること

を表しています。

## permissionの設定
> 参考 https://developer.android.com/guide/topics/permissions/overview?hl=ja
アプリの権限は、以下に対するアクセスを保護してユーザーのプライバシーをサポートするのに役立ちます。

- 制限付きデータ（システム状態やユーザーの連絡先情報など）。
- 制限付きアクション（ペア設定したデバイスへの接続や録音など）。


## Appendix
Intentの他の使い方の例を列挙しておきます。興味があれば詳しく調べてみてください。
- バックグラウンドで何らかの処理をし続けたい。
  - Serviceを利用したいとき
  ※ 通常Activityは画面に描画されていない状態でシステムRAMが逼迫すると強制的にkillされるため、バックグラウンドにいるときは常にkillのリスクがある。そこで、Service(ユーザーと対話しないが、長時間動かしたい時などに用いられるアプリケーションコンポーネント)を用いることがある。
- 時間指定で何らかの処理をしたい。
  - BroadcastRecieverを利用したいとき
  ※ AlermManagerなどによって、画面描画されていない(場合によってはkillされている)アプリケーションに関する通知を飛ばしたいという要望があるときなどに用いることがある。