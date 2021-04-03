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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.guhya.boot.common.exception.GeneralRestException;
import net.guhya.boot.common.web.AbstractRestController;
import net.guhya.boot.common.web.request.Box;
import net.guhya.boot.common.web.response.JsonResult;
import net.guhya.boot.module.file.data.FileData;
import net.guhya.boot.module.file.service.FileService;

@RestController
@RequestMapping(value = "/v1/files", 
	produces = {"application/json", "text/json"}, 
	consumes = MediaType.ALL_VALUE)
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class FileRestController extends AbstractRestController {

	private static Logger log = LoggerFactory.getLogger(FileRestController.class);
	
	private FileService fileService;
	
	public FileRestController(@Qualifier("fileService") FileService fileService) {
		this.fileService = fileService;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public JsonResult<FileData> upload(Box paramBox) throws Exception {
		
		log.info("Upload file");
		
		FileData item;
		try {
			@SuppressWarnings("unchecked")
			List<Map<String, MultipartFile>> fileList = (List<Map<String, MultipartFile>>) paramBox.get(FILE_LIST);
			
			long lastId = 0;
			for(Map<String, MultipartFile> fileMap : fileList){
				for(Entry<String, MultipartFile> file : fileMap.entrySet()){
					final MultipartFile theFile	= file.getValue();
					
					FileData fileDto = new FileData();
					fileDto.setOwnerSeq(paramBox.getLong("ownerSeq"));
					String channel = paramBox.getString("channel");
					fileDto.setChannel(channel);
					fileDto.setPath(channel);
					String category = file.getKey();
					fileDto.setCategory(category);
					fileDto.setOriginalName(theFile.getOriginalFilename());
					String name = generateFileName(fileDto);
					fileDto.setName(name);
					fileDto.setFileSize(theFile.getSize());

					if(!"".equals(theFile.getOriginalFilename())){
						List<FileData> oldFiles = fileService.selectByChannelCategoryOwner(fileDto);
						for(FileData of : oldFiles) {
							fileService.delete(of);
						}
						
						writeFile(theFile, name, PATH_UPLOAD+fileDto.getChannel());
						lastId = fileService.insert(fileDto);
					}
				}
			}
			
			item = fileService.select(new FileData(lastId));
			
		} catch (Exception ex) {
			throw new GeneralRestException("Operation failed");
		}
		
		return new JsonResult<FileData>(item);
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
	
	private String generateFileName(FileData fileData){
		final StringBuilder sb		= new StringBuilder();
		sb.append(fileData.getChannel());
		sb.append("_");
		sb.append(fileData.getCategory());
		sb.append("_");
		sb.append(System.currentTimeMillis());
		sb.append(".");
		sb.append(getFileExtension(fileData.getOriginalName()));
		
		return sb.toString();
	}
	
	private String getFileExtension(String fileName){
		String ext = "";
		if(fileName.indexOf(".") == -1){
			ext = "";
		}else{
			String[] arrFile = fileName.split("\\.");
			ext = arrFile[arrFile.length-1];
		}

		return ext;
	}	
}
