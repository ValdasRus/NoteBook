import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;

public class ApplicationNote {

	private static void createAndShowGUI(Connection con) {

		BodyNote bn = new BodyNote(con);
		JFrame frame = new JFrame("Vilko popieriai");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JList<String> list = new JList<String>();
		list.setBounds(10, 10, 150, 300);

		DefaultListModel<String> listModel = new DefaultListModel<String>();
		List<ContactNote> contacts = bn.showAll();
		for (int i = 0; i < contacts.size(); i++) {
			ContactNote contact = contacts.get(i);
			listModel.addElement(contact.getTitle());
		}
		list.setModel(listModel);
		
		
		JList<String> list1 = new JList<String>();
		list1.setBounds(170, 10, 200, 300);

		DefaultListModel<String> listModel1 = new DefaultListModel<String>();
		List<ContactNote> contacts1 = bn.showAll();
		for (int i = 0; i < contacts1.size(); i++) {
			ContactNote contact1 = contacts1.get(i);
			listModel1.addElement(contact1.getNote());
		}
		list1.setModel(listModel1);
		
		

		JTextField titleTextField = new JTextField();
		titleTextField.setBounds(380, 10, 150, 35);

		JTextField noteTextField = new JTextField();
		noteTextField.setBounds(380, 45, 150, 35);

		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				JList<String> list = extracted(evt);
				if (evt.getClickCount() == 2) {
					// JOptionPane.showMessageDialog(frame, "pazymejai varda");
					int index = list.locationToIndex(evt.getPoint());
					if (list.getModel().getElementAt(index) != null
							&& list.getModel().getElementAt(index) instanceof String) {
						// Populates your textField with the element at this
						// index
						titleTextField.setText((String) list.getModel().getElementAt(index));
					}
				}
			}

			private JList<String> extracted(MouseEvent evt) {
				@SuppressWarnings("unchecked")
				JList<String> source = (JList<String>) evt.getSource();
				return source;
			}
		});

		JButton insert = new JButton("Insert");
		insert.setBounds(380, 80, 150, 35);

		insert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String title = titleTextField.getText();
				String note = noteTextField.getText();
				ContactNote contact = new ContactNote();
				contact.setTitle(title);
				contact.setNote(note);
				bn.insert(contact);
				listModel.addElement(contact.getTitle());
				listModel1.addElement(contact.getNote());

			}
		});

		JButton delete = new JButton("Delete");
		delete.setBounds(380, 115, 150, 35);
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String title = titleTextField.getText();
				bn.delete(title);
				List<ContactNote> contacts = bn.showAll();
				listModel.clear();
				listModel1.clear();
				for (ContactNote contact : contacts) {
					listModel.addElement(contact.getTitle());
					listModel1.addElement(contact.getNote());
					
				}
			}
		});

		frame.getContentPane().setLayout(null);
		frame.setPreferredSize(new Dimension(560, 400));
		frame.getContentPane().add(list);
		frame.getContentPane().add(list1);
		frame.getContentPane().add(titleTextField);
		frame.getContentPane().add(noteTextField);
		frame.getContentPane().add(insert);
		frame.getContentPane().add(delete);

		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");
		final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/notebook", "root", "root");

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(conn);
			}
		});
		// conn.close();

	}

}
