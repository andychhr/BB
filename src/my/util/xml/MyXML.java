package my.util.xml;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;





import my.util.file.MyFile;

//dom4j lib
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;




/**
 * @author hanrchen
 *
 */
public class MyXML extends MyFile {
//        public Document createDocument(	) {
//            Document document = DocumentHelper.createDocument();
//            Element root = document.addElement( "root" );
//
//            Element author1 = root.addElement( "author" )
//                    .addAttribute( "name", "James" )
//                    .addAttribute( "location", "UK" )
//                    .addText( "James Strachan" );
//
//            Element author2 = root.addElement( "author" )
//                    .addAttribute( "name", "Bob" )
//                    .addAttribute( "location", "US" )
//                    .addText( "Bob McWhirter" );
//
//            return document;
//        }
	
	public static File validateXML(String fileName) throws Exception{
		if(MyFile.validateFile(fileName, ".xml")){
			return new File(fileName);
		}else{
			return null;
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param xpath
	 * @return
	 * @throws FileSystemException 
	 */
	public static ArrayList<String> getTextsByXpath(String fileName, String xpath) throws Exception {
		ArrayList<String> vals = new ArrayList<String>();
		
		//check input file
		File xml = MyXML.validateXML(fileName);

		Document document = null;
		try {
			SAXReader saxReader = new SAXReader(); // 
			document = saxReader.read(xml); // 
			List<? extends Node> list = document.selectNodes(xpath);// 
			Iterator<?> iter = list.iterator();
			while (iter.hasNext()) {
				Element xel = (Element) iter.next();
				vals.add(xel.getTextTrim());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			document = null;
		}

		return vals;
	}

	

    /**
     * 
     * @param fileName
     * @param xpath
     * @return
     */
	public static String getTextByXpath(String fileName, String xpath) throws Exception {
		//return value
		String xval = "";
	
		//check input file
		File xml = MyXML.validateXML(fileName);
				
		//
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader(); // 
			document = saxReader.read(xml); // 
			List<? extends Node> list = document.selectNodes(xpath);// 
			Iterator<?> iter = list.iterator();
			while (iter.hasNext()) {
				Element xel = (Element) iter.next();
				xval = xel.getTextTrim();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			document = null;
		}
		//
		return xval;
	}

	
	
	
	/**
	 * 
	 * @param fileName
	 * @param xpath
	 * @return
	 * @throws FileSystemException 
	 */
	public static ArrayList<String> getAttributesByXpath(String fileName, String xpath, String attributeName) throws Exception {
		ArrayList<String> vals = new ArrayList<String>();
		
		//check input file
		File xml = MyXML.validateXML(fileName);

		Document document = null;
		try {
			SAXReader saxReader = new SAXReader(); // 
			document = saxReader.read(xml); // 
			List<? extends Node> list = document.selectNodes(xpath);	//get node by xpath 
			Iterator<?> iter = list.iterator();
			while (iter.hasNext()) {	// loop to get all elements' attribute
				Element xel = (Element) iter.next();
				String xAttrVal = xel.attributeValue(attributeName);
				if(xAttrVal == null){
					throw new Exception("Element : "+ xpath+" cannot find attribute: "+attributeName);
				}
				vals.add(xAttrVal);
			}
			
			//clear resource
			list = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			document = null;
		}

		return vals;
	}
	
	
	
	
	/**
	 * 
	 * @param doc
	 * @param outfileName
	 * @throws Exception
	 */
    public static void writeToFile(org.dom4j.Document doc, String outfileName) throws Exception {
        if(!outfileName.endsWith(".xml") && !outfileName.endsWith(".XML")){
            throw new Exception("output file is not xml file");
        }
        // create file
        File fi = new File(outfileName);
        try {
            synchronized (fi) {
                if (!fi.exists()) {
                    fi.createNewFile(); // no file, create one
                }
                // ----------------------------------------------------------
                // write to xml file
                FileOutputStream fos = null;
                BufferedOutputStream bfos = null;
                XMLWriter writer = null;
                try {
                    fos = new FileOutputStream(fi);
                    bfos = new BufferedOutputStream(fos, 8 * 1024);
                    OutputFormat format = OutputFormat.createPrettyPrint();
                    writer = new XMLWriter(bfos, format);
                    writer.write(doc);
                    writer.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // clear resource
                    writer.close();
                    bfos.close();
                    fos.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
