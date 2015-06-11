package controller;

import java.util.Stack;

import boundary.MyFrame;


public class NavigationManager {

	private MyFrame frame;
	private Stack<MyController> stack;
	
	public NavigationManager(MyFrame frame) {
		setFrame(frame);
		stack = new Stack<MyController>();
	}
	
	public void setFrame(MyFrame frame) {
		this.frame = frame;
	}
	
	public MyFrame getFrame() {
		return frame;
	}
	
	public void replaceController(MyController controller) {
		controller.setNavigationManager(this);
		if (!stack.isEmpty()) {
			dismissController(stack.pop());
		}
		stack.push(controller);
		presentController(stack.peek());
	}
	
	public void pushController(MyController controller) {
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

	private void presentController(MyController controller) {
		controller.viewWillAppear();
		frame.presentView(controller.getPanel());
	}
	
	private void dismissController(MyController controller) {
		frame.dismissView(controller.getPanel());
	}
	
}
