function sound(src) {
    this.sound = document.createElement("audio");
    this.sound.src = src;
    this.sound.setAttribute("preload", "auto");
    this.sound.setAttribute("controls", "none");
    this.sound.style.display = "none";
    document.body.appendChild(this.sound);
    this.play = function () {
        this.sound.play();
    }
    this.stop = function () {
        this.sound.pause();
    }
}

/**
 * @param label - a title for dialog
 * @param msg - a message for dialog
 * @param listenerFunc - a function listener for Dialog Close Button. could be undefined
 * */
function dialogFunc(label, msg, listenerFunc) {
    document.getElementById("dialogLabel").innerText = label
    document.getElementById("dialogMsg").innerHTML = msg
    document.getElementById("dialogBtn").click()
    if (listenerFunc !== undefined) {
        const closeBtn = document.getElementsByClassName("dialogClose")
        for (let i = 0; i < closeBtn.length; i++) {
            closeBtn[i].addEventListener("click", listenerFunc)
        }
    }
}

class API {
    constructor() {
        this.link = "http://localhost:8080/api/congklak/"
        this.prepareAll()
    }

    prepareAll() {
        this.getPlayerName()
        this.isBgmEnabled()
    }

    /**
     * this function will get data from <a href="https://www.w3schools.com/jsref/prop_win_sessionstorage.asp">session storage</a>
     * <br>
     * if <strong>session storage</strong> return null then will make data request to server
     * <br>
     * example of usage async function, <a href="https://www.w3schools.com/js/js_async.asp">see this</a>
     * @return playerName - String
     * */
    async getPlayerName() {
        let result = sessionStorage.getItem("playerName")
        if (result === null) {
            const data = await this.promised_fetch(this.link + "playerName");
            result = data.playerName
            sessionStorage.setItem("playerName", result)
        }
        return result
    }

    /**
     * this function will get data from <a href="https://www.w3schools.com/jsref/prop_win_sessionstorage.asp">session storage</a>
     * <br>
     * if <strong>session storage</strong> return null then will make data request to server
     *<br>
     * example of usage async function, <a href="https://www.w3schools.com/js/js_async.asp">see this</a>
     * @return isBgmEnabled - Boolean
     * */
    async isBgmEnabled() {
        let result = sessionStorage.getItem("bgmEnabled")
        if (result === null) {
            const data = await this.promised_fetch(this.link + "bgmEnabled");
            result = data.bgmEnabled
            sessionStorage.setItem("bgmEnabled", result)
        }

        // VALUE IS STRING REPRESENT OF BOOLEAN
        // FUCKING JAVASCRIPT
        // CAN'T CONVERT OBJECT INTO BOOLEAN
        return (result === "true")
    }

    /**
     * never call this function outside of API class
     * @param _url - valid API URL
     * @param _method - a method for request
     * @param _data - a data for send to server
     * @return Object of Promise
     * */
    async promised_fetch(_url, _data) {
        return new Promise((resolve, reject) => {
            if (_data === undefined) {
                $.ajax({
                    url: _url,
                    type: "GET",
                    success: function (result) {
                        resolve(result)
                    },
                    error: function (status) {
                        dialogFunc("Error", "Server Response: " + status.status + " " + status.statusText)
                        reject(status)
                    },
                    cache: false
                });
            } else {
                $.ajax({
                    url: _url,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    data: _data,
                    success: function (result) {
                        resolve(result)
                    },
                    error: function (status) {
                        dialogFunc("Error", "Server Response: " + status.status + " " + status.statusText)
                        reject(status)
                    },
                    cache: false
                });
            }
        });
    }

    /**
     * example of usage async function, <a href="https://www.w3schools.com/js/js_async.asp">see this</a>
     * @return String represent of List Score
     * */
    async getScore() {
        const data = await this.promised_fetch(this.link + "score");
        let result = ""
        if (data.length === 0) {
            result = "<tr><td>No Data</td><td>No Data</td><td>No Data</td></tr>"
        } else {
            data.forEach((e) => {
                result = result + "<tr>" + "<td>" + e.playerName + "</td>" + "<td>" + e.score + "</td>" + "<td>" + e.status + "</td>" + "</tr>"
            })
        }
        return result
    }

    /**
     * @return LittleHole or undefined
     * */
    async getCombination(humanClass, computerClass) {
        const cLittleHole = computerClass.littleHole
        const model = {
            "human": {
                "littleHole": humanClass.littleHole,
                "bigHole": humanClass.bigHole
            },
            "computer": {
                "littleHole": cLittleHole,
                "bigHole": computerClass.bigHole
            }
        }

        let data = await this.promised_fetch(this.link + "combination", JSON.stringify(model))

        let result = undefined
        if (data.hasSolution) {
            data = data.result;
            let x = cLittleHole.length
            for (let i = 0; i < x; i++) {
                if (cLittleHole[i].id === data.id) {
                    result = cLittleHole[i]
                    break
                }
            }
        }

        return result
    }

    /**
     * save a score into database and <a href="https://www.w3schools.com/jsref/prop_win_sessionstorage.asp">session storage</a>
     * <br>
     * after call this function, you have to get a new data with api.getPlayerName() and api.isBgmEnabled()
     * @param name - name of player (human player)
     * @param bgm - bgm setting, true or false
     * @return object
     * */
    async saveSetting(name, bgm) {
        const model = {
            playerName: name,
            bgmEnabled: bgm
        }

        const result = await this.promised_fetch(this.link + "setting", JSON.stringify(model))
        sessionStorage.setItem("playerName", result.playerName)
        sessionStorage.setItem("bgmEnabled", result.bgmEnabled)
        return result
    }

    /**
     * save a score into database and <a href="https://www.w3schools.com/jsref/prop_win_sessionstorage.asp">session storage</a>
     * <br>
     * after call this function, you have to get a new data with api.getScore()
     * @param name - name of player (human player)
     * @param score - score, should be integer
     * @param status - status, should be one of ("Menang", "Kalah", "Seri")
     * @return nothing
     * */
    async saveScore(name, score, status) {
        const model = {
            playerName: name,
            score: score,
            status: status
        }

        return await this.promised_fetch(this.link + "score", JSON.stringify(model))
    }
}

const api = new API()

const bgmSound = new sound("/assets/bgm.mp3")

function playBgmSound() {
    const enable = sessionStorage.getItem("bgmEnabled")
    if (enable === "true"){
        bgmSound.sound.loop = true
        bgmSound.play()
    }
    window.removeEventListener("click", playBgmSound)
}

function stopBgmSound(){
    const enable = sessionStorage.getItem("bgmEnabled")
    if (enable === "false"){
        bgmSound.stop()
    }
}

window.addEventListener("click", playBgmSound)