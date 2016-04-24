package ru.romster.yandexpop.data;

/**
 * Created by romster on 24/04/16.
 */
public final class JsonSchema {

	public static final class Artist {

		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String GENRES = "genres";
		public static final String TRACK_COUNT = "tracks";
		public static final String ALBUM_COUNT = "albums";
		public static final String LINK = "link";
		public static final String DESCRIPTION = "description";
		public static final String COVER = "cover";

		public static final class Cover {
			public static final String SMALL = "small";
			public static final String BIG = "big";
		}
	}
}
