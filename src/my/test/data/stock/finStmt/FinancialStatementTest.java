package my.test.data.stock.finStmt;

import static org.junit.Assert.*;

import java.util.HashMap;

import my.context.MyContext;
import my.data.stock.finStmt.FinancialStatement;

import org.junit.Test;

public class FinancialStatementTest {

	@Test
	public void testCollectionString() throws Exception {
		HashMap<String, String> configs = MyContext.getStockContext();
		FinancialStatement fs = new FinancialStatement("600036", configs);
		fs.collection("600036");
	}

}
