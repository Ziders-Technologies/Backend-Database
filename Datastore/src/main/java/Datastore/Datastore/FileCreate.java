package Datastore.Datastore;

import java.io.File;
import java.io.IOException;

public class FileCreate {
	public static FileClass fileCreate(){
		FileClass fileClass = new FileClass();
		 File file = new File("E:\\Database.json");
			boolean result = false;
			try   
			{  
				result = file.createNewFile();
				if(result)
				{
//					System.out.println("file created "+file.getCanonicalPath());
					fileClass.setFile(file);
					fileClass.setResult(result);
					return fileClass;
				}
				else
				{
//					System.out.println("File already exist at location: "+file.getCanonicalPath());
					fileClass.setFile(file);
					fileClass.setResult(result);
					return fileClass;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();    //prints exception if any
			}
			fileClass.setFile(file);
			fileClass.setResult(result);
			return fileClass;
	}
}
