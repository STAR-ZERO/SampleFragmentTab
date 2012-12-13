package com.zero.star.sample.fragmenttab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * FragmentTabHostを使ったサンプルActivity.
 *
 */
public class FragmentTabHostActivity extends FragmentActivity {

    /** FragmentTabHost */
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmenttabhost);

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

    /**
     * タブ初期化
     */
    private void initTabs() {

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        addTab("tab1", "Tab1", Tab1Child1Fragment.class);
        addTab("tab2", "Tab2", Tab2Child1Fragment.class);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // タブ切り替え時にBackStackをクリア
                getCurrentFragment().clearBackStack();
            }
        });
    }

    /**
     * タブ追加.
     * @param tag タグ
     * @param indector タブウィジェット表示ラベル
     * @param clazz Fragmentクラス
     */
    private void addTab(String tag, String indector, Class<? extends Fragment> clazz) {

        TabSpec tabSpec = mTabHost.newTabSpec(tag).setIndicator(indector);

        // TabRootFragmentに渡すことでクラス名から初期Fragmentを決定
        Bundle args = new Bundle();
        args.putString("root", clazz.getName());

        mTabHost.addTab(tabSpec, TabRootFragment.class, args);

    }

    /**
     * カレントFragment取得.
     * @return TabRootFragment
     */
    private TabRootFragment getCurrentFragment() {
        return (TabRootFragment) getSupportFragmentManager().findFragmentById(R.id.realtabcontent);
    }

}
