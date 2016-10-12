/**
 * 
 */
package piterlin.doc.jdk;

import java.io.File;

import org.apache.commons.io.FileUtils;

/**
 * @author 林子龙
 *
 */
public class Jdk6Builder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getFileMsg("D:\\git\\homesite\\piterlin.github.io\\doc\\jdk6_cn");
	}

	/**
	 * 加入跟追代码
	 */
	public static void getFileMsg(String url) {

		File file = new File(url);
		if (file.isFile() && file.getName().endsWith("html")) {
			System.out.println("当前文件url:" + url);
			try {
				String page = FileUtils.readFileToString(file, "gb2312");
				page=page.replace("</TITLE>", "</TITLE><script>var _hmt = _hmt || [];(function() {var hm = document.createElement(\"script\");hm.src = \"//hm.baidu.com/hm.js?dd1361ca20a10cc161e72d4bc4fef6df\";var s = document.getElementsByTagName(\"script\")[0];s.parentNode.insertBefore(hm, s);})();</script>");
				page=page.replace("gb2312", "utf-8");
				FileUtils.writeStringToFile(file, page, "utf8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		// System.out.println("进入目录：" + url);
		File[] files = file.listFiles();
		if(files==null){
			return;
		}
		for (int i = 0; i < files.length; i++) {
			getFileMsg(files[i].getAbsolutePath());
		}
	}

}
