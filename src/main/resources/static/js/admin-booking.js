async function loadBookings(container) {
    try {
        const response = await fetch('/api/admin/bookings', { headers: getHeaders() });
        const bookings = await response.json();
        
        const cancelledCount = bookings.filter(b => b.bookingStatus === 'CANCELLED').length;
        const activeCount = bookings.length - cancelledCount;

        let html = `
            <style>
                /* Interactive Top Cards */
                .stat-card-interactive { 
                    border: none; 
                    border-radius: 16px; 
                    background: #fff; 
                    box-shadow: 0 4px 15px rgba(0,0,0,0.05); 
                    transition: all 0.3s ease;
                    border-bottom: 4px solid transparent;
                }
                .stat-card-interactive:hover { 
                    transform: translateY(-8px); 
                    box-shadow: 0 12px 24px rgba(0,0,0,0.1);
                }
                .card-total:hover { border-bottom-color: #64748b; }
                .card-active:hover { border-bottom-color: #0d6efd; }
                .card-cancelled:hover { border-bottom-color: #ef4444; }

                /* Row Hover Effects */
                .booking-table { border-collapse: separate; border-spacing: 0 10px; width: 100%; }
                .booking-row { background: #fff; transition: all 0.2s ease; }
                .booking-row:hover { 
                    background: #f8fbff !important; 
                    transform: scale(1.005);
                }
                .booking-row td { border: none !important; padding: 18px 0; }
                .booking-row td:first-child { border-radius: 12px 0 0 12px; }
                .booking-row td:last-child { border-radius: 0 12px 12px 0; }
                
                .status-pill { padding: 6px 14px; border-radius: 50px; font-size: 0.75rem; font-weight: 700; }
                .status-confirmed { background: #ecfdf5; color: #059669; }
                .status-cancelled { background: #fef2f2; color: #dc2626; }
                
                .btn-action { font-weight: 600; border-radius: 8px; transition: 0.2s; padding: 8px 16px; font-size: 0.85rem; }
                .btn-cancel-refund { border: 1px solid #fee2e2; color: #dc2626; background: #fff; }
                .btn-cancel-refund:hover { background: #dc2626; color: #fff; }
                .btn-refunded { background: #f1f5f9; color: #94a3b8; border: 1px solid #e2e8f0; cursor: default; }
            </style>

            <div class="mb-5">
                <h2 class="fw-bold mb-1"><i class="fa-solid fa-plane-circle-check text-primary me-2"></i>Booking Management</h2>
                <p class="text-muted small">Real-time flight inventory and refund processing.</p>
            </div>

            <div class="row g-4 mb-5">
                <div class="col-md-4">
                    <div class="card stat-card-interactive card-total p-4">
                        <small class="text-muted fw-bold text-uppercase">Total Bookings</small>
                        <h1 class="fw-bold mb-0 mt-1">${bookings.length}</h1>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card stat-card-interactive card-active p-4">
                        <small class="text-muted fw-bold text-uppercase text-primary">Active Bookings</small>
                        <h1 class="fw-bold mb-0 mt-1 text-primary">${activeCount}</h1>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card stat-card-interactive card-cancelled p-4">
                        <small class="text-muted fw-bold text-uppercase text-danger">Cancellations</small>
                        <h1 class="fw-bold mb-0 mt-1 text-danger">${cancelledCount}</h1>
                    </div>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table booking-table align-middle">
                    <thead>
                        <tr class="text-muted small text-uppercase fw-bold">
                            <th class="ps-4">Reference</th>
                            <th>Customer</th>
                            <th>Route</th>
                            <th>Seats</th>
                            <th>Status</th>
                            <th class="text-end pe-4">Actions</th>
                        </tr>
                    </thead>
                    <tbody>`;

        bookings.forEach(b => {
            const isCancelled = b.bookingStatus === 'CANCELLED';
            const statusClass = isCancelled ? 'status-cancelled' : 'status-confirmed';
            const seatList = b.passengers ? b.passengers.map(p => p.seatId).join(', ') : 'N/A';
            const btnLabel = isCancelled ? '<i class="fa-solid fa-check-double me-1"></i> Refunded' : '<i class="fa-solid fa-rotate-left me-1"></i> Cancel & Refund';
            const btnClass = isCancelled ? 'btn-refunded' : 'btn-cancel-refund';

            html += `
                <tr class="booking-row shadow-sm">
                    <td class="ps-4"><span class="badge bg-light text-dark font-monospace">#${b.bookingReference.substring(0,6)}</span></td>
                    <td>
                        <div class="fw-bold text-dark">${b.userName}</div>
                        <button class="btn btn-link btn-sm p-0 text-decoration-none small" 
                                onclick='window.viewPassengers(${JSON.stringify(b.passengers)})'>
                            View ${b.numberOfSeats} Passengers
                        </button>
                    </td>
                    <td>
                        <div class="small fw-bold text-uppercase">${b.origin} <i class="fa-solid fa-arrow-right text-muted mx-1"></i> ${b.destination}</div>
                    </td>
                    <td><span class="badge border text-dark font-monospace">${seatList}</span></td>
                    <td><span class="status-pill ${statusClass}">${b.bookingStatus}</span></td>
                    <td class="text-end pe-4">
                        <button class="btn btn-sm btn-action ${btnClass}" 
                                onclick="${isCancelled ? '' : `window.adminCancelBooking(${b.bookingId})`}"
                                ${isCancelled ? 'disabled' : ''}>
                            ${btnLabel}
                        </button>
                    </td>
                </tr>`;
        });

        html += `</tbody></table></div>`;
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger mx-4">Failed to synchronize booking data.</div>`;
    }
}
window.viewPassengers = function(passengers) {
    document.getElementById('modalTitle').innerText = "Passenger Details";
    let passengerHTML = `
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead class="bg-light">
                    <tr class="small text-uppercase fw-bold text-muted">
                        <th>Name</th><th>Age</th><th>Gender</th><th class="text-end">Seat</th>
                    </tr>
                </thead>
                <tbody>`;
    
    passengers.forEach(p => {
        passengerHTML += `
            <tr>
                <td class="fw-bold">${p.name}</td>
                <td>${p.age}</td>
                <td><span class="badge bg-secondary bg-opacity-10 text-secondary">${p.gender}</span></td>
                <td class="text-end fw-bold text-primary">${p.seatId}</td>
            </tr>`;
    });

    passengerHTML += `</tbody></table></div>`;
    document.getElementById('modalBody').innerHTML = passengerHTML;
    new bootstrap.Modal(document.getElementById('adminModal')).show();
};

window.adminCancelBooking = async function(id) {
    if (!confirm(" Proceed with Cancellation and Seat Release ?")) return;

    try {
        const response = await fetch(`/api/admin/bookings/cancel/${id}`, {
            method: 'PUT',
            headers: getHeaders()
        });

        if (response.ok) {
            alert(" Seats released & Refund processed!");
            showSection('bookings', null); 
        } else {
            alert("Error: " + await response.text());
        }
    } catch (error) { alert("Connection error."); }
};