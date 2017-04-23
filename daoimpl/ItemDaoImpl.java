package daoimpl;

import com.dao.ItemDao;
import com.entities.Budget;
import com.entities.Item;
import com.entities.Person;
import com.util.ConnectionConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jezamartu on 4/18/2017.
 */
public class ItemDaoImpl implements ItemDao {

    public void createItemTable(){
        Connection connection =null;
        Statement statement = null;

        try{
            connection = ConnectionConfiguration.getConnection();
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Item (user_id varchar(55),date varchar(55),name varchar(55),price varchar(55))"); //call id doesn't exist and can only be created once
        }catch(Exception e){
            e.printStackTrace();
        }finally{
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
    }//called once only ever, same with person table and budget table

    public float getTotalExpensesForTheDay(int user_id, String date){
        float totalExpenses = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null; // table of records from database that is retrieved after query

        try{
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Item WHERE user_id = ? AND date = ?");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, date);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                totalExpenses+=resultSet.getFloat("price");
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(resultSet !=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement !=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return totalExpenses;
    }//returns value of total expenses for the date and of user

    public boolean addItem(Item item){
        boolean valid = false;
        BudgetDaoImpl budgetD = new BudgetDaoImpl();
        Budget budget = budgetD.selectByIdDate(item.getUser_ID(), item.getDate());
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            //Checks if expenses do not exceed budget FIRST before adding to table
            if((budget.getExpense()+item.getItem_price())<=budget.getDaily_budget()) {//remove or statement later
                valid = true;
                connection = ConnectionConfiguration.getConnection();
                preparedStatement = connection.prepareStatement("INSERT INTO Item (user_id,date,name,price)" + "VALUES(?,?,?,?)");
                preparedStatement.setInt(1, item.getUser_ID());
                preparedStatement.setString(2, item.getDate());
                preparedStatement.setString(3, item.getItem_name());
                preparedStatement.setFloat(4, item.getItem_price());
                preparedStatement.executeUpdate();
                BudgetDaoImpl bdi = new BudgetDaoImpl();
                bdi.updateBudgetExpenses(item.getUser_ID(),item.getDate());
                System.out.println("IN IMPLDJALKSJDALK"+item.getUser_ID()+" "+ item.getDate());
            }

            else{System.out.println("INVALID ");}

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

        return valid;
    }//adds new item with already initialized variables and values, budget imported to check if the item is to be added, total expenses will not exceed daily budget
    //returns true if expenses do not exceed budget after adding, false otherwise, prompt user nga "Either your expenses exceed your daily budget, or you might want to review the data you entered ;)"

    public void deleteById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Item WHERE user_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

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

    public Object[][] getItemList(int id,String date){
        List<String> items = new ArrayList<String>();
        int rows=0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionConfiguration.getConnection();
            statement = connection.prepareStatement("SELECT * FROM Item WHERE user_id = ? AND date = ?");
            statement.setInt(1, id);
            statement.setString(2, date);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                items.add(resultSet.getString("name"));
                items.add(Float.toString(resultSet.getFloat("price")));
                rows++;
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

        Object[][] data = new Object[rows][2];

        for(int i=0, k=0;i<rows;i++){
            for(int j=0;j<2;j++,k++){
                data[i][j]=items.get(k);
            }
        }

        return data;
    }
}
