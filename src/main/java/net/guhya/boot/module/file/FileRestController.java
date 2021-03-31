package net.guhya.boot.module.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.guhya.boot.common.data.JsonResult;
import net.guhya.boot.common.exception.GeneralRestException;
import net.guhya.boot.common.web.AbstractRestController;
import net.guhya.boot.common.web.request.Box;
import net.guhya.boot.common.web.request.FileBox;
import net.guhya.boot.module.file.service.FileService;

@RestController
@RequestMapping(value = "/v1/files", 
	produces = {"application/json", "text/json"}, 
	consumes = MediaType.ALL_VALUE)
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class FileRestController extends AbstractRestController {

	private static Logger log = LoggerFactory.getLogger(FileRestController.class);
	
	@Autowired
	FileService fileService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public JsonResult upload(Box paramBox) throws Exception {
		
		log.info("Upload file");

		Map<String, Object> item;

		/* Operation based on category */
		String category = paramBox.getString("category");
		switch(category) {
			case "mainImage" :
				item = fileService.selectByOwner(paramBox.getMap());
				if(item != null)
					fileService.delete(item);
				break;
		}
		
		try {
			@SuppressWarnings("unchecked")
			List<Map<String, MultipartFile>> fileList = (List<Map<String, MultipartFile>>) paramBox.get(FILE_LIST);
			
			int lastId = 0;
			for(Map<String, MultipartFile> fileMap : fileList){
				for(Entry<String, MultipartFile> file : fileMap.entrySet()){
					final MultipartFile theFile	= file.getValue();
					
					//Owner of the file is newly inserted seq
					String channel = paramBox.getString("channel");
					Box fileBox = new FileBox(paramBox, theFile, file.getKey(), channel, channel);
					
					if(!"".equals(file.getValue().getOriginalFilename())){
						writeFile(file.getValue(), fileBox.getString("name"), PATH_UPLOAD+channel);
						lastId = fileService.insert(fileBox.getMap());
					}
				}
			}
			
			paramBox.put("seq", lastId);
			item = fileService.selectById(paramBox.getMap());
			
		} catch (Exception ex) {
			throw new GeneralRestException("Operation failed");
		}
		
		return new JsonResult(item);
	}

	private void writeFile(MultipartFile file, String fileName, String filePath) throws IOException {
		InputStream stream = null;
		OutputStream bos = null;

		stream = file.getInputStream();
		File cFile = new File(filePath);

		if (!cFile.isDirectory())
			cFile.mkdirs();

		bos = new FileOutputStream(filePath + File.separator + fileName);

		int bytesRead = 0;
		byte[] buffer = new byte[8096];

		while ((bytesRead = stream.read(buffer, 0, 8096)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		
		bos.close();
		if (stream != null) stream.close();
	}
	
}
