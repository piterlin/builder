package piterlin.builder.bookmd;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import piterlin.builder.RegexUtils;

/**
 * thinking in java
 * 
 * @author 林子龙
 *
 */
public class TijBook {

	public static void main(String[] args) throws IOException {
		new TijBook().transBook();
	}

	public List<Cat> parseCat(String url) throws IOException {
		List<Cat> result = new LinkedList<Cat>();
		String catPage = FileUtils.readFileToString(new File(url), "utf8");
		String[] lines = catPage.split(Pattern.quote("*"));
		for (String line : lines) {
			if (StringUtils.isBlank(line)) {
				continue;
			}
			if (line.indexOf("]") == -1) {
				continue;
			}
			line = line.trim();
			System.out.println(line);
			Cat cat = new Cat();
			cat.mdFile = RegexUtils.getString(line, Pattern.quote("(") + "(.*?)" + Pattern.quote(")"));
			cat.htmlFile = System.currentTimeMillis() + "_" + new Random().nextInt() + ".html";
			cat.name = RegexUtils.getString(line, Pattern.quote("[") + "(.*?)" + Pattern.quote("]"));
			if (cat.name.indexOf(".") == -1) {
				cat.level = 3;
			} else {
				cat.level = 4;
			}
			System.out.println(ReflectionToStringBuilder.reflectionToString(cat));
			result.add(cat);
		}
		return result;
	}

	/**
	 *获取cat的a代码
	 */
	public String getLinkHtml(Cat cat){
		return "<a href='./" + cat.htmlFile + "'>" + cat.name + "</a>";
	}
	/**
	 * 构建整块目录html
	 */
	public String buildCatHtml(List<Cat> cats) {
		String result = "<ul><li>";
		int preLevel = 3;
		for (Cat cat : cats) {
			if (preLevel == 3 && cat.level == 3) {
				result += this.getLinkHtml(cat)+"</li><li>";
			} else if (preLevel == 3 && cat.level == 4) {
				result += "<ul><li>"+this.getLinkHtml(cat)+"</li>";
			}else if(preLevel==4&&cat.level==4){
				result+="<li>"+this.getLinkHtml(cat)+"</li>";
			}else if(preLevel==4&&cat.level==3){
				result+="</ul></li><li>"+this.getLinkHtml(cat)+"</li><li>";
			}
			preLevel=cat.level;
		}
		if(preLevel==3){
			result+="</li>";
		}else{
			result+="</ul></li>";
		}
		return result;
	}

	public void transBook() throws IOException {
		String sourceDir = "C:/Users/Administrator/Desktop/think-in-java-master/think-in-java-master";
		List<Cat> cats = this.parseCat(sourceDir + "/SUMMARY.md");
		String tpl = FileUtils.readFileToString(new File("D:/git/homesite/piterlin.github.io/book/tij/tij_tpl.html"), "utf8");
		String htmlDir = "D:/git/homesite/piterlin.github.io/book/tij";
		String catsHtml=this.buildCatHtml(cats);
		System.out.println("cat"+catsHtml);
		for (Cat cat : cats) {
			String content=FileUtils.readFileToString(new File(sourceDir+"/"+cat.mdFile),"utf8");
			content=content.replaceAll("\\n", "<br>");
			String destContent=tpl.replace("##cat", catsHtml);
			destContent=destContent.replace("##content", content);
			FileUtils.writeStringToFile(new File(htmlDir+"/"+cat.htmlFile), destContent, "utf8");
		}
		System.out.println("结束");
	}
}
