package piterlin.doc.jdk;

public class Spring {
	public static void main(String[] args) {
		DocUtils.dealerPages("E:\\download\\spring-framework-4.3.3.RELEASE-docs", "utf8", "utf8",
				new Replacer() {

					public String dealerPage(String page) {
						page = page.replace("</title>",
								"</title><script>var _hmt = _hmt || [];(function() {var hm = document.createElement(\"script\");hm.src = \"//hm.baidu.com/hm.js?dd1361ca20a10cc161e72d4bc4fef6df\";var s = document.getElementsByTagName(\"script\")[0];s.parentNode.insertBefore(hm, s);})();</script>");
						return page;
					}
				});

	}
}
