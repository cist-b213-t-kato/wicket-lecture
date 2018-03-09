package tips;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

class Cat implements Serializable {
	private static final long serialVersionUID = -2642851031804174335L;

	public long id;
	public String name;
	public Cat(long id, String name) {
		this.id = id;
		this.name = name;
	}
}

/**
 *
 * @author tkato
 *
 */
public class DropDownPage extends WebPage {

	private static final long serialVersionUID = 5348985128549742057L;

	public DropDownPage() {


		List<Cat> catList = new ArrayList<>();
		catList.add(new Cat(1, "よしたか"));
		catList.add(new Cat(2, "ジジ"));
		catList.add(new Cat(3, "課長"));

		DropDownChoice<Cat> dropDownChoice =
				new DropDownChoice<Cat>("dropDownChoice", new Model<>(catList.get(0)),
						catList, new ChoiceRenderer<>("name", "id"));

		queue(dropDownChoice);

		queue(new Form<Void>("form"){
			private static final long serialVersionUID = -4982720489384577097L;

			@Override
			protected void onSubmit() {
				super.onSubmit();
				Cat cat = dropDownChoice.getModelObject();
				System.out.println(cat.id + ":" + cat.name);
			}
		});

	}

}
