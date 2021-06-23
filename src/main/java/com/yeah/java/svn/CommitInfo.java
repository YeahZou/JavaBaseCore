/**
 * 
 */
package com.yeah.java.svn;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**   
 * @ClassName   CommitInfo   
 * @Description TODO
 * @author      zouye
 * @date        2021-06-23   
 *    
 */
public class CommitInfo {
	private String id = null;
	private String commitId = null;
	private String comment = null;
	private String message = null;

	private String author = null;
	private String authorEmail = "";
	private Date authorDate = null;
	private String committer = null;
	private String committerEmail = "";
	private Date committerDate = null;

	private Long authorDateTimestamp = null;
	private Long committerDateTimestamp = null;
	
	
	public CommitInfo() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.commitId = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
		this.message = comment;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public Date getAuthorDate() {
		return authorDate;
	}

	public void setAuthorDate(Date authorDate) {
		this.authorDate = authorDate;
	}

	public String getCommitter() {
		return committer;
	}

	public void setCommitter(String committer) {
		this.committer = committer;
	}

	public String getCommitterEmail() {
		return committerEmail;
	}

	public void setCommitterEmail(String committerEmail) {
		this.committerEmail = committerEmail;
	}

	public Date getCommitterDate() {
		return committerDate;
	}

	public void setCommitterDate(Date committerDate) {
		this.committerDate = committerDate;
	}

	@Override
	public String toString() {
		String content = "";
		content = content + "commitId: " + id + "\n";
		content = content + "Author:\t" + author + " " + authorEmail + "\n";
		content = content + "Author Date:\t" + authorDate + "\n";
		content = content + "committer:\t" + committer + " " + committerEmail + "\n";
		content = content + "Commit Date:\t" + committerDate + "\n";
		content = content + "Message:\t" + comment + "\n";
		
		return content;
	}

	// 兼容写法
	public String getCommitId() {
		return commitId;
	}

	public String getMessage() {
		return message;
	}
	
	public Long getAuthorDateTimestamp() {
		if (authorDate == null) {
			return null;
		}
		
		return authorDate.getTime();
	}
	
	public Long getCommitterDateTimestamp() {
		if (committerDate == null) {
			return null;
		}
		
		return committerDate.getTime();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		
		if (this.getClass() != other.getClass()) {
			return false;
		}
		
		return this.id.equals(((CommitInfo)other).getId());
	}
}
