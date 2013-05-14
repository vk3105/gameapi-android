package com.playtomic.android;

import java.util.Hashtable;
import java.util.Iterator;
import org.json.JSONObject;
import org.json.JSONException;

public class GameVars {
    
    private static String SECTION = "gamevars";
    private static String LOAD = "load";
    private static String LOADSINGLE = "single";
    
    public static void load(final GameVarsHandler callback) {
    	PRequest.load(SECTION, LOAD, null, new PResponseHandler() {
			@Override
			public void onResponse(PResponse response, JSONObject data) {
				if(response.getSuccess())
				{
					callback.onSuccess(process(data), response);
				}
				else
				{
					callback.onFailure(response);
				}
			}
    	});
    }        

    public static void loadSingle(String name, final GameVarsHandler callback) {
    	
    	JSONObject postdata = new JSONObject();
    	
    	try {
    		postdata.put("name", name);
    	}catch(JSONException e) {
    		callback.onFailure(new PResponse(false, 1));
    		return;
    	}
    	
    	PRequest.load(SECTION, LOADSINGLE, postdata, new PResponseHandler() {
			@Override
			public void onResponse(PResponse response, JSONObject data) {		
				if(response.getSuccess())
				{
					callback.onSuccess(process(data), response);
				}
				else
				{
					callback.onFailure(response);
				}
			}
    	});
    }
    
    private static Hashtable<String, Object> process(JSONObject data) { 
		Hashtable<String, Object> gamevars = new Hashtable<String, Object>();
		Iterator<?> keys = data.keys();
		
	    while (keys.hasNext()) {
            String key = keys.next().toString();
            
            if(key == "status" || key == "errorcode") {
            	continue;
            }
            
            String value = data.optString(key);
            gamevars.put(key, value);
	    }
		
		return gamevars;
    }
}