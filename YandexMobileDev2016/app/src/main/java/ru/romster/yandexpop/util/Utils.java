package ru.romster.yandexpop.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by romster on 24/04/16.
 */
public class Utils {

	public static String collectionToString(Collection collection) {
		StringBuilder sb = new StringBuilder();
		Iterator iterator = collection.iterator();
		while(iterator.hasNext()) {
			sb.append(iterator.next());
			if(iterator.hasNext())
				sb.append(", ");
		}
		return sb.toString();
	}
}
