package de.tudortmund.cni.ict4es.ocpp.chargepoint.service.utils;

/**
 * @author    Jens Schmutzler <jens.schmutzler@tu-dortmund.de>
 * @author    Thomas Grabowski <thomas.grabowski@tu-dortmund.de>
 * @author    Mohamad Sbeiti <mohamad.sbeiti@telekom.de>
 * 
 *	This program is free software; you can redistribute it
 *	and/or modify it under the terms of the GNU General Public
 *	License as published by the Free Software Foundation; either
 *	version 2 of the License, or (at your option) any later version.
 *  For further information see file COPYING in the top level directory
 *  
 ********************************************************************************
 * This work is a joint work between Communication Networks Institute 
 * (CNI - Prof. Dr.-Ing. Christian Wietfeld) at Technische Universitaet Dortmund, Germany 
 * and the Deutsche Telekom 
 *  ********************************************************************************/
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DialogUI extends JDialog {

	private JFrame parent = null;
	private String name = null;

	private static final long serialVersionUID = 1L;

	public DialogUI(JFrame parent, String name) {

		super(parent, name, true);

		this.parent = parent;
		this.name = name;

		setResizable(false);
		setLocationRelativeTo(parent);
		Box b = Box.createVerticalBox();
		b.add(Box.createGlue());
		JLabel label = new JLabel(
				"OCPP 1.5 - Charge Box Controller GUI (For Testing Purposes)");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setSize(300, 20);
		b.add(label);
		JLabel label_1 = new JLabel("By Communication Networks Institute (CNI)");
		label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		b.add(label_1);
		JLabel label_2 = new JLabel("TU Dortmund University");
		label_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		b.add(label_2);
		b.add(Box.createGlue());
		getContentPane().add(b, "Center");
		JPanel p2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) p2.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(10);
		b.add(p2);
		JButton ok = new JButton("Ok");
		p2.add(ok);

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});

		setSize(400, 197);
	}

	public void showAboutDialog() {
		JDialog f = new DialogUI(parent, name);
		f.setVisible(true);
	}

	public static void main(String[] args) {

		DialogUI dialogUI = new DialogUI(new JFrame(), "About");
		dialogUI.showAboutDialog();

	}

}