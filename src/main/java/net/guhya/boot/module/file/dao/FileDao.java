/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guhya.boot.module.file.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * List all matching records
	 * @param paramBox HTTP Request parameters wrapped in HashMap
	 * @return List of HashMaps
	 * @exception Exception
	 */
	public List<Map<String, Object>> list(Map<String, Object> parameterMap) throws Exception {
		return sqlSession.selectList("File.list", parameterMap);
	}

	/**
	 * Get an item based on user parameter
	 * @param paramBox HTTP Request parameters wrapped in HashMap
	 * @return HashMap item
	 * @exception Exception
	 */
	public Map<String, Object> selectById(Map<String, Object> parameterMap) throws Exception {
		return sqlSession.selectOne("File.selectById", parameterMap);
	}

	/**
	 * Get an item based on user parameter
	 * @param paramBox HTTP Request parameters wrapped in HashMap
	 * @return HashMap item
	 * @exception Exception
	 */
	public Map<String, Object> selectByOwner(Map<String, Object> parameterMap) throws Exception {
		return sqlSession.selectOne("File.selectByOwner", parameterMap);
	}

	/**
	 * Insert item into table
	 * @param paramBox HTTP Request parameters wrapped in HashMap
	 * @return int last inserted ID (sequence)
	 * @exception Exception
	 */
	public int insert(Map<String, Object> parameterMap) throws Exception {
		Map<String, Object> map = parameterMap;
		int seq = sqlSession.insert("File.insert", map);
		seq = map.get("seq") == null ? seq : Integer.parseInt(map.get("seq").toString());
		
		return seq;
	}

	/**
	 * Delete item from table
	 * @param paramBox HTTP Request parameters wrapped in HashMap
	 * @return int updated ID (sequence)
	 * @exception Exception
	 */
	public int delete(Map<String, Object> parameterMap) throws Exception {
		return sqlSession.update("File.delete", parameterMap);
	}
	/**
	 * Insert item into table
	 * @param paramBox HTTP Request parameters wrapped in HashMap
	 * @return int last inserted ID (sequence)
	 * @exception Exception
	 */
	public int mInsert(Map<String, Object> parameterMap) throws Exception {
		Map<String, Object> map = parameterMap;
		int seq = sqlSession.insert("File.mInsert", map);
		seq = map.get("seq") == null ? seq : Integer.parseInt(map.get("seq").toString());
		
		return seq;
	}
}
