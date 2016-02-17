/**
 * 
 */
package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.ProcessesEntity;

/**
 * @author KietLT
 * 
 * 3:42 PM 2/15/2016
 *
 */
public interface ProcessesRepository extends JpaRepository<ProcessesEntity,Integer>{
	/**
	 * search record by name
	 * @param name
	 * @return list of record have name are input string
	 */
	public ProcessesEntity  findByName(String name);

}
