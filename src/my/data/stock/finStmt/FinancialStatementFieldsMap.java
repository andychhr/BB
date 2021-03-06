package my.data.stock.finStmt;

import java.util.HashMap;

/**
 * Financial statments fields map
 * 
 * 
 * 
 * Singleton pattern
 * @author chenhanrong
 *
 */
public class FinancialStatementFieldsMap {
	
	private final static FinancialStatementFieldsMap instance = new FinancialStatementFieldsMap();
	private static boolean initialized = false;
	
	private static HashMap<String,String> FieldsMap;
	
	private FinancialStatementFieldsMap(){
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static FinancialStatementFieldsMap getInstance(){
		if (initialized) return instance;
	    instance.init();
	    initialized = true;
	    return instance;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static HashMap<String,String> getFieldsMap(){
		return FinancialStatementFieldsMap.FieldsMap;
	}
	
	private void init(){
		if(!initialized){
		FinancialStatementFieldsMap.FieldsMap = new HashMap<String,String>(); 
		FinancialStatementFieldsMap.FieldsMap.put("应付股利(万元)","f0");
		FinancialStatementFieldsMap.FieldsMap.put("退保金(万元)","f1");
		FinancialStatementFieldsMap.FieldsMap.put("长期债务与营运资金比率(%)","f2");
		FinancialStatementFieldsMap.FieldsMap.put("汇率变动对现金及现金等价物的影响(万元)","f3");
		FinancialStatementFieldsMap.FieldsMap.put("主营利润比重(%)","f4");
		FinancialStatementFieldsMap.FieldsMap.put("经营活动现金流入小计(万元)","f5");
		FinancialStatementFieldsMap.FieldsMap.put("托管收益(万元)","f6");
		FinancialStatementFieldsMap.FieldsMap.put("营业外收入(万元)","f7");
		FinancialStatementFieldsMap.FieldsMap.put("应付利息(万元)","f8");
		FinancialStatementFieldsMap.FieldsMap.put("短期借款(万元)","f9");
		FinancialStatementFieldsMap.FieldsMap.put("取得投资收益所收到的现金(万元)","f10");
		FinancialStatementFieldsMap.FieldsMap.put("主营业务利润率(%)","f11");
		FinancialStatementFieldsMap.FieldsMap.put("经营活动现金流出小计(万元)","f12");
		FinancialStatementFieldsMap.FieldsMap.put("投资损失(万元)","f13");
		FinancialStatementFieldsMap.FieldsMap.put("长期待摊费用(万元)","f14");
		FinancialStatementFieldsMap.FieldsMap.put("支付给职工以及为职工支付的现金(万元)","f15");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产清理(万元)","f16");
		FinancialStatementFieldsMap.FieldsMap.put("其他业务成本(万元)","f17");
		FinancialStatementFieldsMap.FieldsMap.put("购建固定资产、无形资产和其他长期资产所支付的现金(万元)","f18");
		FinancialStatementFieldsMap.FieldsMap.put("总负债(万元)","f19");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产(万元)","f20");
		FinancialStatementFieldsMap.FieldsMap.put("一年内到期的非流动负债(万元)","f21");
		FinancialStatementFieldsMap.FieldsMap.put("一年内到期的可转换公司债券(万元)","f22");
		FinancialStatementFieldsMap.FieldsMap.put("预计负债(万元)","f23");
		FinancialStatementFieldsMap.FieldsMap.put("在建工程(万元)","f24");
		FinancialStatementFieldsMap.FieldsMap.put("其他流动负债(万元)","f25");
		FinancialStatementFieldsMap.FieldsMap.put("代理承销证券款(万元)","f26");
		FinancialStatementFieldsMap.FieldsMap.put("研发费用(万元)","f27");
		FinancialStatementFieldsMap.FieldsMap.put("利息支出(万元)","f28");
		FinancialStatementFieldsMap.FieldsMap.put("归属于母公司股东权益合计(万元)","f29");
		FinancialStatementFieldsMap.FieldsMap.put("长期资产与长期资金比率(%)","f30");
		FinancialStatementFieldsMap.FieldsMap.put("递延所得税资产减少(万元)","f31");
		FinancialStatementFieldsMap.FieldsMap.put("资产减值损失(万元)","f32");
		FinancialStatementFieldsMap.FieldsMap.put("应付短期债券(万元)","f33");
		FinancialStatementFieldsMap.FieldsMap.put("减:库存股(万元)","f34");
		FinancialStatementFieldsMap.FieldsMap.put("未确认的投资损失(万元)","f35");
		FinancialStatementFieldsMap.FieldsMap.put("投资活动现金流入小计(万元)","f36");
		FinancialStatementFieldsMap.FieldsMap.put("生产性生物资产(万元)","f37");
		FinancialStatementFieldsMap.FieldsMap.put("营业总收入(万元)","f38");
		FinancialStatementFieldsMap.FieldsMap.put("支付保单红利的现金(万元)","f39");
		FinancialStatementFieldsMap.FieldsMap.put("收回投资所收到的现金(万元)","f40");
		FinancialStatementFieldsMap.FieldsMap.put("非流动负债合计(万元)","f41");
		FinancialStatementFieldsMap.FieldsMap.put("投资收益(万元)","f42");
		FinancialStatementFieldsMap.FieldsMap.put("应付债券(万元)","f43");
		FinancialStatementFieldsMap.FieldsMap.put("主营业务成本率(%)","f44");
		FinancialStatementFieldsMap.FieldsMap.put("投资活动现金流出小计(万元)","f45");
		FinancialStatementFieldsMap.FieldsMap.put("非流动资产合计(万元)","f46");
		FinancialStatementFieldsMap.FieldsMap.put("销售费用(万元)","f47");
		FinancialStatementFieldsMap.FieldsMap.put("融资租入固定资产(万元)","f48");
		FinancialStatementFieldsMap.FieldsMap.put("资产的经营现金流量回报率(%)","f49");
		FinancialStatementFieldsMap.FieldsMap.put("总资产净利润率(%)","f50");
		FinancialStatementFieldsMap.FieldsMap.put("存货的减少(万元)","f51");
		FinancialStatementFieldsMap.FieldsMap.put("预计非流动负债(万元)","f52");
		FinancialStatementFieldsMap.FieldsMap.put("所得税费用(万元)","f53");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产比重(%)","f54");
		FinancialStatementFieldsMap.FieldsMap.put("投资性房地产(万元)","f55");
		FinancialStatementFieldsMap.FieldsMap.put("未分配利润(万元)","f56");
		FinancialStatementFieldsMap.FieldsMap.put("稀释每股收益","f57");
		FinancialStatementFieldsMap.FieldsMap.put("净资产报酬率(%)","f58");
		FinancialStatementFieldsMap.FieldsMap.put("利润总额(万元)","f59");
		FinancialStatementFieldsMap.FieldsMap.put("期末现金及现金等价物余额(万元)","f60");
		FinancialStatementFieldsMap.FieldsMap.put("拟分配现金股利(万元)","f61");
		FinancialStatementFieldsMap.FieldsMap.put("流动负债合计(万元)","f62");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产净值(万元)","f63");
		FinancialStatementFieldsMap.FieldsMap.put("向其他金融机构拆入资金净增加额(万元)","f64");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产周转率(次)","f65");
		FinancialStatementFieldsMap.FieldsMap.put("保单红利支出(万元)","f66");
		FinancialStatementFieldsMap.FieldsMap.put("递延收益增加(减：减少)(万元)","f67");
		FinancialStatementFieldsMap.FieldsMap.put("汇兑收益(万元)","f68");
		FinancialStatementFieldsMap.FieldsMap.put("分配股利、利润或偿付利息所支付的现金(万元)","f69");
		FinancialStatementFieldsMap.FieldsMap.put("其他应付款(万元)","f70");
		FinancialStatementFieldsMap.FieldsMap.put("预付款项(万元)","f71");
		FinancialStatementFieldsMap.FieldsMap.put("支付其他与筹资活动有关的现金(万元)","f72");
		FinancialStatementFieldsMap.FieldsMap.put("非流动资产处置损失(万元)","f73");
		FinancialStatementFieldsMap.FieldsMap.put("流动负债(万元)","f74");
		FinancialStatementFieldsMap.FieldsMap.put("筹资活动产生的现金流量净额(万元)","f75");
		FinancialStatementFieldsMap.FieldsMap.put("流动资产周转率(次)","f76");
		FinancialStatementFieldsMap.FieldsMap.put("无形资产摊销(万元)","f77");
		FinancialStatementFieldsMap.FieldsMap.put("吸收投资收到的现金(万元)","f78");
		FinancialStatementFieldsMap.FieldsMap.put("长期应收款(万元)","f79");
		FinancialStatementFieldsMap.FieldsMap.put("现金及现金等价物净增加额(万元)","f80");
		FinancialStatementFieldsMap.FieldsMap.put("营业外收支净额(万元)","f81");
		FinancialStatementFieldsMap.FieldsMap.put("累计折旧(万元)","f82");
		FinancialStatementFieldsMap.FieldsMap.put("一年内到期的非流动资产(万元)","f83");
		FinancialStatementFieldsMap.FieldsMap.put("长期应付款(万元)","f84");
		FinancialStatementFieldsMap.FieldsMap.put("净资产收益率(%)","f85");
		FinancialStatementFieldsMap.FieldsMap.put("已赚保费(万元)","f86");
		FinancialStatementFieldsMap.FieldsMap.put("总资产(万元)","f87");
		FinancialStatementFieldsMap.FieldsMap.put("应收账款周转天数(天)","f88");
		FinancialStatementFieldsMap.FieldsMap.put("资本公积(万元)","f89");
		FinancialStatementFieldsMap.FieldsMap.put("拆入资金净增加额(万元)","f90");
		FinancialStatementFieldsMap.FieldsMap.put("专项应付款(万元)","f91");
		FinancialStatementFieldsMap.FieldsMap.put("被合并方在合并前实现净利润(万元)","f92");
		FinancialStatementFieldsMap.FieldsMap.put("递延所得税资产(万元)","f93");
		FinancialStatementFieldsMap.FieldsMap.put("国内票证结算(万元)","f94");
		FinancialStatementFieldsMap.FieldsMap.put("递延收益(万元)","f95");
		FinancialStatementFieldsMap.FieldsMap.put("其他应收款(万元)","f96");
		FinancialStatementFieldsMap.FieldsMap.put("股本报酬率(%)","f97");
		FinancialStatementFieldsMap.FieldsMap.put("房地产销售收入(万元)","f98");
		FinancialStatementFieldsMap.FieldsMap.put("预提费用的增加(万元)","f99");
		FinancialStatementFieldsMap.FieldsMap.put("流动资产(万元)","f100");
		FinancialStatementFieldsMap.FieldsMap.put("报告日期","f101");
		FinancialStatementFieldsMap.FieldsMap.put("内部应收款(万元)","f102");
		FinancialStatementFieldsMap.FieldsMap.put("主营业务利润(万元)","f103");
		FinancialStatementFieldsMap.FieldsMap.put("分保费用(万元)","f104");
		FinancialStatementFieldsMap.FieldsMap.put("支付利息、手续费及佣金的现金(万元)","f105");
		FinancialStatementFieldsMap.FieldsMap.put("对联营企业和合营企业的投资收益(万元)","f106");
		FinancialStatementFieldsMap.FieldsMap.put("经营现金净流量对负债比率(%)","f107");
		FinancialStatementFieldsMap.FieldsMap.put("手续费及佣金支出(万元)","f108");
		FinancialStatementFieldsMap.FieldsMap.put("收取利息、手续费及佣金的现金(万元)","f109");
		FinancialStatementFieldsMap.FieldsMap.put("筹资活动现金流出小计(万元)","f110");
		FinancialStatementFieldsMap.FieldsMap.put("处置子公司及其他营业单位收到的现金净额(万元)","f111");
		FinancialStatementFieldsMap.FieldsMap.put("净资产增长率(%)","f112");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产净值率(%)","f113");
		FinancialStatementFieldsMap.FieldsMap.put("总资产周转率(次)","f114");
		FinancialStatementFieldsMap.FieldsMap.put("发行债券收到的现金(万元)","f115");
		FinancialStatementFieldsMap.FieldsMap.put("应收出口退税(万元)","f116");
		FinancialStatementFieldsMap.FieldsMap.put("资产减值准备(万元)","f117");
		FinancialStatementFieldsMap.FieldsMap.put("商誉(万元)","f118");
		FinancialStatementFieldsMap.FieldsMap.put("报告期","f119");
		FinancialStatementFieldsMap.FieldsMap.put("预收账款(万元)","f120");
		FinancialStatementFieldsMap.FieldsMap.put("内部应付款(万元)","f121");
		FinancialStatementFieldsMap.FieldsMap.put("交易性金融负债(万元)","f122");
		FinancialStatementFieldsMap.FieldsMap.put("递延所得税负债增加(万元)","f123");
		FinancialStatementFieldsMap.FieldsMap.put("经营性应收项目的减少(万元)","f124");
		FinancialStatementFieldsMap.FieldsMap.put("长期待摊费用摊销(万元)","f125");
		FinancialStatementFieldsMap.FieldsMap.put("支付的其他与投资活动有关的现金(万元)","f126");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产净额(万元)","f127");
		FinancialStatementFieldsMap.FieldsMap.put("净资产收益率加权(%)","f128");
		FinancialStatementFieldsMap.FieldsMap.put("卖出回购金融资产款(万元)","f129");
		FinancialStatementFieldsMap.FieldsMap.put("吸收存款及同业存放(万元)","f130");
		FinancialStatementFieldsMap.FieldsMap.put("支付原保险合同赔付款项的现金(万元)","f131");
		FinancialStatementFieldsMap.FieldsMap.put("利息收入(万元)","f132");
		FinancialStatementFieldsMap.FieldsMap.put("现金等价物的期初余额(万元)","f133");
		FinancialStatementFieldsMap.FieldsMap.put("经营活动产生现金流量净额(万元)","f134");
		FinancialStatementFieldsMap.FieldsMap.put("赔付支出净额(万元)","f135");
		FinancialStatementFieldsMap.FieldsMap.put("经营现金净流量对销售收入比率(%)","f136");
		FinancialStatementFieldsMap.FieldsMap.put("房地产销售成本(万元)","f137");
		FinancialStatementFieldsMap.FieldsMap.put("手续费及佣金收入(万元)","f138");
		FinancialStatementFieldsMap.FieldsMap.put("发放贷款及垫款(万元)","f139");
		FinancialStatementFieldsMap.FieldsMap.put("支付的其他与经营活动有关的现金(万元)","f140");
		FinancialStatementFieldsMap.FieldsMap.put("每股净资产(元)","f141");
		FinancialStatementFieldsMap.FieldsMap.put("营业利润率(%)","f142");
		FinancialStatementFieldsMap.FieldsMap.put("未确定的投资损失(万元)","f143");
		FinancialStatementFieldsMap.FieldsMap.put("应付职工薪酬(万元)","f144");
		FinancialStatementFieldsMap.FieldsMap.put("应付手续费及佣金(万元)","f145");
		FinancialStatementFieldsMap.FieldsMap.put("三项费用比重(%)","f146");
		FinancialStatementFieldsMap.FieldsMap.put("其他长期投资(万元)","f147");
		FinancialStatementFieldsMap.FieldsMap.put("客户贷款及垫款净增加额(万元)","f148");
		FinancialStatementFieldsMap.FieldsMap.put("总资产周转天数(天)","f149");
		FinancialStatementFieldsMap.FieldsMap.put("主营业务收入增长率(%)","f150");
		FinancialStatementFieldsMap.FieldsMap.put("待摊费用的减少(万元)","f151");
		FinancialStatementFieldsMap.FieldsMap.put("公允价值变动收益(万元)","f152");
		FinancialStatementFieldsMap.FieldsMap.put("处置固定资产、无形资产和其他长期资产所收回的现金净额(万元)","f153");
		FinancialStatementFieldsMap.FieldsMap.put("工程物资(万元)","f154");
		FinancialStatementFieldsMap.FieldsMap.put("一般风险准备(万元)","f155");
		FinancialStatementFieldsMap.FieldsMap.put("支付的各项税费(万元)","f156");
		FinancialStatementFieldsMap.FieldsMap.put("应付保证金(万元)","f157");
		FinancialStatementFieldsMap.FieldsMap.put("应付分保账款(万元)","f158");
		FinancialStatementFieldsMap.FieldsMap.put("处置固定资产、无形资产和其他长期资产的损失(万元)","f159");
		FinancialStatementFieldsMap.FieldsMap.put("货币资金(万元)","f160");
		FinancialStatementFieldsMap.FieldsMap.put("处置交易性金融资产净增加额(万元)","f161");
		FinancialStatementFieldsMap.FieldsMap.put("公允价值变动损失(万元)","f162");
		FinancialStatementFieldsMap.FieldsMap.put("负债与所有者权益比率(%)","f163");
		FinancialStatementFieldsMap.FieldsMap.put("营业税金及附加(万元)","f164");
		FinancialStatementFieldsMap.FieldsMap.put("销售净利率(%)","f165");
		FinancialStatementFieldsMap.FieldsMap.put("补贴收入(万元)","f166");
		FinancialStatementFieldsMap.FieldsMap.put("向中央银行借款净增加额(万元","f167");
		FinancialStatementFieldsMap.FieldsMap.put("净利润(扣除非经常性损益后)(万元)","f168");
		FinancialStatementFieldsMap.FieldsMap.put("其中：子公司吸收少数股东投资收到的现金(万元)","f169");
		FinancialStatementFieldsMap.FieldsMap.put("经营性应付项目的增加(万元)","f170");
		FinancialStatementFieldsMap.FieldsMap.put("开发支出(万元)","f171");
		FinancialStatementFieldsMap.FieldsMap.put("现金比率(%)","f172");
		FinancialStatementFieldsMap.FieldsMap.put("投资所支付的现金(万元)","f173");
		FinancialStatementFieldsMap.FieldsMap.put("预提费用(万元)","f174");
		FinancialStatementFieldsMap.FieldsMap.put("客户存款和同业存放款项净增加额(万元)","f175");
		FinancialStatementFieldsMap.FieldsMap.put("所有者权益(或股东权益)合计(万元)","f176");
		FinancialStatementFieldsMap.FieldsMap.put("盈余公积(万元)","f177");
		FinancialStatementFieldsMap.FieldsMap.put("取得子公司及其他营业单位支付的现金净额(万元)","f178");
		FinancialStatementFieldsMap.FieldsMap.put("国际票证结算(万元)","f179");
		FinancialStatementFieldsMap.FieldsMap.put("其他(万元)","f180");
		FinancialStatementFieldsMap.FieldsMap.put("收到原保险合同保费取得的现金(万元)","f181");
		FinancialStatementFieldsMap.FieldsMap.put("其他非流动负债(万元)","f182");
		FinancialStatementFieldsMap.FieldsMap.put("应收利息(万元)","f183");
		FinancialStatementFieldsMap.FieldsMap.put("主营业务收入(万元)","f184");
		FinancialStatementFieldsMap.FieldsMap.put("期货损益(万元)","f185");
		FinancialStatementFieldsMap.FieldsMap.put("应付票据(万元)","f186");
		FinancialStatementFieldsMap.FieldsMap.put("债务转为资本(万元)","f187");
		FinancialStatementFieldsMap.FieldsMap.put("筹资活动现金流入小计(万元)","f188");
		FinancialStatementFieldsMap.FieldsMap.put("应付账款(万元)","f189");
		FinancialStatementFieldsMap.FieldsMap.put("长期借款(万元)","f190");
		FinancialStatementFieldsMap.FieldsMap.put("向中央银行借款(万元)","f191");
		FinancialStatementFieldsMap.FieldsMap.put("现金流量比率(%)","f192");
		FinancialStatementFieldsMap.FieldsMap.put("拆入资金(万元)","f193");
		FinancialStatementFieldsMap.FieldsMap.put("少数股东权益(万元)","f194");
		FinancialStatementFieldsMap.FieldsMap.put("资本化比率(%)","f195");
		FinancialStatementFieldsMap.FieldsMap.put("增加质押和定期存款所支付的现金(万元)","f196");
		FinancialStatementFieldsMap.FieldsMap.put("长期股权投资(万元)","f197");
		FinancialStatementFieldsMap.FieldsMap.put("公益性生物资产(万元)","f198");
		FinancialStatementFieldsMap.FieldsMap.put("应收股利(万元)","f199");
		FinancialStatementFieldsMap.FieldsMap.put("利息支付倍数(%)","f200");
		FinancialStatementFieldsMap.FieldsMap.put("存放中央银行和同业款项净增加额(万元)","f201");
		FinancialStatementFieldsMap.FieldsMap.put("每股经营活动产生的现金流量净额(元)","f202");
		FinancialStatementFieldsMap.FieldsMap.put("归属于母公司所有者的净利润(万元)","f203");
		FinancialStatementFieldsMap.FieldsMap.put("应收补贴款(万元)","f204");
		FinancialStatementFieldsMap.FieldsMap.put("总资产利润率(%)","f205");
		FinancialStatementFieldsMap.FieldsMap.put("回购业务资金净增加额(万元)","f206");
		FinancialStatementFieldsMap.FieldsMap.put("其他流动资产(万元)","f207");
		FinancialStatementFieldsMap.FieldsMap.put("预计流动负债(万元)","f208");
		FinancialStatementFieldsMap.FieldsMap.put("代理买卖证券款(万元)","f209");
		FinancialStatementFieldsMap.FieldsMap.put("基本每股收益(元)","f210");
		FinancialStatementFieldsMap.FieldsMap.put("收到其他与筹资活动有关的现金(万元)","f211");
		FinancialStatementFieldsMap.FieldsMap.put("应交税费(万元)","f212");
		FinancialStatementFieldsMap.FieldsMap.put("资本固定化比率(%)","f213");
		FinancialStatementFieldsMap.FieldsMap.put("已完工尚未结算款的减少(减:增加)(万元)","f214");
		FinancialStatementFieldsMap.FieldsMap.put("实收资本(或股本)(万元)","f215");
		FinancialStatementFieldsMap.FieldsMap.put("清算价值比率(%)","f216");
		FinancialStatementFieldsMap.FieldsMap.put("待处理流动资产损益(万元)","f217");
		FinancialStatementFieldsMap.FieldsMap.put("股东权益比率(%)","f218");
		FinancialStatementFieldsMap.FieldsMap.put("质押贷款净增加额(万元)","f219");
		FinancialStatementFieldsMap.FieldsMap.put("营业收入(万元)","f220");
		FinancialStatementFieldsMap.FieldsMap.put("长期递延收益(万元)","f221");
		FinancialStatementFieldsMap.FieldsMap.put("总资产增长率(%)","f222");
		FinancialStatementFieldsMap.FieldsMap.put("持有至到期投资(万元)","f223");
		FinancialStatementFieldsMap.FieldsMap.put("衍生金融资产(万元)","f224");
		FinancialStatementFieldsMap.FieldsMap.put("收到的税费返还(万元)","f225");
		FinancialStatementFieldsMap.FieldsMap.put("现金的期初余额(万元)","f226");
		FinancialStatementFieldsMap.FieldsMap.put("结算备付金(万元)","f227");
		FinancialStatementFieldsMap.FieldsMap.put("衍生金融负债(万元)","f228");
		FinancialStatementFieldsMap.FieldsMap.put("应收保费(万元)","f229");
		FinancialStatementFieldsMap.FieldsMap.put("保险合同准备金(万元)","f230");
		FinancialStatementFieldsMap.FieldsMap.put("销售商品、提供劳务收到的现金(万元)","f231");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产减值准备(万元)","f232");
		FinancialStatementFieldsMap.FieldsMap.put("拆出资金(万元)","f233");
		FinancialStatementFieldsMap.FieldsMap.put("加:期初现金及现金等价物余额(万元)","f234");
		FinancialStatementFieldsMap.FieldsMap.put("购买商品、接受劳务支付的现金(万元)","f235");
		FinancialStatementFieldsMap.FieldsMap.put("营业成本(万元)","f236");
		FinancialStatementFieldsMap.FieldsMap.put("应收保证金(万元)","f237");
		FinancialStatementFieldsMap.FieldsMap.put("营业利润(万元)","f238");
		FinancialStatementFieldsMap.FieldsMap.put("交易性金融资产(万元)","f239");
		FinancialStatementFieldsMap.FieldsMap.put("油气资产(万元)","f240");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产折旧、油气资产折耗、生产性物资折旧(万元)","f241");
		FinancialStatementFieldsMap.FieldsMap.put("流动资产合计(万元)","f242");
		FinancialStatementFieldsMap.FieldsMap.put("净利润增长率(%)","f243");
		FinancialStatementFieldsMap.FieldsMap.put("现金的期末余额(万元)","f244");
		FinancialStatementFieldsMap.FieldsMap.put("净利润(万元)","f245");
		FinancialStatementFieldsMap.FieldsMap.put("其他业务利润(万元)","f246");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产报废损失(万元)","f247");
		FinancialStatementFieldsMap.FieldsMap.put("速动比率(%)","f248");
		FinancialStatementFieldsMap.FieldsMap.put("收到的其他与经营活动有关的现金(万元)","f249");
		FinancialStatementFieldsMap.FieldsMap.put("营业总成本(万元)","f250");
		FinancialStatementFieldsMap.FieldsMap.put("资产报酬率(%)","f251");
		FinancialStatementFieldsMap.FieldsMap.put("现金及现金等价物的净增加额(万元)","f252");
		FinancialStatementFieldsMap.FieldsMap.put("股权分置流通权(万元)","f253");
		FinancialStatementFieldsMap.FieldsMap.put("少数股东损益(万元)","f254");
		FinancialStatementFieldsMap.FieldsMap.put("已结算尚未完工款的增加(减:减少)(万元)","f255");
		FinancialStatementFieldsMap.FieldsMap.put("存货周转率(次)","f256");
		FinancialStatementFieldsMap.FieldsMap.put("减少质押和定期存款所收到的现金(万元)","f257");
		FinancialStatementFieldsMap.FieldsMap.put("管理费用(万元)","f258");
		FinancialStatementFieldsMap.FieldsMap.put("收到再保险业务现金净额(万元)","f259");
		FinancialStatementFieldsMap.FieldsMap.put("专项储备(万元)","f260");
		FinancialStatementFieldsMap.FieldsMap.put("长期负债比率(%)","f261");
		FinancialStatementFieldsMap.FieldsMap.put("股东权益不含少数股东权益(万元)","f262");
		FinancialStatementFieldsMap.FieldsMap.put("待摊费用(万元)","f263");
		FinancialStatementFieldsMap.FieldsMap.put("经营活动产生的现金流量净额(万元)","f264");
		FinancialStatementFieldsMap.FieldsMap.put("收到的其他与投资活动有关的现金(万元)","f265");
		FinancialStatementFieldsMap.FieldsMap.put("应收账款周转率(次)","f266");
		FinancialStatementFieldsMap.FieldsMap.put("应收票据(万元)","f267");
		FinancialStatementFieldsMap.FieldsMap.put("未确认投资损失(万元)","f268");
		FinancialStatementFieldsMap.FieldsMap.put("其他业务收入(万元)","f269");
		FinancialStatementFieldsMap.FieldsMap.put("股东权益与固定资产比率(%)","f270");
		FinancialStatementFieldsMap.FieldsMap.put("销售毛利率(%)","f271");
		FinancialStatementFieldsMap.FieldsMap.put("保户储金及投资款净增加额(万元)","f272");
		FinancialStatementFieldsMap.FieldsMap.put("非主营比重(%)","f273");
		FinancialStatementFieldsMap.FieldsMap.put("递延所得税负债(万元)","f274");
		FinancialStatementFieldsMap.FieldsMap.put("财务费用(万元)","f275");
		FinancialStatementFieldsMap.FieldsMap.put("存货周转天数(天)","f276");
		FinancialStatementFieldsMap.FieldsMap.put("经营现金净流量与净利润的比率(%)","f277");
		FinancialStatementFieldsMap.FieldsMap.put("应收分保合同准备金(万元)","f278");
		FinancialStatementFieldsMap.FieldsMap.put("流动资产周转天数(天)","f279");
		FinancialStatementFieldsMap.FieldsMap.put("营业外支出(万元)","f280");
		FinancialStatementFieldsMap.FieldsMap.put("固定资产原值(万元)","f281");
		FinancialStatementFieldsMap.FieldsMap.put("基本每股收益","f282");
		FinancialStatementFieldsMap.FieldsMap.put("成本费用利润率(%)","f283");
		FinancialStatementFieldsMap.FieldsMap.put("应收账款(万元)","f284");
		FinancialStatementFieldsMap.FieldsMap.put("资产总计(万元)","f285");
		FinancialStatementFieldsMap.FieldsMap.put("提取保险合同准备金净额(万元)","f286");
		FinancialStatementFieldsMap.FieldsMap.put("可供出售金融资产(万元)","f287");
		FinancialStatementFieldsMap.FieldsMap.put("期初现金及现金等价物余额(万元)","f288");
		FinancialStatementFieldsMap.FieldsMap.put("偿还债务支付的现金(万元)","f289");
		FinancialStatementFieldsMap.FieldsMap.put("应收分保账款(万元)","f290");
		FinancialStatementFieldsMap.FieldsMap.put("外币报表折算差额(万元)","f291");
		FinancialStatementFieldsMap.FieldsMap.put("现金等价物的期末余额(万元)","f292");
		FinancialStatementFieldsMap.FieldsMap.put("负债合计(万元)","f293");
		FinancialStatementFieldsMap.FieldsMap.put("资产负债率(%)","f294");
		FinancialStatementFieldsMap.FieldsMap.put("其他非流动资产(万元)","f295");
		FinancialStatementFieldsMap.FieldsMap.put("其中：子公司支付给少数股东的股利、利润(万元)","f296");
		FinancialStatementFieldsMap.FieldsMap.put("取得借款收到的现金(万元)","f297");
		FinancialStatementFieldsMap.FieldsMap.put("买入返售金融资产(万元)","f298");
		FinancialStatementFieldsMap.FieldsMap.put("负债和所有者权益(或股东权益)总计(万元)","f299");
		FinancialStatementFieldsMap.FieldsMap.put("产权比率(%)","f300");
		FinancialStatementFieldsMap.FieldsMap.put("无形资产(万元)","f301");
		FinancialStatementFieldsMap.FieldsMap.put("存货(万元)","f302");
		FinancialStatementFieldsMap.FieldsMap.put("投资活动产生的现金流量净额(万元)","f303");
		FinancialStatementFieldsMap.FieldsMap.put("其他应交款(万元)","f304");
		FinancialStatementFieldsMap.FieldsMap.put("流动比率(%)","f305");
		}
		
	}
	
	
	


}
