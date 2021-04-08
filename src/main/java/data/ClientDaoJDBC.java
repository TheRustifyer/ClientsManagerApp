package data;

import domain.Client;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Pyzyryab <zerodaycode.eu>
 */
public class ClientDaoJDBC {

    // Attributes for store the DB queries
    private static final String SQL_SELECT = "SELECT client_id, name, last_name, email, phone_number, balance"
            + " FROM client";

    private static final String SQL_SELECT_BY_ID = "SELECT client_id, name, last_name, email, phone_number, balance"
            + " FROM client WHERE client_id = ?";

    private static final String SQL_INSERT = "INSERT INTO client(name, last_name, email, phone_number, balanace"
            + " VALUES(?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE client"
            + " SET name?, last_name=?, email=?, phone_number=?, balance=? WHERE client_id = ?";

    private static final String SQL_DELETE = "DELETE FROM cliente WHERE client_id = ?";

    public List<Client> listClients() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Client client = null;
        List<Client> clients = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int clientID = rs.getInt("client_id");
                String name = rs.getString("name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                Double balance = rs.getDouble("balance");

                client = new Client(clientID, name, lastName, email, phoneNumber, balance);
                clients.add(client);
            }
            
            DBConnection.close(rs);
            DBConnection.close(stmt);
            DBConnection.close(conn);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        if (clients.isEmpty()) {
            Client default_client = new Client(0, "Client", "By Default", "nice-java-bugs@fixit-oracle.bug", "655666555", 1000000000);
            clients.add(default_client);
            return clients;
        } else {
            return clients;
        }

    }

    public Client findClient(Client client) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, client.getClientID());
            rs = stmt.executeQuery();
            rs.next(); // need to move to the first record returned, if Client client

            String name = rs.getString("name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phone_number");
            Double balance = rs.getDouble("balance");

            client.setName(name);
            client.setLastName(lastName);
            client.setEmail(email);
            client.setPhoneNumber(phoneNumber);
            client.setBalance(balance);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            DBConnection.close(rs);
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return client;
    }

    // Method for insert a new client, which returns the affected rows
    public int insert(Client client) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int affectedRows = 0;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getPhoneNumber());
            stmt.setDouble(5, client.getBalance());

            affectedRows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return affectedRows;
    }

    // Method for update a DB record
    public int update(Client client) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int affectedRows = 0;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getPhoneNumber());
            stmt.setDouble(5, client.getBalance());
            // For filter and modifiy the unique desired client
            stmt.setInt(6, client.getClientID());

            affectedRows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return affectedRows;
    }

    // Method for delete a DB record
    public int delete(Client client) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int affectedRows = 0;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);

            stmt.setDouble(1, client.getClientID());

            affectedRows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return affectedRows;
    }
}
