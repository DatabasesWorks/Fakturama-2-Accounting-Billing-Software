package com.sebulli.fakturama.dao;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperty;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import com.sebulli.fakturama.converter.CommonConverter;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.model.VATCategory;
import com.sebulli.fakturama.model.VATCategory_;

@Creatable
public class VatCategoriesDAO extends AbstractDAO<VATCategory> {

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
    
    protected Class<VATCategory> getEntityClass() {
    	return VATCategory.class;
    }

    @PreDestroy
    public void destroy() {
        if (getEntityManager() != null && getEntityManager().isOpen()) {
            getEntityManager().close();
        }
    }
    
    /**
     * Get all {@link VATCategory}s from Database.
     *
     * @return List<VATCategory> 
     */
    public List<VATCategory> findAll() {
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<VATCategory> cq = cb.createQuery(VATCategory.class);
    	CriteriaQuery<VATCategory> selectQuery = cq.select(cq.from(VATCategory.class));
    	return getEntityManager().createQuery(selectQuery).getResultList();
//    	return getEntityManager().createQuery("select p from VATCategory p", VATCategory.class).getResultList();
    }
    
    /**
     * Finds a {@link VATCategory} by its name. Category in this case is a String separated by 
     * slashes, e.g. "/fooCat/barCat". Searching starts with the rightmost value
     * and then check the parent. 
     * 
     * @param vatCategory the Category to search
     * @return {@link VATCategory}
     */
    public VATCategory findVATCategoryByName(String vatCategory) {
        VATCategory result = null;
        if(StringUtils.isNotEmpty(vatCategory)) {
        	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        	CriteriaQuery<VATCategory> cq = cb.createQuery(getEntityClass());
        	Root<VATCategory> rootEntity = cq.from(getEntityClass());
        	// extract the rightmost value
            String[] splittedCategories = vatCategory.split("/");
        	String leafCategory = splittedCategories[splittedCategories.length - 1];       	
    		CriteriaQuery<VATCategory> selectQuery = cq.select(rootEntity)
    		        .where(cb.and(
        		                cb.equal(rootEntity.get(VATCategory_.name), leafCategory) /*,
        		                cb.equal(rootEntity.get(VATCategory_.parent), VATCategory.class)
        		               ,
        		                cb.equal(rootEntity.get(VATCategory_.deleted), false)*/));
            try {
                List<VATCategory> tmpResultList = getEntityManager().createQuery(selectQuery).getResultList();
                // remove leading slash
                String testCat = StringUtils.removeStart(vatCategory, "/");
                for (VATCategory vatCategory2 : tmpResultList) {
                    if(StringUtils.equals(CommonConverter.getCategoryName(vatCategory2, ""), testCat)) {
                        result = vatCategory2;
                        break;
                    }
                }
            }
            catch (NoResultException nre) {
                // no result means we return a null value 
            }
        }
        return result;
    }
    
 
	/**
	 * @return the em
	 */
	protected EntityManager getEntityManager() {
		return em;
	}

	/**
	 * @param em the em to set
	 */
	protected void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * Find a {@link VATCategory} by its name. If one of the part categories doesn't exist we create it 
	 * (if {@code withPersistOption} is set to <code>true</code>).
	 * 
	 * @param testCat the category to find
	 * @param withPersistOption persist a (part) category if it doesn't exist
	 * @return found category
	 */
    public VATCategory getOrCreateCategory(String testCat, boolean withPersistOption) {
        // to find the complete category we have to start with the topmost category
        // and then lookup each of the child categories in the given path
        String[] splittedCategories = testCat.split("/");
        VATCategory parentCategory = null;
        String category = "";
        try {
            for (int i = 0; i < splittedCategories.length; i++) {
            	if(StringUtils.isBlank(splittedCategories[i])) {
            		continue;
            	}
                category += "/" + splittedCategories[i];
                VATCategory searchCat = findVATCategoryByName(category);
                if (searchCat == null) {
                    // not found? Then create a new one.
                    VATCategory newCategory = modelFactory.createVATCategory();
                    newCategory.setName(splittedCategories[i]);
                    newCategory.setParent(parentCategory);
                    newCategory = save(newCategory);
                    searchCat = newCategory;
                }
                // save the parent and then dive deeper...
                parentCategory = searchCat;
            }
        }
        catch (FakturamaStoringException e) {
            getLog().error(e);
        }
        return parentCategory;
    }
}
