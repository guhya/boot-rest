package net.guhya.boot.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.guhya.boot.common.dao.GenericDao;

@Service(value = "genericService")
public class GenericService<T> {

	private GenericDao<T> dao;

	public GenericService(@Qualifier("genericDao") GenericDao<T> dao) {
		this.dao = dao;
	}

	public void setEntityName(String entityName) {
		this.dao.setNamespace(entityName);
	}
	
	/**
	 * Select 
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> select(Map<String, Object> parameterMap){
		return dao.select(parameterMap);
	}
	
	/**
	 * List 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> list(Map<String, Object> parameterMap) {
		return dao.list(parameterMap);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public int countList(Map<String, Object> parameterMap) {
		return dao.countList(parameterMap);
	}

	/**
	 * Insert 
	 * @param parameterMap
	 * @return
	 */
	public int insert(Map<String, Object> parameterMap) {
		return dao.insert(parameterMap);
	}
	
	/**
	 * Update 
	 * @param parameterMap
	 * @return
	 */
	public int update(Map<String, Object> parameterMap) {
		return dao.update(parameterMap);
	}

	/**
	 * Delete 
	 * @param parameterMap
	 * @return
	 */
	public int delete(Map<String, Object> parameterMap) {
		return dao.delete(parameterMap);
	}	
}
