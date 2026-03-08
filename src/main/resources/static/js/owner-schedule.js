
/* ── STATE ───────────────────────────────────────────── */
let _activeFlightId = null;

/* ── LOAD SCHEDULES PAGE ─────────────────────────────── */
function loadSchedules(container) {
  container.innerHTML = `
    <div class="fade-in">
      <div class="topbar">
        <div>
          <div class="page-title">Schedules</div>
          <div class="page-subtitle">Search by flight ID to view &amp; manage schedules</div>
        </div>
      </div>

      <div class="search-card">
        <div class="search-inner">
          <div class="form-group" style="margin:0; flex:1;">
            <label>Flight ID</label>
            <input class="form-control" id="s-search-flightId" type="number"
              placeholder="Enter a flight ID to load its schedules"
              onkeydown="if(event.key==='Enter') fetchSchedulesByFlight()"/>
          </div>
          <button class="btn btn-primary" style="align-self:flex-end;"
            onclick="fetchSchedulesByFlight()">🔍 Search</button>
        </div>
      </div>

      <div id="schedule-results"></div>
    </div>`;
}

/* ── FETCH SCHEDULES ─────────────────────────────────── */
async function fetchSchedulesByFlight() {
  const flightId = document.getElementById('s-search-flightId').value.trim();
  if (!flightId) { alert('Please enter a Flight ID.'); return; }

  _activeFlightId = flightId;
  const results = document.getElementById('schedule-results');
  results.innerHTML = `<div class="spinner-wrap"><div class="spinner"></div></div>`;

  try {
    const schedules = await apiFetch(`/api/owner/schedules/${flightId}`);
    renderScheduleResults(schedules, flightId, results);
  } catch (e) {
    results.innerHTML = `<div class="alert-error"><span>⚠</span><span>${e.message}</span></div>`;
  }
}

/* ── RENDER RESULTS TABLE ────────────────────────────── */
function renderScheduleResults(schedules, flightId, resultsEl) {
  const count = schedules.length;
  const totalSeats = schedules.reduce((a, s) => a + Number(s.totalSeats || 0), 0);
  const totalAvail = schedules.reduce((a, s) => a + Number(s.availableSeats || 0), 0);

  const rows = count
    ? schedules.map(s => `
        <tr>
          <td><strong>${s.id}</strong></td>
          <td>${formatDate(s.departureTime)}</td>
          <td>${formatDate(s.arrivalTime)}</td>
          <td>${s.totalSeats ?? '—'}</td>
          <td>${s.availableSeats ?? '—'}</td>
          <td>₹${s.fare != null ? Number(s.fare).toLocaleString() : '—'}</td>
          <td>
            <div class="actions-cell">
              <button class="btn btn-sm btn-edit"
                onclick='openScheduleModal(${JSON.stringify(s)}, ${flightId})'>Edit</button>
              <button class="btn btn-sm btn-danger"
                onclick="deleteSchedule(${s.id})">Delete</button>
            </div>
          </td>
        </tr>`).join('')
    : `<tr><td colspan="7">
          <div class="empty-state">
            <div class="empty-icon">📅</div>
            <p>No schedules found for Flight ID ${flightId}.</p>
          </div>
       </td></tr>`;

  resultsEl.innerHTML = `
    <div class="stats-row" style="margin-top:0;">
      <div class="stat-card" data-icon="📅">
        <div class="stat-label">Total Schedules</div>
        <div class="stat-value">${count}</div>
      </div>
      <div class="stat-card" data-icon="💺">
        <div class="stat-label">Total Seats</div>
        <div class="stat-value">${totalSeats.toLocaleString()}</div>
      </div>
      <div class="stat-card" data-icon="✅">
        <div class="stat-label">Available Seats</div>
        <div class="stat-value green">${totalAvail.toLocaleString()}</div>
      </div>
    </div>

    <div class="table-card">
      <div class="table-card-header">
        <div class="table-card-title">Schedules — Flight #${flightId}</div>
        <button class="btn btn-primary btn-sm"
          onclick="openScheduleModal(null, ${flightId})">＋ Add Schedule</button>
      </div>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Total Seats</th>
            <th>Available</th>
            <th>Fare</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>${rows}</tbody>
      </table>
    </div>`;
}

/* ── OPEN MODAL ──────────────────────────────────────── */
function openScheduleModal(schedule = null, flightId = null) {
  const isEdit = !!schedule;
  const fid    = isEdit ? (schedule.flightId || flightId) : flightId;

  // Pre-fill datetime values — slice to "yyyy-MM-ddTHH:mm" for datetime-local input
  const depVal = isEdit && schedule.departureTime ? schedule.departureTime.slice(0, 16) : '';
  const arrVal = isEdit && schedule.arrivalTime   ? schedule.arrivalTime.slice(0, 16)   : '';

  openModal(isEdit ? 'Edit Schedule' : `Add Schedule — Flight #${fid}`, `
    <div class="form-row">
      <div class="form-group">
        <label>Departure Time</label>
        <input class="form-control" id="s-departure" type="datetime-local"
          value="${depVal}"/>
      </div>
      <div class="form-group">
        <label>Arrival Time</label>
        <input class="form-control" id="s-arrival" type="datetime-local"
          value="${arrVal}"/>
      </div>
    </div>
    <div class="form-row">
      <div class="form-group">
        <label>Total Seats</label>
        <input class="form-control" id="s-totalSeats" type="number"
          placeholder="e.g. 180"
          value="${isEdit && schedule.totalSeats != null ? schedule.totalSeats : ''}"/>
      </div>
      <div class="form-group">
        <label>Available Seats</label>
        <input class="form-control" id="s-availableSeats" type="number"
          placeholder="e.g. 180"
          value="${isEdit && schedule.availableSeats != null ? schedule.availableSeats : ''}"/>
      </div>
    </div>
    <div class="form-group">
      <label>Fare (₹)</label>
      <input class="form-control" id="s-fare" type="number" step="0.01"
        placeholder="e.g. 4500"
        value="${isEdit && schedule.fare != null ? schedule.fare : ''}"/>
    </div>
    <div class="form-actions">
      <button class="btn btn-ghost" onclick="closeModal()">Cancel</button>
      <button class="btn btn-primary"
        onclick="saveSchedule(${isEdit ? schedule.id : 'null'}, ${fid})">
        ${isEdit ? 'Update Schedule' : 'Add Schedule'}
      </button>
    </div>
  `);
}

/* ── SAVE SCHEDULE ───────────────────────────────────── */
async function saveSchedule(scheduleId, flightId) {
  const departureTime  = document.getElementById('s-departure').value;
  const arrivalTime    = document.getElementById('s-arrival').value;
  const totalSeats     = document.getElementById('s-totalSeats').value;
  const availableSeats = document.getElementById('s-availableSeats').value;
  const fare           = document.getElementById('s-fare').value;

  if (!departureTime || !arrivalTime) {
    alert('Departure and arrival times are required.');
    return;
  }
  if (!totalSeats || !availableSeats || !fare) {
    alert('Total seats, available seats and fare are required.');
    return;
  }

  // Send field names exactly as ScheduleDTO expects
  const data = {
    departureTime,
    arrivalTime,
    totalSeats:     parseInt(totalSeats),
    availableSeats: parseInt(availableSeats),
    fare:           parseFloat(fare)
  };

  try {
    await apiFetch(
      scheduleId
        ? `/api/owner/schedules/${scheduleId}`   // PUT — update
        : `/api/owner/schedules/${flightId}`,     // POST — add
      { method: scheduleId ? 'PUT' : 'POST', body: JSON.stringify(data) }
    );
    closeModal();
    // Restore the flight ID in the search box and refresh
    document.getElementById('s-search-flightId').value = flightId;
    _activeFlightId = flightId;
    fetchSchedulesByFlight();
  } catch (e) {
    alert(`Error: ${e.message}`);
  }
}

/* ── DELETE SCHEDULE ─────────────────────────────────── */
async function deleteSchedule(scheduleId) {
  if (!confirm('Delete this schedule? This cannot be undo.')) return;
  try {
    await apiFetch(`/api/owner/schedules/${scheduleId}`, { method: 'DELETE' });
    fetchSchedulesByFlight();
  } catch (e) {
    alert(`Error: ${e.message}`);
  }
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
}