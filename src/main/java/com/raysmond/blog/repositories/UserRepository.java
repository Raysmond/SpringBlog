package com.raysmond.blog.repositories;

import javax.persistence.*;
import javax.inject.Inject;

import com.raysmond.blog.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
@Transactional
public class UserRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		entityManager.persist(user);
		return user;
	}
	
	public User findByEmail(String email) {
		try {
			return entityManager.createNamedQuery(User.FIND_BY_EMAIL, User.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	
}
