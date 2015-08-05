package util;

import java.io.File;
import java.util.HashMap;


public class MyFile {
	
	/**
	 * check if it is file
	 * @param filePath: check if it is existing or not
	 * @param suffix: check if it is such suffix
	 * @return
	 * @throws Exception 
	 */
	public static boolean validateFile(String filePath, String suffix) throws Exception{
		boolean isOk = false;
		//check file is exists or not
		File f = new File(filePath);
		if(!f.exists()){
			throw new Exception("File: "+filePath + " does not exists, pls. double check.");
		}
		
		//check file is directory or not
		if(f.isDirectory()){
			throw new Exception("Expected file: "+filePath + " is a file but now it is a directory, pls. double check.");
		}
		
		//check file is end with certain suffix
		if(!filePath.endsWith(suffix.toLowerCase().trim())){
			throw new Exception("Expected file suffix is : "+suffix + " , but now it is "+filePath+", pls. double check.");
		}
		
		if(!f.canRead()){
			throw new Exception("Expected file : "+filePath + " , can be read, but now it cannot, pls. double check.");
		}
		
		isOk = true;
		return isOk;
	}
	
	
	/**
	 * 
	 * @param dirPath
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String,String> getAllFilesUnderDirectory(String dirPath) throws Exception {
        //init
        HashMap<String,String> indFiles = new HashMap<String, String>();
        indFiles.clear();

        //
        File fd = new File(dirPath);
        if(!fd.isDirectory()){
            throw new Exception("it should be a directory, but it is not. detail:"+dirPath);
        }

        String[] chld = fd.list();
        if(chld == null){
           throw new Exception("Specified directory does not exist or is not a directory.");
        }

        for(String xf : chld){
            File f = new File(dirPath+"\\"+xf);
            if(!f.exists()){
                throw new Exception("No such csv file "+xf+" can be found, please double check your input file full location and file name.");
            }

//            if(!f.isFile() || (!xf.endsWith(".csv") && !xf.endsWith(".CSV"))){
//                throw new Exception("file: "+xf+" is not a csv file.");
//            }

            String absPath = f.getAbsolutePath();
            //indFiles.put(CN_A_Meta.processStockCodeString(xf.trim()),absPath);
            indFiles.put(xf.trim(),absPath);
//            Pattern p = Pattern.compile("\\d{6}");
//            Matcher m = p.matcher(xf);
//            if(m.find()){
//                String xsc = m.group();
//                indFiles.put(xsc, absPath);
//            }
        }

        return indFiles;
    }

}
