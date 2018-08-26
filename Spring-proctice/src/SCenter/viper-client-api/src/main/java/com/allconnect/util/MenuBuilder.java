package com.A.util;

import org.apache.log4j.Logger;

public enum MenuBuilder {

	INSTANCE;

	public static final Logger logger = Logger.getLogger(MenuBuilder.class);
	
	public String build(  String data) {
		logger.debug("build menu ... ");

		data = data.replace(" of ", " ").replace(" information ", "");
		StringBuilder contents = new StringBuilder("<div  class=\"link-image\"> <a href=\"#0\">Top</a></div>");
		String[] lines = data.split(System.getProperty("line.separator"));

		try {

			int i=1;
			boolean first = true;
			boolean isPrevLineSection = false;
			for (String text : lines) {
				int headerTemplateIndex = text.indexOf("lheadertemplate");
				
				if (headerTemplateIndex == -1) {
					headerTemplateIndex = text.indexOf("orderTitletemplate");
				}
				
				if (headerTemplateIndex == -1) {
					headerTemplateIndex = text.indexOf("ordertitletemplate");
				}
				
				if(text.indexOf("numberCircle") > 0 || text.indexOf("numbercircle") > 0){
					isPrevLineSection = true;
				}
				
				if (headerTemplateIndex != -1 && isPrevLineSection) {
					int titleStart = text.indexOf("\">", headerTemplateIndex) + 2;
					int titleEnd = text.indexOf("</", titleStart);

					if ((titleStart != -1) && (titleEnd != -1)) {
						String title = text.substring(titleStart,
								titleEnd);
		
						if(!first){
							title = "<div  class=\"link-image\"> <span class=\"menuNumber\">"+(i)+" - </span> <a href=\"#"+(i++)+"\">"+title+"</a></div>";
							contents.append(title).append(System.getProperty("line.separator"));
						} else {
							first = false;
						}
					}
					isPrevLineSection = false;
				}
			}

			return contents.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents.toString();
	}

	 
	public static void main(String[] arg) {

		String fileName = "c:\\projects\\fulfillment\\tmp\\verizon\\20411442_10311.vm";
		String data = FileUtil.getStringContent(fileName);

		String rR = MenuBuilder.INSTANCE.build(data);

		logger.info(rR);
	}
}
