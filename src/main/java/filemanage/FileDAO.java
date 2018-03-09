package filemanage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import wicket_lecture.DBSetting;

/**
 *
 * @author tkato
 * @see https://www.postgresql.jp/document/7.4/html/jdbc-binary-data.html
 * @see https://www.postgresql.jp/document/pg653doc/ej/programmer/x8420.htm
 * @see http://nemoplus.hateblo.jp/entry/20090221/1235230805
 */
public class FileDAO {

	public void insertFile( String fileName ) {
		// ファイルを開きます。
		File file = new File(fileName);
		try {
			InputStream fis = new FileInputStream(file);
			insertFile(fis, fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void insertFile( InputStream fis, String fileName ) {

		try (Connection conn = DBSetting.getConnection();
				PreparedStatement ps = conn.prepareStatement("insert into file(name, objectid) values(?, ?)");) {

			// 全ての LargeObject API の呼び出しはトランザクション内部でなければなりません。
			conn.setAutoCommit(false);

			// 操作を行なうためにラージオブジェクトマネージャを入手します。
			LargeObjectManager lom = ((org.postgresql.PGConnection) conn).getLargeObjectAPI();

			// 新規にラージオブジェクトを作成します。
			long oid = lom.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);

			// 書き出すためにラージオブジェクトを開きます。
			LargeObject obj = lom.open(oid, LargeObjectManager.WRITE);

			// ファイル内のデータをラージオブジェクトにコピーします。
			byte buf[] = new byte[2048];
			for ( int s; (s = fis.read(buf, 0, 2048)) > 0; ) {
				obj.write(buf, 0, s);
			}

			// ラージオブジェクトを閉じます。
			obj.close();

			// imageslo に行を挿入します。
			ps.setString(1, fileName);
			ps.setLong(2, oid);
			ps.executeUpdate();

			fis.close();

			//コミットする（結果の反映）
			conn.commit();

		} catch (SQLException | IOException e) {
			System.err.println("error");
			e.printStackTrace();
		}
	}

	public byte[] selectFile(long id) {

		byte[] buf = null;

		try (Connection conn = DBSetting.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT objectid FROM file WHERE id = ?");) {
			// 全ての LargeObject API の呼び出しはトランザクション内部でなければなりません。
			conn.setAutoCommit(false);

			// 操作を行なうためにラージオブジェクトマネージャを入手します。
			LargeObjectManager lobj = ((org.postgresql.PGConnection) conn).getLargeObjectAPI();

			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				// 読み取り用にラージオブジェクトを開く
				long oid = rs.getLong(1);
				LargeObject obj = lobj.open(oid, LargeObjectManager.READ);

				// データを読みとる
				buf = new byte[obj.size()];
				obj.read(buf, 0, obj.size());

				// 読みとったデータを使用してここで何かを行う

				obj.close();
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return buf;

	}

	public List<FileKeyBean> selectIdList() {

		List<FileKeyBean> list = new ArrayList<>();

		try (Connection conn = DBSetting.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT id, name FROM file");) {

			ResultSet rs = ps.executeQuery();

			while ( rs.next() ) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				list.add(new FileKeyBean(id, name));
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		}

		return list;
	}

	public static void main(String[] args) {

		byte[] buf = new FileDAO().selectFile(6);

		try (FileOutputStream fileOutStm = new FileOutputStream("/Users/tkato/Desktop/nuko2.pdf");) {
			for (int i = 0; i < buf.length; i++) {
				fileOutStm.write(buf[i]);
			}
		} catch (FileNotFoundException e1) {
			System.err.println("ファイルが見つかりませんでした");
			e1.printStackTrace();
		} catch (IOException e2) {
			System.err.println("入出力エラー");
			e2.printStackTrace();
		}

	}
}




