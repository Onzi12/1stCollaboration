package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

import model.ItemFile;
import boundary.FileUpdate_GUI;
import client.Client;
import common.Boundary;
import common.ByteArray;
import common.Controller;
import common.Message;
import common.MessageType;

public class FileUpdateController extends Controller{

	FileUpdate_GUI gui;
	private ItemFile file;
	private File f;
	private String fileDesc;
	
	public FileUpdateController(ItemFile file) {
		super(file);
		this.gui = (FileUpdate_GUI)super.gui;
		this.file = file;
		
		if (file == null) {
			gui.setFilename("");
			gui.setLocation("");
		} else {
			gui.setFilename(file.getName());
			gui.setLocation(file.getFullPath());
		}

	}
	
	
	
	public void btnSaveClicked() {
		 
        try {
            byte[] bFile = ByteArray.convertFileToByteArray(f);		    		    
		    
			ItemFile newFile = new ItemFile();
			newFile.setName(gui.getFilenameText());
			newFile.setFile(bFile);
			newFile.setName(f.getName());
			
			Message msg = new Message(newFile, MessageType.UPLOAD_FILE);
			Client.getInstance().sendMessage(msg);
	 
		    System.out.println("Done");
        }catch(Exception e){
            e.printStackTrace();
        }
		
		
//		try {
//			Message msg = new Message(f, MessageType.UPLOAD_FILE);
//			Client.getInstance().sendMessage(msg);
//			
////			Client.getInstance().sendToServer(f);
//		} catch (IOException e1) {
//			gui.showMessage("Exception: " + e1.getMessage());
//		}
		
//		if (file == null) {
//			
//			ItemFile newFile = new ItemFile();
//			newFile.setName(gui.getFilenameText());
////			newFile.setLocation(gui.getLocationText());
//			
//			try {
//				Message msg = new Message(newFile, MessageType.ADD_FILE);
//				Client.getInstance().sendMessage(msg);
//			} catch (IOException e1) {
//				gui.showMessage("Exception: " + e1.getMessage());
//			}
//			
//		} else {
//			
//			ItemFile newFile = new ItemFile(file.getID());
//			newFile.setName(gui.getFilenameText());
//			newFile.setFile(file.getFile());
//			
////			newFile.setLocation(gui.getLocationText());
//			System.out.println("dahkfdajlshfljakdshfkjads");
//			try {
//				Message msg = new Message(f, MessageType.UPLOAD_FILE);
//				Client.getInstance().sendMessage(msg);
//				
//				Client.getInstance().sendToServer(f);
//			} catch (IOException e1) {
//				gui.showMessage("Exception: " + e1.getMessage());
//			}
//			
//		}
		
		gui.close();
	}
	
	public void btnCancelClicked() {
		gui.close();
	}

	@Override
	protected Boundary initBoundary(ItemFile file) {
		return new FileUpdate_GUI(this,file);
	}


	public void btnPathClicked() {

		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fc.showOpenDialog(gui);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            
            if (f != null) {
            	System.out.println("File not null");
            } else {
            	System.out.println("File null");
            }

            gui.setLocation(f.getAbsolutePath());
            
            this.f = f;
            
//            file.setFile(f);
            
        } else {
        	
        	
        }

	}

	public void btnSaveLocationClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Boundary initBoundary() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
