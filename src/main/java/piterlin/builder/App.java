package piterlin.builder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!");
		BuilderService builder = new BuilderService();
		builder.buildTpls(builder.getSites(null));
		builder.buildIndex(builder.getSites(new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.getName().indexOf("20161008_9") >= 0 || pathname.getName().indexOf("20161008_4") >= 0
						|| pathname.getName().indexOf("20161008_3") >= 0
						|| pathname.getName().indexOf("20161008_7") >= 0) {
					return true;
				} else {
					return false;
				}
			}
		}));
	}
}
