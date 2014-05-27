package com.truongtvd.anhvui.model;

public class ItemNewFeed {
	private String message;
	private String post_id;
	private String image;
	private String defaut_image;
	private int time;
	private int like_count;
	private int comment_count;

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public int getLike_count() {
		return like_count;
	}

	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDefaut_image() {
		return defaut_image;
	}

	public void setDefaut_image(String defaut_image) {
		this.defaut_image = defaut_image;
	}

}
