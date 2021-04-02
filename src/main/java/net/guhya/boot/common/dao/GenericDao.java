package net.guhya.boot.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository(value = "genericDao")
public class GenericDao<T> {
	
	protected SqlSession sqlSession;
	protected String namespace = "";
	
	public GenericDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void setNamespace(String className) {
		this.namespace = "net.guhya.boot.module."+className+".dao.Mapper.";
	}

	/**
	 * List 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return sqlSession.selectList(namespace + "list", map);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public int countList(Map<String, Object> parameterMap) {
		return sqlSession.selectOne(namespace + "countList", parameterMap);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public Map<String, Object> select(Map<String, Object> parameterMap) {
		return sqlSession.selectOne(namespace + "select", parameterMap);
	}
	
	/**
	 * Insert 
	 * @param parameterMap
	 * @return
	 */
	public int insert(Map<String, Object> parameterMap) {
		Map<String, Object> map = parameterMap;
		int seq = sqlSession.insert(namespace + "insert", map);
		seq = map.get("seq") == null ? seq : Integer.parseInt(map.get("seq").toString());
		
		return seq;
	}
	
	/**
	 * Update 
	 * @param parameterMap
	 * @return
	 */
	public int update(Map<String, Object> parameterMap) {
		return sqlSession.update(namespace + "update", parameterMap);
	}

	/**
	 * Delete 
	 * @param parameterMap
	 * @return
	 */
	public int delete(Map<String, Object> parameterMap) {
		return sqlSession.delete(namespace + "delete", parameterMap);
	}
}