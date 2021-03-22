package net.guhya.boot.module.board;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.guhya.boot.common.web.AbstractRestController;
import net.guhya.boot.common.web.request.Box;
import net.guhya.boot.data.JsonResult;
import net.guhya.boot.module.board.service.BoardService;

@RestController
@RequestMapping(value = "/v1/boards", 
	produces = {"application/json", "text/json"}, 
	consumes = MediaType.ALL_VALUE)
public class BoardRestController extends AbstractRestController {

	private static Logger log = LoggerFactory.getLogger(BoardRestController.class);
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JsonResult list(Box paramBox){
		int count = boardService.countList(paramBox.getMap());
		listPaging(paramBox, count);
		List<Map<String, Object>> list = boardService.list(paramBox.getMap());
		JsonResult result = new JsonResult(count, paramBox.getInt(PARAM_PAGE), list);
		
		return result;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public JsonResult select(@PathVariable String id, Box paramBox){
		paramBox.put("seq", id);
		Map<String, Object> item = boardService.select(paramBox.getMap());
		JsonResult result = new JsonResult(1, 1, item);
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JsonResult insert(@RequestBody Map<String, Object> body) {
		log.debug("Body ["+body.toString()+"]");
		int result = boardService.insert(body);
		
		return new JsonResult(result > 0);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@RequestBody Map<String, Object> body) {
		int result = boardService.update(body);
		
		return new JsonResult(result > 0);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResult delete(@RequestBody Map<String, Object> body) {
		int result = boardService.delete(body);
		
		return new JsonResult(result > 0);
	}
	


}
