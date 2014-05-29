package com.truongtvd.anhvui.util;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.truongtvd.anhvui.model.CommentInfo;
import com.truongtvd.anhvui.model.ItemNewFeed;

public class JsonUtils {
	public static JSONObject parseResponToJson(Response response) {
		JSONObject json = new JSONObject();
		try {
			GraphObject go = response.getGraphObject();
			json = go.getInnerJSONObject();

		} catch (Exception e) {

		}
		return json;
	}

	public static ArrayList<ItemNewFeed> getListItem(JSONObject json,
			ArrayList<ItemNewFeed> list) {
		try {
			JSONArray data = json.getJSONArray("data");
			ItemNewFeed item;
			for (int i = 0; i < data.length(); i++) {
				JSONObject content = data.getJSONObject(i);
				item = new ItemNewFeed();
				String message = content.getString("caption");
				item.setMessage(message + "");
				JSONObject like_info = content.getJSONObject("like_info");
				if (like_info.has("like_count")) {
					item.setLike_count(like_info.getInt("like_count"));
				}
				JSONObject comment_info = content.getJSONObject("comment_info");
				if (comment_info.has("comment_count")) {
					item.setComment_count(comment_info.getInt("comment_count"));
				}
				item.setTime(content.getInt("created"));
				item.setPost_id(content.getString("object_id") + "");
				item.setImage(content.getString("src_big"));
				list.add(item);
				Collections.shuffle(list);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public static ArrayList<CommentInfo> getCommentInfo(JSONObject json,
			ArrayList<CommentInfo> list) {
		CommentInfo comment;

		try {
			JSONArray data = json.getJSONArray("data");
			JSONObject jcomment = data.getJSONObject(0);
			JSONObject juser = data.getJSONObject(1);
			JSONArray arrayComment = jcomment.getJSONArray("fql_result_set");
			JSONArray arrayUser = juser.getJSONArray("fql_result_set");
			for (int i = 0; i < arrayComment.length(); i++) {
				JSONObject com = arrayComment.getJSONObject(i);
				JSONObject user = arrayUser.getJSONObject(i);
				comment = new CommentInfo();
				comment.setAvatar(user.getString("pic"));
				comment.setComment(com.getString("text"));
				comment.setTime(com.getInt("time"));
				comment.setUid(user.getString("uid"));
				comment.setUsername(user.getString("name"));
				list.add(comment);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
