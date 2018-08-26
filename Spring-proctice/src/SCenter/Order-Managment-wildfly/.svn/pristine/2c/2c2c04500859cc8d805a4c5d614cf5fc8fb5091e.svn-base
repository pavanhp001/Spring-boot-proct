package com.AL.ome.ie.arbiter;

import java.io.File;
import java.io.IOException;

public class TestFileCreate {

	 

		public static void main(String[] arg)
		{
			File dir1 = new File (".");
			String searchDir = "\\ome-trunk\\";
			
			
			try {
				System.out.println (  dir1.getCanonicalPath());
				int indexOfSearchDir = dir1.getCanonicalPath().indexOf(searchDir);
				String pathToSearchDir = dir1.getCanonicalPath().substring(0,indexOfSearchDir);
				System.out.println ( pathToSearchDir );
				System.out.println ( pathToSearchDir+searchDir );
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	 

}
