let menu = document.querySelector(".img");
let size = document.querySelector(".nav-listd");
let maxWidth = 600;
let isVisible = false;
console.log(isVisible);

menu.addEventListener("click", () => {
  if (isVisible) {
    size.style.display = "none";
  } else {
    size.style.display = "block";
  }
  isVisible = !isVisible;
  console.log(isVisible);
});

// Function to check screen width and adjust display accordingly
function checkWidth() {
  let screen = window.innerWidth;
  if (screen >= maxWidth) {
    size.style.display = "none";
    isVisible = false;
    
  }
}

// Call the function once to set initial display
checkWidth();

// Add event listener for window resize to adjust display
window.addEventListener("resize", checkWidth);

let log = document.querySelector(".log");

log.addEventListener("click", () => {
  document.querySelector(".loginr").style.display = "none";
  document.querySelector(".login").style.display = "flex";

});

let reg = document.querySelector(".reg");

reg.addEventListener("click", () => {
  document.querySelector(".login").style.display = "none";
  document.querySelector(".loginr").style.display = "flex";

});