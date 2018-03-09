package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.StockHistory;

public class StockHistoryMapper implements RowMapper<StockHistory>{

	@Override
	public StockHistory mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int stockhistoryid = rs.getInt("ID");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
		String historydate = rs.getString("HISTORY_DATE");
//		historydate = formatDateTime(historydate);
		String historytime = rs.getString("HISTORY_TIME");
		historytime = formatDateTime(historytime, "time");
		int stocktypeid = rs.getInt("STOCK_TYPE_ID");
		int reasonid = rs.getInt("REASON_ID");
		String reasondesc = rs.getString("REASON_DESC");
		String logdatetime = rs.getString("LOG_DATETIME");
		logdatetime = formatDateTime(logdatetime, "datetime");
		String loguser = rs.getString("LOG_USER");
		String approval = rs.getString("APPROVAL");
		int mainlocid = rs.getInt("MAIN_LOC_ID");
		int sublocid = rs.getInt("SUB_LOC_ID");
		
		return new StockHistory(stockhistoryid, productid, quantity, historydate, historytime, stocktypeid, 
				reasonid, reasondesc, logdatetime, loguser, approval, mainlocid, sublocid);
	}
	// reformat time
	public String formatDateTime(String datetime, String type){
		
		SimpleDateFormat timeformatter = new SimpleDateFormat ("HH:mm:ss");
		SimpleDateFormat datetimeformatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatter;
		
		formatter = (type=="time") ?  timeformatter : datetimeformatter;
		
		try {
			datetime = formatter.format(formatter.parse(datetime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return datetime;
	}
}
