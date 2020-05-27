package com.dasanjos.java;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("all")

public class Server extends javax.swing.JFrame implements ActionListener, ChangeListener {

	private JTextField puerto;

	public Server() {

		getContentPane().addKeyListener(new KeyAdapter() {

			@Override

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					System.exit(0);

				}
			}

		});

		setTitle("Simple Web Server");

		setType(Type.NORMAL);

		initComponents();

		this.setVisible(true);

	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setResizable(false);

		puerto = new JTextField();
		puerto.setHorizontalAlignment(SwingConstants.CENTER);

		puerto.setText("8080");
		puerto.setFont(new Font("Arial", Font.PLAIN, 16));
		puerto.setColumns(10);

		JLabel lblNewLabel = new JLabel("Port >1023");

		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(Server.class.getResource("/imagenes/port.png")));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));

		final JButton btnNewButton = new JButton("Start");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {

					String estado = btnNewButton.getText();

					int numPort = 0;

					numPort = Integer.parseInt(puerto.getText());

					if (numPort > 1023) {

						WebServer server = new WebServer();

						server.start(numPort);

					}

				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnNewButton.setIcon(new ImageIcon(Server.class.getResource("/imagenes/start.png")));
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(55).addComponent(btnNewButton))
								.addGroup(layout.createSequentialGroup().addGap(32)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addComponent(puerto, GroupLayout.PREFERRED_SIZE, 181,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNewLabel))))
						.addContainerGap(32, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGap(19).addComponent(lblNewLabel).addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(puerto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(btnNewButton).addContainerGap(49, Short.MAX_VALUE)));

		getContentPane().setLayout(layout);
		setSize(new Dimension(249, 264));
		setLocationRelativeTo(null);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
