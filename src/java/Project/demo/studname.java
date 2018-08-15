/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.demo;

import Project.Connect.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AkAsH KrIsHnA
 */
public class studname extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DBConnect con = new DBConnect();
        String output = "";
        boolean flag=true;
        int i = 0;
          try (PrintWriter out = response.getWriter()) {
            try {
                con.getConnection();
                if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loaddata")) {
                    output = "<table>";
                    con.read("SELECT u.id,u.name FROM studentdetails.user u WHERE STATUS=1");
                    while (con.rs.next()) {
                        flag=false;
                        output += "<tr><td><input type='text' name='name_" + con.rs.getString("id") + "' id='name_" + con.rs.getString("id") + "' value='" + con.rs.getString("name") + "' onchange=\"update(" + con.rs.getString("id") + ");\"/></td>";
                        for (i = 1; i < 10; i++) {
                            if (con.rs.next()) {
                                output += "<td><input type='text' name='name_" + con.rs.getString("id") + "' id='name_" + con.rs.getString("id") + "' value='" + con.rs.getString("name") + "' onchange=\"update(" + con.rs.getString("id") + ");\"/></td>";
                            }
                            if (con.rs.isLast() || con.rs.isAfterLast()) {
                                break;
                            }
                        }
                        if (con.rs.isLast() || con.rs.isAfterLast()) {
                            if (i < 9) {
                                output += "<td><input type='text' name='data_new' id='data_new' value='' onchange=\"add();\"/></td>";
                            } else {
                                output += "<tr><td><input type='text' name='data_new' id='data_new' value='' onchange=\"add();\"/></td></tr>";

                            }
                        }
                        output += "</tr>";
                    }
                    
                    if (flag) {
                            con.insert("INSERT INTO studentdetails.user (id, name, STATUS)VALUES  (null, 'Student Name', '1');");
                            output+="refresh this page";
                        }
                    
                    out.print(output);
                }
                if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("adddata")) {
                    con.insert("INSERT INTO studentdetails.user (id, name, STATUS)VALUES  (null, '" + request.getParameter("addval") + "', '1');");
                }
                if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("updatedata")) {
                    if (request.getParameter("updateval").equalsIgnoreCase("")) {
                        con.delete("UPDATE  studentdetails.user SET  STATUS = '0' WHERE id = '" + request.getParameter("updateid") + "';");
                    } else {
                        con.update("UPDATE  studentdetails.user SET name = '" + request.getParameter("updateval") + "' WHERE id = '" + request.getParameter("updateid") + "';");
                    }
                }
            } catch (Exception e) {
                out.print(e);
            } finally {
                try {
                    con.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(studname.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
        
        
        

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
