package controller;

import java.util.Stack;

import common.Controller;
import boundary.AppFrame;
import boundary.AppFrame;


public class NavigationManager {

	private AppFrame frame;
	private Stack<Controller> stack;
	
	public NavigationManager(AppFrame frame) {
		setFrame(frame);
		stack = new Stack<Controller>();
	}
	
	public void setFrame(AppFrame frame) {
		this.frame = frame;
	}
	
	public AppFrame getFrame() {
		return frame;
	}
	
	public void replaceController(Controller controller) {
		controller.setNavigationManager(this);
		if (!stack.isEmpty()) {
			dismissController(stack.pop());
		}
		stack.push(controller);
		presentController(stack.peek());
	}
	
	public void pushController(Controller controller) {
		controller.setNavigationManager(this);
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
		controller.viewWillAppear();
		frame.presentView(controller.getPanel());
	}
	
	private void dismissController(Controller controller) {
		frame.dismissView(controller.getPanel());
	}
	
}
