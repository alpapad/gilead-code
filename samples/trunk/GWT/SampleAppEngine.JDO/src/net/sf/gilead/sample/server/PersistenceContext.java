package net.sf.gilead.sample.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * JDO Persistence Manager Factory
 * @author bruno.marchesson
 *
 */
public final class PersistenceContext {
    private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PersistenceContext() {}

    public static PersistenceManagerFactory getPersistenceManagerFactory() {
        return pmfInstance;
    }
}