/**
 * 
 */
package piterlin.doc.jdk;

/**
 * @author 林子龙
 *
 */
public class Technode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DocUtils.dealerPages("E:\\download\\jdk-8u101-docs-all\\docs\\technotes", "utf8", "utf8", new Replacer() {
			public String dealerPage(String page) {
				page = page.replace("</title>",
						"</title><script>var _hmt = _hmt || [];(function() {var hm = document.createElement(\"script\");hm.src = \"//hm.baidu.com/hm.js?dd1361ca20a10cc161e72d4bc4fef6df\";var s = document.getElementsByTagName(\"script\")[0];s.parentNode.insertBefore(hm, s);})();</script>");
				return page;
			}
		});

	}

}
