package net.guhya.boot.module.board.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import net.guhya.boot.common.data.AbstractData;
import net.guhya.boot.module.file.data.FileData;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"seq","title","subtitle","summary","content","author"})
public class Board extends AbstractData {
	
	private Long seq;
	private String title;
	private String subtitle;
	private String summary;
	private String content;
	private String author;
	private List<FileData> files;
	
	public Board() {
	}

	public Board(Long seq) {
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
		builder.append("Board [seq=");
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
