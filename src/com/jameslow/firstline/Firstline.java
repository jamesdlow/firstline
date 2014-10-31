package com.jameslow.firstline;

import com.jameslow.*;

public class Firstline extends Main {
	public Firstline(String args[]) {
		super(args,null,null,FirstlineSettings.class.getName(),FirstlineWindow.class.getName(),null,null,FirstlinePref.class.getName());
	}
	public static void main(String args[]) {
		instance = new Firstline(args);
	}
	public static void refresh() {
		if (Main.Window() != null) {
			FirstlineWindow win = (FirstlineWindow) Main.Window();
			win.refresh();
		}
	}
}