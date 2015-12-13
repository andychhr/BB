package my.test.data.R;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.rosuda.JRI.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RserveTest {
	@Test
	public void testRserveConnection() throws RserveException, REXPMismatchException{
		 RConnection re = new RConnection("127.0.0.1");  
	        org.rosuda.REngine.REXP x = re.eval("R.version.string");  
	        System.out.println(x.asString());  
	        double[] arr = re.eval("rnorm(20)").asDoubles();  
	        for (double a : arr) {  
	            System.out.print(a + ",");  
	        }  
	        //保存为图像文件  
	        File tempFile = null;  
	        try {  
	            re.assign("x", arr);   
	            tempFile = File.createTempFile("test-", ".jpg");  
	            String filePath = tempFile.getAbsolutePath();  
	            re.eval("jpeg('d://test-1.jpg')");  
	            re.eval("plot(x)");   
	            re.eval("dev.off()");  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (REngineException e) {  
	            e.printStackTrace();  
	        } finally {  
	            re.close();   
	  
	        }    
	}
	
	
//	@Test
//	public void testCallR() throws Exception
//	{
//	  REngine re = null;
//	  try
//	  {
//	    re = REngine.engineForClass("org.rosuda.REngine.JRI.JRIEngine");
//
//	    re.assign("num", new int[] { 1 });
//		        
//	    org.rosuda.REngine.REXP result = re.parseAndEval("num + 1");
//		        
//	    System.out.println(result.asInteger());
//	  }
//	  finally
//	  {
//	     if (re != null)
//	     {
//	       re.close();
//	     }
//	  }
//	}
	
}
