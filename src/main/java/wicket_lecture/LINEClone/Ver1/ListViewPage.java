package wicket_lecture.LINEClone.Ver1;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;

public class ListViewPage extends WebPage{
    IModel<String> bodyModel;
    List<String> bodyList;

    public ListViewPage(){
        bodyModel = Model.of("");
        bodyList = new ArrayList<>();

        Form<Void> form = new Form<Void>("form"){
            @Override
            protected void onSubmit() {
                super.onSubmit();
                bodyList.add(bodyModel.getObject());
            }
        };
        add(form);

        TextField<String> bodyField = new TextField<>("bodyField", bodyModel);
        form.add(bodyField);

        ListView<String> bodyListView = new ListView<String>("bodyList",bodyList) {
            @Override
            protected void populateItem(ListItem<String> item) {
                item.add(new Label("body", item.getModelObject()));
            }
        };

        add(bodyListView);
    }
}
