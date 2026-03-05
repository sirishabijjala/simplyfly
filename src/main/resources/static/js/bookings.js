const ownerId = 1;

function loadBookings(){

fetch("/owner/bookings/"+ownerId,{
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})

.then(res=>res.json())

.then(data=>{

let table = document.getElementById("bookingTable");

table.innerHTML="";

data.forEach(b=>{

table.innerHTML += `
<tr>
<td>${b.bookingId}</td>
<td>${b.bookingReference}</td>
<td>${b.numberOfSeats}</td>
<td>${b.totalAmount}</td>
<td>${b.bookingStatus}</td>
<td>
<button onclick="refund(${b.bookingId})" class="btn btn-danger">
Refund
</button>
</td>
</tr>
`;

});

});

}

function refund(id){

fetch("/owner/bookings/refund/"+id,{

method:"PUT",

headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}

})

.then(res=>res.text())

.then(data=>{
alert("Booking Refunded");
loadBookings();
});

}