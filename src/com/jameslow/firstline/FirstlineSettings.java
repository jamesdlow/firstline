package com.jameslow.firstline;

import com.jameslow.*;

public class FirstlineSettings extends Settings {
	//XML Constants
	public static final String SETTINGS = "Settings";
	public static final String FOLDER = SETTINGS + ".Folder";
	public static final String FILTER = SETTINGS + ".Filter";

	public String getFolder() {
		return getSetting(FOLDER,Main.OS().homeDir());
	}
	public void setFolder(String foldername) {
		setSetting(FOLDER,foldername);
	}
	public String getFilter() {
		return getSetting(FILTER,".txt");
	}
	public void setFilter(String filter) {
		setSetting(FILTER,filter);
	}
	public void postSaveSettings() {
		Firstline.refresh();
	}
}
