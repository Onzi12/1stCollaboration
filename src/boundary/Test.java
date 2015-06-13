import javax.swing.JTextField;

setBounds(100, 100, 601, 211);
			getContentPane().setBackground(UIManager.getColor("text"));
			getContentPane().setLayout(null);
			lblUpdateFile = new JLabel("Update File");
			lblUpdateFile.setBounds(10, 3, 209, 42);
			lblUpdateFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
			getContentPane().add(lblUpdateFile);
			
			JSeparator separator = new JSeparator();
			separator.setBounds(10, 42, 522, 11);
			separator.setForeground(Color.BLUE);
			getContentPane().add(separator);
			
			JPanel contentsPanel = new JPanel();
			contentsPanel.setBounds(10, 64, 565, 65);
			contentsPanel.setBackground(UIManager.getColor("text"));
			getContentPane().add(contentsPanel);
			contentsPanel.setLayout(null);
			
			JLabel label = new JLabel("Filename: ");
			label.setBounds(10, 14, 49, 14);
			label.setForeground(Color.BLACK);
			contentsPanel.add(label);
			
			tfFilename = new JTextField(30);
			tfFilename.setBounds(64, 11, 491, 20);
			tfFilename.setEditable(false);
			tfFilename.setBackground(Color.WHITE);
			contentsPanel.add(tfFilename);
			
			label_1 = new JLabel("Path: ");
			label_1.setBounds(10, 42, 47, 14);
			contentsPanel.add(label_1);
			
			tfPath = new JTextField(30);
			tfPath.setBackground(Color.WHITE);
			tfPath.setEditable(false);
			tfPath.setBounds(64, 39, 448, 20);
			contentsPanel.add(tfPath);
			
			btnPath = new JButton("");
			btnPath.setBounds(522, 42, 33, 20);
			contentsPanel.add(btnPath);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBounds(10, 126, 565, 36);
			buttonPanel.setBackground(Color.WHITE);
			getContentPane().add(buttonPanel);
			buttonPanel.setLayout(null);
			
			btnCancel = new JButton("Cancel");
			btnCancel.setBounds(481, 11, 74, 23);
			buttonPanel.add(btnCancel);
			
			btnSave = new JButton("Save");
			btnSave.setBounds(406, 11, 65, 23);
			buttonPanel.add(btnSave);