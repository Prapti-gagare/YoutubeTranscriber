async function convertVideo() {

    const url =
        document.getElementById("youtubeUrl").value;

    const loading =
        document.getElementById("loading");

    const result =
        document.getElementById("result");

    const button =
        document.getElementById("convertBtn");

    if (!url) {

        alert("Please enter YouTube URL");

        return;
    }

    loading.innerHTML =
        "<h3>Processing... Please wait.</h3>";

    result.innerHTML = "";

    button.disabled = true;

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

        button.disabled = false;

        if (data.status === "success") {

            result.innerHTML = `

                <h2>Conversion Successful</h2>

                <p>
                    <b>Detected Language:</b>
                    ${data.language}
                </p>

                <h3>Transcript Preview</h3>

                <textarea
                    rows="10"
                    cols="80"
                    readonly
                >
${data.transcriptText}
                </textarea>

                <br><br>

                <a href="${data.audioUrl}" target="_blank">
                    Download Audio
                </a>

                <br><br>

                <a href="${data.transcriptUrl}" target="_blank">
                    Download Transcript
                </a>
            `;

            loadHistory();

        } else {

            result.innerHTML = `
                <h3>Error</h3>
                <p>${data.message}</p>
            `;
        }

    } catch (error) {

        button.disabled = false;

        loading.innerHTML = "";

        result.innerHTML = `
            <h3>Server Error</h3>
        `;
    }
}

async function loadHistory() {

    const historyDiv =
        document.getElementById("history");

    try {

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

    } catch (error) {

        historyDiv.innerHTML =
            "<p>Failed to load history</p>";
    }
}

loadHistory();