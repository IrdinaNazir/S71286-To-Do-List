<%-- 
    Document   : notification
    Created on : 12 Jun 2025, 4:54:13 PM
    Author     : Irdina Nazir
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Notifications</title>
        <style>
            .notification {
                border: 1px solid #ddd;
                padding: 10px;
                margin: 5px 0;
                border-radius: 4px;
            }
            #notification-count {
                background: red;
                color: white;
                border-radius: 50%;
                padding: 2px 6px;
                font-size: 12px;
            }
        </style>
        <script>
            // Ask for permission on load
            document.addEventListener("DOMContentLoaded", function () {
                if ("Notification" in window) {
                    if (Notification.permission !== "granted") {
                        Notification.requestPermission();
                    }
                }
            });

            // Trigger the pop-up notification
            function showNotification(title, body) {
                if ("Notification" in window && Notification.permission === "granted") {
                    new Notification(title, {
                        body: body,
                        icon: "images/bell.png" // Optional: change or remove
                    });
                }
            }
        </script>
    </head>
    <body>
        <h1>Your Notifications</h1>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <div id="notification-list">
            <c:choose>
                <c:when test="${empty notifications}">
                    <p>No new notifications</p>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${notifications}" var="notification">
                        <div class="notification" data-id="${notification.id}">
                            <p>${notification.message}</p>
                            <small>${notification.createdAt}</small>
                            <form action="notifications" method="POST" style="display:inline">
                                <input type="hidden" name="notificationId" value="${notification.id}">
                                <button type="submit">Dismiss</button>
                            </form>
                        </div>

                        <!-- Call JavaScript to show popup -->
                        <script>
                            showNotification("New Notification", "${fn:escapeXml(notification.message)}");
                        </script>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <script>
            // Ask for notification permission
            document.addEventListener('DOMContentLoaded', () => {
                if (Notification.permission !== "granted") {
                    Notification.requestPermission();
                }

                // Replace this with real notifications from server
            <c:forEach items="${notifications}" var="notification">
                new Notification("📢 New Notification", {
                    body: "${notification.message}",
                    icon: "https://cdn-icons-png.flaticon.com/512/1827/1827392.png" // optional
                });
            </c:forEach>
            });
        </script>

        <script>
            console.log("Checking if notifications exist...");
            <c:forEach items="${notifications}" var="notification">
            console.log("Notification: ${notification.message}");
            </c:forEach>
        </script>



        <script src="assets/js/notification.js"></script>
    </body>
</html>
