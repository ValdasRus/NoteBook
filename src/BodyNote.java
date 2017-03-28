import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BodyNote {
	
	
	private ArrayList<ContactNote> list = new ArrayList<ContactNote>();
	
	private Connection conn;
	
	public BodyNote(Connection con) {
		conn = con;
	}
	
	public void insert(ContactNote contact) {
		String title = contact.getTitle();
		String note = contact.getNote();
		
    	String query = "INSERT INTO notebook.notebook (Title, Note) VALUES('" + title + "','" + note + "')";
    	System.out.println(query);
    	try {
			Statement stat = conn.createStatement();
			stat.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//print(list, "Insert : " + contact.getnote() + ", " + contact.gettitle());
	}
	
	public void delete(String titlename) {
		for(int i = 0; i < list.size(); i++) {
			ContactNote value  = list.get(i);
			if(value.getTitle().indexOf(titlename) != -1) {
				list.remove(i);
				break;
			}
		}
		String query = "DELETE FROM notebook.notebook WHERE title='" + titlename + "'; "; 
		
		try {
			Statement stat = conn.createStatement();
			stat.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//print(list, "Delete: " + titlename);
	}
	
public List<ContactNote> showAll() {	
		
		ArrayList<ContactNote> list = new ArrayList<ContactNote>();
		
    	String query = "SELECT Title, Note from notebook.notebook";
    	System.out.println(query);
    	try {
			Statement stat = conn.createStatement();
			ResultSet result = stat.executeQuery(query);
			while(result.next()) {
				String title = result.getString("title");
				String note = result.getString("note");
				ContactNote contact = new ContactNote();
				contact.setTitle(title);
				contact.setNote(note);
				list.add(contact);
				//System.out.println(name + ", " + email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//print(list, "Show all ");
		return list;
	}
	
	

}
