package wicket_lecture;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import api.JSAcceptPage;
import api.JSRefusePage;
import wicket_lecture.LINEClone.TopPage;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 * @see wicket_lecture.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return TopPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		// add your configuration here

		mountPage("/JSAcceptPage", JSAcceptPage.class);
		mountPage("/JSRefusePage", JSRefusePage.class);

	}

	@Override
	public RuntimeConfigurationType getConfigurationType() {
//		return super.getConfigurationType();
		return RuntimeConfigurationType.DEPLOYMENT;
	}

}
