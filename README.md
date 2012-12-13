# Fragment + TabHostのサンプル

## 概要

TabActivityがdeprecatedになったので、それに変わるものを作りたかった  

ActivityとTabRootFragmentでタブ制御自体は完結してるので、タブ内に表示するFragmentはそんなに意識せず使えると思う（たぶん…）


## FragmentTabHostを使った場合

#### できること

* タブ内でのFragment切り替え

#### できないこと
* タブ切り替えた時の状態保持
* TabWidgetの表示位置変更（まだ詳しくは調査していない）

## TabHostを使った場合

#### できること

* タブ内でのFragment切り替え
* タブ切り替えた時の状態保持
* TabWidgetの表示位置変更

#### できないこと

* ほぼ前のTabActivityと同様のことはできると思う


## 使い方
AndroidManifest.xml の Activity の箇所を変更して試してください

* com.zero.star.sample.fragmenttab.FragmentTabHostActivity
* com.zero.star.sample.fragmenttab.TabHostActivity

## 注意点
まだそんなに動かしてないです…