package piterlin.builder.bookmd;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import piterlin.builder.RegexUtils;

/**
 * css3t
 * 
 * @author 林子龙
 *
 */
public class Css3tbook {

	public static void main(String[] args) throws IOException {
		new Css3tbook().transBook();
	}

	public List<Cat> parseCat(String url) throws IOException {
		List<Cat> result = new LinkedList<Cat>();
		List<String> lines = FileUtils.readLines(new File(url), "utf8");
		int i=0;
		for (String line : lines) {
			if (StringUtils.isBlank(line)) {
				continue;
			}
			line = line.trim();
			System.out.println(line);
			Cat cat = new Cat();
			cat.mdFile = RegexUtils.getString(line, Pattern.quote("/") + "(.*?)" + Pattern.quote(")"))+"";
			cat.htmlFile = i + ".html";
			i++;
			cat.name = RegexUtils.getString(line, Pattern.quote("[") + "(.*?)" + Pattern.quote("]"));
				cat.level = 3;
			System.out.println(ReflectionToStringBuilder.reflectionToString(cat));
			result.add(cat);
		}
		return result;
	}

	/**
	 * 获取cat的a代码
	 */
	public String getLinkHtml(Cat cat) {
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
				result += this.getLinkHtml(cat) + "</li><li>";
			} else if (preLevel == 3 && cat.level == 4) {
				result += "<ul><li>" + this.getLinkHtml(cat) + "</li>";
			} else if (preLevel == 4 && cat.level == 4) {
				result += "<li>" + this.getLinkHtml(cat) + "</li>";
			} else if (preLevel == 4 && cat.level == 3) {
				result += "</ul></li><li>" + this.getLinkHtml(cat) + "</li><li>";
			}
			preLevel = cat.level;
		}
		if (preLevel == 3) {
			result += "</li>";
		} else {
			result += "</ul></li>";
		}
		return result;
	}

	public void transBook() throws IOException {
		String sourceDir = "D:/git/book/css3-tutorial-master/docs";
		List<Cat> cats = this.parseCat(sourceDir + "/SUMMARY.md");
		String tpl = FileUtils.readFileToString(new File("D:/git/homesite/piterlin.github.io/book/csst3/tpl.html"),
				"utf8");
		String htmlDir = "D:/git/homesite/piterlin.github.io/book/csst3";
		String catsHtml = this.buildCatHtml(cats);
		System.out.println("cat" + catsHtml);
		Cat preCat = null;
		for (int i = 0; i < cats.size(); i++) {
			Cat cat = cats.get(i);
			String content = FileUtils.readFileToString(new File(sourceDir + "/" + cat.mdFile), "utf8");
			content = content.replaceAll("\\n", "<br>");
			String destContent = tpl.replace("##cat", catsHtml);
			destContent = destContent.replace("##content", content);
			String preLink="";
			if(preCat!=null){
			preLink="<li class='previous'><a href='"+preCat.htmlFile+"'>&larr; "+preCat.name+"</a></li>";
			}
			destContent=destContent.replace("###pre", preLink);
			destContent=destContent.replace("###pre", preLink);
			String nextLink="";
			if(i!=cats.size()-1){
				nextLink="<li class='next'><a href='"+cats.get(i+1).htmlFile+"'>"+cats.get(i+1).name+"&rarr;</a></li>";
			}
			destContent=destContent.replace("###next", nextLink);
			destContent=destContent.replace("###next", nextLink);
			FileUtils.writeStringToFile(new File(htmlDir + "/" + cat.htmlFile), destContent, "utf8");
			preCat=cat;
		}
		System.out.println("结束");
	}
}
