package my.util.file;


import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;


public class MyFile {

	/**
	 * check if it is file
	 * 
	 * @param filePath
	 *            : check if it is existing or not
	 * @param suffix
	 *            : check if it is such suffix
	 * @return
	 * @throws Exception
	 */
	public static boolean validateFile(String filePath, String suffix)
			throws Exception {
		boolean isOk = false;
		// check file is exists or not
		File f = new File(filePath);
		synchronized (f) {
			if (!f.exists()) {
				throw new Exception("File: " + filePath
						+ " does not exists, pls. double check.");
			}

			// check file is directory or not
			if (f.isDirectory()) {
				throw new Exception(
						"Expected file: "
								+ filePath
								+ " is a file but now it is a directory, pls. double check.");
			}

			// check file is end with certain suffix
			if (!filePath.endsWith(suffix.toLowerCase().trim())) {
				throw new Exception("Expected file suffix is : " + suffix
						+ " , but now it is " + filePath
						+ ", pls. double check.");
			}

			// check if the file can be read
			if (!f.canRead()) {
				throw new Exception(
						"Expected file : "
								+ filePath
								+ " , can be read, but now it cannot, pls. double check.");
			}

			isOk = true;
			return isOk;
		}
	}

	public static boolean validateFile(String filePath) throws Exception {
		boolean isOk = false;
		// check file is exists or not
		File f = new File(filePath);
		synchronized (f) {
			if (!f.exists()) {
				throw new Exception("File: " + filePath
						+ " does not exists, pls. double check.");
			}

			// check file is directory or not
			if (f.isDirectory()) {
				throw new Exception(
						"Expected file: "
								+ filePath
								+ " is a file but now it is a directory, pls. double check.");
			}

			// check if the file can be read
			if (!f.canRead()) {
				throw new Exception(
						"Expected file : "
								+ filePath
								+ " , can be read, but now it cannot, pls. double check.");
			}

			isOk = true;
			return isOk;
		}
	}

	public static boolean validateDirectory(String filePath) throws Exception {
		boolean isOk = false;
		// check file is exists or not
		File f = new File(filePath);
		if (!f.exists()) {
			throw new Exception("File: " + filePath
					+ " does not exists, pls. double check.");
		}

		// check file is directory or not
		if (!f.isDirectory()) {
			throw new Exception("Expected file: " + filePath
					+ " is a directory but now it is not, pls. double check.");
		}

		// check if the file can be read
		if (!f.canRead()) {
			throw new Exception("Expected file : " + filePath
					+ " , can be read, but now it cannot, pls. double check.");
		}

		isOk = true;
		return isOk;
	}

	
	/**
	 * create File If NotExits
	 * @param fileAbsPath
	 * @return
	 * @throws Exception
	 */
	public static File createFileIfNotExits(String fileAbsPath)
			throws Exception {
		// check file is exists or not
		File f = new File(fileAbsPath);
		synchronized (f) {
			if (!f.exists()) {
				File fdir = f.getParentFile();
				MyFile.createDirIfNotExists(fdir.getAbsolutePath());
				// throw new Exception("File: " + fileAbsPath+
				// " does not exists, pls. double check.");
				if(!f.createNewFile()){
					throw new Exception("file : "+fileAbsPath+" was not created successfully");
				}
			}

			return f;
		}
	}

	
	/**
	 * create Directory If Not Exists
	 * @param dirAbsPath
	 * @return
	 * @throws Exception
	 */
	public static File createDirIfNotExists(String dirAbsPath) throws Exception
	{
		// check dir is exists or not
		File f = new File(dirAbsPath);
		synchronized (f) {
			if (!f.exists()) {
				if(!f.mkdirs()){
					throw new Exception("Directory : "+ dirAbsPath + " was not created successfully");
				}
			}

			return f;
		}
	}

	/**
	 * 
	 * @param dirPath
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> getAllFilesUnderDirectory(
			String dirPath) throws Exception {
		// init
		HashMap<String, String> indFiles = new HashMap<String, String>();
		indFiles.clear();

		//
		File fd = new File(dirPath);
		if (!fd.isDirectory()) {
			throw new Exception(
					"it should be a directory, but it is not. detail:"
							+ dirPath);
		}

		String[] childs = fd.list();
		if (childs == null) {
			throw new Exception(
					"Specified directory does not exist or is not a directory.");
		}

		// add all files into hashmap
		for (String xf : childs) {
			File f = new File(dirPath + "\\" + xf);
			String absPath = f.getAbsolutePath();
			indFiles.put(xf.trim(), absPath);
		}

		return indFiles;
	}

	// if(!f.exists()){
	// throw new
	// Exception("No such csv file "+xf+" can be found, please double check your input file full location and file name.");
	// }

	// if(!f.isFile() || (!xf.endsWith(".csv") && !xf.endsWith(".CSV"))){
	// throw new Exception("file: "+xf+" is not a csv file.");
	// }

	// indFiles.put(CN_A_Meta.processStockCodeString(xf.trim()),absPath);

	// Pattern p = Pattern.compile("\\d{6}");
	// Matcher m = p.matcher(xf);
	// if(m.find()){
	// String xsc = m.group();
	// indFiles.put(xsc, absPath);
	// }

	public static void WriteOutputStreamToFile(String absFileName, OutputStream os) throws Exception {
		// create file
		File fi = MyFile.createFileIfNotExits(absFileName);

		try {
			synchronized (fi) {
				if (!fi.exists()) {
					fi.createNewFile(); // no file, create one
				}
				// ----------------------------------------------------------
				// write to xml file
				//FileOutputStream fos = null;
				//BufferedOutputStream bfos = null;
				FileWriter writer = null;
				try {
					//fos = new FileOutputStream(fi);
					//bfos = new BufferedOutputStream(fos, 8 * 1024);
					//OutputFormat format = OutputFormat.createPrettyPrint();
					writer = new FileWriter(fi);
					String content = os.toString();
					writer.write(content);
					writer.flush();
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// clear resource
					writer.close();
					//bfos.close();
					//fos.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteStringToFile(String xStmt, String content) throws IOException {
		// TODO Auto-generated method stub
		FileUtils.writeStringToFile(new File(xStmt), content);
	}
	
	public static void WriteStringToFile(File xStmt, String content) throws IOException {
		// TODO Auto-generated method stub
		FileUtils.writeStringToFile(xStmt, content);
	}
	
	public static void  WriteStringToFile(File xStmt, String content, Charset encoding, boolean append) throws IOException {
		// TODO Auto-generated method stub
		FileUtils.writeStringToFile(xStmt, content, encoding, append);
	}
	
	public static void  WriteStringToFile(String xStmt, String content, Charset encoding, boolean append) throws IOException {
		// TODO Auto-generated method stub
		FileUtils.writeStringToFile(new File(xStmt), content, encoding, append);
	}
	
	
	public static String readFileToString(String file, Charset encoding) throws IOException{
		return FileUtils.readFileToString(new File(file), encoding);
	}
	
	
	

}
