package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ProductCategoryService extends AbstractService {

    @Transactional(readOnly = true)
    public ProductCategory getById(Integer id) {
        Session session = getCurrentSession();
        return (ProductCategory) session.get(ProductCategory.class, id);
    }

    @Transactional(readOnly = true)
    public ProductCategory getByUrlName(String urlName) {
        Session session = getCurrentSession();
        return (ProductCategory) session.createCriteria(ProductCategory.class).add(Restrictions.eq("urlName", urlName))
                .addOrder(Order.asc("name")).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> getAll() {
        Session session = getCurrentSession();
        return session.createCriteria(ProductCategory.class).list();
    }

    public Map<Integer, ProductCategory> createTree() {
        List<ProductCategory> categories = getAll();
        Map<Integer, ProductCategory> categoryMap = new TreeMap<Integer, ProductCategory>();
        for (ProductCategory category : categories) {
            categoryMap.put(category.getId(), category);
        }

        for (ProductCategory category : categories) {
            Integer parentId = category.getParentId();
            if (null != parentId) {
                ProductCategory parentCategory = categoryMap.get(parentId);
                category.setParentProductCategory(parentCategory);
            }
        }

        return categoryMap;
    }

    public List<ProductCategory> expandBranch(ProductCategory category) {
        Map<Integer, ProductCategory> categoryMap = createTree();
        List<ProductCategory> resultContainer = new ArrayList<ProductCategory>();
        resultContainer.add(category);
        expandBranchPrivate(categoryMap, category.getId(), resultContainer);
        return resultContainer;
    }

    private void expandBranchPrivate(Map<Integer, ProductCategory> categoryMap, Integer categoryId, List<ProductCategory> container) {
        ProductCategory category = categoryMap.get(categoryId);
        for (ProductCategory subCategory : category.getSubProductCategories()) {
            container.add(subCategory);
            expandBranchPrivate(categoryMap, subCategory.getId(), container);
        }
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> getRootProductCategories() {
        Session session = getCurrentSession();
        return session.createCriteria(ProductCategory.class).add(Restrictions.isNull("parentId"))
                .addOrder(Order.asc("name")).list();
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> getSubProductCategories(ProductCategory parentProductCategory) {
        Session session = getCurrentSession();
        return session.createCriteria(ProductCategory.class)
                .add(Restrictions.eq("parentId", parentProductCategory.getId())).addOrder(Order.asc("name")).list();
    }


}
