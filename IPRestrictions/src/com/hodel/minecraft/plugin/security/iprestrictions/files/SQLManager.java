package com.hodel.minecraft.plugin.security.iprestrictions.files;

import com.hodel.minecraft.plugin.security.iprestrictions.config.Configuration;
import com.hodel.minecraft.plugin.security.iprestrictions.logger.IPLogger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lib.PatPeter.SQLibrary.MySQL;

/**
 * @version 
 * @author 
 */
public class SQLManager implements Manager {

    MySQL mysql;
    Connection conn;

    @Override
    public Manager setup() {
    	IPLogger.info("Running DB init");
        String[] info = Configuration.getSQLInfo();
        IPLogger.info("Openning Database: " + info[0] +", " + info[1] + ", "+ info[2] + ", "+ info[3] + ", " + info[4]);

        SQLConnect();
        
        if (mysql.checkConnection()) {
            mysql.query("CREATE TABLE IF NOT EXISTS ipr_ip_list (name VARCHAR(16), ip VARCHAR(160), ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, PRIMARY KEY (name, ip));");
            IPLogger.info("[" + Configuration.getPlugin().getName() + "] MySQL table ipr_ip_list create");
            return this;
        } else {
// ERROR	
        }
		return null;
    }
    
    private void SQLConnect() {
    	String[] info = Configuration.getSQLInfo();
    	
        mysql = new MySQL(Configuration.getPlugin().getLogger(), "[IPR]", info[0], info[1], info[4], info[2], info[3]);
        conn = mysql.getConnection();
        mysql.open();
        
        return;
    }

    @Override
    public void close() {
        try {
            conn.close();
            mysql.close();
        } catch (SQLException ex) {
            IPLogger.error(ex);
        }
    }

    
    //// NOT WORKING YET
    @Override
    public String[] getIPs(String name) {
    	if (!mysql.checkConnection()) {
    		SQLConnect();
    	}
    	
        ResultSet result = mysql.query("SELECT ip FROM ipr_ip_list WHERE name=' " + name + "' LIMIT 6;");
        if (result == null) {
            return null;
        }
        try {
            String ip;
            if (!result.last()) {
                ip = null;
            } else {
                result.first();
                ip = result.getString("ip");
            }
            if (ip == null) {
                return new String[0];
            } else {
                String[] ips = ip.split(",");
                return ips;
            }
        } catch (SQLException ex) {
            IPLogger.error(ex);
            return new String[0];
        }
    }
    
    ////   NEED METHOD TO CHECK IF IP IS IN TABLE

    @Override
    public void addIP(String name, String ip) {
    	String newIP;
//        String[] oldIPs = getIPs(name);
//        ArrayList<String> ips = new ArrayList<String>();
//        ips.addAll(Arrays.asList(oldIPs));
//        for (int i = 0; i < ips.size(); i++) {
//            ips.add(i, ips.remove(i).toLowerCase());
//        }
//        if (ips.contains(ip.toLowerCase())) {
//            return;
//        }

    	if (!mysql.checkConnection()) {
    		SQLConnect();
    	}
    	
        newIP = ip.trim();
        mysql.query("REPLACE INTO ipr_ip_list (name,ip) VALUES ('" + name + "','" + newIP + "');");
    }


    
    //// THIS IS NOT WORKING YET
	@Override
	public String[] getIPs(String name, int playerTimeLimit) {
		List<String> ip_list = new ArrayList<String>();
		
    	if (!mysql.checkConnection()) {
    		SQLConnect();
    	}
    	
		String query = "SELECT ip FROM ipr_ip_list WHERE LOWER(name)=LOWER('" + name + "') AND ts > SYSDATE() - " + playerTimeLimit + ";";
		IPLogger.info("SQL Query: getIPs() : " + query);
		ResultSet result = mysql.query(query);
        if (result == null) {
            return null;
        }
        try {
            if (!result.first()) {
                ip_list = null;
                IPLogger.info("No IPs Found");
            } else {
            	
        		do {            			
        			ip_list.add(result.getString("ip"));
        			IPLogger.info("IP Found: " + result.getString("ip"));
        		} while (result.next());

            	
                
            }
            if (ip_list == null) {
                return new String[0];
            } else {
            	String[] ips = new String[ip_list.size()];
            	ip_list.toArray(ips);
                return ips;
            }
        } catch (SQLException ex) {
            IPLogger.error(ex);
            return new String[0];
        }
	}

	@Override
	public int checkIPCount(String name, int seconds) throws SQLException {
		int num = 0;
		
    	if (!mysql.checkConnection()) {
    		SQLConnect();
    	}
    	
		String query = "SELECT count(ip) num FROM ipr_ip_list WHERE name=' " + name + "' AND ts > SYSDATE() - " + seconds + ";";
		IPLogger.info("SQL Query: checkIPCount() : " + query);
		ResultSet result = mysql.query(query);
        if (result == null) {
            return 0;
        }

        try {
        	result.first();
        	if (result != null) {
        		do {
        			num = result.getInt("num");
        		} while (result.next());        		
        	}
        	
        } catch (SQLException ex) {
        	IPLogger.error(ex);
        	return 0;
        }
        
        return num;
		
	}

	@Override
	public boolean checkIP(String name, String ip, int seconds) throws SQLException {
		int num = 0;
		
    	if (!mysql.checkConnection()) {
    		SQLConnect();
    	}
    	
		String query = "SELECT count(ip) num FROM ipr_ip_list WHERE LOWER(name)=LOWER(' " + name + "') AND ip='"+ip+"' AND ts > SYSDATE() - " + seconds + ";";
		IPLogger.info("SQL Query: checkIP() : " + query);
        ResultSet result = mysql.query(query);
        if (result == null) {
            return false;
        }
        
        try {
        	result.first();
        	if (result != null) {
        		do {
        			num = result.getInt("num");
        		} while (result.next());        		
        	}
        } catch (SQLException ex) {
        	IPLogger.error(ex);
        	return false;
        }
        
        return (num == 1);        
	}
}