package se.jpa.extreme.repository;

import javax.persistence.EntityManagerFactory;

import se.jpa.extreme.model.User;

public final class JpaUserRepository extends AbstractJpaRepository<User> implements UserRepository {

	public JpaUserRepository(EntityManagerFactory factory) {
		super(factory, User.class);
	}

}
