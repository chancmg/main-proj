package org.ire.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FrontUi extends JPanel {

	public JCheckBox[] features;

	public JTextField cluster, seed;
	public boolean checkedFields[];
	public String selected;
	public static String featuresList[] = { "dd", "UNIGRAM", "BIGRAM",
		"TRIGRAM", "CAPITALIZE", "SENTENCECOUNT", "PUNCTUATION", "URL",
		"POSITIVE_NEGATIVE", "LENGTH_OF_DOC" };

	// Create a form with the specified labels, tooltips, and sizes.
	public FrontUi(String[] labels, String[] tips) {
		super(new BorderLayout());
		JPanel labelPanel = new JPanel(new GridLayout(12, 1));
		JPanel fieldPanel = new JPanel(new GridLayout(12, 1));
		// JPanel imagePanel = new JPanel(new FlowLayout());
		ImageIcon image = new ImageIcon("image/cover.jpg");
		JLabel label = new JLabel("", image, JLabel.CENTER);
		JPanel imgpanel = new JPanel(new BorderLayout());
		imgpanel.add(label, BorderLayout.CENTER);

		add(labelPanel, BorderLayout.WEST);
		add(fieldPanel, BorderLayout.CENTER);
		add(imgpanel, BorderLayout.EAST);

		features = new JCheckBox[labels.length];
		checkedFields = new boolean[labels.length];
		cluster = new JTextField();
		cluster.setColumns(3);

		seed = new JTextField();
		seed.setColumns(3);
		for (int i = 0; i < labels.length; i += 1) {
			final int j = i;
			features[i] = new JCheckBox();
			features[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (features[j].isSelected()) {
						checkedFields[j] = true;
					} else {
						checkedFields[j] = false;
					}
				}
			});
			JLabel lab = new JLabel(labels[i], JLabel.LEFT);
			lab.setLabelFor(features[i]);
			labelPanel.add(lab);
			JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p.add(features[i]);
			fieldPanel.add(p);
		}
		
		JLabel lab = new JLabel(" -------------------------------", JLabel.LEFT);
		JLabel lab2 = new JLabel(" -----------------------------", JLabel.RIGHT);
		labelPanel.add(lab);
		fieldPanel.add(lab2);
		
		lab = new JLabel("    Cluster Count", JLabel.LEFT);
		lab.setLabelFor(cluster);
		labelPanel.add(lab);
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(cluster);
		fieldPanel.add(p);
		lab = new JLabel("    Seed Count", JLabel.LEFT);
		lab.setLabelFor(seed);
		labelPanel.add(lab);
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(seed);
		fieldPanel.add(p);

	}

	public String getText(int i) {
		return (features[i].getText());
	}

	public static void main(String[] args) {
		final String[]  labels = { "    Unigram", "    Bigram", "    Trigram",
				"    Capitalize", "    Senetence count", "    Punctuation",
				"    Url", "    positive and negative words","    Document length" };

		String[] descs = { "Unigram", "Bigram", "Trigram", "Capitalize",
				"Senetence count", "Punctuation", "Url",
				"positive and negative words","Document length" };

		final FrontUi form = new FrontUi(labels, descs);

		JButton submit = new JButton("Submit Form");

		String selected = "";
		submit.addActionListener(new ActionListener() {
			final boolean[] cfields = form.checkedFields;
			String sel = "";

			public void actionPerformed(ActionEvent e) {
				System.out.println("Selected fields : ");
				sel = "";
				for (int i = 0; i < cfields.length; i++) {
					if (cfields[i]) {
						System.out.println(cfields[i]);
						sel += (i + 1) + ",";
					}
				}
				int seedCount, clusterCount;
				try {
					seedCount = Integer.parseInt(form.seed.getText());
					clusterCount = Integer.parseInt(form.cluster.getText());
				} catch (NumberFormatException e1) {
					form.cluster.setText("1");
					form.seed.setText("1");
					seedCount = 1;
					clusterCount = 1;

				}
				form.selected = sel;
				if (form.selected.endsWith(",")) {
					form.selected = form.selected.substring(0,
							form.selected.length() - 1);
				} else {
					JOptionPane.showMessageDialog(null, "No feature Selected");
				}
				if (form.selected.length() > 0) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane
							.showConfirmDialog(
									null,
									"Would You Like to Run The Clustering Alorithm on Documents in rawdata Folder?",
									"Confirm", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {
						System.out.println(form.selected);
						if(ExampleApplication.run(form.selected, clusterCount, seedCount)){
							JOptionPane.showMessageDialog(null, "Document Clustering Is successfull Check the cluster in output folder");
							int dialogButton1 = JOptionPane.YES_NO_OPTION;
							int dialogResult1 = JOptionPane
									.showConfirmDialog(
											null,
											"Would You Like Visualize the cluster?",
											"Confirm", dialogButton1);
							if (dialogResult1 == JOptionPane.YES_OPTION) {
								System.out.println(form.selected);
								List<String> curfeatureList = new ArrayList<String>();
								for(int i=0;i<labels.length;i++ ){
								  if(form.checkedFields[i]){
									  curfeatureList.add(featuresList[i+1].toLowerCase());
								  }
								}
								VisualUi.analyseResult(clusterCount,curfeatureList.toArray(new String[curfeatureList.size()]));
							}
							
						}else{
							JOptionPane.showMessageDialog(null, "Error in clustering please check the logs for more details");
						}
					}
				}

			}
		});

		JFrame f = new JFrame("Document clustering");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(form, BorderLayout.NORTH);
		JPanel p = new JPanel();
		p.add(submit);
		f.getContentPane().add(p, BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
	}
}
