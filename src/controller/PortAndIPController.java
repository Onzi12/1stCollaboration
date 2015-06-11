package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import boundary.PortAndIP_GUI;
import client.Client;

public class PortAndIPController {
	private PortAndIP_GUI gui;
	
	public PortAndIPController(PortAndIP_GUI gui) {
		this.gui = gui;		
		gui.registerSaveListener(new BtnSaveActionListener());
		gui.registerCancelListener(new BtnCancelActionListener());
		Client client = Client.getInstance();
		gui.setPort(client.getPort());
		gui.setIP(client.getHost());
	}
	
	private class BtnSaveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			btnSaveClicked();
		}
	}
	
	private class BtnCancelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnCancelClicked();
		}
	}
	
	private void btnSaveClicked() {
		Client client = Client.getInstance();
		client.setPort(gui.getPort());
		client.setHost(gui.getHost());
		gui.closeWindow();
	}
	
	private void btnCancelClicked() {
		gui.closeWindow();
	}
}
