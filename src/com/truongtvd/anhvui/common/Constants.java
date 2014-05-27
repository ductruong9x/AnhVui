package com.truongtvd.anhvui.common;

public class Constants {

	public static final String FANPAGE_KEY = "383923618322771";
	public static final String FANPAGE_URL = "http://graph.facebook.com/383923618322771";
	public static String QUERY_INFO = "SELECT post_id, actor_id,created_time,description FROM stream WHERE filter_key in (SELECT filter_key FROM stream_filter WHERE uid =340505782696997 AND type = 'newsfeed') LIMIT ";
	public static final String APP_ID = "242607052596625";
	public static final String SERCEP_KEY = "114b74c6f76fee1795e8327ba950404d";
}
