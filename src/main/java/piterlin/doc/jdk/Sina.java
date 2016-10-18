package piterlin.doc.jdk;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class Sina {

	public static void main(String[] args) throws MalformedURLException, IOException {
		String content = IOUtils.toString(
				new URL("http://blog.sina.com.cn/u/6048954839").openStream(), "utf8");
		System.out.println("a"+content);
	}

}
