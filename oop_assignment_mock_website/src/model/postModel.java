package model;

import model.categoryModel;

public class postModel {
	
	private int postID;
	private String postTitle;
	private String postIntroduction;
	private String postContent;
	private categoryModel postCategory = null;
	private String postDate;
	private int postReadingCount;
	private int postCategoryID;
	
	public int getPostCategoryID() {
		return postCategoryID;
	}
	public void setPostCategoryID(int postCategoryID) {
		this.postCategoryID = postCategoryID;
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public void setPostReadingCount(int postReadingCount) {
		this.postReadingCount = postReadingCount;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostIntroduction() {
		return postIntroduction;
	}
	public void setPostIntroduction(String postIntroduction) {
		this.postIntroduction = postIntroduction;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	
	public categoryModel getPostCategory() {
		return postCategory;
	}
	public void setPostCategory(categoryModel postCategory) {
		this.postCategory = postCategory;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public int getPostReadingCount() {
		return postReadingCount;
	}
}
