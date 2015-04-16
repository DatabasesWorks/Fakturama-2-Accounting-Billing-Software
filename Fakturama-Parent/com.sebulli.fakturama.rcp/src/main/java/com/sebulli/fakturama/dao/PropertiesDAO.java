package com.sebulli.fakturama.dao;

import java.sql.SQLException;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperty;
import org.eclipse.persistence.config.BatchWriting;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import com.sebulli.fakturama.model.UserProperty;
import com.sebulli.fakturama.oldmodel.OldProperties;

@Creatable
public class PropertiesDAO extends AbstractDAO<UserProperty> {

    @Inject
    @GeminiPersistenceContext(unitName = "unconfigured2", properties = {
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_DRIVER, valuePref = @Preference(PersistenceUnitProperties.JDBC_DRIVER)),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_URL, valuePref = @Preference(PersistenceUnitProperties.JDBC_URL)),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_USER, valuePref = @Preference(PersistenceUnitProperties.JDBC_USER)),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_PASSWORD, valuePref = @Preference(PersistenceUnitProperties.JDBC_PASSWORD)),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.LOGGING_LEVEL, value = "INFO"),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING, value = "false"),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING_INTERNAL, value = "false") })
    //    @GeminiPersistenceContext(unitName = "mysql-datasource")
    //    @GeminiPersistenceContext(unitName = "origin-datasource")
    private EntityManager em;

    protected Class<UserProperty> getEntityClass() {
        return UserProperty.class;
    }

    @PreDestroy
    public void destroy() {
        if (getEntityManager() != null && getEntityManager().isOpen()) {
            getEntityManager().close();
        }
    }

    /**
     * Finds a {@link UserProperty} by a given {@link OldProperties}.
     * 
     * @param oldVat
     * @return
     */
    public OldProperties findByOldProperty(OldProperties oldProperties) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<OldProperties> criteria = cb.createQuery(OldProperties.class);
        Root<OldProperties> root = criteria.from(OldProperties.class);
        CriteriaQuery<OldProperties> cq = criteria.where(cb.and(cb.equal(root.<String> get("description"), oldProperties.getName()),
                cb.equal(root.<String> get("name"), oldProperties.getName())));
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    /**
     * @return the em
     */
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * @param em
     *            the em to set
     */
    protected void setEntityManager(EntityManager em) {
        this.em = em;
        this.em.setProperty(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
    }

    /**
     * Updates a user property. If the given property isn't available then it
     * will be created.
     * 
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        try {
            UserProperty prop = findByName(key);
            if (prop != null) {
                prop.setValue(value);
                update(prop);
            }
            else {
                prop = new UserProperty();
                prop.setName(key);
                prop.setValue(value);
                save(prop);
            }
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
