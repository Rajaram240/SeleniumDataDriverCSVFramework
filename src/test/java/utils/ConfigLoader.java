package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
	Properties  properties=new Properties();;
		public ConfigLoader()
		{
		 try {
	            // Load properties file
	            FileInputStream fis = new FileInputStream("./configuration/Configure.properties");
	            properties.load(fis);
	            fis.close();
	 
	        } catch (IOException e) {
	            System.err.println("Error reading properties file: " + e.getMessage());
	        }
	    
	}
		public String getUsername() {
			 String Username = properties.getProperty("username");
			 System.out.println(Username);
			 return Username;
	    }
		public String getPassword() {
			 String Password = properties.getProperty("password");
			 return Password;
	    }
}