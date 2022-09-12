const backendUrl = "http://192.168.1.11:8080"

export function imageToText(image) {
    return fetch(backendUrl + "/image-to-text", {method: 'POST', body: image})
        .then(response => response.json())
        .then((data) => {
            return data["board"];
        })
}

export function solveScrabble(gameState) {
    return fetch(backendUrl + "/solve-scrabble", {
        method: 'POST',
        body: JSON.stringify(gameState),
        headers: {'Content-Type': 'application/json'},
    })
        .then(response => response.json())
        .then((data) => {
            return data;
        })
}