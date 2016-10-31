package ru.innopolis.web.springMVC.server.dao;


import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DbPool {
    private static DataSource dataSource;
    DbPool() throws NamingException {

            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/mvc");
    }
    public static Connection getConn() throws SQLException {
        return dataSource.getConnection();
    }
}
