package controller;

import java.io.File;

import javax.swing.JFileChooser;

import model.ItemFile;
import model.ItemFile.Privilege;
import model.ItemFolder;
import boundary.FileTreeModel;
import boundary.FileUpdate_GUI;
import callback.UploadFileCallback;
import client.Client;
import common.Boundary;
import common.ByteArray;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FileUpdateController extends Controller{

	FileUpdate_GUI gui;
	private ItemFile file;
	private boolean isSelectedFile;
	
	public FileUpdateController(ItemFile file) {
		super(file);
		this.gui = (FileUpdate_GUI)super.gui;
		this.file = file;
		
		if (file == null) {
			this.file = new ItemFile();
			MyBoxController myBoxController = (MyBoxController)NavigationManager.getInstance().getCurrentController();
			ItemFolder rootFolder = (ItemFolder)myBoxController.gui.getTree().getRoot().getUserObject();
			this.file.setFolder(rootFolder.getID());
			gui.setSaveLocationText(rootFolder.getFullPath());
			gui.setPrivilege(Privilege.PUBLIC);
			gui.setDescription("");
			gui.setFilename("");
			gui.setLocation("");
		} else {
			isSelectedFile = true;
			gui.setDescription(file.getDescription());
			gui.setPrivilege(file.getPrivilege());
			gui.setFilename(file.getName());
			gui.setLocation(file.getFullPath());
		}

	}
	
	
	
	public void btnSaveClicked() {
		
		if (isSelectedFile == false) {
			
	        try {
			    
	        	if (file != null) {
	        		
					file.setName(gui.getFilenameText());
					file.setDescription(gui.getDescription());
					file.setPrivilege(gui.getPrivilege());
					file.setOwner(Client.getInstance().getUser().getID());
					
					Message msg = new Message(file, MessageType.UPLOAD_FILE);
					Client.getInstance().sendMessage(msg, new UploadFileCallback() {
						
						@Override
						protected void done(ItemFile file, MyBoxException exception) {
							MyBoxController controller = (MyBoxController)NavigationManager.getInstance().getCurrentController();
							controller.handleUploadedFileCallback(file, exception);
						}
						
					});
	        		
	        	} 
		 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
			
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

		String userhome = System.getProperty("user.home") + "\\desktop";
		JFileChooser filechooser = new JFileChooser(userhome);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = filechooser.showOpenDialog(gui);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = filechooser.getSelectedFile();

            gui.setLocation(f.getAbsolutePath());
            
            try {
            	
				byte[] bFile = ByteArray.convertFileToByteArray(f);
				file.setFile(bFile);
				String extension = f.getName().substring(f.getName().lastIndexOf('.'));
				file.setType(extension);
						
			} catch (Exception e) {}		    		    

            
        } else {
        	
        	
        }

	}

	public void btnSaveLocationClicked() {
		new VirtualLocationChooserController(this);
	}

	@Override //On: Leave this blank , this is fine 
	protected Boundary initBoundary() {
		return null;
	}

	public void setVirtualSaveLocation(ItemFolder folder) {
		gui.setSaveLocationText(folder.getFullPath());
		file.setFolder(folder.getID());
	}
	
}
