package controller;

import java.util.Stack;
import common.Controller;
import boundary.AppFrame;



public class NavigationManager {

	private static NavigationManager instance=null;
	private AppFrame frame;
	private Stack<Controller> stack;
	
	
	private NavigationManager() {
		frame = AppFrame.getInstance();
		stack = new Stack<Controller>();
	}
	
	public static NavigationManager getInstance() {
		if (instance == null) {
			instance = new NavigationManager();
		}
		return instance;
	}

	
	public AppFrame getFrame() {
		return frame;
	}
	
	public void replaceController(Controller controller) {
		if (!stack.isEmpty()) {
			dismissController(stack.pop());
		}
		stack.push(controller);
		presentController(stack.peek());
	}
	
	public void pushController(Controller controller) {
		if (!stack.isEmpty()) {
			dismissController(stack.peek());
		}
		stack.push(controller);
		presentController(stack.peek());
	}
	
	public void popController() {
		if (stack.size() > 1) {
			dismissController(stack.pop());
			presentController(stack.peek());
		}
	}

	private void presentController(Controller controller) {
		controller.updateBoundary();
		frame.presentView(controller.getGui());
		
	}
	
	private void dismissController(Controller controller) {
		frame.dismissView(controller.getGui());
	}


	
}
