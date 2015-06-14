package controller;


import boundary.PortAndIP_GUI;
import client.Client;

import common.Boundary;
import common.Controller;

public class PortAndIPController extends Controller{

	
	public PortAndIPController() {

		Client client = Client.getInstance();
		((PortAndIP_GUI)gui).setPort(client.getPort());
		((PortAndIP_GUI)gui).setIP(client.getHost());
	}
	
	
	public void btnSaveClicked() {
		Client client = Client.getInstance();
		client.setPort(((PortAndIP_GUI)gui).getPort());
		client.setHost(((PortAndIP_GUI)gui).getHost());
		gui.close();
	}
	
	public void btnCancelClicked() {
		gui.close();
	}

	@Override
	protected Boundary initBoundary() {
		return new PortAndIP_GUI(this);
	}
}
