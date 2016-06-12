package com.amazon.cache.lru;

public class CheckCache {

	public static void main(String[] args) {
		Cache sytemCache = new Cache(5);
		runTestSuite(sytemCache);
	}

	public static void runTestSuite(Cache sytemCache){
		System.out.println("TestCacheCount " + TestCacheCount(sytemCache,5));
		System.out.println("TestCacheFullLimit " + TestCacheFullLimit(sytemCache,5));
		System.out.println("TestCacheEmptyTillZeroCount " + TestCacheEmptyTillZeroCount(sytemCache,5));
		System.out.println("TestCacheEmpty " + TestCacheEmpty(sytemCache));
		System.out.println("TestDataInCache " + TestDataInCache(sytemCache,10,5));
		
	}

	public static boolean TestCacheCount(Cache sytemCache, int size) {
		for (int i = 0; i < size; i++) {
			sytemCache.insertIntoCache(i, i);
		}
		return sytemCache.getCount() == size;
	}

	public static boolean TestCacheFullLimit(Cache sytemCache, int size) {
		for (int i = 0; i < size; i++) {
			sytemCache.insertIntoCache(i, i);
		}
		return sytemCache.isCacheFull();
	}

	public static boolean TestCacheEmptyTillZeroCount(Cache sytemCache, int size) {
		for (int i = 0; i < size; i++) {
			sytemCache.removeDataFromCache(i);
		}
		return sytemCache.getCount() == 0;
	}

	public static boolean TestCacheEmpty(Cache sytemCache) {
		return sytemCache.isCacheEmpty();
	}

	public static boolean TestDataInCache(Cache sytemCache, int data, int pageNumber) {
		sytemCache.insertIntoCache(pageNumber, data);
		return sytemCache.referenceNodeFromCache(pageNumber) == data;
	}
}
