package api;

import org.apache.wicket.markup.html.WebPage;

/**
 *
 * @author tkato
 *
 */
public class JSAcceptPage extends WebPage {

	private static final long serialVersionUID = 5348985128549742057L;

	public JSAcceptPage() {

		String message = getRequest().getPostParameters().getParameterValue("message").toString();

		System.out.println(message);

	}

}
