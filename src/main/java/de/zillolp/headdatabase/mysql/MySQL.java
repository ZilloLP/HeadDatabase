package de.zillolp.headdatabase.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import de.zillolp.headdatabase.utils.ConfigUtil;
import org.bukkit.Bukkit;

public class MySQL {
    public final static String tableName = "headdatabase";
    private static String host;
    private static String port;
    private static String database;
    private static String user;
    private static String password;
    public static Connection connection;
    public static boolean connected;
    private static boolean disabled;

    public static void load() {
        ConfigUtil configUtil = new ConfigUtil("mysql.yml");
        host = configUtil.getString("Host");
        port = configUtil.getString("Port");
        database = configUtil.getString("Database");
        user = configUtil.getString("User");
        password = configUtil.getString("Password");
        connect();
    }

    private static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
            update("CREATE TABLE IF NOT EXISTS " + tableName + "(CATEGORY varchar(64), HEADNAME varchar(64), TEXTUREURL tinytext);");
            Bukkit.getConsoleSender().sendMessage("§7[§aHeadDatabase§7] §aDie Verbindung mit der MySQL wurde hergestellt!");
            connected = true;
            disabled = false;
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§7[§aHeadDatabase§7] §cDie Verbindung mit der MySQL ist fehlgeschlagen! §4Fehler: " + e.getMessage());
            connected = false;
            disabled = true;
        }
    }

    public static void close() {
        try {
            if (connection != null) {
                connection.close();
                Bukkit.getConsoleSender().sendMessage("§7[§aHeadDatabase§7] §cDie Verbindung mit der MySQL wurde beendet!");
                connected = false;
                disabled = true;
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§7[§aHeadDatabase§7] §cDie Verbindung mit der MySQL konnte nicht beendet werden! §4Fehler: " + e.getMessage());
            connected = false;
            disabled = true;
        }
    }

    public static void update(String qre) {
        if (!(disabled)) {
            CompletableFuture.runAsync(() -> {
                if (connection != null) {
                    try {
                        PreparedStatement st = connection.prepareStatement(qre);
                        st.executeUpdate();
                        st.close();
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                }
            });
        } else {
            if (connection != null) {
                try {
                    PreparedStatement st = connection.prepareStatement(qre);
                    st.executeUpdate();
                    st.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public static ResultSet query(String qre) {
        if (connection != null) {
            ResultSet rs = null;
            try {
                PreparedStatement st = connection.prepareStatement(qre);
                rs = st.executeQuery();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            return rs;
        }
        return null;
    }
}
