package classes;

import java.io.File;

public class newgradebook {
   
	public void newGradeBook()
	{

      File f = null;
      boolean bool = false;
      
      try{
         // create new file
         f = new File("grade.json");
         
         // tries to create new file in the system
         bool = f.createNewFile();
         
        
         System.out.println("File created: "+bool);
      
            
      }catch(Exception e){
         e.printStackTrace();
      }
   }
}