document.addEventListener("DOMContentLoaded", function() {
    var firstName = "<%= request.getAttribute('firstName') %>";
    document.getElementById("userFirstName").textContent = firstName;
});
