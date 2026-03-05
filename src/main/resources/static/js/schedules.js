function loadSchedules(){

let flightId = document.getElementById("flightId").value;

fetch("/owner/schedules/"+flightId,{
headers:{
"Authorization":"Bearer "+localStorage.getItem("token")
}
})

.then(res=>res.json())

.then(data=>{

let table = document.getElementById("scheduleTable");

table.innerHTML="";

data.forEach(s=>{

table.innerHTML += `
<tr>
<td>${s.id}</td>
<td>${s.departureTime}</td>
<td>${s.arrivalTime}</td>
<td>${s.totalSeats}</td>
<td>${s.availableSeats}</td>
<td>${s.fare}</td>
</tr>
`;

});

});

}

function addSchedule(){

let flightId = document.getElementById("flightId").value;

let data = {

departureTime: document.getElementById("departureTime").value,
arrivalTime: document.getElementById("arrivalTime").value,
totalSeats: document.getElementById("totalSeats").value,
availableSeats: document.getElementById("availableSeats").value,
fare: document.getElementById("fare").value

};

fetch("/owner/schedules/"+flightId,{

method:"POST",

headers:{
"Content-Type":"application/json",
"Authorization":"Bearer "+localStorage.getItem("token")
},

body:JSON.stringify(data)

})

.then(res=>res.json())

.then(data=>{
alert("Schedule Added");
});

}