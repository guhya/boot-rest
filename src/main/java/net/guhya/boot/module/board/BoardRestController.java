package net.guhya.boot.module.board;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.guhya.boot.common.data.JsonResult;
import net.guhya.boot.common.exception.GeneralRestException;
import net.guhya.boot.common.web.AbstractRestController;
import net.guhya.boot.common.web.request.Box;
import net.guhya.boot.module.board.service.BoardService;
import net.guhya.boot.security.data.UserInfo;
import net.guhya.boot.security.rest.service.RestLoginService;

@RestController
@RequestMapping(value = "/v1/boards", 
	produces = {"application/json", "text/json"}, 
	consumes = MediaType.ALL_VALUE)
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class BoardRestController extends AbstractRestController {

	private static Logger log = LoggerFactory.getLogger(BoardRestController.class);
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	private RestLoginService loginService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JsonResult list(Box paramBox) throws Exception {
		int count = boardService.countList(paramBox.getMap());
		listPaging(paramBox, count);
		List<Map<String, Object>> list = boardService.list(paramBox.getMap());
		JsonResult result = new JsonResult(count, paramBox.getInt(PARAM_PAGE), list);
		
		UserInfo userInfo = loginService.getLoggedInUser();
		log.info("User : " + userInfo);
		
		return result;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public JsonResult select(@PathVariable String id, Box paramBox) throws Exception{
		paramBox.put("seq", id);
		Map<String, Object> item = boardService.select(paramBox.getMap());
		JsonResult result = new JsonResult(item);
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JsonResult insert(@RequestBody Map<String, Object> body) throws Exception {
		int result = boardService.insert(body);
		
		if(result > 0) {
			Box paramBox = new Box();
			paramBox.put("seq", result);
			Map<String, Object> item = boardService.select(paramBox.getMap());
			return new JsonResult(item);
		}
		
		throw new GeneralRestException("Operation failed");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@RequestBody Map<String, Object> body) throws Exception {
		int result = boardService.update(body);
		
		if(result > 0) {
			Map<String, Object> item = boardService.select(body);
			return new JsonResult(item);
		}
		
		throw new GeneralRestException("Operation failed");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResult delete(@RequestBody Map<String, Object> body) throws Exception {
		int result = boardService.delete(body);
	
		if(result > 0) {
			Map<String, Object> item = body;
			return new JsonResult(item);
		}		
		
		throw new GeneralRestException("Operation failed");
	}
	


}
