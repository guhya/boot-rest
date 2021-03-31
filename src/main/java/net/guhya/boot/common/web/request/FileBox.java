package net.guhya.boot.common.web.request;

import org.springframework.web.multipart.MultipartFile;

import net.guhya.boot.common.web.WebUtil;

public class FileBox extends Box{
	
	public FileBox(Box ownerBox, MultipartFile file, String category, String channel, String path){
		final String originalName 	= file.getOriginalFilename();
		final String fileSize 		= String.valueOf(file.getSize());		
		final String fileName		= generateFileName(channel, category, originalName);
		
		this.put("category"			, category);
		this.put("channel"			, channel);
		this.put("name"				, fileName);
		this.put("originalName"		, originalName);
		this.put("fileSize"			, fileSize);
		this.put("path"				, path);
		this.put("title"			, ownerBox.getString("fileTitle"));
		this.put("ownerSeq"			, ownerBox.getString("ownerSeq"));
		
		initMeta(ownerBox);
	}
	
	public FileBox(Box ownerBox, String seq){
		this.put("seq"				, seq);		
		this.put("ownerSeq"			, ownerBox.getString("ownerSeq"));				
		
		initMeta(ownerBox);
	}
	
	private void initMeta(Box ownerBox){
		this.put("regId"	, ownerBox.getString("regId"));
		this.put("regIp"	, ownerBox.getString("regIp"));
		this.put("modId"	, ownerBox.getString("modId"));
		this.put("modIp"	, ownerBox.getString("modIp"));
	}
	
	private String generateFileName(String channel, String category, String originalName){
		final StringBuffer sb		= new StringBuffer();
		sb.append(channel);
		sb.append("_");
		sb.append(category);
		sb.append("_");
		sb.append(System.currentTimeMillis());
		sb.append(".");
		sb.append(WebUtil.getFileExtension(originalName));
		
		return sb.toString();
	}
	
}
