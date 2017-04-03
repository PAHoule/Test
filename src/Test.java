import java.util.ConcurrentModificationException;
import java.sql.*;

/**
 * Created by Pierre-Anthony Houle on 2017-02-16.
 */
public class Test
{
    public static void main(String args[])
    {
        String url = "jdbc:oracle:thin:@mercure.clg.qc.ca:1521:orcl";
        String usr = "houlepie";
        String passw = "ORACLE1";
        Connection conn = null;
        Statement stm = null;
        Statement stm2 = null;
        ResultSet rst = null;
        String sqlinsert = "INSERT INTO EMPLOYESBIDON(NOMEMP, PRENOMEMP, EMPLOI, SALAIRE) VALUES('TEST', 'TEST', 'ANALYSTE', '50000')";
        String sqldelete = "DELETE FROM EMPLOYESBIDON WHERE NOMEMP = 'TEST'";
        String sqlselect = "SELECT * FROM EMPLOYESBIDON";

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Driver chargé!");
            conn = DriverManager.getConnection(url, usr, passw);
            System.out.println("Connection ouverte!");

            try
            {
                stm = conn.createStatement();
                stm2 = conn.createStatement();
                int n = stm.executeUpdate(sqlinsert);
                System.out.println(n + " lignes on été insérée(s)");
                rst = stm2.executeQuery(sqlselect);
                while(rst.next())
                {
                    System.out.println(rst.getString(2) + " " + rst.getString(3));
                }
                n = stm.executeUpdate(sqldelete);
                System.out.println(n + " lignes on été suprimée(s)");
                rst = stm2.executeQuery(sqlselect);
                while(rst.next())
                {
                    System.out.println(rst.getString(2) + " " + rst.getString(3));
                }
                stm.close();
                stm2.close();
                rst.close();

            } catch (SQLException sqle)
            {
                System.err.println(sqle.getMessage());
            }
        }
        catch (ClassNotFoundException classexcep)
        {
            System.out.println("Driver manquant!");
        }
        catch (SQLException sqlexcep)
        {
            System.out.println("Connection impossible! \n" + sqlexcep.getMessage());
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
                else
                    throw new SQLException();
                System.out.println("Connection fermée!");
            }
            catch (SQLException slqexcep)
            {
                System.out.println("La connection était déjà fermé!");
            }
        }
    }
}
