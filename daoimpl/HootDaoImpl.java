package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dao.HootDao;
import com.entities.Hoot;
import com.entities.Person;
import com.util.ConnectionConfiguration;

public class HootDaoImpl implements HootDao {
	
    public void createHootTable(){

		Connection connection = null;
		Statement statement = null;
		
		try{
			connection = ConnectionConfiguration.getConnection();
			statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS Hoot (id int primary key unique auto_increment, hootid varchar(55), message varchar(55),date varchar(55))");
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
    public int countHoot(int id){

    	
    	Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int counter = 0;
		try{
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM hoot WHERE hootid = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				counter++;
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
		//System.out.println("hoot count of user = "+counter);
		return counter;
    	
    }
    
    public int insertHoot(Hoot hoot){

    	Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO Hoot (hootid, message,date)" + "VALUES(?,?,?)");
			preparedStatement.setInt(1, hoot.getHootID());
			preparedStatement.setString(2,  hoot.getMessage());
			preparedStatement.setString(3,  hoot.getDate());
			preparedStatement.executeUpdate();
			//System.out.println("INSERT INTO Hoot (message,date)" + "VALUES(?,?)");
			
			
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
    	return hoot.getId();
    }
    public List<Hoot> selectAll(){

		List <Hoot> hoots = new ArrayList<Hoot>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try{
			
			connection = ConnectionConfiguration.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Hoot");
			
			while(resultSet.next()){
				Hoot hoot = new Hoot();
				hoot.setId(resultSet.getInt("id"));
				hoot.setHootID(resultSet.getInt("hootid"));
				hoot.setMessage(resultSet.getString("message"));
				hoot.setDate(resultSet.getString("date"));
				hoots.add(hoot);
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
		
		return hoots;
	}
    
    public boolean isListEmpty(int id){
    	List<Hoot> h = selectById(id);
    	return h.isEmpty();
    }
    
    public List<Hoot> selectById(int id){

    	
    	List <Hoot> hoots = new ArrayList<Hoot>();
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try{
			
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Hoot WHERE hootid = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
				
			while(resultSet.next()){
				Hoot hoot = new Hoot();
		    	hoot.setId(resultSet.getInt("id"));
		    	hoot.setHootID(resultSet.getInt("hootid"));
				hoot.setMessage(resultSet.getString("message"));
				hoot.setDate(resultSet.getString("date"));
				hoots.add(hoot);
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
		
		return hoots;    }
    public void deleteHoot(int id){
    	Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Hoot WHERE hootid = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		//	System.out.println("DELETE FROM Hoot WHERE hootid = ?");
			
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
