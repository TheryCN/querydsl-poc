package fr.thery.poc.repository;

import static fr.thery.poc.entity.QPlayer.player;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import com.mysema.query.jpa.HQLTemplates;
import com.mysema.query.jpa.impl.JPAQuery;

public class StandaloneQuerydslTest {

  private EntityManager entityManager;

  @Before
  public void init() {
    // Create entity manager
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("manager");
    entityManager = factory.createEntityManager();
  }

  @Test
  public void testNonSpringQuerydsl() {
    createQuery().from(player).list(player);
  }

  private JPAQuery createQuery() {
    return new JPAQuery(entityManager, HQLTemplates.DEFAULT);
  }
}
