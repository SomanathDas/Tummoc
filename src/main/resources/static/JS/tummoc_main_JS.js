/*
let html5QrCode;

function fetchPassengerDetails() {
    fetch('https://tummoc-production.up.railway.app/api/passenger/get')
        .then(response => response.json())
        .then(data => {
            console.log(data); // For testing purposes
            document.getElementById('passengerName').textContent = data.passengerName;
            document.getElementById('identificationType').textContent = data.identificationType;
            document.getElementById('identificationNumber').textContent = data.identificationNumber;
            document.getElementById('passPurchased').textContent = data.passPurchased;
            document.getElementById('passValidFrom').textContent = data.passValidFrom;
            document.getElementById('passValidTill').textContent = data.passValidTill;
            document.getElementById('passengerAvatar').src = 'data:image/png;base64,' + data.passengerAvatar;
            document.getElementById('passFare').textContent = '₹' + data.passFare;
        })
        .catch(error => console.error('Error fetching passenger details:', error));
}


function fetchDataAndUpdateContent() {
    fetch('https://tummoc-production.up.railway.app/api/qrcode/last-validation')
        .then(response => response.json())
        .then(data => {
            console.log(data); // For testing purposes
            document.getElementById('lastValidatedInfo').textContent = data.validatedAt;
            document.getElementById('busNumberInfo').textContent = data.busNumber;
        })
        .catch(error => console.error('Error:', error));
}

// Load data from the server and update content
fetchDataAndUpdateContent();


// Function to open the QR scanner
function openQRScanner() {
    var modal = document.getElementById("qrModal");
    modal.style.display = "block";

    html5QrCode = new Html5Qrcode("reader");
    html5QrCode
        .start(
            { facingMode: "environment" },
            {
                fps: 10,
                qrbox: { width: 250, height: 250 },
                disableFlip: false,
            },
            (qrCodeMessage) => {
                console.log(`QR Code detected: ${qrCodeMessage}`);

                // Handle the QR code data here
                sendQRCodeData(qrCodeMessage);

                // Close the modal after QR code is detected
                html5QrCode
                    .stop()
                    .then(() => {
                        closeQRScanner();
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            (errorMessage) => {
                // Parse error, ignore it
            }
        )
        .catch((err) => {
            console.log(err);
        });
}


function closeQRScanner() {
    var modal = document.getElementById("qrModal");
    modal.style.display = "none";
    if (html5QrCode) {
        html5QrCode
            .stop()
            .then(() => {
                html5QrCode.clear();
            })
            .catch((err) => {
                console.log(err);
            });
    }
}

function sendQRCodeData(qrCodeMessage) {
    fetch('https://tummoc-production.up.railway.app/api/qrcode/decode', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ qrData: qrCodeMessage })
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
            fetchDataAndUpdateContent();
            showModal();
        })
        //    updateContent(data);
        .catch(error => {
            console.error('Error:', error);
        });
}

// Function to show the modal popup with static details
function showModal() {
    // Get the current date and time
    const currentDate = new Date();
        let formattedDateTime = currentDate.toLocaleString('en-GB', {
            day: '2-digit',
            month: 'short',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            hour12: true
        }).replace(',', '').replace(' ', ' ');

        // Convert only the AM/PM part to uppercase
        formattedDateTime = formattedDateTime.replace(/(am|pm)/i, match => match.toUpperCase());
    // Set the passValidTill element in the modal
    //document.getElementById('passValidTill').textContent = formattedDate;
    const details = {
        passNumber: 'TEHDI274784',
        passType: 'daily',
        passValidTill: formattedDateTime,
        passFare: data.passFare
    };

    document.getElementById('passNumber').textContent = details.passNumber;
    document.getElementById('passType').textContent = details.passType;
    document.getElementById('validTill').textContent = details.passValidTill;
    document.getElementById('passRate').textContent = details.passFare;

    const modal = document.getElementById('successModal');
    modal.style.display = 'flex';
}

// Function to close the modal popup
function closeModal() {
    const modal = document.getElementById('successModal');
    modal.style.display = 'none';
}


// Function to handle QR code detection
function updateContent(data) {
    // Update specific elements on your page with data from the backend
    document.getElementById('lastValidatedInfo').textContent = data.validatedAt;
    document.getElementById('busNumberInfo').textContent = data.busNumber;
    // You can add more code here to update other elements as needed
}

function updateZoom() {
    const zoomLevel = document.getElementById("zoom-bar").value;
    html5QrCode
        .applyVideoConstraints({ advanced: [{ zoom: zoomLevel }] })
        .catch((err) => {
            console.log(err);
        });
}

window.onclick = function (event) {
    var modal = document.getElementById("qrModal");
    if (event.target == modal) {
        closeQRScanner();
    }
};

window.onload = function () {
    fetch('https://dns.google/resolve?name=tummoc-production.up.railway.app&type=A')
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(error => console.error('Error fetching DNS status:', error));
    fetchPassengerDetails();
}

function toggleDropdown() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function (event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}*/


let html5QrCode;

let localPassengerDetails = null;

function fetchPassengerDetails() {
    fetch("https://tummoc-production.up.railway.app/api/passenger/get")
        .then((response) => response.json())
        .then((data) => {
            console.log(data); // For testing purposes

            // Store the data in local storage
            localStorage.setItem('passengerDetails', JSON.stringify(data));

            const globalPassengerDetails = JSON.parse(localStorage.getItem('passengerDetails'));
            localPassengerDetails = globalPassengerDetails;

            // Update the DOM with fetched data
            updatePassengerDetails(data);
        })
        .catch((error) =>
            console.error("Error fetching passenger details:", error)
        );
}

function updatePassengerDetails(data) {
    document.getElementById('passengerName').textContent = data.passengerName;
    document.getElementById('identificationType').textContent = data.identificationType;
    document.getElementById('identificationNumber').textContent = data.identificationNumber;
    document.getElementById('passPurchased').textContent = data.passPurchased;
    document.getElementById('passValidFrom').textContent = data.passValidFrom;
    document.getElementById('passValidTill').textContent = data.passValidTill;
    document.getElementById('passengerAvatar').src = 'data:image/png;base64,' + data.passengerAvatar;
    document.getElementById('passFare').textContent = '₹' + data.passFare;
}

function fetchDataAndUpdateContent() {
    fetch("https://tummoc-production.up.railway.app/api/qrcode/last-validation")
        .then((response) => response.json())
        .then((data) => {
            console.log(data); // For testing purposes

            // Store the data in local storage
            localStorage.setItem('lastValidationDetails', JSON.stringify(data));

            // Update the DOM with fetched data
            updateLastValidationDetails(data);

        })
        .catch((error) => console.error("Error:", error));
}

function updateLastValidationDetails(data) {
    document.getElementById('lastValidatedInfo').textContent = data.validatedAt;
    document.getElementById('busNumberInfo').textContent = data.busNumber;
}

// Load data from the server and update content
//fetchDataAndUpdateContent();

// Function to open the QR scanner
function openQRScanner() {
    var modal = document.getElementById("qrModal");
    modal.style.display = "block";

    html5QrCode = new Html5Qrcode("reader");
    html5QrCode
        .start(
            { facingMode: "environment" },
            {
                fps: 10,
                qrbox: { width: 250, height: 250 },
                disableFlip: false,
            },
            (qrCodeMessage) => {
                console.log(`QR Code detected: ${qrCodeMessage}`);

                // Handle the QR code data here
                sendQRCodeData(qrCodeMessage);

                // Close the modal after QR code is detected
                html5QrCode
                    .stop()
                    .then(() => {
                        closeQRScanner();
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            (errorMessage) => {
                // Parse error, ignore it
            }
        )
        .catch((err) => {
            console.log(err);
        });
}

function closeQRScanner() {
    var modal = document.getElementById("qrModal");
    modal.style.display = "none";
    if (html5QrCode) {
        html5QrCode
            .stop()
            .then(() => {
                html5QrCode.clear();
            })
            .catch((err) => {
                console.log(err);
            });
    }
}

function sendQRCodeData(qrCodeMessage) {
    fetch("https://tummoc-production.up.railway.app/api/qrcode/decode", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ qrData: qrCodeMessage }),
    })
        .then((response) => response.text())
        .then((data) => {
            console.log(data);
            fetchDataAndUpdateContent();
            showModal();
        })
        //    updateContent(data);
        .catch((error) => {
            console.error("Error:", error);
        });
}

// Function to show the modal popup with static details
function showModal() {
    const details = {
        passNumber: "TEHDI274784",
        passType: "daily",
        passValidTill: localPassengerDetails.passValidTill,
        passFare: localPassengerDetails.passFare
    };

    document.getElementById("passNumber").textContent = details.passNumber;
    document.getElementById("passType").textContent = details.passType;
    document.getElementById("validTill").textContent = details.passValidTill;
    document.getElementById("passRate").textContent = '₹' + details.passFare;

    const modal = document.getElementById("successModal");
    modal.style.display = "flex";
}

// Function to close the modal popup
function closeModal() {
    const modal = document.getElementById("successModal");
    modal.style.display = "none";
}

// Function to handle QR code detection
function updateContent(data) {
    // Update specific elements on your page with data from the backend
    document.getElementById("lastValidatedInfo").textContent = data.validatedAt;
    document.getElementById("busNumberInfo").textContent = data.busNumber;
    // You can add more code here to update other elements as needed
}

function updateZoom() {
    const zoomLevel = document.getElementById("zoom-bar").value;
    html5QrCode
        .applyVideoConstraints({ advanced: [{ zoom: zoomLevel }] })
        .catch((err) => {
            console.log(err);
        });
}

window.onclick = function (event) {
    var modal = document.getElementById("qrModal");
    if (event.target == modal) {
        closeQRScanner();
    }
};

window.onload = function () {
    // fetch(
    //     "https://dns.google/resolve?name=tummoc-production.up.railway.app&type=A"
    // )
    //     .then((response) => response.json())
    //     .then((data) => console.log(data))
    //     .catch((error) => console.error("Error fetching DNS status:", error));
    // fetchPassengerDetails();

    // Check if passenger details are already stored in local storage
    const storedPassengerDetails = localStorage.getItem('passengerDetails');
    if (storedPassengerDetails) {
        const globalPassengerDetails = JSON.parse(storedPassengerDetails);
        localPassengerDetails = globalPassengerDetails;
        updatePassengerDetails(globalPassengerDetails);
    } else {
        fetchPassengerDetails();
    }

    // Check if last validation details are already stored in local storage
    const storedValidationDetails = localStorage.getItem('lastValidationDetails');
    if (storedValidationDetails) {
        updateLastValidationDetails(JSON.parse(storedValidationDetails));
    } else {
        fetchDataAndUpdateContent();
    }
};

function toggleDropdown() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function (event) {
    if (!event.target.matches(".dropbtn")) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains("show")) {
                openDropdown.classList.remove("show");
            }
        }
    }
};
