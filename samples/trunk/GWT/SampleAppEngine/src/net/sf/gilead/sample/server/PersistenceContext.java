package net.sf.gilead.sample.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JPA Entity Manager Factory
 * @author bruno.marchesson
 *
 */
public final class PersistenceContext {
    private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("sample");

    private PersistenceContext() {}

    public static EntityManagerFactory getEntityManagerFactory() {
        return emfInstance;
    }
}