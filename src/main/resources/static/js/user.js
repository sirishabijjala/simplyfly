// Minimum date
document.addEventListener("DOMContentLoaded", function () {
    let today = new Date().toISOString().split("T")[0];
    document.getElementById("journeyDate").setAttribute("min", today);
    document.getElementById("returnDate").setAttribute("min", today);
	
});

// Passengers counters
let counts = { adult: 1, child: 0, infant: 0 };

function updatePassengerSummary() {
    const parts = [];
    if (counts.adult) parts.push(`${counts.adult} Adult${counts.adult > 1 ? 's' : ''}`);
    if (counts.child) parts.push(`${counts.child} Child${counts.child > 1 ? 'ren' : ''}`);
    if (counts.infant) parts.push(`${counts.infant} Infant${counts.infant > 1 ? 's' : ''}`);
    const summary = parts.length ? parts.join(', ') : '0';
    document.getElementById("passengerSummary").textContent = summary;
}

function increment(type) {
    counts[type]++;
    document.getElementById(type + "Count").textContent = counts[type];
    updatePassengerSummary();
}

function decrement(type) {
    if (counts[type] > 0 && !(type === 'adult' && counts[type] === 1)) {
        counts[type]--;
        document.getElementById(type + "Count").textContent = counts[type];
        updatePassengerSummary();
    }
}

// Flight search
document.getElementById("flightSearchForm").addEventListener("submit", function (e) {
    e.preventDefault();

    let origin = document.getElementById("origin").value;
    let destination = document.getElementById("destination").value;
    let journeyDate = document.getElementById("journeyDate").value;
    let returnDate = document.getElementById("returnDate").value;
    let travelClass = document.getElementById("travelClass").value;

    if (!origin || !destination) {
        alert("Please select both From and To cities!");
        return;
    }
    if (origin === destination) {
        alert("Origin and Destination cannot be same!");
        return;
    }

    window.location.href = `/user/search-results.html?origin=${origin}&destination=${destination}&journeyDate=${journeyDate}&returnDate=${returnDate}&adult=${counts.adult}&child=${counts.child}&infant=${counts.infant}&class=${travelClass}`;
});

// Logout redirect
function logout() {
    window.location.href = "/auth/login.html";
}

// Manage booking redirect
function goToBookings() {
    window.location.href = "/user/manage-booking.html";
}