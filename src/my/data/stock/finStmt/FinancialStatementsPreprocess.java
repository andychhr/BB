package my.data.stock.finStmt;

import java.util.HashMap;

public class FinancialStatementsPreprocess implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

	public static HashMap<String, String> getFieldsValue(String inStr,
			HashMap<String, String> fieldsValues) {

		String[] str_is_line = inStr.split("\n");
		for (String xline : str_is_line) {
			int firstComma = xline.indexOf(",");
			String fieldName_key = xline.substring(0, firstComma).trim();
			String fieldValue = xline.substring(firstComma + 1).trim();
			if (fieldsValues.containsValue(fieldName_key)) {
				// key is exists
				continue;
			} else {
				// key is not exists
				fieldsValues.put(fieldName_key, fieldValue);
			}
		}

		return fieldsValues;
	}
	
	//PICK UP ONE STOCK TO REFRESH FINAN STMT FIELDS MAPPING FILE
	public static refresh(){

        //------------------------------------------------------------------
        //get class content
        String hm = "package my.data.stock.finStmt; \n"
                + "import java.util.HashMap; \n"
                + "public class FinancialStatementFieldsMap { \n"
                + "public static HashMap<String, String> hm; \n"
                + "public FinancialStatementFieldsMap(){ \n"
                + "FinancialStatementFieldsMap.hm = new HashMap<String,String>(); \n";
        int ii = 0;
        for (String key : fieldsVal.keySet()) {

            String xkey = "\"" + key.trim() + "\"";
            String xval = "\"f" + (ii++) + "\"";
            hm += "FinancialStatementFieldsMap.hm.put(" + xkey + "," + xval + ");\n";
        }
        hm += "}\n";
        hm += "}";

        System.out.println(hm);

        //--------------------------------
        //create file
        String f = "src/my/data/stock/finanStmt/FinancialStatementFieldsMap.java";
//        H:\baiduyun\workspace\0.MyProject\code\IdeaProjects\MM\src\main\java\mm\data\mining\preprocess\FinancialStatement\FinancialStatementFieldsMap.java
//        File fx = new File("src/mm/data/mining/stock/meta/FieldsMap.java");
        File fx = new File(f);
        if (!fx.exists()) {
            try {
                fx.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // write class content to the class file
        FileWriter wr = null;
        try {
            wr = new FileWriter(fx);
            wr.write(hm);
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

}
