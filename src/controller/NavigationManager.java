package controller;

import java.util.Stack;

import boundary.AppFrame;

import common.Controller;


/**
 * The {@link NavigationManager} manages the navigation of hierarchical content.<br> 
 * and makes it possible to present your data efficiently <br>and makes it easier for the user to navigate that content.
 */
public class NavigationManager {

	private static NavigationManager instance=null;
	/**
	 * The main frame of the application.
	 */
	private AppFrame frame;
	/**
	 * A stack that holds the controllers.
	 */
	private Stack<Controller> stack;
	
	/**
	 * Constructs the NavigationManager
	 */
	private NavigationManager() {
		frame = AppFrame.getInstance();
		stack = new Stack<Controller>();
	}
	
	/**
	 * Get the {@link NavigationManager}.
	 * @return
	 */
	public static NavigationManager getInstance() {
		if (instance == null) {
			instance = new NavigationManager();
		}
		return instance;
	}

	/**
	 * Get the {@link AppFrame}.
	 * @return
	 */
	public AppFrame getFrame() {
		return frame;
	}
	
	/**
	 * Replace the current view with another view.
	 * @param controller
	 */
	public void replaceController(Controller controller) {
		if (!stack.isEmpty()) {
			dismissController(stack.pop());
		}
		stack.push(controller);
		presentController(stack.peek());
	}
	
	/**
	 * Push a new view above the current view.
	 * @param controller
	 */
	public void pushController(Controller controller) {
		if (!stack.isEmpty()) {
			dismissController(stack.peek());
		}
		stack.push(controller);
		presentController(stack.peek());
	}
	
	/**
	 * Pop the current view and present the view below.
	 */
	public void popController() {
		if (stack.size() > 1) {
			dismissController(stack.pop());
			presentController(stack.peek());
		
		}
	}

	/**
	 * Present the view.
	 * @param controller
	 */
	private void presentController(Controller controller) {
		controller.updateBoundary();
		frame.presentView(controller.getGui());

	}
	
	/**
	 * Dismiss the view.
	 * @param controller
	 */
	private void dismissController(Controller controller) {
		frame.dismissView(controller.getGui());
	}

	/**
	 * Get the current view controller.
	 * @return
	 */
	public Controller getCurrentController() {
		return stack.peek();
	}
	
}
