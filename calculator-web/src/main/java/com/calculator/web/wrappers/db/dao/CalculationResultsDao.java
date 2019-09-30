package com.calculator.web.wrappers.db.dao;

import com.calculator.web.aspects.annotations.InteractWithDb;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationStatus;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.List;
import java.util.function.BiConsumer;

import javax.persistence.*;

public class CalculationResultsDao implements IDao<CalculationResult, Integer> {
	
	private EntityManager entityManager;
	
	@Inject public CalculationResultsDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@InteractWithDb
	public CalculationResult getItem(Integer key) throws SQLException {
		return entityManager.find(CalculationResult.class, key);
	}
	
	@SuppressWarnings("unchecked")
	@InteractWithDb
	@Override
	public List<CalculationResult> getItems() throws SQLException {
		Query query = entityManager.createNamedQuery("CalculationResult.findAll");
		return query.getResultList();
	}

	@Override
	@InteractWithDb
	public void save(CalculationResult item) throws SQLException {
		executeTransaction(
				(entityManager, result) -> { entityManager.persist(result); },
				entityManager, item
				);
	}

	@Override
	@InteractWithDb
	public void update(CalculationResult item) throws SQLException {
		executeTransaction(
				(entityManager, result) -> { entityManager.merge(result); },
				entityManager, item
				);
	}

	@Override
	@InteractWithDb
	public void delete(CalculationResult item) throws SQLException {
		executeTransaction(
				(entityManager, result) -> {
					if (!entityManager.contains(result)) {
						result = entityManager.merge(result);
					}
					
					entityManager.remove(result);
				},
				entityManager, item
				);
	}
	
	@SuppressWarnings("unchecked")
	@InteractWithDb
	public List<CalculationResult> getPendingItems() throws SQLException {
		Query query = entityManager.createNamedQuery("CalculationResult.findPendingItems");
		query.setParameter("status", CalculationStatus.PENDING.getStatusValue());
		return query.getResultList();
	}
	
	private void executeTransaction(BiConsumer<EntityManager, CalculationResult> consumer, EntityManager entityManager, CalculationResult result) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		consumer.accept(entityManager, result);
		transaction.commit();
	}
}
