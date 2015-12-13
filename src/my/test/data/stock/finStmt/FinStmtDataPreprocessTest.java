package my.test.data.stock.finStmt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import my.data.stock.finStmt.FinStmtDataObj;
import my.data.stock.finStmt.FinStmtDataPreprocess;
import my.util.data.MyData;

import org.junit.Test;

public class FinStmtDataPreprocessTest {

	/*
	@Test
	public void testGetFeildValuesFromXMLFile() throws Exception {
		FinStmtDataObj fsDO = new FinStmtDataObj();
		FinStmtDataPreprocess fsDP = new FinStmtDataPreprocess();
		// TreeMap<String, String> xmls = fsDP.getFinStmtDataXMLFiles();
		for (String xsc : FinStmtDataObj.getStockCodes()) {
			
			//get tradedates
			
			TreeMap<String, String> netProfit_IncreaseRatio = FinStmtDataPreprocess
					.getFieldValuesFromXML(xsc, "净利润增长率(%)");
			netProfit_IncreaseRatio = FinStmtDataPreprocess.getQnData(netProfit_IncreaseRatio, 4);
		

			TreeMap<String, String> netProfit = FinStmtDataPreprocess
					.getFieldValuesFromXML(xsc, "净利润(万元)");
			netProfit = FinStmtDataPreprocess.getQnData(netProfit, 4);
			
			
			TreeMap<String, String> netProfit_afterDeduct = FinStmtDataPreprocess
					.getFieldValuesFromXML(xsc, "净利润(扣除非经常性损益后)(万元)");
			netProfit_afterDeduct = FinStmtDataPreprocess.getQnData(netProfit_afterDeduct, 4);
			
			
			TreeMap<String, String> netProfit_totalAsset = FinStmtDataPreprocess
					.getFieldValuesFromXML(xsc, "总资产净利润率(%)");
			netProfit_totalAsset = FinStmtDataPreprocess.getQnData(netProfit_totalAsset, 4);
			
			
			TreeMap<String, String> netProfit_belongToMainCompany = FinStmtDataPreprocess
					.getFieldValuesFromXML(xsc, "归属于母公司所有者的净利润(万元)");
			netProfit_belongToMainCompany = FinStmtDataPreprocess.getQnData(netProfit_belongToMainCompany, 4);

			List<String> rptdtes = new ArrayList<String>(netProfit_IncreaseRatio.keySet());
			Collections.reverse(rptdtes);
			for (String xdt : rptdtes) {
				System.out.println("rptDate:" + xdt + " 	净利润增长率(%)："+ netProfit_IncreaseRatio.get(xdt)
						+ "		净利润(万元):"+ netProfit.get(xdt)
						+"		总资产净利润率(%):"+netProfit_totalAsset.get(xdt)
						+"		净利润(扣除非经常性损益后)(万元):"+netProfit_afterDeduct.get(xdt)
						+"		归属于母公司所有者的净利润(万元):"+netProfit_belongToMainCompany.get(xdt)
						);
			}
		}
	}
	*/
	
	
	
	
	@Test
	public void testGetFeildValuesFromXMLFile() throws Exception {
		final int analysisDuration = 3;
		final double netProfits_increaseRatio_yearly_min = 20; 
		
		final int Qn = 4;
		
		List<String> LessValues = new ArrayList<String>();
		List<String> invalidValues = new ArrayList<String>();
		List<String> goodValues = new ArrayList<String>();
		List<String> badValues = new ArrayList<String>();
		
		
		FinStmtDataObj fsDO = new FinStmtDataObj();
		//Map<String, TreeMap<String,String>> netProfits_increaseRatio_yearly = FinStmtDataPreprocess.getQnFieldValuesFromXML("净利润增长率(%)",4);
		//Map<String, TreeMap<String,String>> ROE_W_yearly = FinStmtDataPreprocess.getQnFieldValuesFromXML("净资产收益率加权(%)",4);
		//Map<String, TreeMap<String,String>> ROE_yearly = FinStmtDataPreprocess.getQnFieldValuesFromXML("净资产收益率(%)",4);
		
		
		
		
		for (String xsc : FinStmtDataObj.getStockCodes()) {
			if(xsc==null){
				continue;
			}
			
			//Step 1: filter 净资产收益率加权(%)
			String filter_netProfit_yearly = FinStmtDataPreprocess.isGoodStock(xsc, analysisDuration, Qn, "净资产收益率(%)",true, netProfits_increaseRatio_yearly_min,true);
			
			//主营业务收入(万元)
			String filter_mainIncome_yearly = "";
			if(filter_netProfit_yearly.equals("good")){
				filter_mainIncome_yearly = FinStmtDataPreprocess.isGoodStock(xsc, analysisDuration, Qn, "主营业务收入(万元)", false, netProfits_increaseRatio_yearly_min,true);
			}
			
			//EPS 基本每股收益(元)
			String filter_eps_yr = "";
			if(filter_mainIncome_yearly.equals("good")){
				filter_eps_yr = FinStmtDataPreprocess.isGoodStock(xsc, analysisDuration, Qn, "基本每股收益(元)", false, netProfits_increaseRatio_yearly_min,true);
			}
			
			switch(filter_eps_yr){
				case "good": 
					goodValues.add(xsc);
					break;
				case "bad":
					badValues.add(xsc);
					break;
				case "lessValues":
					LessValues.add(xsc);
					break;
				case "invalidValues":
					invalidValues.add(xsc);
					break;
				 default :
					 ;
			}
//			TreeMap<String,String> xVals = netProfits_increaseRatio_yearly.get(xsc);
//			List<String> rptdtes = new ArrayList<String>(xVals.keySet());
//			if (rptdtes.size() < analysisDuration) {
//				LessValues.add(xsc);
//			}else{
//				Collections.reverse(rptdtes);
//				List<String> analysisRptDates = rptdtes.subList(0, analysisDuration);
//				for (int i=0;i<analysisDuration;i++) {
//					String xRptDate = analysisRptDates.get(i);
//					String xVal = xVals.get(xRptDate);
//					if(MyData.isDouble(xVal)){	//if value is double
//						if(netProfits_increaseRatio_yearly_min < Double.parseDouble(xVal) && i< analysisDuration-1){	//if value is > min value
//							continue;
//						}else if(netProfits_increaseRatio_yearly_min < Double.parseDouble(xVal) && i== analysisDuration-1){	//if all values are > min value
//							goodValues.add(xsc);
//						}else{
//							badValues.add(xsc);
//							break;
//						}
//						
//					}else{
//						invalidValues.add(xsc);
//						break;
//					}
//				}
//			}
		}
		
		System.out.println("Stop here");
	}
}
