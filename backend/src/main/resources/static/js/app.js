async function convertVideo() {

    const url =
        document.getElementById("youtubeUrl").value;

    const loading =
        document.getElementById("loading");

    const result =
        document.getElementById("result");

    loading.innerHTML =
        "Processing video... Please wait.";

    result.innerHTML = "";

    try {

        const response = await fetch(
            "/api/convert",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    url: url
                })
            }
        );

        const data = await response.json();

        loading.innerHTML = "";

        if (data.status === "success") {

            result.innerHTML = `

                <h3>Conversion Successful</h3>

                <p>
                    <a href="${data.audioUrl}" target="_blank">
                        Download Audio
                    </a>
                </p>

                <p>
                    <a href="${data.transcriptUrl}" target="_blank">
                        Download Transcript
                    </a>
                </p>
            `;

            loadHistory();

        } else {

            result.innerHTML =
                "<p>Conversion failed</p>";
        }

    } catch (error) {

        loading.innerHTML = "";

        result.innerHTML =
            "<p>Error occurred</p>";
    }
}

async function loadHistory() {

    const historyDiv =
        document.getElementById("history");

    const response =
        await fetch("/api/history");

    const data =
        await response.json();

    historyDiv.innerHTML = "";

    data.forEach(item => {

        historyDiv.innerHTML += `

            <div class="history-card">

                <p>
                    <b>Language:</b>
                    ${item.language}
                </p>

                <p>
                    <a href="${item.audioUrl}" target="_blank">
                        Audio
                    </a>
                </p>

                <p>
                    <a href="${item.transcriptUrl}" target="_blank">
                        Transcript
                    </a>
                </p>

            </div>
        `;
    });
}

loadHistory();