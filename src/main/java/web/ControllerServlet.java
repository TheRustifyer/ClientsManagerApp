package web;

import data.ClientDaoJDBC;
import domain.Client;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author Pyzyryab <zerodaycode.eu>
 */

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        // This method will retrieve the Clients and gonna pass it to a JSP, with request scope
        String action = request.getParameter("action");
        
        if (action != null) {
            switch(action) {
                case "edit" :
                    this.editClient(request, response);
                    break;
                case "delete":
                    this.deleteClient(request, response);
                    break;
                default:
                    this.defaultAction(request, response);
            }
        } else {
            this.defaultAction(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException { 
        
        String action = request.getParameter("action");
        
        if (action != null) {
            switch(action) {
                case "add" :
                    this.insertClient(request, response);
                    break;
                case "modify":
                    this.modifyClient(request, response);
                    break;
                default:
                    this.defaultAction(request, response);
            }
        } else {
            this.defaultAction(request, response);
        }
    }
    
    private void defaultAction (HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        // Retrieving all the current clients on DB
        List<Client> clients = new ClientDaoJDBC().listClients();
        
        // Sendind dynamic data to our JSP's
        HttpSession session = request.getSession();
        session.setAttribute("clients", clients);
        session.setAttribute("totalClients", clients.size());
        session.setAttribute("totalBalance", this.calculateTotalBalance(clients));
        
        // Instead of sending foward (leads to duplicate data), when attrs are ready, the default action it's going to the main JSP
        response.sendRedirect("clients.jsp"); // This methods notifies the browser about the action
    }
    
    private void insertClient(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        // Retrieve the values of the add client form
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        double balance = 0.0; 
        String balanceStr = request.getParameter("balance");
        if (balanceStr != null && balanceStr != "") {
            balance = Double.parseDouble(balanceStr);
        }
        
        // Create the new client
        Client client = new Client(name, lastName, email, phoneNumber, balance);
        
        // Insert the client into the DB
        int rowsAffected  = new ClientDaoJDBC().insert(client);
        System.out.println("Rows affected => " +  rowsAffected);
        
        // Redirect to the default action
        this.defaultAction(request, response);
    }
    
    // This method REDIRECTS to the modify client page!
    private void editClient(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        // Get the unique identifier
        int clientID = Integer.parseInt(request.getParameter("clientID"));
        
        // Find the client by ID
        Client client = new ClientDaoJDBC().findClient(new Client(clientID));
        
        // Sending to a reachable scope
        request.setAttribute("client", client);
        request.getRequestDispatcher("/WEB-INF/pages/client/modifyClient.jsp").forward(request, response);
    }
    
     private void modifyClient(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
         
        // Get the unique identifier
        int clientID = Integer.parseInt(request.getParameter("clientID"));
        
        // Retrieve the values of the form
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        double balance = 0.0; 
        String balanceStr = request.getParameter("balance");
        if (balanceStr != null && balanceStr != "") {
            balance = Double.parseDouble(balanceStr);
        }
        
        // Create the new client
        Client client = new Client(clientID, name, lastName, email, phoneNumber, balance);
        
        // Modify the client into the DB
        int rowsAffected  = new ClientDaoJDBC().update(client);
        System.out.println("Rows affected => " +  rowsAffected);
        
        // Redirect to the default action
        this.defaultAction(request, response);
    }
     
    private void deleteClient(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException {
        
        // Get the unique identifier
        int clientID = Integer.parseInt(request.getParameter("clientID"));
        
        // Create the new client
        Client client = new Client(clientID);
        
        // Insert the client into the DB
        int rowsAffected  = new ClientDaoJDBC().delete(client);
        System.out.println("Rows affected => " +  rowsAffected);
        
        // Redirect to the default action
        this.defaultAction(request, response);
        
    }
    
    private double calculateTotalBalance(List<Client> clients) {
        
        int totalBalance = 0;
        for (Client client: clients) {
            totalBalance += client.getBalance();
        }
        return totalBalance;
    }


    
}
