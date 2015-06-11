package common;

public interface Displayable {
	
	/**
	 * Method that when overridden is used to display the UI
	 */
	public void displayWindow();
	
	/**
	 * Method that when overridden is used to display messages onto the UI
	 */
	public void showMessage(String str);
	
	/**
	 * Method that when overridden is used to close the displayed window
	 */
	public void closeWindow();
}
