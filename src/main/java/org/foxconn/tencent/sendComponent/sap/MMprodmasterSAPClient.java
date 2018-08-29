package org.foxconn.tencent.sendComponent.sap;
//需要将sapjco3.dll 放在system32目录下
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

//@Component
public class MMprodmasterSAPClient extends SAPBaseClient
{
    
 	Logger logger = Logger.getLogger(MMprodmasterSAPClient.class);
 	public static JCoDestination destination ;
    public static JCoFunction function ;
   
    public  MMprodmasterSAPClient(){
    	try {
    		if(null==destination){
    			destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
    		}
    		if(null==function){
    			function = destination.getRepository().getFunction("ZRFC_GET_PROD_MASTER");
    			logger.info("--create function");
    		}
    	} catch (JCoException e) {
    		logger.error(e.getCause().toString());
		}
    }
    
    public  String downMMprodmastercalls(String partno) throws JCoException{
   
        
        if(function == null)
            throw new RuntimeException("ZRFC_GET_PROD_MASTER not found in SAP.");

        try
        {
        	JCoParameterList  jCoParameterList2 = function.getImportParameterList();
        	jCoParameterList2.setValue("PARTNO", partno);
        	jCoParameterList2.setValue("PLANT", "GHUS");
        	jCoParameterList2.setValue("LASTEDITDT", "20170101");
        	logger.info("--calling sap");;
        	function.execute(destination);
         
        }
        catch(AbapException e)
        {	
        	logger.error(e.getCause().toString());
            return null;
        }
        JCoParameterList jCoParameterList= function.getTableParameterList();
        JCoTable table =  jCoParameterList.getTable("PROD_MASTER");
        //System.out.println(table.toString());
       //System.out.println(table.toXML());
       String mfrpn=null;
       for (int i = 0; i < table.getNumRows(); i++) {
    	   table.setRow(i);
    	   mfrpn = table.getString("MFRPN");
    	  // System.out.println(mfrpn);
       }
       return  mfrpn;
    }
    
    public static void main(String[] args) throws JCoException
    {
    	MMprodmasterSAPClient client = new MMprodmasterSAPClient();
    	client.downMMprodmastercalls("35121J500-245-G");
    }
}
