package net.guhya.boot.module.board;

import java.util.List;

import javax.validation.Valid;

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

import net.guhya.boot.common.service.GenericService;
import net.guhya.boot.common.web.AbstractRestController;
import net.guhya.boot.common.web.request.Box;
import net.guhya.boot.common.web.response.JsonListResult;
import net.guhya.boot.common.web.response.JsonResult;
import net.guhya.boot.module.board.data.BoardData;

@RestController
@RequestMapping(value = "/v1/boards", 
	produces = {"application/json", "text/json"}, 
	consumes = MediaType.ALL_VALUE)
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class BoardRestController extends AbstractRestController {

	private static Logger log = LoggerFactory.getLogger(BoardRestController.class);
	
	private GenericService<BoardData> boardService;
	
	public BoardRestController(@Qualifier("genericService") GenericService<BoardData> boardService) {
		this.boardService = boardService;
		this.boardService.setEntityName("board");
		
		log.info("BoardData rest controller initialized");
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JsonListResult<BoardData> list(Box paramBox) {
		int count = boardService.countList(paramBox.getMap());
		listPaging(paramBox, count);
		List<BoardData> list = boardService.list(paramBox.getMap());
		return new JsonListResult<BoardData>(getPagingData(paramBox), list);
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public JsonResult<BoardData> select(@PathVariable String id) {
		BoardData item = boardService.select(new BoardData(Long.valueOf(id)));
		return new JsonResult<BoardData>(item);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JsonResult<BoardData> insert(@Valid @RequestBody BoardData dto) {
		boardService.insert(dto);
		BoardData item = boardService.select(dto);
		return new JsonResult<BoardData>(item);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult<BoardData> update(@Valid @RequestBody BoardData dto) {
		boardService.update(dto);
		BoardData item = boardService.select(dto);
		return new JsonResult<BoardData>(item);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResult<BoardData> delete(@RequestBody BoardData dto) {
		boardService.delete(dto);
		return new JsonResult<BoardData>(dto);
	}
	
}
