package com.zero.star.sample.fragmenttab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 各タブ共通のFragment.
 *
 * このFragment内のFragmentを切り替えてタブ内での画面遷移を実現
 * 初期Fragmentの設定、BackStackの管理を行う
 */
public class TabRootFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_root, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 引数から初期のFragmentを取得
        Bundle args = getArguments();
        String rootClass = args.getString("root");

        // 初期Fragmentを設定
        initFragment(rootClass);

    }

    /**
     * BackStack取り出し.
     * BackStackがあった場合はtrue, なかった場合はfalseを返す
     *
     * @return true:BackStackあり false:BackStackなし
     */
    public boolean popBackStack() {
        return getChildFragmentManager().popBackStackImmediate();
    }

    /**
     * BackStackをクリア
     */
    public void clearBackStack() {
        getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * 初期Fragmentを設定.
     * @param rootClass 初期Fragmentクラス名
     */
    private void initFragment(String rootClass) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = Fragment.instantiate(getActivity(), rootClass);
        fragmentTransaction.replace(R.id.fragment, fragment);

        fragmentTransaction.commit();
    }
}
