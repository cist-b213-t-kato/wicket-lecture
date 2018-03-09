package wicket_lecture.LINEClone.Ver1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.apache.wicket.model.util.ListModel;

public class DBModelPage extends WebPage{
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

		try ( Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/board","postgres","postgres");
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

		String url = "jdbc:postgresql://localhost:5432/board";
		String user = "postgres";
		String pass = "postgres";

		try ( Connection conn = DriverManager.getConnection(url , user, pass); ) {
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

    public DBModelPage() {
        bodyModel = Model.of("");
        nameModel = Model.of("");
        messageListModel = new ListModel<Message>(){
        	@Override
        	public List<Message> getObject() {
        		return select();
        	}
        };

        Form<Void> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                Message message = new Message(nameModel.getObject(), bodyModel.getObject());
                insert(message);
            }
        };
        add(form);

        TextField<String> nameField = new TextField<>("nameField", nameModel);
        form.add(nameField);
        TextField<String> bodyField = new TextField<>("bodyField", bodyModel);
        form.add(bodyField);

        ListView<Message> bodyListView = new ListView<Message>("bodyList", messageListModel) {
            @Override
            protected void populateItem(ListItem<Message> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new Label("body", item.getModelObject().getBody()));
            }
        };

        add(bodyListView);
    }

}
