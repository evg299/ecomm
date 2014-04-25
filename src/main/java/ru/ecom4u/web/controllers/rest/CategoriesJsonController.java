package ru.ecom4u.web.controllers.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.ecom4u.web.controllers.dto.CategoryDTO;
import ru.ecom4u.web.domain.db.entities.ProductCategory;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;

@Controller
@RequestMapping(value = "categories-json")
public class CategoriesJsonController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<CategoryDTO> getRootCategories() {
		List<CategoryDTO> result = new ArrayList<CategoryDTO>();
		List<ProductCategory> dbCategories = productCategoryService.getRootProductCategories();
		for (ProductCategory dbCategory : dbCategories) {
			CategoryDTO categoryDTO = new CategoryDTO(dbCategory.getId(), dbCategory.getName(),
					dbCategory.getUrlName(), 0);
			result.add(categoryDTO);
		}
		return result;
	}

	@RequestMapping(value = "{parentId}", method = RequestMethod.GET)
	@ResponseBody
	public List<CategoryDTO> getSubCategories(@PathVariable(value = "parentId") Integer parentId) {
		List<CategoryDTO> result = new ArrayList<CategoryDTO>();
		ProductCategory parentProductCategory = productCategoryService.getById(parentId);
		List<ProductCategory> dbCategories = productCategoryService.getSubProductCategories(parentProductCategory);
		for (ProductCategory dbCategory : dbCategories) {
			CategoryDTO categoryDTO = new CategoryDTO(dbCategory.getId(), dbCategory.getName(),
					dbCategory.getUrlName(), 0);
			if (null != dbCategory.getParentId())
				categoryDTO.setPid(dbCategory.getParentId());
			result.add(categoryDTO);
		}
		return result;
	}
}
