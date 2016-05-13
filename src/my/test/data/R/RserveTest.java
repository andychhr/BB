package my.test.data.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Test;
import org.rosuda.REngine.REXP;
//import org.rosuda.JRI.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import my.data.stock.finStmt.FinStmtDataObj;
import my.data.stock.finStmt.FinStmtDataPreprocess;

public class RserveTest {
	@Test
	public void testRserveConnection() throws Exception{
		RConnection re = new RConnection("127.0.0.1");
		REXP x = re.eval("R.version.string");
	        System.out.println(x.asString());  
//	        double[] arr = re.eval("rnorm(20)").asDoubles();  
//	        for (double a : arr) {  
//	            System.out.print(a + ",");  
//	        }  
	        
	        String sc = "600036";
	        String fieldName = "基本每股收益(元)";
	        int Qn = 4;
	        
			FinStmtDataObj obj = new FinStmtDataObj();
	        TreeMap<String, String> allValues = FinStmtDataPreprocess.getFieldValuesFromXML(sc, fieldName);
	     
			List<String> rptdates = new ArrayList<String>(allValues.keySet());
			List<String> epsValues = new ArrayList<String>(allValues.values());
			
			String vecPES  = "c(";
			for(int i=0;i<epsValues.size();i++){
				String xv = epsValues.get(i);
				if(xv.equalsIgnoreCase("--")){
					xv = "NA";
				}

				if(i==epsValues.size()-1){
					vecPES+=xv+")";
				}else{
					vecPES+=xv+",";
				}
			}
			
//			String tmpArray[] = new String[epsValues.size()];
//			re.assign("x", epsValues.toArray(tmpArray));
			re.assign("x",vecPES);
			System.out.println(re.eval("x").asString());
			System.out.println(re.eval("class(x)").asString());
			double []data = re.eval("n<-as.numeric(x)").asDoubles();
			System.out.println(re.eval("class(n)").asString());
//			double[] epsV = re.eval("");
			
			
			 System.out.print("stop here");  
			
			
			
			
			
	        
//	        //保存为图像文件  
//	        File tempFile = null;  
//	        try {  
//	            re.assign("x", arr);   
//	            tempFile = File.createTempFile("test-", ".jpg");  
//	            String filePath = tempFile.getAbsolutePath();  
//	            re.eval("jpeg('d://test-1.jpg')");  
//	            re.eval("plot(x)");   
//	            re.eval("dev.off()");  
//	        } catch (IOException e) {  
//	            e.printStackTrace();  
//	        } catch (REngineException e) {  
//	            e.printStackTrace();  
//	        } finally {  
//	            re.close();   
//	  
//	        }    
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
