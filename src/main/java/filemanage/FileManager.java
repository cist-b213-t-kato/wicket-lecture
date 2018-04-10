package filemanage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import wicket_lecture.DBUtil;

/**
 *
 * @author tkato
 * @see https://www.postgresql.jp/document/7.4/html/jdbc-binary-data.html
 */
public class FileManager {

	public static void upload( String fileName ) {

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement("insert into file(objectid) values(?)");) {

			// 全ての LargeObject API の呼び出しはトランザクション内部でなければなりません。
			conn.setAutoCommit(false);

			// 操作を行なうためにラージオブジェクトマネージャを入手します。
			LargeObjectManager lom = ((org.postgresql.PGConnection) conn).getLargeObjectAPI();

			// 新規にラージオブジェクトを作成します。
			long oid = lom.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);

			// 書き出すためにラージオブジェクトを開きます。
			LargeObject obj = lom.open(oid, LargeObjectManager.WRITE);

			// ファイルを開きます。
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);

			// ファイル内のデータをラージオブジェクトにコピーします。
			byte buf[] = new byte[2048];
			for ( int s; (s = fis.read(buf, 0, 2048)) > 0; ) {
				obj.write(buf, 0, s);
			}

			// ラージオブジェクトを閉じます。
			obj.close();

			// imageslo に行を挿入します。
			// ps.setString(1, file.getName());
			ps.setLong(1, oid);
			ps.executeUpdate();

			fis.close();

			//コミットする（結果の反映）
			conn.commit();

		} catch (SQLException | IOException e) {
			System.err.println("error");
			e.printStackTrace();
		}
	}

	public static void download(String fileName) {

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT objectid FROM file WHERE id = 4");) {
			// 全ての LargeObject API の呼び出しはトランザクション内部でなければなりません。
			conn.setAutoCommit(false);

			// 操作を行なうためにラージオブジェクトマネージャを入手します。
			LargeObjectManager lobj = ((org.postgresql.PGConnection) conn).getLargeObjectAPI();

			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				// 読み取り用にラージオブジェクトを開く
				long oid = rs.getLong(1);
				LargeObject obj = lobj.open(oid, LargeObjectManager.READ);

				// データを読みとる
				byte buf[] = new byte[obj.size()];
				obj.read(buf, 0, obj.size());
				// 読みとったデータを使用してここで何かを行う
				try (FileOutputStream fileOutStm = new FileOutputStream(fileName);) {
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

				obj.close();
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		download("/Users/tkato/Desktop/nuko2.jpg");

	}
}




