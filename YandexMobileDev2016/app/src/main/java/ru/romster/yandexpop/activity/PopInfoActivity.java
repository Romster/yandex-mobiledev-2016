package ru.romster.yandexpop.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ru.romster.yandexpop.fragment.PopInfoFragment;

/**
 * Created by romster on 24/04/16.
 */
public class PopInfoActivity  extends AbstractFragmentActivity{

	public static final String EXTRA_ITEM_ID = "ru.romster.yandexpop.item_id";

	public static Intent createIntent(Context context, int popItemId) {
		Intent intent = new Intent(context, PopInfoActivity.class);
		intent.putExtra(EXTRA_ITEM_ID, popItemId);
		return intent;
	}

	@Override
	protected Fragment createFragment() {
		Integer index = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);
		return PopInfoFragment.newInstance(index);
	}
}
