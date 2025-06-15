/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

/* global Notification, fetch */

// web/assets/js/notification.js
function showPopup(message) {
    // Using browser notifications
    if (!("Notification" in window)) {
        alert(message); // fallback
    } else if (Notification.permission === "granted") {
        new Notification(message);
    } else if (Notification.permission !== "denied") {
        Notification.requestPermission().then(permission => {
            if (permission === "granted") {
                new Notification(message);
            }
        });
    }
}



// Check for new notifications every 30 seconds
function pollNotifications() {
    fetch('/NotificationSystem/notifications')
        .then(response => response.json())
        .then(data => {
            document.getElementById('notification-count').textContent = data.length;
            data.forEach(n => showPopup(n.message));
        });
}

setInterval(pollNotifications, 30000);

function markAsRead(notificationId) {
    fetch(`/NotificationSystem/notifications?action=markRead&id=${notificationId}`, {
        method: 'POST'
    }).then(() => pollNotifications());
}