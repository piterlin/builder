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
public class DocUtils {
	/**
	 * 处理页面
	 */
	public static void dealerPages(String url, String inputCode, String outputCode, Replacer placer) {
		File file = new File(url);
		if (file.isFile() && file.getName().endsWith("html")) {
			System.out.println("当前文件url:" + url);
			try {
				String page = FileUtils.readFileToString(file, inputCode);
				page=placer.dealerPage(page);
				FileUtils.writeStringToFile(file, page, outputCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			dealerPages(files[i].getAbsolutePath(), inputCode, outputCode, placer);
		}
	}

}
