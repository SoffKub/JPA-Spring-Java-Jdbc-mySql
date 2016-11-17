package se.jpa.extreme.repository;

import se.jpa.extreme.model.AbstractEntity;

public interface CrudRepository <E extends AbstractEntity>{

	E saveOrUpdate(E entity);
	E remove(E entity);
	E findByBy(Long id);
}
