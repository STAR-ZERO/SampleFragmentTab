package com.zero.star.sample.fragmenttab;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

/**
 * TabHostを使ったサンプルActivity.
 *
 */
public class TabHostActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

    /** TabHost */
    private TabHost mTabHost;

    /** 直近で選択されているタブ情報 */
    private TabInfo mLastTabInfo;

    /** タブTagとタブ情報のMap */
    private Map<String, TabInfo> mMapTaInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);

        initTabs();
    }

    @Override
    public void onBackPressed() {
        if (!getCurrentFragment().popBackStack()) {
            // タブ内FragmentのBackStackがない場合は終了
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }

    @Override
    public void onTabChanged(String tabId) {
        TabInfo newTab = mMapTaInfo.get(tabId);

        if (mLastTabInfo != newTab) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (mLastTabInfo != null) {
                fragmentTransaction.detach(mLastTabInfo.fragment);
            }
            if (newTab.fragment == null) {
                newTab.fragment = createTabRootFragment(newTab);
                fragmentTransaction.add(R.id.realtabcontent, newTab.fragment);
            } else {
                fragmentTransaction.attach(newTab.fragment);
            }

            mLastTabInfo = newTab;

            fragmentTransaction.commit();
        }
    }

    /**
     * タブ初期化
     */
    private void initTabs() {

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mMapTaInfo = new HashMap<String, TabInfo>();

        addTab(new TabInfo("tab1", "TAB1", Tab1Child1Fragment.class.getName()));
        addTab(new TabInfo("tab2", "TAB2", Tab2Child1Fragment.class.getName()));

        // タブ変更時イベントハンドラ
        mTabHost.setOnTabChangedListener(this);

        // 初期タブ選択
        onTabChanged("tab1");

    }

    /**
     * タブ追加
     * @param tabInfo タブ情報
     */
    private void addTab(TabInfo tabInfo) {

        mMapTaInfo.put(tabInfo.tag, tabInfo);

        TabSpec tabSpec = mTabHost
                .newTabSpec(tabInfo.tag)
                .setIndicator(tabInfo.label)
                .setContent(new DummyTabFactory(this));

        mTabHost.addTab(tabSpec);

    }

    /**
     * 各タブのルートとなるFragment生成
     * @param tabInfo タブ情報
     * @return Fragment
     */
    private Fragment createTabRootFragment(TabInfo tabInfo) {

        // ルートFragmentで初期表示するFragmentクラス名を引数にする
        Bundle args = new Bundle();
        args.putString("root", tabInfo.className);

        TabRootFragment fragment = new TabRootFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * カレントFragment取得.
     * @return TabRootFragment
     */
    private TabRootFragment getCurrentFragment() {
        return (TabRootFragment) getSupportFragmentManager().findFragmentById(R.id.realtabcontent);
    }

    /**
     * android:id/tabcontent のダミーコンテンツ
     *
     */
    private static class DummyTabFactory implements TabContentFactory {

        /** Context */
        private final Context mContext;

        /**
         * コンストラクタ
         * @param context Context
         */
        DummyTabFactory(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            return v;
        }

    }

    /**
     * タブ情報
     *
     */
    private class TabInfo {

        /** タグ */
        private String tag;
        /** タブラベル */
        private String label;
        /** タブ初期Fragmentクラス名 */
        private String className;
        /** ルートFragmentインスタンス */
        private Fragment fragment;

        /**
         * コンストラクタ
         * @param tag タグ
         * @param lable タブラベル
         * @param className タブ初期Fragmentクラス名
         */
        TabInfo(String tag, String lable, String className) {
            this.tag = tag;
            this.label = lable;
            this.className = className;
        }
    }

}
