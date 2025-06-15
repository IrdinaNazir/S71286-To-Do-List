/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Dao.NotificationDao;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
/**
 *
 * @author Irdina Nazir
 */
@WebServlet("/notifications")
public class NotificationServlet extends HttpServlet {
    
    private NotificationDao notificationDao;
    
    @Override
    public void init() throws ServletException {
        this.notificationDao = new NotificationDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String notificationID = request.getParameter("notificationId");
            // Implement marking as read logic here
            response.sendRedirect("notifications");
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            int userId = (Integer) session.getAttribute("userID");
            request.setAttribute("notifications", notificationDao.getUnreadNotifications(userId));
            request.getRequestDispatcher("/notifications.jsp").forward(request, response);
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }
    
    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("error", "Error processing notifications: " + e.getMessage());
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}