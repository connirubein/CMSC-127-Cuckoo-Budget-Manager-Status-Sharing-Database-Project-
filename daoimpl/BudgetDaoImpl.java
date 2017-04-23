package daoimpl;

import com.dao.BudgetDao;
import com.entities.Budget;
import com.util.ConnectionConfiguration;

import java.sql.*;

/**
 * Created by jezamartu on 4/17/2017.
 */
public class BudgetDaoImpl implements BudgetDao {
    public void createBudgetTable(){
        Connection connection =null;
        Statement statement = null;

        try{
            connection = ConnectionConfiguration.getConnection();
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Budget (user_id varchar(55),daily_budget varchar(55),date varchar(55),target varchar(55),meet varchar(55),expense varchar(55))"); //call id doesn't exist and can only be created once
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
    }//called once only ever, same with person table and items table

    public void insertBudget(Budget budget){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Budget (user_id,daily_budget,date,target,meet,expense)" + "VALUES(?,?,?,?,?,?)");
            preparedStatement.setInt(1, budget.getUser_ID());
            preparedStatement.setFloat(2, budget.getDaily_budget());
            preparedStatement.setString(3, budget.getDate());
            preparedStatement.setFloat(4,  budget.getTarget());
            preparedStatement.setBoolean(5, budget.isMeet());
            preparedStatement.setFloat(6, budget.getExpense());
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
    }//adds new day with new set of expenses

    public boolean ifMet(int id, String day){
        Budget bud = selectByIdDate(id,day);
        if(bud.getExpense()<=(bud.getDaily_budget()-bud.getTarget())){
            return true;
        }
        else{
            return false;
        }
    }//returns true if total expenses is less than budget-expenses, false if not

    public Budget selectByIdDate(int id, String day) {
        Budget budget = new Budget();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null; // table of records from database that is retrieved after query

        try{
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Budget WHERE user_id = ? AND date = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, day);
            resultSet = preparedStatement.executeQuery();

            System.out.println(id+ " "+day);
            
            while (resultSet.next()){
                budget.setUser_ID(resultSet.getInt("user_id"));
                budget.setDaily_budget(resultSet.getFloat("daily_budget"));
                budget.setDate(resultSet.getString("date"));
                budget.setTarget(resultSet.getFloat("target"));
                budget.setMeet(resultSet.getBoolean("meet"));
                budget.setExpense(resultSet.getFloat("expense"));
            }
            
            System.out.println("IN IMPL: "+budget.getExpense()+" "+budget.getTarget()+" "+budget.getDaily_budget()+" ");
			

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

        return budget;
    }//gets day specified, called when user selects day they want to view progress of, returns budget of that day

    public int selectAllMeet(int id) {
        Budget budget = new Budget();
        boolean condition = true;
        int meetInt = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null; // table of records from database that is retrieved after query

        try{
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Budget WHERE user_id = ? AND meet = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setBoolean(2, condition);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                meetInt++;
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

        return meetInt;
    }//returns number of days the user met target percentage; called to update level from person table

    public void deleteById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Budget WHERE user_id = ?");
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

    public void updateBudgetExpenses(int user_id, String date){
        ItemDaoImpl item = new ItemDaoImpl();
        float tmp = item.getTotalExpensesForTheDay(user_id,date);
        boolean M = ifMet(user_id,date);
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Budget SET expense = ?,meet = ? WHERE user_id = ? AND date = ?");
            preparedStatement.setFloat(1, tmp);
            preparedStatement.setBoolean(2, M);
            preparedStatement.setInt(3, user_id);
            preparedStatement.setString(4, date);
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
    }//updates expenses of user id sent through summing up price of items from that user on that day

}
