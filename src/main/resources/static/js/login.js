document
.getElementById("loginForm")
.addEventListener("submit", function(e){

e.preventDefault();

let email = document.getElementById("email").value.trim();
let password = document.getElementById("password").value.trim();

let msg = document.getElementById("loginMessage");

/* reset message */
msg.style.display = "none";
msg.classList.remove("alert-success");
msg.classList.remove("alert-danger");

/* EMAIL VALIDATION */

let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

if(email === ""){
msg.style.display = "block";
msg.classList.add("alert-danger");
msg.innerText = "Email cannot be empty";
return;
}

if(!emailPattern.test(email)){
msg.style.display = "block";
msg.classList.add("alert-danger");
msg.innerText = "Please enter a valid email address";
return;
}

/* PASSWORD VALIDATION */

if(password === ""){
msg.style.display = "block";
msg.classList.add("alert-danger");
msg.innerText = "Password cannot be empty";
return;
}

if(password.length < 6){
msg.style.display = "block";
msg.classList.add("alert-danger");
msg.innerText = "Password must be at least 6 characters";
return;
}

/* CREATE DATA OBJECT */

let data = {

email: email,
password: password

};

/* API CALL */

fetch("/auth/login",{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify(data)

})

.then(res=>res.json())

.then(res=>{

// save token
localStorage.setItem("token",res.token);

// show success message
msg.style.display = "block";
msg.classList.add("alert-success");
msg.innerText = "Login successful! Redirecting...";

// redirect after 2 seconds
setTimeout(() => {

if(res.role === "ADMIN"){
window.location.href="/admin/admin-dashboard.html";
}

else if(res.role === "OWNER"){
window.location.href="/owner/dashboard.html";
}

else{
window.location.href="/user/loginprofile.html";
}

},2000);

})

.catch(err=>{

console.log(err);

msg.style.display = "block";
msg.classList.add("alert-danger");
msg.innerText = "Login failed. Please check your credentials.";

});

});


/* PASSWORD SHOW / HIDE */

function togglePassword(){

let password = document.getElementById("password");
let icon = document.getElementById("eyeIcon");

if(password.type === "password"){
password.type = "text";
icon.classList.remove("fa-eye");
icon.classList.add("fa-eye-slash");
}

else{
password.type = "password";
icon.classList.remove("fa-eye-slash");
icon.classList.add("fa-eye");
}

}