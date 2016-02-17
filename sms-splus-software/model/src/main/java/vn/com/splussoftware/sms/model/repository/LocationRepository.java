/**
 * 
 */
package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.CategoryEntity;
import vn.com.splussoftware.sms.model.entity.LocationEntity;

/**
 * @author KietLT
 * 
 * 3:00 PM 
 * 
 * 2/15/2016
 *
 */
public interface LocationRepository extends JpaRepository<LocationEntity,Integer> {
	/**
	 * search record by name
	 * @param name
	 * @return list of record have name are input string
	 */
	public LocationEntity  findByName(String name);
}
