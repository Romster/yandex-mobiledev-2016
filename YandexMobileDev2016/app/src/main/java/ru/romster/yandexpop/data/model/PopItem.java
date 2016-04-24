package ru.romster.yandexpop.data.model;

import java.util.Set;

/**
 * Created by romster on 24/04/16.
 */
public class PopItem {

	private int id;
	private String name;
	private Set<String> genres;
	private String biography;
	private int albumsCount;
	private int trackCount;
	private String urlImageSmall;
	private String urlImageBig;
	private String link;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getGenres() {
		return genres;
	}

	public void setGenres(Set<String> genres) {
		this.genres = genres;
	}

	public int getAlbumsCount() {
		return albumsCount;
	}

	public void setAlbumsCount(int albumsCount) {
		this.albumsCount = albumsCount;
	}

	public int getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(int trackCount) {
		this.trackCount = trackCount;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getUrlImageSmall() {
		return urlImageSmall;
	}

	public void setUrlImageSmall(String urlImageSmall) {
		this.urlImageSmall = urlImageSmall;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUrlImageBig() {
		return urlImageBig;
	}

	public void setUrlImageBig(String urlImageBig) {
		this.urlImageBig = urlImageBig;
	}
}
