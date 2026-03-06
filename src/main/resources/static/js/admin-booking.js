/**
 * admin-booking.js
 * Integrated with Seat Release and Refund Logic
 */

async function loadBookings(container) {
    try {
        const response = await fetch('/api/admin/bookings', { headers: getHeaders() });
        const bookings = await response.json();

        let html = `
            <div class="mb-4">
                <h3 class="text-primary"><i class="fa-solid fa-ticket"></i> Global Booking Management</h3>
                <p class="text-muted">Manage airline inventory and process refunds.</p>
            </div>
            <div class="table-responsive">
                <table class="table table-hover shadow-sm bg-white border">
                    <thead class="table-light">
                        <tr>
                            <th>Ref #</th>
                            <th>Primary User</th>
                            <th>Airline / Flight</th>
                            <th>Route</th>
                            <th>Seats</th>
                            <th>Amount</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>`;

        bookings.forEach(b => {
            let badgeClass = b.bookingStatus === 'CONFIRMED' ? "bg-success" : 
                             b.bookingStatus === 'CANCELLED' ? "bg-danger" : "bg-warning text-dark";

            // Format Seat List (e.g., "1A, 1B")
            const seatList = b.passengers ? b.passengers.map(p => p.seatId).join(', ') : 'N/A';

            html += `
                <tr>
                    <td class="small fw-bold text-secondary">${b.bookingReference.substring(0,8)}...</td>
                    <td>
                        <div class="fw-bold">${b.userName}</div>
                        <button class="btn btn-link btn-sm p-0 text-decoration-none" 
                                onclick='viewPassengers(${JSON.stringify(b.passengers)})'>
                            View ${b.numberOfSeats} Passengers
                        </button>
                    </td>
                    <td>
                        <div class="text-primary fw-bold" style="font-size: 0.9rem;">${b.flightName}</div>
                    </td>
                    <td>
                        <small class="fw-bold text-uppercase">${b.origin} → ${b.destination}</small>
                    </td>
                    <td><span class="badge border text-dark">${seatList}</span></td>
                    <td>₹${b.totalAmount.toLocaleString()}</td>
                    <td><span class="badge ${badgeClass}">${b.bookingStatus}</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-danger" 
                                onclick="adminCancelBooking(${b.bookingId})"
                                ${b.bookingStatus === 'CANCELLED' ? 'disabled' : ''}>
                            Cancel & Refund
                        </button>
                    </td>
                </tr>`;
        });

        html += `</tbody></table></div>`;
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger">Error loading bookings. Check if server is running.</div>`;
    }
}

// 2. Modal to View Individual Passenger Details
window.viewPassengers = function(passengers) {
    document.getElementById('modalTitle').innerText = "Passenger Manifest";
    let passengerHTML = `
        <table class="table table-sm">
            <thead>
                <tr><th>Name</th><th>Age</th><th>Gender</th><th>Seat</th></tr>
            </thead>
            <tbody>`;
    
    passengers.forEach(p => {
        passengerHTML += `
            <tr>
                <td>${p.name}</td>
                <td>${p.age}</td>
                <td>${p.gender}</td>
                <td class="fw-bold text-primary">${p.seatId}</td>
            </tr>`;
    });

    passengerHTML += `</tbody></table>`;
    document.getElementById('modalBody').innerHTML = passengerHTML;
    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

// 3. Integrated Cancel Logic (Triggers Release Seats + Refund)
window.adminCancelBooking = async function(id) {
    if (!confirm("⚠️ This will: \n1. Release specific seats back to the plane \n2. Process a FULL REFUND to the user. \n\nContinue?")) return;

    try {
        const response = await fetch(`/api/admin/bookings/cancel/${id}`, {
            method: 'PUT',
            headers: getHeaders()
        });

        if (response.ok) {
            alert("✅ Success: Seats released and Refund processed!");
            showSection('bookings', null); 
        } else {
            const error = await response.text();
            alert("Error: " + error);
        }
    } catch (error) {
        alert("Connection error occurred.");
    }
};