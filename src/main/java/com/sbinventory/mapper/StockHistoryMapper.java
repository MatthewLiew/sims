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
		// TODO Auto-generated method stub\
		int stockhistoryid = rs.getInt("ID");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
		String historydate = rs.getString("HISTORY_DATE");
		String historytime = rs.getString("HISTORY_TIME");
		SimpleDateFormat  timeformatter = new SimpleDateFormat ("HH:mm:ss");
		try {
			historytime = timeformatter.format(timeformatter.parse(historytime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int stocktypeid = rs.getInt("STOCK_TYPE_ID");
		int reasonid = rs.getInt("REASON_ID");
		String reasondesc = rs.getString("REASON_DESC");
		
		String logdatetime = rs.getString("LOG_DATETIME");
		SimpleDateFormat datetimeformatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		try {
			logdatetime = datetimeformatter.format(datetimeformatter.parse(logdatetime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String loguser = rs.getString("LOG_USER");
		String approval = rs.getString("APPROVAL");
		
		return new StockHistory(stockhistoryid, productid, quantity, historydate, historytime, stocktypeid, 
				reasonid, reasondesc, logdatetime, loguser, approval);
	}

}
