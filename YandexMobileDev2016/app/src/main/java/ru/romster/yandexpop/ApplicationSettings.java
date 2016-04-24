package ru.romster.yandexpop;

/**
 * Created by romster on 24/04/16.
 */
public class ApplicationSettings {

	private static ApplicationSettings instance = new ApplicationSettings();

	public static ApplicationSettings getInstance() {
		return instance;
	}

	public String getDataUrl() {
		return "http://cache-default01h.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
	}

	private ApplicationSettings(){

	}
}
