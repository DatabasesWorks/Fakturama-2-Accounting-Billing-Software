package com.sebulli.fakturama.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.sebulli.fakturama.converter.CommonConverter;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.model.ShippingCategory;
import com.sebulli.fakturama.model.ShippingCategory_;

@Creatable
public class ShippingCategoriesDAO extends AbstractDAO<ShippingCategory> {

    protected Class<ShippingCategory> getEntityClass() {
    	return ShippingCategory.class;
    }
    
    /**
     * Finds a {@link ShippingCategory} by its name. Category in this case is a String separated by 
     * slashes, e.g. "/fooCat/barCat". Searching starts with the rightmost value
     * and then check the parent. 
     * 
     * @param shippingCategory the Category to search
     * @return {@link ShippingCategory}
     */
    public ShippingCategory findShippingCategoryByName(String shippingCategory) {
        ShippingCategory result = null;
        if(StringUtils.isNotEmpty(shippingCategory)) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<ShippingCategory> cq = cb.createQuery(getEntityClass());
            Root<ShippingCategory> rootEntity = cq.from(getEntityClass());
            // extract the rightmost value
            String[] splittedCategories = shippingCategory.split("/");
            String leafCategory = splittedCategories[splittedCategories.length - 1];        
            CriteriaQuery<ShippingCategory> selectQuery = cq.select(rootEntity)
                    .where(cb.and(
                                cb.equal(rootEntity.get(ShippingCategory_.name), leafCategory) /*,
                                cb.equal(rootEntity.get(ShippingCategory_.parent), ShippingCategory.class)
                               ,
                                cb.equal(rootEntity.get(ShippingCategory_.deleted), false)*/));
            try {
                List<ShippingCategory> tmpResultList = getEntityManager().createQuery(selectQuery).getResultList();
                // remove leading slash
                String testCat = StringUtils.removeStart(shippingCategory, "/");
                for (ShippingCategory shippingCategory2 : tmpResultList) {
                    if(StringUtils.equals(CommonConverter.getCategoryName(shippingCategory2, ""), testCat)) {
                        result = shippingCategory2;
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
     * Find a {@link ShippingCategory} by its name. If one of the part categories doesn't exist we create it 
     * (if withPersistOption is set).
     * 
     * @param testCat the category to find
     * @param withPersistOption persist a (part) category if it doesn't exist
     * @return found category
     */
    public ShippingCategory getCategory(String testCat, boolean withPersistOption) {
        // to find the complete category we have to start with the topmost category
        // and then lookup each of the child categories in the given path
        String[] splittedCategories = testCat.split("/");
        ShippingCategory parentCategory = null;
        String category = "";
        try {
            for (int i = 0; i < splittedCategories.length; i++) {
                category += "/" + splittedCategories[i];
                ShippingCategory searchCat = findShippingCategoryByName(category);
                if (searchCat == null) {
                    // not found? Then create a new one.
                    ShippingCategory newCategory = new ShippingCategory();
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
