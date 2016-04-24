package ru.romster.yandexpop.data.convert;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ru.romster.yandexpop.data.model.PopItem;

import static ru.romster.yandexpop.data.JsonSchema.*;

/**
 * Created by romster on 24/04/16.
 */
public class PopItemJsonConverter {

	public static List<PopItem> convertMultiple(JSONArray popItemJsonArray,
	                                            @Nullable Comparator<PopItem> comparator) throws JSONException {
		List<PopItem> list = new ArrayList<>();
		for(int i =0; i < popItemJsonArray.length(); i++) {
			list.add(parse(popItemJsonArray.getJSONObject(i)));
		}
		if(comparator != null) {
			Collections.sort(list, comparator);
		}
		return list;
	}

	public static PopItem parse(JSONObject jsonObject) throws JSONException {
		PopItem popItem = new PopItem();
		popItem.setId(jsonObject.getInt(Artist.ID));
		popItem.setName(jsonObject.getString(Artist.NAME));
		popItem.setAlbumsCount(jsonObject.getInt(Artist.ALBUM_COUNT));
		popItem.setTrackCount(jsonObject.getInt(Artist.TRACK_COUNT));
		popItem.setBiography(jsonObject.getString(Artist.DESCRIPTION));
		if(jsonObject.has(Artist.LINK)) {
			popItem.setLink(jsonObject.getString(Artist.LINK));
		}
		popItem.setGenres(getGenres(jsonObject.getJSONArray(Artist.GENRES)));
		JSONObject coverJson = jsonObject.getJSONObject(Artist.COVER);
		popItem.setUrlImageSmall(coverJson.getString(Artist.Cover.SMALL));
		popItem.setUrlImageBig(coverJson.getString(Artist.Cover.BIG));
		return popItem;
	}

	public static JSONObject toJson(PopItem popItem) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Artist.ID, popItem.getId());
		jsonObject.put(Artist.NAME, popItem.getName());
		jsonObject.put(Artist.DESCRIPTION, popItem.getBiography());
		if(popItem.getLink() != null) {
			jsonObject.put(Artist.LINK, popItem.getLink());
		}
		jsonObject.put(Artist.ALBUM_COUNT, popItem.getAlbumsCount());
		jsonObject.put(Artist.TRACK_COUNT, popItem.getTrackCount());
		jsonObject.put(Artist.GENRES, genresToJson(popItem.getGenres()));
		JSONObject coverJson = new JSONObject();
		coverJson.put(Artist.Cover.SMALL, popItem.getUrlImageSmall());
		coverJson.put(Artist.Cover.BIG, popItem.getUrlImageBig());
		jsonObject.put(Artist.COVER, coverJson);
		return jsonObject;
	}

	private static Set<String> getGenres(JSONArray jsonArray) throws JSONException {
		Set<String> set = new TreeSet<>();
		for(int i =0; i < jsonArray.length(); i++) {
			String s = jsonArray.getString(i);
			set.add(s);
		}
		return set;
	}

	private static JSONArray genresToJson(Collection<String> genres) {
		JSONArray jsonArray = new JSONArray();
		for(String s : genres) {
			jsonArray.put(s);
		}
		return jsonArray;
	}



}
