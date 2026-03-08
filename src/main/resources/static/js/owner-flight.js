

/* ── LOAD FLIGHTS ────────────────────────────────────── */
async function loadFlights(container) {
  try {
    const flights = await apiFetch('/api/owner/flights');
    const count = flights.length;
    const avgCheckin = count
      ? Math.round(flights.reduce((a, f) => a + Number(f.checkInBaggage || 0), 0) / count)
      : 0;

    const rows = count
      ? flights.map(f => `
          <tr>
            <td><strong>${f.flightNumber}</strong></td>
            <td>${f.flightName}</td>
            <td>${f.checkInBaggage} kg</td>
            <td>${f.cabinBaggage} kg</td>
            <td>
              <div class="actions-cell">
                <button class="btn btn-sm btn-edit"
                  onclick='openFlightModal(${JSON.stringify(f)})'>Edit</button>
                <button class="btn btn-sm btn-danger"
                  onclick="deleteFlight(${f.id})">Delete</button>
              </div>
            </td>
          </tr>`).join('')
      : `<tr><td colspan="6">
            <div class="empty-state">
              <div class="empty-icon">🛩</div>
              <p>No flights yet. Add your first flight.</p>
            </div>
         </td></tr>`;

    container.innerHTML = `
      <div class="fade-in">
        <div class="topbar">
          <div>
            <div class="page-title">Flights</div>
            <div class="page-subtitle">${count} flight${count !== 1 ? 's' : ''} registered</div>
          </div>
          <button class="btn btn-primary" onclick="openFlightModal()">＋ Add Flight</button>
        </div>

        <div class="stats-row">
          <div class="stat-card" data-icon="🛩">
            <div class="stat-label">Total Flights</div>
            <div class="stat-value">${count}</div>
          </div>
          
          <div class="stat-card" data-icon="🧳">
            <div class="stat-label">Avg Check-in Bag</div>
            <div class="stat-value">${avgCheckin}<span class="stat-unit"> kg</span></div>
          </div>
        </div>

        <div class="table-card">
          <div class="table-card-header">
            <div class="table-card-title">All Flights</div>
          </div>
          <table>
            <thead>
              <tr>
                <th>Flight #</th>
                <th>Name</th>
                <th>Check-in</th>
                <th>Cabin</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>${rows}</tbody>
          </table>
        </div>
      </div>`;
  } catch (e) {
    showError(container, `Failed to load flights: ${e.message}`);
  }
}

/* ── OPEN MODAL ──────────────────────────────────────── */
function openFlightModal(flight = null) {
  const isEdit = !!flight;
  openModal(isEdit ? 'Edit Flight' : 'Add Flight', `

    ${!isEdit ? `
    <div class="form-group">
      <label>Route ID</label>
      <input class="form-control" id="f-routeId" type="number"
        placeholder="Enter route ID for this flight"/>
    </div>` : ''}

    <div class="form-row">
      <div class="form-group">
        <label>Flight Name</label>
        <input class="form-control" id="f-name"
          placeholder="e.g. IndiGo Express"
          value="${isEdit ? flight.flightName : ''}"/>
      </div>
      <div class="form-group">
        <label>Flight Number</label>
        <input class="form-control" id="f-number"
          placeholder="e.g. 6E-204"
          value="${isEdit ? flight.flightNumber : ''}"/>
      </div>
    </div>
    <div class="form-row">
      <div class="form-group">
        <label>Check-in Baggage (kg)</label>
        <input class="form-control" id="f-checkin" type="number"
          placeholder="25"
          value="${isEdit ? flight.checkInBaggage : ''}"/>
      </div>
      <div class="form-group">
        <label>Cabin Baggage (kg)</label>
        <input class="form-control" id="f-cabin" type="number"
          placeholder="7"
          value="${isEdit ? flight.cabinBaggage : ''}"/>
      </div>
    </div>
    <div class="form-actions">
      <button class="btn btn-ghost" onclick="closeModal()">Cancel</button>
      <button class="btn btn-primary" onclick="saveFlight(${isEdit ? flight.id : 'null'})">
        ${isEdit ? 'Update Flight' : 'Add Flight'}
      </button>
    </div>
  `);
}

/* ── SAVE FLIGHT (add or update) ─────────────────────── */
async function saveFlight(id) {
  const name    = document.getElementById('f-name').value.trim();
  const number  = document.getElementById('f-number').value.trim();
  const routeId = !id ? document.getElementById('f-routeId')?.value?.trim() : null;

  if (!name || !number) {
    alert('Flight name and number are required.');
    return;
  }
  if (!id && !routeId) {
    alert('Route ID is required to add a flight.');
    return;
  }

  const data = {
    flightName:     name,
    flightNumber:   number,
    checkInBaggage: document.getElementById('f-checkin').value,
    cabinBaggage:   document.getElementById('f-cabin').value
  };

  try {
    // POST /api/owner/flights/{routeId}  — add new flight under a route
    // PUT  /api/owner/flights/{id}       — update existing flight
    await apiFetch(
      id ? `/api/owner/flights/${id}` : `/api/owner/flights/${routeId}`,
      { method: id ? 'PUT' : 'POST', body: JSON.stringify(data) }
    );
    closeModal();
    navigate('flights');
  } catch (e) {
    alert(`Error: ${e.message}`);
  }
}

/* ── DELETE FLIGHT ───────────────────────────────────── */
async function deleteFlight(id) {
  if (!confirm('Delete this flight? This cannot be undone.')) return;
  try {
    await apiFetch(`/api/owner/flights/${id}`, { method: 'DELETE' });
    navigate('flights');
  } catch (e) {
    alert(`Error: ${e.message}`);
  }
}