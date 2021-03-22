package net.guhya.boot.module.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guhya.boot.module.board.dao.BoardDao;

@Service
public class BoardService {

	@Autowired
	BoardDao boardDao;

	/**
	 * Select 
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> select(Map<String, Object> parameterMap){
		return boardDao.select(parameterMap);
	}
	
	/**
	 * List 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> list(Map<String, Object> parameterMap) {
		return boardDao.list(parameterMap);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public int countList(Map<String, Object> parameterMap) {
		return boardDao.countList(parameterMap);
	}

	/**
	 * Insert 
	 * @param parameterMap
	 * @return
	 */
	public int insert(Map<String, Object> parameterMap) {
		return boardDao.insert(parameterMap);
	}
	
	/**
	 * Update 
	 * @param parameterMap
	 * @return
	 */
	public int update(Map<String, Object> parameterMap) {
		return boardDao.update(parameterMap);
	}

	/**
	 * Delete 
	 * @param parameterMap
	 * @return
	 */
	public int delete(Map<String, Object> parameterMap) {
		return boardDao.delete(parameterMap);
	}	
}
