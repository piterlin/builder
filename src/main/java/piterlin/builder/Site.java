/**
 * 
 */
package piterlin.builder;

/**
 * @author 林子龙
 *
 */
public class Site {
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 图标的url
	 */
	private String iconUrl;
	/**
	 * 下载文件的url
	 */
	private String fileUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
