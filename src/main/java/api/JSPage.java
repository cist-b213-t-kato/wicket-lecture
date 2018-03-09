package api;

import org.apache.wicket.markup.html.WebPage;

/**
 *
 * @author tkato
 * @see http://d.hatena.ne.jp/n314/20120131/1327982795
 * @see https://github.com/apache/wicket/blob/master/wicket-core/src/main/java/org/apache/wicket/ajax/res/js/wicket-ajax-jquery.js?source
 * @see https://www.youtube.com/watch?v=aqNQShdSOvM
 * @see https://qiita.com/chrischris0801/items/a8beaeffb58b618d64a4
 * @see http://d.hatena.ne.jp/sekom/20111226/p1
 * @see spring https://qiita.com/maekun/items/d092e9c9453b6b901b14
 *
 */
// TODO 要CSRF対策？
public class JSPage extends WebPage {

	private static final long serialVersionUID = 5348985128549742057L;
//	private final AbstractAjaxBehavior behavior;

//	class MyBehavior extends AbstractDefaultAjaxBehavior {
//		private static final long serialVersionUID = 9174045872699723888L;
//
//		@Override
//		public void renderHead(Component component, IHeaderResponse response) {
////			super.renderHead(component, response); // 呼んではいけない。謎。
//			response.render(JavaScriptHeaderItem.forScript(String.format("var myurl='%s';", behavior.getCallbackUrl()), this.toString()));
//		}
//
//		@Override
//		protected void respond(AjaxRequestTarget target) {
//			String message = getRequest().getRequestParameters().getParameterValue("message").toString();
//
//			System.out.println(message);
//		}
//
//	}

	public JSPage() {

//		behavior = new MyBehavior();
//		add(behavior);

	}

}
