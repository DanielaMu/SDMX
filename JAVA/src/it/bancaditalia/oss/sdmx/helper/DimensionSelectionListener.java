/* Copyright 2010,2014 Bank Of Italy
*
* Licensed under the EUPL, Version 1.1 or - as soon they
* will be approved by the European Commission - subsequent
* versions of the EUPL (the "Licence");
* You may not use this work except in compliance with the
* Licence.
* You may obtain a copy of the Licence at:
*
*
* http://ec.europa.eu/idabc/eupl
*
* Unless required by applicable law or agreed to in
* writing, software distributed under the Licence is
* distributed on an "AS IS" basis,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied.
* See the Licence for the specific language governing
* permissions and limitations under the Licence.
*/
package it.bancaditalia.oss.sdmx.helper;

import it.bancaditalia.oss.sdmx.client.SdmxClientHandler;
import it.bancaditalia.oss.sdmx.util.Configuration;
import it.bancaditalia.oss.sdmx.util.SdmxException;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DimensionSelectionListener implements ListSelectionListener{
	
	private String provider = null;
	private String dataflow = null;
	protected static Logger logger = Configuration.getSdmxLogger();
	
	public DimensionSelectionListener(String provider, String dataflow) {
		super();
		this.provider = provider;
		this.dataflow = dataflow;
	}

	public void valueChanged(ListSelectionEvent e) {
        try {
        	String dimension = (String)((JList)e.getSource()).getSelectedValue();
        	QueryPanel.clearCodes();
        	QueryPanel.selectedDimension = dimension;
			Map<String, String> codes = SdmxClientHandler.getCodes(provider, dataflow, dimension);
			JTable codesTable = (JTable)QueryPanel.codesPane.getViewport().getComponent(0);
			codesTable.setModel(new KeyValueTableModel("Code ID", "Code Description", codes));
		} catch (SdmxException ex) {
			logger.severe("Exception. Class: " + ex.getClass().getName() + " .Message: " + ex.getMessage());
			logger.log(Level.FINER, "", ex);
		}
     }
}