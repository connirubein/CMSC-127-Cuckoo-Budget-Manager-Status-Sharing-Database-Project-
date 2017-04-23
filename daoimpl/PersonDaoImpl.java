package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dao.PersonDao;
import com.entities.Person;
//import com.mysql.jdbc.Statement;
import com.util.ConnectionConfiguration;

public class PersonDaoImpl implements PersonDao{
	
	public void createPersonTable(){

		//System.out.println("create table Person class");
		Connection connection = null;
		Statement statement = null;
		
		try{
			connection = ConnectionConfiguration.getConnection();
			statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS Person (id int primary key unique auto_increment, first_name varchar(55),last_name varchar(55), user_name varchar(55),pass_word varchar(55), level varchar(55), dateOfLastAdd varchar(55), numberOfMeets varchar(55))");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
			if(statement!=null){
				try{
					statement.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			
			if(connection!=null){
				try{
					connection.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public int insert(Person person){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO Person (first_name,last_name,user_name,pass_word, level, dateOfLastAdd, numberOfMeets)" + "VALUES(?,?,?,?,?,?,?)");
			preparedStatement.setString(1, person.getFirstName());
			preparedStatement.setString(2,  person.getLastName());
			preparedStatement.setString(3,  person.getUserName());
			preparedStatement.setString(4,  person.getPassWord());
			preparedStatement.setInt(5,  person.getLevel());
			preparedStatement.setString(6,  person.getDateOfLastAdd());
			preparedStatement.setInt(7,  person.getNumberOfMeets());
			preparedStatement.executeUpdate();
			//System.out.println("INSERT INTO Person (first_name,last_name,user_name,pass_word, level, dateOfLastAdd, numberOfMeets)" + "VALUES(?,?,?,?,?,?,?)");
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(preparedStatement != null){
				try{
					preparedStatement.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			try{
				if(connection != null){
					connection.close();
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return person.getId();
	}
	
	public Person selectById(int id){
		
		Person person = new Person();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Person WHERE id = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
				
			while(resultSet.next()){
				person.setId(resultSet.getInt("id"));
				person.setFirstName(resultSet.getString("first_name"));
				person.setLastName(resultSet.getString("last_name"));
				person.setUserName(resultSet.getString("user_name"));
				person.setPassword(resultSet.getString("pass_word"));
				person.setLevel(resultSet.getInt("level"));
				person.setDateOfLastAdd(resultSet.getString("dateOfLastAdd"));
				person.setNumberofMeets(resultSet.getInt("numberOfMeets"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(resultSet!=null){
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(preparedStatement!=null){
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return person;
	}
	
	public Person selectByUserName(String uName){
		
		Person person = new Person();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Person WHERE user_name = ?");
			preparedStatement.setString(1, uName);
			resultSet = preparedStatement.executeQuery();
				
			while(resultSet.next()){
				person.setId(resultSet.getInt("id"));
				person.setFirstName(resultSet.getString("first_name"));
				person.setLastName(resultSet.getString("last_name"));
				person.setUserName(resultSet.getString("user_name"));
				person.setPassword(resultSet.getString("pass_word"));
				person.setLevel(resultSet.getInt("level"));
				person.setDateOfLastAdd(resultSet.getString("dateOfLastAdd"));
				person.setNumberofMeets(resultSet.getInt("numberOfMeets"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(resultSet!=null){
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(preparedStatement!=null){
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return person;
	}
	
	public List<Person> selectAll(){
		List <Person> persons = new ArrayList<Person>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try{
			
			connection = ConnectionConfiguration.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM person");
			
			while(resultSet.next()){
				Person person = new Person();
				person.setId(resultSet.getInt("id"));
				person.setFirstName(resultSet.getString("first_name"));
				person.setLastName(resultSet.getString("last_name"));
				person.setUserName(resultSet.getString("user_name"));
				person.setLevel(resultSet.getInt("level"));
				person.setDateOfLastAdd(resultSet.getString("dateOfLastAdd"));
				person.setNumberofMeets(resultSet.getInt("numberOfMeets"));
				persons.add(person);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(resultSet!=null){
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(statement!=null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return persons;
	}
	
	public void delete(int id){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			//System.out.println("DELETE FROM Person WHERE id = ?");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(preparedStatement != null){
				try{
					preparedStatement.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			try{
				if(connection != null){
					connection.close();
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public void delete(String delUserName){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE user_name = ?");
			preparedStatement.setString(1, delUserName);
			preparedStatement.executeUpdate();
			//System.out.println("DELETE FROM Person WHERE user_name = ?");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(preparedStatement != null){
				try{
					preparedStatement.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			try{
				if(connection != null){
					connection.close();
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public void update(Person person, int id){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE Person SET "+"first_name = ?, last_name = ?, user_name = ?, pass_word = ?, level = ?, dateOfLastAdd = ?, numberOfMeets = ? WHERE id = ?");
			preparedStatement.setString(1, person.getFirstName());
			preparedStatement.setString(2, person.getLastName());
			preparedStatement.setString(3, person.getUserName());
			preparedStatement.setString(4, person.getPassWord());
			preparedStatement.setInt(5, person.getLevel());
			preparedStatement.setString(6, person.getDateOfLastAdd());
			preparedStatement.setInt(7, person.getNumberOfMeets());
			
			preparedStatement.setInt(8,id);
			preparedStatement.executeUpdate();
		//	System.out.println("UPDATE Person SET "+"first_name = ?, last_name = ?, user_name = ?, pass_word = ? , level = ?, dateOfLastAdd = ?, numberOfMeets = ? WHERE id = ?");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(preparedStatement != null){
				try{
					preparedStatement.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			try{
				if(connection != null){
					connection.close();
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
