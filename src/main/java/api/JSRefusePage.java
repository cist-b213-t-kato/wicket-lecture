package api;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.http.WebResponse;

import com.google.gson.Gson;

/**
 *
 * @author tkato
 *
 */
public class JSRefusePage extends WebPage {

	private static final long serialVersionUID = 5348985128549742057L;

    private static final String CONTENT_TYPE = "application/json";

    @Override
    protected void configureResponse(WebResponse response) {
        super.configureResponse(response);
        response.setContentType(CONTENT_TYPE);
    }

    public JSRefusePage() {

		Map<String, Object> map = new HashMap<>();
    	map.put("name", "suiseiseki");
    	map.put("job", "mywife");
    	map.put("null", null); // nullの時はJSONには何も出力されないみたい？

//    		getRequestCycle().scheduleRequestHandlerAfterCurrent(
//    				new TextRequestHandler(CONTENT_TYPE, "UTF-8", "{\"hoge\":\"piyo\"}"));
        getRequestCycle().scheduleRequestHandlerAfterCurrent(
        		new TextRequestHandler(CONTENT_TYPE, "UTF-8", new Gson().toJson(map)));

    }

}
