function errorFunc(status) {
    document.getElementById("errorMsg").innerText = "Server Response: " +
        status.status + " " + status.statusText;
    document.getElementById("error").click()
}

class API {
    constructor() {
        this.playerName = undefined
        this.bgmEnabled = undefined
        this.link = "http://" + window.location.host + "/api/congklak/"
        this.prepareAll()
    }

    /**
     * never call this function outside of API class
     * */
    async prepareAll() {
        let result = sessionStorage.getItem("playerName")
        if (result !== null) {
            this.playerName = result
        } else {
            const data = await this.promised_fetch(this.link + "playerName")
            this.playerName = data.playerName
            sessionStorage.setItem("playerName", this.playerName)
        }

        result = sessionStorage.getItem("bgmEnabled")
        if (result !== null) {
            this.bgmEnabled = result
        } else {
            const data = await this.promised_fetch(this.link + "bgmEnabled")
            this.bgmEnabled = data.bgmEnabled
            sessionStorage.setItem("bgmEnabled", this.bgmEnabled)
        }
    }

    getPlayerName() {
        return this.playerName
    }

    isBgmEnabled() {
        return this.bgmEnabled
    }

    /**
     * never call this function outside of API class
     * @param _url - valid API URL
     * @return Object of Promise
     * */
    async promised_fetch(_url) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: _url,
                method: "GET",
                success: function (result) {
                    resolve(result)
                },
                error: function (status) {
                    errorFunc(status)
                    reject(status)
                },
                cache: false
            });
        });
    }

    /**
     * @param tableBodyHtml - HTML Element represent table body
     * */
    async getScore(tableBodyHtml) {
        const data = await this.promised_fetch(this.link + "score");
        let result = ""
        data.forEach((e) => {
            result = result + "<tr>" + "<td>" + e.playerName + "</td>" + "<td>" + e.score + "</td>" + "<td>" + e.status + "</td>" + "</tr>"
        })
        tableBodyHtml.innerHTML = result
    }
}


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