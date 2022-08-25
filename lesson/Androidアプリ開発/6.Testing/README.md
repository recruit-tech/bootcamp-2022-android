## :computer: ハンズオン
1. 新しいクラスを作る
2. addValues関数を作る
3. GenerateからTestを作る
4. Testケースを書いて実行する


# テストについて
公式に勝るドキュメントなし
> https://developer.android.com/training/testing/fundamentals?hl=ja
> https://developer.android.com/training/testing/fundamentals?hl=en

(日本語と英語で内容が違います。英語の方が最新です。)

ここでは、英語版の記事の和訳を掲載します。

## Androidにおけるテストの種類

モバイルアプリケーションは複雑で、多くの環境でうまく動作する必要があります。そのため、様々な種類のテストが存在します。


### テスト対象
例えば、テストには対象によって様々な種類があります。

- 機能テスト
    - アプリが想定通りに動作しているか？
- パフォーマンステスト
    - アプリが迅速かつ効率的に動作するか？
- アクセシビリティ・テスト
    - アクセシビリティ・サービスとうまく連動しているか？
- 互換性テスト
    - あらゆるデバイスとAPIレベルでうまく動作するか？


### テストスコープ

また、テストはサイズ、つまり分離の度合いによって変化します。

- Unit test/Small test
    - メソッドやクラスなど、アプリのごく一部を検証する
- End to end test/Big test
    - 画面全体やユーザーフローなど、アプリのより大きな部分を同時に検証する。
- Integration test
    - 2つ以上のユニット間の統合をチェックする。

![image](https://developer.android.com/training/testing/fundamentals/test-scopes.png)

テストを分類する方法はたくさんあります。しかし、アプリ開発者にとって最も重要な区別は、テストがどこで実行されるかということです。

### InstrumentedテストとLocalテスト

テストは、Android端末または他のコンピュータで実行することができます。

- Instrumented test
    - 物理的またはエミュレートされた Android デバイス上で実行されます。アプリは、コマンドを注入し、状態を読み取るテストアプリと一緒にビルドされ、インストールされます。Instrumentedテストは通常UIテストであり、アプリを起動し、そのアプリと対話します。
    
- local test
    - 開発マシンまたはサーバー上で実行されるため、ホストサイドテストとも呼ばれます。通常、テスト対象はアプリの他の部分から分離され、小規模かつ高速に実行されます。

![image](https://developer.android.com/training/testing/fundamentals/instru-vs-local.png)

