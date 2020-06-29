package se.lexicon;

import se.lexicon.entity.DbSource;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        System.out.println( "Get DB Connection" );
        DbSource.getConnection();
    }
    
}
