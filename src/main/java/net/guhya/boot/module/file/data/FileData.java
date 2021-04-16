package net.guhya.boot.module.file.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.guhya.boot.common.data.AbstractData;

@JsonInclude(Include.NON_NULL)
public class FileData extends AbstractData {
	private Long seq;
	private String category;
	private String channel;
	private String name;
	private String originalName;
	private Long fileSize;
	private String path;
	private String title;
	private Long ownerSeq;
	
	public FileData() {
	}
	
	public FileData(Long seq) {
		this.seq = seq;
	}
	
	public FileData(Long seq, Long ownerSeq) {
		this.seq = seq;
		this.ownerSeq = ownerSeq;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getOwnerSeq() {
		return ownerSeq;
	}

	public void setOwnerSeq(Long ownerSeq) {
		this.ownerSeq = ownerSeq;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileData [seq=");
		builder.append(seq);
		builder.append(", category=");
		builder.append(category);
		builder.append(", channel=");
		builder.append(channel);
		builder.append(", name=");
		builder.append(name);
		builder.append(", originalName=");
		builder.append(originalName);
		builder.append(", fileSize=");
		builder.append(fileSize);
		builder.append(", path=");
		builder.append(path);
		builder.append(", title=");
		builder.append(title);
		builder.append(", ownerSeq=");
		builder.append(ownerSeq);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
