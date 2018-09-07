package com.sims.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sims.model.AssetReqs;
import com.sims.model.ITF;
import com.sims.model.RMA;

public class AssetReqsMapper implements RowMapper<AssetReqs>{

	@Override
	public AssetReqs mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int assetreqsid = rs.getInt("ID");
		String loguser = rs.getString("LOG_USER");
		String logdatetime = rs.getString("LOG_DATETIME");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
		int mainlocid = rs.getInt("MAIN_LOC");
		int sublocid = rs.getInt("SUB_LOC");		
		String approval = rs.getString("APPROVAL");
		
		return new AssetReqs(assetreqsid, loguser, logdatetime, productid, quantity, mainlocid, sublocid, approval);
	}

}
