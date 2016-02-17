/**
 * 
 */
package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.CategoryEntity;
import vn.com.splussoftware.sms.model.entity.ServicesEntity;

/**
 * @author KietLT
 * 
 *         2:46 PM
 * 
 *         2/15/2016
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
	/**
	 * search record by name
	 * @param name
	 * @return list of record have name are input string
	 */
	public CategoryEntity  findByName(String name);
}
