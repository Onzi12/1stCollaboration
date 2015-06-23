package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFileChooser;

import model.ItemFile;
import model.ItemFile.Privilege;
import model.ItemFile.State;
import model.ItemFolder;
import boundary.FileUpdate_GUI;
import callback.FinishedEditingFileCallback;
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
			this.file.setParentID(rootFolder.getID());
			gui.setSaveLocationText(rootFolder.getFullPath());
			gui.setPrivilege(Privilege.PUBLIC);
			gui.setDescription("");
			gui.setFilename("");
			gui.setLocation("");
			isSelectedFile = false;
		} else {
			isSelectedFile = true;
//			gui.setDescription(file.getDescription());
//			gui.setPrivilege(file.getPrivilege());
			gui.setFilename(file.getName());
//			gui.setLocation(file.getFullPath());
		}

	}
	
	
	
	public void btnSaveClicked() {
		
		if (isSelectedFile == false) {
			
			file.setName(gui.getFilenameText()+file.getName());
			file.setDescription(gui.getDescription());
			file.setPrivilege(gui.getPrivilege());
			file.setOwner(Client.getInstance().getUser());
			//file.setUserID(Client.getInstance().getUser().getID()); //On 
			file.setIsEdited(false);
			file.setState(State.NORMAL);

		} 
		
		if (file != null) {
	        try {
			    HashMap<String, Object> data = new HashMap<String, Object>();
			    data.put("file", file);
			    data.put("isUpdate", isSelectedFile);
				Message msg = new Message(data, MessageType.UPLOAD_FILE);
				Client.getInstance().sendMessage(msg, new UploadFileCallback() {
					
					@Override
					protected void done(ItemFile file, MyBoxException exception) {		
						
						handleUpdateCallback(file, exception);
						
					}
					
				});
	  
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		}

		
		close();
	}
	
	public void btnCancelClicked() {
		close();
	}
	
	public void close() {
		gui.close();
		
		try {
			Message setEditable = new Message(file, MessageType.FINISHED_EDITING_FILE);
			Client.getInstance().sendMessage(setEditable, new FinishedEditingFileCallback() {
				
				@Override
				public void finishedEditingFile(MyBoxException exception) {
					if (exception == null) {
						
					} else {
						gui.showMessage(exception.getMessage());
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Boundary initBoundary(ItemFile file) {
		return new FileUpdate_GUI(this,file);
	}

	private void handleUpdateCallback(ItemFile file, MyBoxException exception) {
		
		MyBoxController controller = (MyBoxController)NavigationManager.getInstance().getCurrentController();
		if ( isSelectedFile == false ) {
			controller.handleUploadedFileCallback(file, exception);
		} else {
			controller.handleUpdateFileCallback(file, exception);
		}
		
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
				if (isSelectedFile == false) {
					file.setName(f.getName().substring(f.getName().lastIndexOf(".")));
				}
				else {
					String s = file.getName();
					s = s.substring(0, s.indexOf("."));
					file.setName(s);
					file.setName(file.getName() + f.getName().substring(f.getName().lastIndexOf(".")));
				}

			} catch (Exception e) {
				e.getStackTrace();
			}		    		    

            
        } else {
        	
        	
        }

	}

	public void btnSaveLocationClicked() {
		new VirtualLocationChooserController(this, file);
	}

	@Override 
	protected Boundary initBoundary() {
		return null;
	}

	public void setVirtualSaveLocation(ItemFolder folder) {
		gui.setSaveLocationText(folder.getFullPath());
		file.setParentID(folder.getID());
	}

}
