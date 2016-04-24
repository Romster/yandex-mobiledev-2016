package ru.romster.yandexpop.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import ru.romster.yandexpop.R;
import ru.romster.yandexpop.data.DataSource;
import ru.romster.yandexpop.data.model.PopItem;
import ru.romster.yandexpop.util.Utils;


public class PopInfoFragment extends Fragment {

	private static String ARG_POP_ID = "pop_idx";

	private PopItem popItem;

	private TextView genreTV;
	private TextView statTV;
	private TextView bioTV;
	private ImageView coverIV;

	private RequestQueue imageRequestQueue;

	public static PopInfoFragment newInstance(int popItemIndex) {
		Bundle args = new Bundle();
		args.putInt(ARG_POP_ID, popItemIndex);

		PopInfoFragment fragment = new PopInfoFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int popId = getArguments().getInt(ARG_POP_ID);
		popItem = DataSource.getInstance(getActivity()).findById(popId);
		getActivity().setTitle(popItem.getName());
		imageRequestQueue = Volley.newRequestQueue(getActivity());

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_pop_info, null);

		bioTV = (TextView) v.findViewById(R.id.artist_biography);
		bioTV.setText(popItem.getBiography());

		genreTV = (TextView) v.findViewById(R.id.artist_genres);
		genreTV.setText(Utils.collectionToString(popItem.getGenres()));

		statTV = (TextView) v.findViewById(R.id.artist_stats);
		String statString = getString(R.string.musician_stats, popItem.getAlbumsCount(), popItem.getTrackCount());
		statTV.setText(statString);

		coverIV = (ImageView) v.findViewById(R.id.artist_cover);
		final ImageRequest imageRequest = new ImageRequest(popItem.getUrlImageBig(),
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						coverIV.setImageBitmap(response);
					}
				},
				1000, 1000, ImageView.ScaleType.CENTER, null,
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
					}
				});
		imageRequestQueue.add(imageRequest);
		return v;
	}
}
