package com.exchange.ordermatch.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.exchange.ordermatch.entity.OrderEntity;

@Repository
public class OrderEntityDao {

	@PersistenceContext
	private EntityManager em;

	public long checkBudget() {
		
		//TODO
		return Long.MAX_VALUE;
	}
	
	public OrderEntity findOne(long id) {
		return em.find(OrderEntity.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<OrderEntity> findAll() {
		return em.createNativeQuery("SELECT * FROM ORDER_ENTITY").getResultList();
	}
		
	@SuppressWarnings("unchecked")
	public List<OrderEntity> findAskOrders() {
		return em.createNativeQuery("SELECT * FROM Orders o WHERE o.orderType = 'ASK' ORDER BY o.price AND o.timestamp DESC").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderEntity> findBidOrders() {
		return em.createNativeQuery("SELECT * FROM Orders o WHERE o.orderType = 'BID' ORDER BY o.price AND o.timestamp ASC").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderEntity> tryMatchInstantly() {
		return em.createNativeQuery("SET @runtot=10;"
				+ "SELECT * FROM ORDER_ENTITY o (@runtot=@runtot + o.price) "
				+ "WHERE @runtot = 30;").getResultList();
	}	

	public void create(OrderEntity entity) {
		em.persist(entity);
	}

	public OrderEntity update(OrderEntity entity) {
		return em.merge(entity);
	}

	public void delete(OrderEntity entity) {
		em.remove(entity);
	}

	public void deleteById(long entityId) {
		OrderEntity entity = findOne(entityId);
		delete(entity);
	}

}
