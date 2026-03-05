/**
 * admin-booking.js
 * Handles the global view of all passenger bookings.
 */

// 1. Fetch and Display Table
async function loadBookings(container) {
    try {
        const response = await fetch('/api/admin/bookings', { headers: getHeaders() });
        const bookings = await response.json();

        let html = `
            <div class="mb-4">
                <h3 class="text-primary"><i class="fa-solid fa-ticket"></i> Global Booking Management</h3>
                <p class="text-muted">Monitor all ticket sales and flight statuses.</p>
            </div>
            <div class="table-responsive">
                <table class="table table-hover shadow-sm bg-white">
                    <thead>
                        <tr>
                            <th>Ref #</th>
                            <th>Passenger</th>
                            <th>Flight / Route</th>
                            <th>Date</th>
                            <th>Seats</th>
                            <th>Amount</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>`;

        bookings.forEach(b => {
            // Status Badge Logic
            let badgeClass = "bg-success";
            if (b.bookingStatus === 'CANCELLED') badgeClass = "bg-danger";
            if (b.bookingStatus === 'PENDING') badgeClass = "bg-warning text-dark";

            html += `
                <tr>
                    <td class="fw-bold text-secondary">${b.bookingReference}</td>
                    <td>${b.userName}</td>
                    <td>
                        <small class="d-block fw-bold">${b.flightName}</small>
                        <small class="text-muted">${b.origin} → ${b.destination}</small>
                    </td>
                    <td>${new Date(b.bookingDate).toLocaleDateString()}</td>
                    <td>${b.numberOfSeats}</td>
                    <td>₹${b.totalAmount}</td>
                    <td><span class="badge ${badgeClass}">${b.bookingStatus}</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-danger" 
                                onclick="adminCancelBooking(${b.bookingId})"
                                ${b.bookingStatus === 'CANCELLED' ? 'disabled' : ''}>
                            <i class="fa-solid fa-ban"></i> Cancel
                        </button>
                    </td>
                </tr>`;
        });

        html += `</tbody></table></div>`;
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger">Error loading bookings.</div>`;
    }
}

// 2. Cancel Logic
window.adminCancelBooking = async function(id) {
    if (!confirm("Are you sure you want to cancel this booking? This will return seats to the flight schedule.")) return;

    try {
        const response = await fetch(`/api/admin/bookings/cancel/${id}`, {
            method: 'PUT',
            headers: getHeaders()
        });

        if (response.ok) {
            alert("Booking has been cancelled and seats have been released.");
            showSection('bookings', null); // Refresh table
        } else {
            alert("Failed to cancel booking. It may already be cancelled.");
        }
    } catch (error) {
        alert("Connection error occurred.");
    }
};