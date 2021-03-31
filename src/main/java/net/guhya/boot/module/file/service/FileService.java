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
package net.guhya.boot.module.file.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guhya.boot.module.file.dao.FileDao;


@Service
public class FileService {

	@Autowired
	private FileDao fileDao;

	/**
	 * List all matching records
	 * @param parameterMap HTTP Request parameters wrapped in HashMap
	 * @return List of HashMaps
	 * @exception Exception
	 */
	public List<Map<String, Object>> list(Map<String, Object> parameterMap) throws Exception {
		return fileDao.list(parameterMap);
	}

	/**
	 * Get an item based on user parameter
	 * @param parameterMap HTTP Request parameters wrapped in HashMap
	 * @return HashMap item
	 * @exception Exception
	 */
	public Map<String, Object> selectById(Map<String, Object> parameterMap) throws Exception {
		return fileDao.selectById(parameterMap);
	}
	
	/**
	 * Get an item based on user parameter
	 * @param parameterMap HTTP Request parameters wrapped in HashMap
	 * @return HashMap item
	 * @exception Exception
	 */
	public Map<String, Object> selectByOwner(Map<String, Object> parameterMap) throws Exception {
		return fileDao.selectByOwner(parameterMap);
	}
	
	/**
	 * Insert item into table
	 * @param parameterMap HTTP Request parameters wrapped in HashMap
	 * @return int last inserted ID (sequence)
	 * @exception Exception
	 */
	public int insert(Map<String, Object> parameterMap) throws Exception {
		return fileDao.insert(parameterMap);
	}	
	
	/**
	 * Delete item from table
	 * @param parameterMap HTTP Request parameters wrapped in HashMap
	 * @return int updated ID (sequence)
	 * @exception Exception
	 */
	public int delete(Map<String, Object> parameterMap) throws Exception {
		return fileDao.delete(parameterMap);
	}

}
