package common;

public enum MessageType {
	GET_GROUP_FILE_PRIV,
	RESTORE_FILE,
	GET_DELETE_FILE_PHYSICAL,
	GET_FILE_GROUPS_ACCESS,
	GET_RESTORE_FILES,
	ERROR_MESSAGE, 
	LOGIN, 
	UPDATE_FILE_LOCATION, 
	GET_ADD_FILES,
	GET_FILES, 
	ADD_FILE, 
	DELETE_FILE_PHYSICAL, 
	DELETE_FILE_VIRTUAL,
	LOGOUT, 
	CHANGE_FOLDER_NAME,
	CREATE_ACCOUNT, 
	UPLOAD_FILE, 
	FILE_EDIT,
	CREATE_NEW_FOLDER, 
	DOWNLOAD_FILE,
	UPDATE_FILE_GROUPS_ACCESS,
	GET_USER_GROUPS,
	CAN_EDIT_FILE,
	BROADCAST_FILE_STATE_CHANGE, 
	FINISHED_EDITING_FILE, 
	GET_ALL_GROUPS,
	GET_ALL_FILES,
	GET_ALL_OTHER_GROUPS,
	GET_GROUP_REQUESTS,
	REQUEST_ANSWER_ACCEPT,
	REQUEST_ANSWER_REJECT, 
	SEND_NEW_GROUP_REQUESTS, 
	REMOVE_FOLDER
}
