package ru.romster.yandexpop.activity;

import android.support.v4.app.Fragment;

import ru.romster.yandexpop.fragment.PopListFragment;

public class PopListActivity extends AbstractFragmentActivity {


	@Override
	protected Fragment createFragment() {
		return new PopListFragment();
	}

}
