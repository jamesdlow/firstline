package com.jameslow.firstline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.jameslow.*;
import com.jameslow.gui.*;
import com.jameslow.gui.Droppable.DroppableListener;

public class DragableTable extends Droppable implements SearchTextFilter, TableModelListener {
	private Map<String,String> list;
	private String folder;
	private SortableTable table = new SortableTable();
	private DefaultTableModel model;
	
	public DragableTable() {
		table.getTableHeader().setReorderingAllowed(false);
		//table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		init(table);
	}
	public SortableTable getTable() {
		return table;
	}
	public String getFolder() {
		return folder;
	}
	private void setTableModel(String str) {
		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addTableModelListener(this);
		model.addColumn("Name");
		model.addColumn("First Line");
		
		if (str.compareTo("") != 0 || str.length() > 0) {
			for(Map.Entry<String, String> entry : list.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (MiscUtils.match(str,key) || MiscUtils.match(str,value)) {
					model.addRow(new Object[]{key, value});
				}
			}
		} else {
			for(Map.Entry<String, String> entry : list.entrySet()) {
				model.addRow(new Object[]{entry.getKey(), entry.getValue()});
			}
		}
		table.setModel(model);
		table.sortAllRowsBy();
	}
	public void setFolder(String folder, String ext) {
		this.folder = folder;
		list = new HashMap<String,String>();

		File dir = new File(folder);
		String[] files = dir.list();
		String[] exts = ext.split(",");
		for (int i=0; i<files.length; i++) {
			if (FileUtils.exts(files[i], exts)) {
				File file = new File(dir.getAbsolutePath() + Main.OS().fileSeparator() + files[i]);
				if (file.canRead() && file.isFile() && !file.isHidden()) {
					list.put(files[i], FileUtils.readFirstLine(file));
				}
			}
		}
		setTableModel("");
		table.reset();
		table.sortAllRowsBy(0);
	}
	public void doFilter(String text) {
		setTableModel(text.toUpperCase());
	}
	public void tableChanged(TableModelEvent e) {
		removeAllFilepaths();
		for (int i = 0; i < model.getRowCount(); i++) {
			addFilepath(folder + Main.OS().fileSeparator() + (String)model.getValueAt(i, 0));
		}
	}
}