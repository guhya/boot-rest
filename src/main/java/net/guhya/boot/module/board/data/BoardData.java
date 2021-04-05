package net.guhya.boot.module.board.data;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import net.guhya.boot.common.data.AbstractData;
import net.guhya.boot.module.file.data.FileData;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"seq","title","subtitle","summary","content","author"})
public class BoardData extends AbstractData {
	
	private Long seq;
	
	@Size(min = 2, max = 100, message = "Length between 2 - 100 characters.")
	private String title;
	
	@Size(min = 2, max = 100, message = "Length between 2 - 100 characters.")
	private String subtitle;
	
	@Size(min = 2, max = 200, message = "Length between 2 - 200 characters.")
	private String summary;
	
	@Size(min = 5, max = 400, message = "Length between 5 - 400 characters.")
	private String content;
	
	@Size(min = 2, max = 50, message = "Length between 2 - 50 characters.")
	private String author;
	
	private List<FileData> files;
	
	public BoardData() {
	}

	public BoardData(Long seq) {
		this.seq = seq;
	}
	
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<FileData> getFiles() {
		return files;
	}

	public void setFiles(List<FileData> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardData [seq=");
		builder.append(seq);
		builder.append(", title=");
		builder.append(title);
		builder.append(", subtitle=");
		builder.append(subtitle);
		builder.append(", summary=");
		builder.append(summary);
		builder.append(", content=");
		builder.append(content);
		builder.append(", author=");
		builder.append(author);
		builder.append(", files=");
		builder.append(files);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	
}
