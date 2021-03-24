package net.guhya.boot.module.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * List 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return sqlSession.selectList("Board.list", map);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public int countList(Map<String, Object> parameterMap) {
		return sqlSession.selectOne("Board.countList", parameterMap);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public Map<String, Object> select(Map<String, Object> parameterMap) {
		return sqlSession.selectOne("Board.select", parameterMap);
	}
	
	/**
	 * Insert 
	 * @param parameterMap
	 * @return
	 */
	public int insert(Map<String, Object> parameterMap) {
		return sqlSession.insert("Board.insert", parameterMap);
	}
	
	/**
	 * Update 
	 * @param parameterMap
	 * @return
	 */
	public int update(Map<String, Object> parameterMap) {
		return sqlSession.update("Board.update", parameterMap);
	}

	/**
	 * Delete 
	 * @param parameterMap
	 * @return
	 */
	public int delete(Map<String, Object> parameterMap) {
		return sqlSession.delete("Board.delete", parameterMap);
	}
}