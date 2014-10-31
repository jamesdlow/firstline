package com.jameslow.firstline;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import com.jameslow.*;

public class FirstlinePref extends PrefPanel {
	private JTextField folder,filter;
	private FirstlineSettings settings;
	
	public FirstlinePref() {
		super();
		setLayout(new GridLayout(3,2));
		Dimension size = new Dimension(300,80); 
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		settings = (FirstlineSettings) Main.Settings();
		
		final PrefPanel parent = this;
		JButton folderbrowse = new JButton("Folder");
		folderbrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = Main.OS().openFileDialog(parent.getParentFrame(), true);
				if (file != null) {
					folder.setText(file.getPath());   
	            }
			}
		});
		add(folderbrowse);
		
		folder = new JTextField();
			folder.setText(settings.getFolder());
			folder.setEditable(false);
		add(folder);
		
		JLabel filterlabel = new JLabel("Filter:");
		add(filterlabel);
		
		filter = new JTextField();
			filter.setText(settings.getFilter());
		add(filter);
	}
	
	public void savePreferences() {
		settings.setFolder(folder.getText());
		settings.setFilter(filter.getText());
		settings.saveSettings();
		((FirstlineWindow) Main.Window()).setFolder(folder.getText(),filter.getText());
	}
}
