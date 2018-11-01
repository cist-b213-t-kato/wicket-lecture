package wicket_lecture.LINEClone;

import org.apache.wicket.markup.html.link.Link;

import filemanage.FileManagementPage;
import wicket_lecture.LINEClone.Ver1.BoardPage;

public class TopPage extends MyPage {

    public TopPage(){

//        //最初はここから
//        Link<Void> linkExample = new Link<Void>("linkExample"){
//
//            @Override
//            public void onClick() {
//                System.out.println("click!");
//            }
//        };
//        add(linkExample);
//        //ここまで
//
//        //SysOutFormPageを作る時に追加 ここから
//        Link<Void> toSysOutFormPage = new Link<Void>("toSysOutFormPage") {
//            @Override
//            public void onClick() {
//                setResponsePage(new SysOutFormPage());
//            }
//        };
//        add(toSysOutFormPage);
//        //ここまで
//
//        //LabelFormPageを作る時に追加　ここまで
//        Link<Void> toLabelFormPage = new Link<Void>("toLabelFormPage"){
//
//            @Override
//            public void onClick() {
//                setResponsePage(new LabelFormPage());
//            }
//        };
//        add(toLabelFormPage);
//        //ここまで
//
//        //ListViewPageを作る時に追加　ここから
//        Link<Void> toListViewPage = new Link<Void>("toListViewPage"){
//
//            @Override
//            public void onClick() {
//                setResponsePage(new ListViewPage());
//            }
//        };
//        add(toListViewPage);
//        //ここまで
//
//        //BeanListViewPageを作る時に追加 ここから
//        Link<Void> toBeanListViewPage = new Link<Void>("toBeanListViewPage"){
//
//            @Override
//            public void onClick() {
//                setResponsePage(new BeanListViewPage());
//            }
//        };
//        add(toBeanListViewPage);
//        //ここまで
//
//        Link<Void> toDBPage = new Link<Void>("toDBPage"){
//
//            @Override
//            public void onClick() {
//                setResponsePage(new DBPage());
//            }
//        };
//        add(toDBPage);
//
//        Link<Void> toDBModelPage = new Link<Void>("toDBModelPage"){
//
//        	@Override
//        	public void onClick() {
//        		setResponsePage(new DBModelPage());
//        	}
//        };
//        add(toDBModelPage);
//
//        Link<Void> toAjaxPage = new Link<Void>("toAjaxPage"){
//
//        	@Override
//        	public void onClick() {
//        		setResponsePage(new AjaxPage());
//        	}
//        };
//        add(toAjaxPage);

        Link<Void> toBoardPage = new Link<Void>("toBoardPage"){

        	@Override
        	public void onClick() {
        		setResponsePage(new BoardPage());
        	}
        };
        add(toBoardPage);

        Link<Void> toFMPage = new Link<Void>("toFMPage"){

        	@Override
        	public void onClick() {
        		setResponsePage(new FileManagementPage());
        	}
        };
        add(toFMPage);

//        Link<Void> toCalendarPage = new Link<Void>("toCalendarPage"){
//
//        	@Override
//        	public void onClick() {
//        		setResponsePage(new CalendarPage());
//        	}
//        };
//        add(toCalendarPage);
//
//        Link<Void> toAPIPage = new Link<Void>("toAPIPage"){
//
//        	@Override
//        	public void onClick() {
//        		setResponsePage(new JSPage());
//        	}
//        };
//        add(toAPIPage);
//
//        Link<Void> toDropDownPage = new Link<Void>("toDropDownPage"){
//
//        	@Override
//        	public void onClick() {
//        		setResponsePage(new DropDownPage());
//        	}
//        };
//        add(toDropDownPage);

    }

}
