package org.javacoo.crawler.core.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * Miscellaneous collection utility methods.
 * Mainly for internal use within the framework.
 *
 * @since 1.1.3
 */
public abstract class CollectionUtils {
	
	private static final Log logger = Logs.get();

	/**
	 * Return <code>true</code> if the supplied <code>Collection</code> is null or empty.
	 * Otherwise, return <code>false</code>.
	 * @param collection the <code>Collection</code> to check
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * Return <code>true</code> if the supplied <code>Map</code> is null or empty.
	 * Otherwise, return <code>false</code>.
	 * @param map the <code>Map</code> to check
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

	/**
	 * Determine whether the given collection only contains a
	 * single unique object.
	 * @param coll the collection to check
	 * @return <code>true</code> if the collection contains a
	 * single reference or multiple references to the same
	 * instance, <code>false</code> else
	 */
	public static boolean hasUniqueObject(Collection<?> coll) {
		if (coll.isEmpty()) {
			return false;
		}
		Object candidate = null;
		for (Iterator<?> it = coll.iterator(); it.hasNext();) {
			Object elem = it.next();
			if (candidate == null) {
				candidate = elem;
			}
			else if (candidate != elem) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Find a value of the given type in the given collection.
	 * @param coll the collection to search
	 * @param type the type to look for
	 * @return a value of the given type found, or <code>null</code> if none
	 * @throws IllegalArgumentException if more than one value
	 * of the given type found
	 */
	public static Object findValueOfType(Collection<?> coll, Class<?> type) throws IllegalArgumentException {
		Object value = null;
		for (Iterator<?> it = coll.iterator(); it.hasNext();) {
			Object obj = it.next();
			if (type.isInstance(obj)) {
				if (value != null) {
					throw new IllegalArgumentException("More than one value of type [" + type.getName() + "] found");
				}
				value = obj;
			}
		}
		return value;
	}

	/**
	 * Find a value of one of the given types in the given collection:
	 * searching the collection for a value of the first type, then
	 * searching for a value of the second type, etc.
	 * @param coll the collection to search
	 * @param types the types to look for, in prioritized order
	 * @return a of one of the given types found, or <code>null</code> if none
	 * @throws IllegalArgumentException if more than one value
	 * of the given type found
	 */
	public static Object findValueOfType(Collection coll, Class[] types) throws IllegalArgumentException {
		for (int i = 0; i < types.length; i++) {
			Object value = findValueOfType(coll, types[i]);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

    /**
     * Calculates initial capacity needed to hold <code>size</code> elements in
     * a HashMap or Hashtable without forcing an expensive increase in internal
     * capacity. Capacity is based on the default load factor of .75.
     * <p>
     * Usage: <code>Map map = new HashMap(HashMapUtils.calcCapacity(10));<code>
     * </p>
     * @param size the number of items that will be put into a HashMap
     * @return initial capacity needed
     */
    public static final int calcCapacity(int size)
    {
        return ((size * 4) + 3) / 3;
    }

    /**
     * Creates a new <code>HashMap</code> that has all of the elements
     * of <code>map1</code> and <code>map2</code> (on key collision, the latter
     * override the former).
     *
     * @param map1 the fist hashmap to merge
     * @param map2 the second hashmap to merge
     * @return new hashmap
     */
    public static HashMap merge(Map map1, Map map2)
    {
        HashMap retval = new HashMap(calcCapacity(map1.size() + map2.size()));

        retval.putAll(map1);
        retval.putAll(map2);

        return retval;
    }
    
    public static int getIntFromCollection(Collection<?> c) {
        Object o = findSingleObject(c);
        return o == null ? 0 : ((Integer)o).intValue();
    }
    
    public static Object findSingleObject(Collection<?> c) {
        if (CollectionUtils.isEmpty(c)) {
            return null;
        }
        Iterator<?> results = c.iterator();
        Object result = null;
        
        while (results.hasNext()) {
            result = results.next();
            break;
        }
        if (results.hasNext()) {
            logger.error("Uh oh - found more than one object when single object requested: ");
            if (logger.isDebugEnabled()) {
                Iterator<?> results1 = c.iterator();
                while (results1.hasNext()) {
                    Object result1 = results1.next();
                    logger.debug(result1);
                }
            }
        }
        return result;
    }

	/**
	 * Merge the given Properties instance into the given Map,
	 * copying all properties (key-value pairs) over.
	 * <p>Uses <code>Properties.propertyNames()</code> to even catch
	 * default properties linked into the original Properties instance.
	 * @param props the Properties instance to merge (may be <code>null</code>)
	 * @param map the target Map to merge the properties into
	 */
	public static void mergePropertiesIntoMap(Properties props, Map<String, String> map) {
		if (map == null) {
			throw new IllegalArgumentException("Map must not be null");
		}
		if (props != null) {
			for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				map.put(key, props.getProperty(key));
			}
		}
	}


}
