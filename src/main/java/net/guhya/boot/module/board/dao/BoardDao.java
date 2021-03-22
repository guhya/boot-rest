package net.guhya.boot.module.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDao {
	
	protected static final String NAMESPACE =  "net.guhya.boot.board.dao.";
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * List 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return sqlSession.selectList(NAMESPACE+"list", map);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public int countList(Map<String, Object> parameterMap) {
		return sqlSession.selectOne(NAMESPACE+"countList", parameterMap);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public Map<String, Object> select(Map<String, Object> parameterMap) {
		return sqlSession.selectOne(NAMESPACE+"select", parameterMap);
	}
	
	/**
	 * Insert 
	 * @param parameterMap
	 * @return
	 */
	public int insert(Map<String, Object> parameterMap) {
		return sqlSession.insert(NAMESPACE+"insert", parameterMap);
	}
	
	/**
	 * Update 
	 * @param parameterMap
	 * @return
	 */
	public int update(Map<String, Object> parameterMap) {
		return sqlSession.update(NAMESPACE+"update", parameterMap);
	}

	/**
	 * Delete 
	 * @param parameterMap
	 * @return
	 */
	public int delete(Map<String, Object> parameterMap) {
		return sqlSession.delete(NAMESPACE+"delete", parameterMap);
	}
}