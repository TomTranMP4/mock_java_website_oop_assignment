package model;

public class commentModel {
	
	private int commentID;
	private int postID;
	private String commentContent;
	private String commentUsername;
	private String commentDate;
	
	public int getCommentID() {
		return commentID;
	}
	
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	public int getPostID() {
		return postID;
	}
	
	public String getCommentContent() {
		return commentContent;
	}
	
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	public String getCommentUsername() {
		return commentUsername;
	}
	
	public void setCommentUsername(String commentUsername) {
		this.commentUsername = commentUsername;
	}
	
	public String getCommentDate() {
		return commentDate;
	}
	
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	
}
