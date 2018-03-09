package calendar;

import org.apache.wicket.markup.html.WebPage;

import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.calendar.Calendar;

/**
 *
 * @author tkato
 *
 */
public class CalendarPage extends WebPage {

	private static final long serialVersionUID = 5348985128549742057L;

	public CalendarPage() {

		String message = getRequest().getPostParameters().getParameterValue("message").toString();

		System.out.println(message);

		Options options = new Options();
		options.set("theme", true);
		options.set("header", "{ left: 'title', right: 'month,agendaWeek,agendaDay, today, prev,next' }");

		Calendar calendar = new Calendar("calendar", options);

		this.add(calendar);


	}

}
