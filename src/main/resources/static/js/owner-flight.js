// Function triggered by sidebar
function showOwnerSection(section) {
    const content = document.getElementById('content-area');
    if (section === 'flights') {
        renderFlightManagement(content);
    }
    // other sections...
}

async function renderFlightManagement(container) {
    container.innerHTML = `
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="text-primary fw-bold">Manage My Fleet</h3>
            <button class="btn btn-primary" onclick="openAddFlightModal()">+ Add New Flight</button>
        </div>
        
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Flight No</th>
                        <th>Airlines Name</th>
                        <th>Route ID</th>
                        <th>Baggage (Check/Cabin)</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="flight-list">
                    </tbody>
            </table>
        </div>
    `;
    loadOwnerFlights();
}

async function loadOwnerFlights() {
    const token = localStorage.getItem('token');
    const response = await fetch('http://localhost:8080/api/owner/my-flights', {
        headers: { 'Authorization': `Bearer ${token}` }
    });
    const flights = await response.json();
    
    const tbody = document.getElementById('flight-list');
    tbody.innerHTML = flights.map(f => `
        <tr>
            <td><strong>${f.flightNumber}</strong></td>
            <td>${f.flightName}</td>
            <td><span class="badge bg-info">Route ${f.routeId}</span></td>
            <td>${f.checkInBaggage} / ${f.cabinBaggage}</td>
            <td>
                <button class="btn btn-sm btn-outline-primary" onclick="showOwnerSection('schedules')">Schedules</button>
            </td>
        </tr>
    `).join('');
}