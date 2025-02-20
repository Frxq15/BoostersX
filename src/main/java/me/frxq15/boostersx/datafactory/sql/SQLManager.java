package me.frxq15.boostersx.datafactory.sql;

import me.frxq15.boostersx.BoostersX;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager {
    private final BoostersX plugin;
    private Connection connection;
    private String host, database, username, password, table;
    private int port;
    public SQLManager(BoostersX plugin) {
        this.plugin = plugin;
        setDetails();
    }
    public void setDetails() {
        this.host = plugin.getConfig().getString("database.mysql.host");
        this.port = plugin.getConfig().getInt("database.mysql.port");
        this.database = plugin.getConfig().getString("database.mysql.database");
        this.username = plugin.getConfig().getString("database.mysql.username");
        this.password = plugin.getConfig().getString("database.mysql.password");
    }

    public synchronized boolean connect() {
        try {
            if (connection != null && !connection.isClosed()) return true;

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
            connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            connection = null;
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void disconnect() {
        if (connection == null) return;

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public synchronized Connection getConnection() {
        return connection;
    }
}
