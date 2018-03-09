package filemanage;

import java.io.Serializable;

public class FileKeyBean implements Serializable {

	private static final long serialVersionUID = -5521076138824932829L;

	private long id;
	private String name;

	public FileKeyBean( long id, String name ) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
