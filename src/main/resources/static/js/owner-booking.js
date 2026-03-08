async function loadBookings(container){

let ownerId=localStorage.getItem("ownerId")

const res=await fetch(`/api/owner/${ownerId}/bookings`,{
headers:getHeaders()
})

const bookings=await res.json()

let html=`

<h4>Bookings</h4>

<table class="table table-bordered">

<tr>
<th>ID</th>
<th>Reference</th>
<th>Seats</th>
<th>Amount</th>
<th>Status</th>
<th>Action</th>
</tr>
`

bookings.forEach(b=>{

html+=`

<tr>

<td>${b.bookingId}</td>
<td>${b.bookingReference}</td>
<td>${b.numberOfSeats}</td>
<td>${b.totalAmount}</td>
<td>${b.bookingStatus}</td>

<td>

<button class="btn btn-danger btn-sm"
onclick="refundBooking(${b.bookingId})">

Refund

</button>

</td>

</tr>
`

})

html+=`</table>`

container.innerHTML=html

}

async function refundBooking(id){

await fetch(`/api/owner/bookings/${id}/refund`,{

method:"PUT",
headers:getHeaders()

})

alert("75% Refund Processed")

showSection("bookings")

}