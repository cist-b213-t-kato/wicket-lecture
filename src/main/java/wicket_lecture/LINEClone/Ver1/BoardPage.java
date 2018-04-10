package wicket_lecture.LINEClone.Ver1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.time.Duration;

import wicket_lecture.DBUtil;

public class BoardPage extends WebPage{
    IModel<String> bodyModel;
    IModel<String> nameModel;
    ListModel<Message> messageListModel;


	//コネクションの取得
	public static List<Message> select() {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		List<Message> messageList = new ArrayList<>();

		try ( Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement("select * from message"); ) {
			ResultSet rs = ps.executeQuery();

			while ( rs.next() ) {
				String name = rs.getString("name");
				String body = rs.getString("body");
				Message message = new Message(name, body);
				messageList.add(message);
			}

			rs.close();

		}catch(SQLException e){
			System.err.println("コネクションの取得に失敗しました。");
			e.printStackTrace();
		}

		return messageList;

	}

	//コネクションの取得
	public static void insert( Message message ) {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try ( Connection conn = DBUtil.getConnection(); ) {
			String sql = "insert into message( name, body ) VALUES( ?, ? )";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, message.getName());
			ps.setString(2, message.getBody());
			ps.executeUpdate();

			ps.close();

		}catch(SQLException e){
			System.err.println("コネクションの取得に失敗しました。");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		select();

	}

    public BoardPage() {
        bodyModel = Model.of("");
        nameModel = Model.of("");
        messageListModel = new ListModel<Message>(){
        	@Override
        	public List<Message> getObject() {
        		return select();
        	}
        };

        TextField<String> nameField = new TextField<>("nameField", nameModel);
        TextField<String> bodyField = new TextField<>("bodyField", bodyModel);

        Form<Void> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                Message message = new Message(nameModel.getObject(), bodyModel.getObject());
                insert(message);
                bodyField.setModel(Model.of(""));
            }
        };
        add(form);
        form.add(nameField);
        form.add(bodyField);

        WebMarkupContainer messageContainer = new WebMarkupContainer("messageContainer");
        add(messageContainer);

        ListView<Message> messageListView = new ListView<Message>("messageListView", messageListModel) {
            @Override
            protected void populateItem(ListItem<Message> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new Label("body", item.getModelObject().getBody()));
            }
        };

        messageListView.setOutputMarkupId(true);

        messageContainer.add(messageListView);

        AjaxSelfUpdatingTimerBehavior behavior = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)){
        	@Override
        	protected void onPostProcessTarget(AjaxRequestTarget target) {
        		super.onPostProcessTarget(target);
        		target.add(messageContainer);
    			target.appendJavaScript("messageReload();");
        	}
        };

        messageContainer.add(behavior);

    }

}
