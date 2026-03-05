function toggleFields(){

let role = document.getElementById("role").value;
let userFields = document.getElementById("userFields");

if(role === "USER"){
userFields.style.display = "block";
}
else{
userFields.style.display = "none";
}

}

document
.getElementById("registerForm")
.addEventListener("submit", function(e){

e.preventDefault();

let role = document.getElementById("role").value;

let data = {

name: document.getElementById("name").value,

email: document.getElementById("email").value,

password: document.getElementById("password").value,

role: role

};

if(role === "USER"){

data.phone = document.getElementById("phone").value;

data.address = document.getElementById("address").value;

data.gender = document.getElementById("gender").value;

data.dateOfBirth = document.getElementById("dateOfBirth").value;

}

fetch("/auth/register",{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify(data)

})

.then(res=>res.text())

.then(data=>{

alert(data);

window.location.href="/auth/login.html";

})

.catch(err=>console.log(err));

});