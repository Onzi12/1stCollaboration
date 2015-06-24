package controller;


import java.io.IOException;

import javax.swing.JFileChooser;

import model.ItemFile;
import boundary.FileRead_GUI;
import callback.FileDownloadCallback;
import client.Client;

import common.Boundary;
import common.ByteArray;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FileReadController extends Controller {

	public FileRead_GUI gui;
	public ItemFile file;
	
	public FileReadController(ItemFile file) {
		this.file = file;
		gui = (FileRead_GUI)super.gui;
		gui.setLocation(file.getFullPath());
		gui.setFilename(file.getName());
		gui.setDescription(file.getDescription());
		gui.setCbPrivilegeIndex(file.getPrivilege().getValue());
	}
	
	public void btnCancelClicked() {
		gui.close();
	}

	@Override
	protected Boundary initBoundary() {
		return new FileRead_GUI(this);
	}

	public void btnDownloadClicked() {
		Message msg = new Message(file, MessageType.DOWNLOAD_FILE);
		
		try {
			Client.getInstance().sendMessage(msg, new FileDownloadCallback() {
				
				@Override
				protected void done(ItemFile file, MyBoxException exception) {

					byte[] bFile = file.getFile();
					String path = System.getProperty("user.home") + "\\desktop\\";
					JFileChooser filechooser = new JFileChooser(path);
					int returnVal = filechooser.showSaveDialog(gui);
			        if (returnVal == JFileChooser.APPROVE_OPTION)
						try {
							ByteArray.writeByteArrayToFile(bFile, path + file.getName());
						} catch (Exception e) {
							e.printStackTrace();
						}				
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
