package com.exchange.ordermatch.model;

import com.exchange.ordermatch.service.OrderServiceOP.Bucket;
import com.exchange.ordermatch.service.OrderServiceOP.DirectOrder;

import exchange.core2.collections.art.LongAdaptiveRadixTreeMap;
import exchange.core2.collections.objpool.ObjectsPool;
import exchange.core2.core.common.CoreSymbolSpecification;

//@Service
public class CurrencyPool {

	private final long symobolId;
	private final CoreSymbolSpecification symbolSpec;
	private ObjectsPool objectsPool;
	//private LongAdaptiveRadixTreeMap<ObjectsPool> poolMap; 
	private final LongAdaptiveRadixTreeMap<Bucket> askPriceBuckets;
	private final LongAdaptiveRadixTreeMap<Bucket> bidPriceBuckets;

	private final LongAdaptiveRadixTreeMap<DirectOrder> orderIdIndex;

	// heads
	private DirectOrder bestAskOrder = null;
	private DirectOrder bestBidOrder = null;	
	
	public CurrencyPool(long symobolId, CoreSymbolSpecification symbolSpec) {
		this.symobolId = symobolId;
		this.symbolSpec = symbolSpec;
		this.objectsPool = ObjectsPool.createDefaultTestPool();
		//this.poolMap = new LongAdaptiveRadixTreeMap<>();
		this.askPriceBuckets = new LongAdaptiveRadixTreeMap<>(objectsPool);
		this.bidPriceBuckets = new LongAdaptiveRadixTreeMap<>(objectsPool);
		this.orderIdIndex = new LongAdaptiveRadixTreeMap<>(objectsPool);
	}

	public long getSymobolId() {
		return symobolId;
	}

	public CoreSymbolSpecification getSymbolSpec() {
		return symbolSpec;
	}

	public ObjectsPool getObjectsPool() {
		return objectsPool;
	}

	public DirectOrder getBestAskOrder() {
		return bestAskOrder;
	}

	public void setBestAskOrder(DirectOrder bestAskOrder) {
		this.bestAskOrder = bestAskOrder;
	}

	public DirectOrder getBestBidOrder() {
		return bestBidOrder;
	}

	public void setBestBidOrder(DirectOrder bestBidOrder) {
		this.bestBidOrder = bestBidOrder;
	}

	public LongAdaptiveRadixTreeMap<Bucket> getAskPriceBuckets() {
		return askPriceBuckets;
	}

	public LongAdaptiveRadixTreeMap<Bucket> getBidPriceBuckets() {
		return bidPriceBuckets;
	}

	public LongAdaptiveRadixTreeMap<DirectOrder> getOrderIdIndex() {
		return orderIdIndex;
	}

	public void setObjectsPool(ObjectsPool objectsPool) {
		this.objectsPool = objectsPool;
	}
	
	
	
//	public ObjectsPool getObjectsPool(long currencyId) {
//		
//		return poolMap.get(currencyId);
//	}
//	
//	public void saveNewPool(long currencyId, ObjectsPool pool) {
//		
//		poolMap.put(currencyId, pool);
//	}
}
