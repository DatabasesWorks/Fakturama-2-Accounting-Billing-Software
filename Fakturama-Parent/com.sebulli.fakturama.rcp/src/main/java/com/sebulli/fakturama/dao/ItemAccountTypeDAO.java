/* 
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2015 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     The Fakturama Team - initial API and implementation
 */
 
package com.sebulli.fakturama.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.model.ItemAccountType;
import com.sebulli.fakturama.model.ItemAccountType_;
import com.sebulli.fakturama.model.ItemListTypeCategory_;
import com.sebulli.fakturama.model.VATCategory;

/**
 *
 */
@Creatable
public class ItemAccountTypeDAO extends AbstractDAO<ItemAccountType> {

    protected Class<ItemAccountType> getEntityClass() {
        return ItemAccountType.class;
    }
    
    
    /**
     * Collect all salutations from list item table 
     * @return list of salutations
     */
    public List<ItemAccountType> findAllSalutations() {
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ItemAccountType> cq = cb.createQuery(getEntityClass());
        Root<ItemAccountType> rootEntity = cq.from(ItemAccountType.class);
        CriteriaQuery<ItemAccountType> selectQuery = cq.select(rootEntity)
                .where(cb.and(
                            cb.equal(rootEntity.get(ItemAccountType_.category).get(ItemListTypeCategory_.name), "salutations"),
                            cb.isFalse(rootEntity.get(ItemAccountType_.deleted))));
        return getEntityManager().createQuery(selectQuery).getResultList();
    }
    
    /**
     * Finds an {@link ItemAccountType} by its name. An {@link ItemAccountType} doesn't have a hierarchical
     * structure.
     * 
     * @param account the Category to search
     * @return {@link ItemAccountType}
     */
    public ItemAccountType findItemListTypeByName(String account) {
        ItemAccountType result = null;
        if(StringUtils.isNotEmpty(account)) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<ItemAccountType> cq = cb.createQuery(getEntityClass());
            Root<ItemAccountType> rootEntity = cq.from(ItemAccountType.class);
            CriteriaQuery<ItemAccountType> selectQuery = cq.select(rootEntity)
                    .where(cb.and(
                                cb.equal(rootEntity.get(ItemAccountType_.name), account),
                                cb.isFalse(rootEntity.get(ItemAccountType_.deleted))));
            try {
                result = getEntityManager().createQuery(selectQuery).getSingleResult();
            }
            catch (NoResultException nre) {
                // no result means we return a null value 
            }
        }
        return result;
    }     

    /**
     * Find a {@link VATCategory} by its name. If one of the part categories doesn't exist we create it 
     * (if {@code withPersistOption} is set to <code>true</code>).
     * 
     * @param testCat the category to find
     * @param withPersistOption persist a (part) category if it doesn't exist
     * @return found category
     */
    public ItemAccountType getOrCreateCategory(String testCat, boolean withPersistOption) {
        // to find the complete category we have to start with the topmost category
        // and then lookup each of the child categories in the given path
        String[] splittedCategories = testCat.split("/");
        ItemAccountType parentCategory = null;
        String category = "";
        try {
            for (int i = 0; i < splittedCategories.length; i++) {
                category += "/" + splittedCategories[i];
                ItemAccountType searchCat = findByName(category);
                if (searchCat == null) {
                    // not found? Then create a new one.
                    ItemAccountType newCategory = modelFactory.createItemAccountType();
                    newCategory.setName(splittedCategories[i]);
                    // we don't have parents...
//                    newCategory.setParent(parentCategory);
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

    
    @Override
    protected Set<Predicate> getRestrictions(ItemAccountType object, CriteriaBuilder criteriaBuilder, Root<ItemAccountType> root) {
        Set<Predicate> restrictions = new HashSet<>();
        // Compare customer number, only if it is set.
        if(StringUtils.isNotBlank(object.getName())) {
            restrictions.add(criteriaBuilder.equal(root.get(ItemAccountType_.name), object.getName()));
        }
        return restrictions;
    }

    public String[] getVisibleProperties() {
        return new String[] { ItemAccountType_.name.getName(), ItemAccountType_.value.getName()};
    }

    /**
     * Gets the count of items with the given category.
     * 
     * @param category
     * @return
     */
	public Long getCountOf(String category) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<ItemAccountType> rootEntity = cq.from(getEntityClass());
        cq.select(cb.count(rootEntity))
          .where(cb.and(cb.equal(rootEntity.get(ItemAccountType_.category).get(ItemListTypeCategory_.name), category),
                        cb.isFalse(rootEntity.get(ItemAccountType_.deleted))));
        
        return getEntityManager().createQuery(cq).getSingleResult();
	}
}
