package wicket_lecture.LINEClone.Ver1;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class BeanListViewPage extends WebPage{
    IModel<String> bodyModel;
    IModel<String> nameModel;
    List<Message> bodyList;

    public BeanListViewPage() {
        bodyModel = Model.of("");
        nameModel = Model.of("");
        bodyList = new ArrayList<>();

        Form<Void> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                bodyList.add(new Message(nameModel.getObject(), bodyModel.getObject()));
            }
        };
        add(form);

        TextField<String> nameField = new TextField<>("nameField", nameModel);
        form.add(nameField);
        TextField<String> bodyField = new TextField<>("bodyField", bodyModel);
        form.add(bodyField);

        ListView<Message> bodyListView = new ListView<Message>("bodyList", bodyList) {
            @Override
            protected void populateItem(ListItem<Message> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new Label("body", item.getModelObject().getBody()));
            }
        };

        add(bodyListView);
    }

}
