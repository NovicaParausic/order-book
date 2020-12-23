package com.exchange.ordermatch.service;

import com.exchange.ordermatch.model.CurrencyPool;

import exchange.core2.collections.art.LongAdaptiveRadixTreeMap;

public class PoolTree {

	private LongAdaptiveRadixTreeMap<CurrencyPool> currencyPoolTree;
	
	public CurrencyPool getObjectsPool(long currencyId) {
		return currencyPoolTree.get(currencyId);
	}
	
	public void saveNewPool(long currencyId, CurrencyPool pool) {
		currencyPoolTree.put(currencyId, pool);
	}
}
