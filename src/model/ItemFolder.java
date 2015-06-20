package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

import server.Server;

public class ItemFolder extends Item {

	private static final long serialVersionUID = -1040876964303827361L;
	private LinkedHashSet<Item> contents;
	private DefaultMutableTreeNode treeNode;
	
	
	
	public void addItem(Item item) {
			contents.add(item);
		}

	
	public void removeItem(Item item) {
		if( contents.remove(item) == false )
			System.err.println("ERROR removeItem: the item was not in the folder!");
		}

	
	
	public DefaultMutableTreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(DefaultMutableTreeNode treeNode) {
		this.treeNode = treeNode;
	}
	
	
}
