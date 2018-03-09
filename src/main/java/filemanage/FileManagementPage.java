package filemanage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

import wicket_lecture.LINEClone.MyPage;
/**
 *
 * @author tkato
 * @see http://d-kami.hatenablog.com/entry/20080324/1206355764
 */
public class FileManagementPage extends MyPage {
	private static final long serialVersionUID = 6702805165122149370L;

	/**
	 * @see http://d-kami.hatenablog.com/entry/20080324/1206355764
	 *
	 */
	public FileManagementPage() {
        Form<Void> form = new Form<Void>("uploadForm");
        FileUploadField uploadField = new FileUploadField("uploadField");

        //ファイルのアップロードに必要な設定
        form.setMultiPart(true);
        //アップロードできるのは1ギガバイトまで
        form.setMaxSize(Bytes.gigabytes(1));

        Button submitButton = new Button("uploadButton") {
            @Override
            public void onSubmit(){
                FileUpload upload = uploadField.getFileUpload();
                //ローカルファイルが無かった場合などは何もしない
                if ( upload == null )
                    return;

                try {
					InputStream is = upload.getInputStream();
					new FileDAO().insertFile(is, upload.getClientFileName());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        };

        form.add(uploadField);
        form.add(submitButton);
        add(form);

        Form<Void> downloadForm = new Form<Void>("downloadForm");
        add(downloadForm);

        ListModel<FileKeyBean> fileKeyBeanListModel = new ListModel<FileKeyBean>() {
        	@Override
        	public List<FileKeyBean> getObject() {
        		return new FileDAO().selectIdList();
        	}
        };

        DropDownChoice<FileKeyBean> choice = new DropDownChoice<FileKeyBean>(
        		"choice", new Model<>(), fileKeyBeanListModel, new ChoiceRenderer<>("name", "id"));
        downloadForm.add(choice);

        Button downloadButton = new Button("downloadButton") {

        	@Override
        	public void onSubmit() {
        		super.onSubmit();

        		FileKeyBean bean = choice.getModelObject();

        		// getContentType を Override すべきかも
        		ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(new AbstractResourceStreamWriter() {

        			@Override
        			public void write(OutputStream outputStream) {

        				long selectedId = bean.getId();
        				byte buf[] = new FileDAO().selectFile(selectedId);//new byte[256];
        				try {
        					outputStream.write(buf, 0, buf.length);
        				} catch (IOException e) {
        					e.printStackTrace();
        				}

        			}

        		}, bean.getName()); // ダウンロードファイル名

        		getRequestCycle().replaceAllRequestHandlers(handler); //これでダウンロードのダイアログが出る
        	}
        };

        downloadForm.add(downloadButton);

	}
}
