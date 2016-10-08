/**
 * 
 */
package piterlin.builder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 页面生成器
 * 
 * @author 林子龙
 *
 */
public class BuilderService {
	
	public void buildIndex(List<Site> sites) throws IOException{
		String siteStrs = "";
		String tpl = FileUtils
				.readFileToString(new File("D:\\git\\homesite\\builder\\target\\classes\\tpl\\index.html"), "utf8");
		for (int i = 0; i < sites.size(); i++) {
			siteStrs += this.buildSingleSite(sites.get(i));
		}
		String sitePage=tpl.replace("###site", siteStrs);
		File pageFile = new File("D:\\git\\homesite\\builder\\target\\classes\\site\\index.html");
		FileUtils.write(pageFile, sitePage, "utf8");
	}

	/**
	 * 构建所有模板页面
	 */
	public void buildTpls(List<Site> sites) throws IOException {
		int page = 0;
		String siteStrs = "";
		String tpl = FileUtils
				.readFileToString(new File("D:\\git\\homesite\\builder\\target\\classes\\tpl\\template.html"), "utf8");
		for (int i = 0; i < sites.size(); i++) {
			siteStrs += this.buildSingleSite(sites.get(i));
			if ((i + 1) % 4 == 0) {
				buildSingleSitePage(page, siteStrs, tpl, i == sites.size() - 1);
				siteStrs = "";
				page++;
			}
		}
		if (StringUtils.isNotEmpty(siteStrs)) {
			buildSingleSitePage(page, siteStrs, tpl, true);
		}
	}

	/**
	 * 获取上一页的按钮
	 */
	public String getPre(int page) {
		String result = "";
		String pageStr = "";
		if (page == 0) {
			return result;
		}
		pageStr = page == 1 ? "" : (page - 1) + "";
		return "<li class=\"previous\"><a href=\"" + "template" + pageStr + ".html" + "\">&larr; 上一页</a></li>";
	}

	/**
	 * 获取下一页的按钮
	 */
	public String getNext(int page) {
		String pageStr = (page + 1) + "";
		return "<li class=\"next\"><a href=\"./template" + pageStr + ".html\">下一页 &rarr;</a></li>";
	}

	/**
	 * 构建单个下载页
	 */
	public String buildSingleSite(Site site) {
		String result = "<div class=\"col-md-3 col-sm-6 hero-feature\"><div class=\"thumbnail\"><img src=\""
				+ site.getIconUrl() + "\"><div class=\"caption\"><h3>" + site.getName() + "</h3><p>" + site.getDesc()
				+ "</p><p><a href=\"" + site.getFileUrl() + "\" class=\"btn btn-primary\">下载</a></p></div></div></div>";
		return result;
	}

	/**
	 * 构建一个页面
	 */
	public void buildSingleSitePage(int page, String siteStr, String tpl, Boolean isLast) throws IOException {
		String sitePage = tpl.replace("###site", siteStr).replace("###pre", this.getPre(page)).replace("###next",
				isLast ? "" : this.getNext(page));
		String pageStr = page == 0 ? "" : page + "";
		File pageFile = new File("D:\\git\\homesite\\builder\\target\\classes\\site\\template" + pageStr + ".html");
		FileUtils.write(pageFile, sitePage, "utf8");
	}

	/**
	 * 获取单个下载页信息
	 */
	public List<Site> getSites(FileFilter filter) throws IOException {
		List<Site> sites = new LinkedList<Site>();
		File root = new File("D:\\git\\homesite\\builder\\target\\classes\\site\\res");
		String siteRoot = "D:\\git\\homesite\\builder\\target\\classes\\site";

		File[] dirs = null;
		if (filter == null) {
			dirs = root.listFiles();
		} else {
			dirs = root.listFiles(filter);
		}

		for (File dir : dirs) {
			if (dir.isDirectory() && dir.getName().startsWith("201")) {
				File[] files = dir.listFiles();
				Site site = new Site();
				for (File file : files) {
					if ("p.png".equals(file.getName())) {
						site.setIconUrl(file.getPath());
						site.setIconUrl(site.getIconUrl().replace(siteRoot, ".").replaceAll("\\\\", "/"));
					}
					if (file.getName().endsWith("zip") || file.getName().endsWith("rar")) {
						site.setFileUrl(file.getPath());
						site.setFileUrl(site.getFileUrl().replace(siteRoot, ".").replaceAll("\\\\", "/"));
					}
					if (file.getName().equals("r.txt")) {
						List<String> lines = FileUtils.readLines(file, "gbk");
						site.setName(lines.get(0));
						site.setDesc(lines.get(1));
					}
				}
				sites.add(site);
				System.out.println(ReflectionToStringBuilder.toString(site));
			}

		}
		return sites;
	}
}
