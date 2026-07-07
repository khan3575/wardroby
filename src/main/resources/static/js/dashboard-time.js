const getGreeting = () => {
    const hour = new Date().getHours(); // Returns 0 - 23
    let greeting = "";
    if (hour >= 5 && hour < 12) {
        greeting= "Good Morning!";
    } else if (hour >= 12 && hour < 17) {
        greeting= "Good Afternoon!";
    } else if (hour >= 17 && hour < 21) {
        greeting= "Good Evening!";
    } else {
        greeting= "Good Night!";
    }
    document.getElementById("greeting").textContent = greeting;
}
window.onload = getGreeting;