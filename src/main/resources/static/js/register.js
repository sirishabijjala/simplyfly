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

let name = document.getElementById("name").value.trim();
let email = document.getElementById("email").value.trim();
let password = document.getElementById("password").value.trim();

let messageBox = document.getElementById("messageBox");

/* reset message */

messageBox.style.display = "none";
messageBox.classList.remove("alert-success");
messageBox.classList.remove("alert-danger");

/* NAME VALIDATION */

if(name === ""){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Name cannot be empty";
return;
}

/* EMAIL VALIDATION */

let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

if(email === ""){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Email cannot be empty";
return;
}

if(!emailPattern.test(email)){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Enter a valid email address";
return;
}

/* PASSWORD VALIDATION */

if(password === ""){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Password cannot be empty";
return;
}

if(password.length < 6){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Password must be at least 6 characters";
return;
}

/* CREATE DATA OBJECT */

let data = {

name: name,
email: email,
password: password,
role: role

};

/* USER FIELD VALIDATION */

if(role === "USER"){

let phone = document.getElementById("phone").value.trim();
let address = document.getElementById("address").value.trim();
let gender = document.getElementById("gender").value;
let dateOfBirth = document.getElementById("dateOfBirth").value;

let phonePattern = /^[0-9]{10}$/;

if(phone === ""){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Phone number cannot be empty";
return;
}

if(!phonePattern.test(phone)){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Phone number must be 10 digits";
return;
}

if(address === ""){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Address cannot be empty";
return;
}

if(gender === ""){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Please select gender";
return;
}

if(dateOfBirth === ""){
messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Please select date of birth";
return;
}

/* add to data object */

data.phone = phone;
data.address = address;
data.gender = gender;
data.dateOfBirth = dateOfBirth;

}

/* API CALL */

fetch("/auth/register",{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify(data)

})

.then(res=>res.text())

.then(data=>{

messageBox.style.display = "block";
messageBox.classList.add("alert-success");
messageBox.innerText = "Account created successfully! Redirecting to login...";

setTimeout(() => {
window.location.href="/auth/login.html";
},2000);

})

.catch(err=>{

console.log(err);

messageBox.style.display = "block";
messageBox.classList.add("alert-danger");
messageBox.innerText = "Registration failed. Please try again.";

});

});