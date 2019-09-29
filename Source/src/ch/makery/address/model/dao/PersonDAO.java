package ch.makery.address.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ch.makery.address.model.Person;
import ch.makery.address.util.db.MySQLConnection;

public class PersonDAO {
     private static final PersonDAO instance = new PersonDAO();
    
    
    public static PersonDAO getInstance(){
        return instance;
    }
    
    public void insert(Person person) throws Exception {
        
       
        if (person == null) {
            throw new Exception("Person cannot be null");
        }
	try {
            Connection connection = MySQLConnection.getInstance().getConnection();
            
            
	    String sql = "INSERT INTO `person`"
                        + "(`FirstName`, `LastName`, `Street`, `PostalCode`, `City`, `Birthday`) "
                        + "VALUES (?, ?, ?, ?, ?, ?);";

	    PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.setString(3, person.getStreet());
            pstmt.setInt(4, person.getPostalCode());
            pstmt.setString(5, person.getCity());
            pstmt.setDate(6, getDateLocalToSql(person.getBirthday()));


            pstmt.executeUpdate();

            pstmt.close();
            connection.close();

	    } catch(SQLException ex){
        	
	}
        
    }
    
	public void update(String firstName, String lastName, Person person) throws Exception {
		if (person == null) {
            throw new Exception("Person cannot be null");
        }

		try {
			Connection connection = MySQLConnection.getInstance().getConnection();


	        String sql = "UPDATE `person` SET `FirstName`=?,`LastName`=?,"
	        		+ "`Street`=?,`PostalCode`=?,`City`=?,`Birthday`=?"
	        		+ "WHERE `FirstName` = ? AND `LastName` = ?";

	        PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.setString(3, person.getStreet());
            pstmt.setInt(4, person.getPostalCode());
            pstmt.setString(5, person.getCity());
            pstmt.setDate(6, getDateLocalToSql(person.getBirthday()));
            pstmt.setString(7, firstName);
            pstmt.setString(8, lastName);


            pstmt.executeUpdate();

            pstmt.close();
            connection.close();

	    } catch(SQLException ex){
        	throw new Exception(ex);
        }
	}

	public boolean remove(String firstName, String lastName) throws Exception {
		if(firstName == null || lastName == null)
			throw new Exception("Parameters cant be null");

		try {
			Connection connection = MySQLConnection.getInstance().getConnection();


	        String sql = "DELETE FROM `person` WHERE `FirstName` = ? AND `LastName` = ?";

	        PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);


            int removedRows = pstmt.executeUpdate();

            pstmt.close();
            connection.close();

            if(removedRows == 1){
                return true;
            }

            throw new Exception("Something went wrong when delete.");

	    } catch (ClassNotFoundException ex) {
            throw new Exception("Driver not found");
        } catch(SQLException ex){
        		throw new Exception(ex);
        }
	}

	public boolean removeAll() throws Exception {
		try {
			Connection connection = MySQLConnection.getInstance().getConnection();


	        String sql = "DELETE FROM `person`";

	        PreparedStatement pstmt = connection.prepareStatement(sql);

            int removedRows = pstmt.executeUpdate();

            pstmt.close();
            connection.close();

            if(removedRows >= 1){
                return true;
            }

            throw new Exception("Something went wrong when delete.");

	    } catch (ClassNotFoundException ex) {
            throw new Exception("Driver not found");
        } catch(SQLException ex){
        		throw new Exception(ex);
        }
	}

	public List<Person> listAll() throws Exception {
		List<Person> people = new ArrayList<Person>();

		 try {
            Connection connection = MySQLConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM Person;";

            ResultSet rs = stmt.executeQuery(sql);
            Person person;

            while(rs.next()){
            	person = new Person();

            	person.setFirstName(rs.getString("FirstName"));
            	person.setLastName(rs.getString("LastName"));
            	person.setCity(rs.getString("City"));
            	person.setStreet(rs.getString("Street"));
            	person.setPostalCode(rs.getInt("PostalCode"));
            	person.setBirthday(getDateSqlToLocal(rs.getDate("Birthday")));

                people.add(person);
            }

            rs.close();
            stmt.close();
            connection.close();

            return  people;
        } catch(SQLException ex){
            throw new Exception(ex);
        }

	}

	 private java.sql.Date getDateLocalToSql (LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

        private LocalDate getDateSqlToLocal (java.sql.Date date) {
	    return date.toLocalDate();
    }
}
