package wicket_lecture.LINEClone.Ver1;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class SysOutFormPage extends WebPage{
    public SysOutFormPage(){
        final IModel<String> bodyModel = Model.of("");

        Form<Void> form = new Form<Void>("form"){
            @Override
            protected void onSubmit() {
                super.onSubmit();
                System.out.println(bodyModel.getObject());
            }
        };
        add(form);

        TextField<String> bodyField = new TextField<>("bodyField", bodyModel);
        form.add(bodyField);
    }
}
