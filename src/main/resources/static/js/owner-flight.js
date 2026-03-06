async function loadFlights(container) {

    let ownerId = localStorage.getItem("ownerId");

    try {

        const res = await fetch(`/api/owner/flights/${ownerId}`, {
            method: "GET",
            headers: getHeaders()
        });

        let flights = [];

        const text = await res.text();

        if (text) {
            flights = JSON.parse(text);
        }

        let html = `
        <div class="d-flex justify-content-between mb-3">
            <h4>Flights</h4>
            <button class="btn btn-primary" onclick="openFlightModal()">
                Add Flight
            </button>
        </div>

        <table class="table table-bordered">
        <tr>
            <th>ID</th>
            <th>Number</th>
            <th>Name</th>
            <th>Actions</th>
        </tr>
        `;

        if (flights.length === 0) {
            html += `
            <tr>
                <td colspan="4" class="text-center">
                    No Flights Found
                </td>
            </tr>`;
        }

        flights.forEach(f => {

            html += `
            <tr>
                <td>${f.id}</td>
                <td>${f.flightNumber}</td>
                <td>${f.flightName}</td>

                <td>

                    <button class="btn btn-sm btn-warning"
                    onclick='editFlight(${JSON.stringify(f)})'>
                    Edit
                    </button>

                    <button class="btn btn-sm btn-danger"
                    onclick="deleteFlight(${f.id})">
                    Delete
                    </button>

                </td>

            </tr>
            `;
        });

        html += `</table>`;

        container.innerHTML = html;

    } catch (error) {

        console.error(error);

        container.innerHTML = `
        <div class="alert alert-danger">
        Failed to load flights
        </div>
        `;
    }
}