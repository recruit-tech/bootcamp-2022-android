# build.gradle
Androidアプリのビルドや、ライブラリの依存関係を明記するファイルです。アプリ全体とモジュールに対してで、それぞれ別のファイルを使います。

この章までで、アプリの実装からローカル環境での実行までが意図を持ってできるようになります。

## ビルドプロセス
何よりまずは公式ソース
> https://developer.android.com/studio/build

### ビルドとは
![image](https://developer.android.com/images/tools/studio/build-process_2x.png?hl=ja)
- ソースコード
- リソースファイル
- 依存関係

などをコンパイルした上で、DEX(Dalvik Executable)形式の形式のファイルに変換し、APK(Android Application Package)の形に格納すること。

### DEXとは
Androidは独自のランタイムを持っています。古くはDalvikというランタイムでしたが、今はART(Android Runtime)が使われています。
Javaで書かれたコードもKotlinで書かれたコードも一度`.class`ファイルにコンパイルされた後、Android実行用に最適化・難読化されてDalvik Executableという形式に変換されます。APKの中には、このDEXファイルが含まれます。

## リソースの行方
リソースファイルもコンパイルされます。XMLの形式はほぼ変わらないのですが、付与したidや制約などがバイナリ形式にダンプされています。

## 依存関係
Kotlinの標準ライブラリやサードパーティライブラリなどもコンパイルした上でDEXファイルに変換します。すべて依存関係はbuild.gradleを見てAPKに反映するため、ここで依存に追加されていない限り関連ライブラリを使用することはできません。
実際は、AndroidStudioが適宜Gradleから依存関係を収集しているため、依存関係に明記されていない場合はそもそもIDE上でエラーが吐かれているはずです。

## ARTについて
Androidの実行環境はAndroid5.0からARTに変わりました。(現在の最新のAndroidOSは12です。)
Android5系,6系は違いますが、現在使われているほぼ全ての端末では、JIT(ジャストインタイム)コンパイルと、AOT(Ahead of Time)コンパイルの２種類を使い分けています。実行時にコンパイルするのが前者で、インストール時にコンパイルするのが後者です。これらを使い分けることで、高速にアプリケーションを実行できるようにしています。
また、全身のDalvikから比較してGCもより効率的に働くようになっています。
> 詳細 https://source.android.com/devices/tech/dalvik/jit-compiler

> ARTの進化 https://www.youtube.com/watch?v=fwMM6g7wpQ8

## 書き方
### groovyとkts
buildファイルはGroovyという言語または、Kotlin scriptで書くことができます。
どちらで書いているパターンもあるみたいですが、今回はGroovyの方を採用します。(※慣れ以外の理由は持っていません)

### レベル別の`build.gradle`
- プロジェクトレベル

    プロジェクト内全てのモジュールに適用される設定を記載する。
- モジュールレベル

    各モジュールに対して適用する設定を記載する。

下記はそれぞれのブロックが何を表しているのかをコメント共に記したものです。
> 参考　https://developer.android.com/studio/build?hl=ja#module-level


```groovy
/**
* ビルド設定の最初の行は、このビルドにGradle用のAndroidプラグインを適用し、Android固有のビルドオプションを指定するためのandroidブロックを利用できるようにしています。
*/
plugins {
  id 'com.android.application'
}

/**
 * android ブロックは、Android 固有のすべての設定を行う場所です。ビルドオプションを指定します。
 */

android {

    /**
     * compileSdkVersion は、Gradle がアプリのコンパイルに使用する Android API レベルを指定します。これは、アプリがこの API レベル以下に含まれる API 機能を使用できることを意味します。
     */

    compileSdkVersion 28


    /**
     * defaultConfig ブロックは、すべてのビルド・バリアントのデフォルト設定とエントリをカプセル化し、ビルド・システムから動的に main/AndroidManifest.xml の一部の属性をオーバーライドすることが可能です。アプリの異なるバージョンに対してこれらの値を上書きするように、プロダクトフレーバーを設定することができます。
     */

    defaultConfig {


        applicationId 'com.example.myapp'

        // アプリの実行に必要な最小限のAPIレベルを定義します。
         minSdkVersion 15

        // アプリのテストに使用するAPIレベルを指定します。
        targetSdkVersion 28

        // アプリのバージョン番号を定義します。
        versionCode 1

        // アプリのユーザーフレンドリーなバージョン名を定義します。
        versionName "1.0"
    }

    /**
     * buildTypesブロックでは、複数のビルドタイプを設定することができます。デフォルトでは、ビルドシステムは2つのビルドタイプを定義しています：debugとreleaseです。デバッグビルドタイプは、デフォルトのビルド構成では明示的に表示されませんが、デバッグツールを含み、デバッグキーで署名されます。releaseビルドタイプは、Proguardの設定を適用し、デフォルトでは署名されていません。
     */

    buildTypes {

        /**
         * デフォルトでは、Android Studioは、minifyEnabledを使用して、コードの縮小化が有効になるようにリリースビルドタイプを構成し、デフォルトのProguardルールファイルを指定します。
         */

        release {
              minifyEnabled true 
              // リリースビルドタイプでコードの縮小化を有効にします。
              proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }

    /**
     * productFlavorsブロックでは、複数のプロダクトフレーバーを設定することができます。これにより、defaultConfigブロックを独自の設定でオーバーライドできる、異なるバージョンのアプリを作成できます。プロダクトフレーバーは任意であり、ビルドシステムはデフォルトでこれらを作成しません。
     
     この例では、無料と有料のプロダクトフレーバーを作成しています。そして、各プロダクトフレーバーは、Google Play StoreまたはAndroidデバイスに同時に存在できるように、独自のアプリケーションIDを指定します。プロダクトフレーバーを宣言する場合、フレーバーディメンジョンも宣言し、各フレーバーをフレーバーディメンジョンに割り当てる必要があります。
     */

    flavorDimensions "tier"
    productFlavors {
        free {
            dimension "tier"
            applicationId 'com.example.myapp.free'
        }

        paid {
            dimension "tier"
            applicationId 'com.example.myapp.paid'
        }
    }
}

/**
 *  モジュールレベルのビルド設定ファイルの依存関係ブロックは、モジュール自身のみをビルドするために必要な依存関係を指定します。
 */

dependencies {
    implementation project(":lib")
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation fileTree(dir: 'libs', include: ['*.jar'])
}
```




