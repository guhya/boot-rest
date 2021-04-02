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
package net.guhya.boot.common.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.guhya.boot.common.data.FileBox;

@Repository(value = "fileDao")
public class FileDao extends GenericDao<FileBox> {

	public FileDao(SqlSession sqlSession) {
		super(sqlSession);
	}

	/**
	 * Get an item based on user parameter
	 * @param paramBox HTTP Request parameters wrapped in HashMap
	 * @return HashMap item
	 * @exception Exception
	 */
	public Map<String, Object> selectByOwner(Map<String, Object> parameterMap) throws Exception {
		return sqlSession.selectOne(namespace + "selectByOwner", parameterMap);
	}

}
