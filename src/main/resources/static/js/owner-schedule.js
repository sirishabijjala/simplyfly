/**
 * owner-schedule.js
 * Handles Schedule CRUD for Flight Owner
 */

async function loadSchedules(container) {

    const ownerId = localStorage.getItem("ownerId");

    try {

        const response = await fetch(`/api/owner/schedules/${ownerId}`, {
            method: "GET",
            headers: getHeaders()
        });

        if (!response.ok) {
            throw new Error("Unauthorized or API error");
        }

        let schedules = [];

        const text = await response.text();

        if (text) {
            schedules = JSON.parse(text);
        }

        let html = `
        <div class="d-flex justify-content-between align-items-center mb-4">

            <h3 class="text-primary">
                <i class="fa-solid fa-clock"></i> Flight Schedules
            </h3>

            <button class="btn btn-primary" onclick="openScheduleModal()">
                <i class="fa-solid fa-plus"></i> Add Schedule
            </button>

        </div>

        <div class="table-responsive">

        <table class="table table-hover shadow-sm bg-white">

        <thead>
            <tr>
                <th>ID</th>
                <th>Flight</th>
                <th>Departure</th>
                <th>Arrival</th>
                <th>Total Seats</th>
                <th>Available Seats</th>
                <th>Fare</th>
                <th>Actions</th>
            </tr>
        </thead>

        <tbody>
        `;

        if (schedules.length === 0) {

            html += `
            <tr>
                <td colspan="8" class="text-center">
                    No schedules available
                </td>
            </tr>
            `;
        }

        schedules.forEach(s => {

            const scheduleData = JSON.stringify(s).replace(/'/g, "\\'");

            html += `
            <tr>

                <td>${s.scheduleId}</td>

                <td>${s.flight?.flightName || "N/A"}</td>

                <td>${formatDate(s.departureTime)}</td>

                <td>${formatDate(s.arrivalTime)}</td>

                <td>${s.totalSeats}</td>

                <td>${s.availableSeats}</td>

                <td>₹${s.fare}</td>

                <td>

                    <button class="btn btn-sm btn-outline-primary me-2"
                    onclick='editSchedule(${scheduleData})'>
                        <i class="fa-solid fa-pen"></i>
                    </button>

                    <button class="btn btn-sm btn-outline-danger"
                    onclick="deleteSchedule(${s.scheduleId})">
                        <i class="fa-solid fa-trash"></i>
                    </button>

                </td>

            </tr>
            `;
        });

        html += `</tbody></table></div>`;

        container.innerHTML = html;

    }
    catch (error) {

        console.error(error);

        container.innerHTML = `
        <div class="alert alert-danger">
            Failed to load schedules
        </div>
        `;
    }
}