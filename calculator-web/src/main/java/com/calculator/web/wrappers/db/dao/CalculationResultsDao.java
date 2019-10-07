package com.calculator.web.wrappers.db.dao;

import com.calculator.web.aspects.annotations.InteractWithDb;
import com.calculator.web.wrappers.db.dao.dbMappers.*;

import static com.calculator.web.wrappers.db.dao.dbMappers.tables.CalculationResultsTable.*;

import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.List;
import java.util.function.BiConsumer;

import javax.persistence.*;

public class CalculationResultsDao implements IDao<CalculationResult> {
	
	public static final String SQL_NOT_FOUND_MESSAGE = "Sql error. Row not found.";
	
	private EntityManager entityManager;
	
	@Inject public CalculationResultsDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	@InteractWithDb
	public CalculationResult getItem(CalculationResult key) throws SQLException {
		Query query = entityManager.createNamedQuery("CalculationResult.find");
		query.setParameter(EMAIL, key.getEmail());
		query.setParameter(REQUEST_ID, key.getRequestId());
		
		List<CalculationResult> queryResult = query.getResultList();
		
		if (queryResult.size() != 1) {
			return null;
		}
		
		final int firstArrayElement = 0;
		return queryResult.get(firstArrayElement);
	}
	
	@SuppressWarnings("unchecked")
	@InteractWithDb
	@Override
	public List<CalculationResult> getItems(CalculationResult key) throws SQLException {
		Query query = entityManager.createNamedQuery("CalculationResult.findAll");
		query.setParameter(EMAIL, key.getEmail());
		
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
		query.setParameter(STATUS, CalculationStatus.PENDING.getStatusValue());
		
		return query.getResultList();
	}
	
	private void executeTransaction(BiConsumer<EntityManager, CalculationResult> consumer, EntityManager entityManager, CalculationResult result) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		consumer.accept(entityManager, result);
		transaction.commit();
	}
}
