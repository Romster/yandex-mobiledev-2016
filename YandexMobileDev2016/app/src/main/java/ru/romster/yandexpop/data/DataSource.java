package ru.romster.yandexpop.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ru.romster.yandexpop.data.convert.PopItemJsonConverter;
import ru.romster.yandexpop.data.model.PopItem;

/**
 * Created by romster on 24/04/16.
 */
public class DataSource {

	private static DataSource instance;

	private Context context;
	private List<PopItem> items = new ArrayList<>();

	public static DataSource getInstance(Context context) {
		if (instance == null) {
			synchronized (DataSource.class) {
				if (instance == null) {
					instance = new DataSource(context);
				}
			}
		}
		return instance;
	}

	private DataSource(Context context) {
		this.context = context.getApplicationContext();
	}

	public synchronized void loadFromJsonArray(JSONArray jsonArray) throws JSONException {
		List<PopItem> popItemList = PopItemJsonConverter.convertMultiple(jsonArray, null);
		items = popItemList;
	}

	public PopItem getByIndex(int index) {
		return items.get(index);
	}

	public PopItem findById(int id) {
		for(PopItem popItem : items) {
			if(popItem.getId() == id) {
				return popItem;
			}
		}
		return null;
	}

	public int count() {
		return items.size();
	}

}
