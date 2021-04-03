package net.guhya.boot.module.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.guhya.boot.common.exception.GeneralRestException;
import net.guhya.boot.common.exception.ItemNotFoundException;
import net.guhya.boot.common.service.GenericService;
import net.guhya.boot.common.web.AbstractRestController;
import net.guhya.boot.common.web.request.Box;
import net.guhya.boot.common.web.response.JsonListResult;
import net.guhya.boot.common.web.response.JsonResult;
import net.guhya.boot.module.board.data.Board;

@RestController
@RequestMapping(value = "/v1/boards", 
	produces = {"application/json", "text/json"}, 
	consumes = MediaType.ALL_VALUE)
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class BoardRestController extends AbstractRestController {

	private static Logger log = LoggerFactory.getLogger(BoardRestController.class);
	
	private GenericService<Board> boardService;
	
	public BoardRestController(@Qualifier("genericService") GenericService<Board> boardService) {
		this.boardService = boardService;
		this.boardService.setEntityName("board");
		
		log.info("Board rest controller initialized");
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JsonListResult<Board> list(Box paramBox) throws Exception {
		int count = boardService.countList(paramBox.getMap());
		listPaging(paramBox, count);
		List<Board> list = boardService.list(paramBox.getMap());
		JsonListResult<Board> result = 
				new JsonListResult<Board>(getPagingData(paramBox), list);
		
		return result;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public JsonResult<Board> select(@PathVariable String id) throws Exception{
		Board item = boardService.select(new Board(Long.valueOf(id)));
		if(item != null) {
			return new JsonResult<Board>(item);
		}
		
		throw new ItemNotFoundException("Item not found");
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JsonResult<Board> insert(@RequestBody Board dto) throws Exception {
		long result = boardService.insert(dto);
		
		if(result > 0) {
			Board item = boardService.select(dto);
			return new JsonResult<Board>(item);
		}
		
		throw new GeneralRestException("Operation failed");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult<Board> update(@RequestBody Board dto) throws Exception {
		int result = boardService.update(dto);
		
		if(result > 0) {
			Board item = boardService.select(dto);
			return new JsonResult<Board>(item);
		}
		
		throw new GeneralRestException("Operation failed");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResult<Board> delete(@RequestBody Board dto) throws Exception {
		int result = boardService.delete(dto);
	
		if(result > 0) {
			return new JsonResult<Board>(dto);
		}		
		
		throw new GeneralRestException("Operation failed");
	}
	
}
