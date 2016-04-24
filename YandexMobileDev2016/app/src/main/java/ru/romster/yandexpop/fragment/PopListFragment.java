package ru.romster.yandexpop.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import ru.romster.yandexpop.ApplicationSettings;
import ru.romster.yandexpop.R;
import ru.romster.yandexpop.activity.PopInfoActivity;
import ru.romster.yandexpop.data.DataSource;
import ru.romster.yandexpop.data.model.PopItem;
import ru.romster.yandexpop.util.Utils;

/**
 * Created by romster on 24/04/16.
 */
public class PopListFragment extends Fragment {

	private RecyclerView recyclerView;
	private PopListAdapter recycleAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.musicians);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
	                         ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_pop_list, null);
		recyclerView = (RecyclerView) rootView.findViewById(R.id.pop_recycle_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		updateUi();
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.pop_list_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_refresh: {
				final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
				String url = ApplicationSettings.getInstance().getDataUrl();
				JsonArrayRequest arrayRequest = new JsonArrayRequest(url,
						new Response.Listener<JSONArray>() {
							@Override
							public void onResponse(JSONArray response) {
								recycleAdapter.setDataAsJsonArray(response);
								updateUi();
							}
						},
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
							}
						});
				requestQueue.add(arrayRequest);
				return true;
			}
			default: {
				return super.onOptionsItemSelected(item);
			}
		}
	}

	private void updateUi() {
		if (recycleAdapter == null) {
			recycleAdapter = new PopListAdapter(DataSource.getInstance(getActivity()));
			recyclerView.setAdapter(recycleAdapter);
		} else {
			recycleAdapter.notifyDataSetChanged();
		}
	}


	private class PopHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private RequestQueue imageRequestQueue = Volley.newRequestQueue(getActivity());

		private ImageView image;
		private TextView titleTV;
		private TextView genresTV;
		private TextView infoTV;

		private PopItem popItem;

		public PopHolder(View itemView) {
			super(itemView);
			image = (ImageView) itemView.findViewById(R.id.pop_item_image_iv);
			titleTV = (TextView) itemView.findViewById(R.id.pop_item_title_tv);
			genresTV = (TextView) itemView.findViewById(R.id.pop_item_genre_tv);
			infoTV = (TextView) itemView.findViewById(R.id.pop_item_info_tv);
			itemView.setOnClickListener(this);
		}

		private void bind(PopItem popItem) {
			this.popItem = popItem;
			titleTV.setText(popItem.getName());
			genresTV.setText(Utils.collectionToString(popItem.getGenres()));
			infoTV.setText(getString(R.string.musician_stats, popItem.getAlbumsCount(), popItem.getTrackCount()));
			final ImageRequest imageRequest = new ImageRequest(popItem.getUrlImageSmall(),
					new Response.Listener<Bitmap>() {
				@Override
				public void onResponse(Bitmap response) {
					image.setImageBitmap(response);
				}
			},
					300, 300, ImageView.ScaleType.CENTER, null,
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
						}
					});
			imageRequestQueue.add(imageRequest);
		}

		@Override
		public void onClick(View v) {
			Intent intent = PopInfoActivity.createIntent(getActivity(), popItem.getId());
			startActivity(intent);
		}
	}


	private class PopListAdapter extends RecyclerView.Adapter<PopHolder> {

		DataSource dataSource;

		public PopListAdapter(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		@Override
		public PopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View v = inflater.inflate(R.layout.list_item_pop_item, parent, false);
			return new PopHolder(v);
		}

		@Override
		public void onBindViewHolder(PopHolder holder, int position) {
			PopItem popItem = dataSource.getByIndex(position);
			holder.bind(popItem);
		}

		@Override
		public int getItemCount() {
			return dataSource.count();
		}


		private void setDataAsJsonArray(final JSONArray popItemsJsonArray) {
			final AsyncTask parseResultAsyncTask = new AsyncTask() {
				ProgressDialog progressDialog;
				Exception exception = null;
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					progressDialog = new ProgressDialog(getActivity());
					progressDialog.setMessage(getString(R.string.parsing_process));
					progressDialog.show();
					progressDialog.setCanceledOnTouchOutside(false);
					progressDialog.setCancelable(false);
				}

				@Override
				protected Object doInBackground(Object[] params) {
					try {
						DataSource.getInstance(getActivity()).loadFromJsonArray(popItemsJsonArray);
					} catch (JSONException e) {
						exception = e;
						Log.e(PopListFragment.class.getName(), "Error during json parsing", e);
					}
					return null;
				}

				@Override
				protected void onPostExecute(Object o) {
					if(progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					if(exception != null) {
						Toast.makeText(getActivity(), R.string.error_parse, Toast.LENGTH_LONG).show();
					}
				}
			};
			parseResultAsyncTask.execute();
		}
	}
}
