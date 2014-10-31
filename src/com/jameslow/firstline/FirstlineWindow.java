package com.jameslow.firstline;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;
import com.jameslow.*;
import com.jameslow.gui.*;

public class FirstlineWindow extends MainWindow implements ActionListener {
	private JPanel panel;
	private SearchText filter;
	private DragableTable dragable;
	private SortableTable table;
	private JScrollPane scroll;
	private FirstlineSettings settings;
	protected Action refreshAction;
	private JMenuItem edit,show;
	
	public FirstlineWindow() {
		super();
		settings = (FirstlineSettings) Main.Settings();
		
		panel = new JPanel();
			panel.setLayout(new BorderLayout());
			dragable = new DragableTable();
			filter = new SearchText(true,dragable);
			panel.add(filter,BorderLayout.PAGE_START);
				table = dragable.getTable();
				scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			panel.add(scroll, BorderLayout.CENTER);
		this.add(panel);
		createPopupMenu();
		refresh();
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == show) {
			if (table.getSelectedRow() >= 0) {
				Main.OS().showFile(dragable.getFolder() + Main.OS().fileSeparator() + (String)table.getModel().getValueAt(table.getSelectedRow(),0));
			}
		} else if (e.getSource() == edit) {
			if (table.getSelectedRow() >= 0) {
				Main.OS().openFile(dragable.getFolder() + Main.OS().fileSeparator() + (String)table.getModel().getValueAt(table.getSelectedRow(),0));
			}
		}
	}
	private void createPopupMenu() {
		List<JMenuItem> list = new ArrayList<JMenuItem>();
		edit = new JMenuItem("Edit File");
		edit.addActionListener(this);
		list.add(edit);
		show = new JMenuItem("Show File");
		show.addActionListener(this);
		list.add(show);
		GUIUtils.addPopupListener(table, list);
	}
	
	public void refresh() {
		setFolder(settings.getFolder(),settings.getFilter());
	}

	public WindowSettings getDefaultWindowSettings() {
		return new WindowSettings(500,300,0,0,true);
	}
	
	public void createOtherActions() {
		int shortcutKeyMask = Main.OS().shortCutKey();
		refreshAction = new refreshActionClass("Refresh",KeyStroke.getKeyStroke(KeyEvent.VK_R, shortcutKeyMask));
	}
	public void createViewMenu(JMenu viewMenu) {
		viewMenu.add(new JMenuItem(refreshAction));
	}
	
	public void setFolder(String folder, String ext) {
		try {
			dragable.setFolder(folder,ext);			
		} catch (Exception e) {
			Main.Logger().warning("Could not set folder to: " + folder + " "+ e.getMessage());
		}
	}	
		
	private String readFirstLine(File file) {
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			line = br.readLine();
		} catch (Exception e) {
			Main.Logger().warning("Could not read line from file: " + file.getPath() + " "+ e.getMessage());
		}
		return line;
	}
	
	public class refreshActionClass extends AbstractAction {
		public refreshActionClass(String text, KeyStroke shortcut) {
			super(text);
			putValue(ACCELERATOR_KEY, shortcut);
		}
		public void actionPerformed(ActionEvent e) {
			Firstline.refresh();
		}
	}
}