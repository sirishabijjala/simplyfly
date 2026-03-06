function getHeaders() {

    const token = localStorage.getItem("token");

    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
    };
}

function showSection(section) {

    const container = document.getElementById("content");

    if (section === "flights") {
        loadFlights(container);
    }

    else if (section === "schedules") {
        loadSchedules(container);
    }

    else if (section === "bookings") {
        loadBookings(container);
    }

    else if (section === "routes") {
        loadRoutes(container);
    }
}

function logout() {

    localStorage.removeItem("token");
    localStorage.removeItem("ownerId");

    window.location.href = "/login.html";
}
document.addEventListener("DOMContentLoaded", () => {
    showSection("flights");
});
function formatDate(dateTime){

    if(!dateTime) return "N/A"

    return new Date(dateTime).toLocaleString()
}