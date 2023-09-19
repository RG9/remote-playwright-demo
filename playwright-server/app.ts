import express from "express"
import {Browser, chromium} from "playwright"


const app = express()
const appPort = 3000;
const playwrightPort = 3001;

app.post("/browsers", async (req, res) => {
    const launchOpts = {
        headless: true,
        args: [`--remote-debugging-port=${playwrightPort}`]
    }

    const browser: Browser = await chromium.launch(launchOpts)

    const response = await fetch(`http://127.0.0.1:${playwrightPort}/json/version`)
    const {webSocketDebuggerUrl: wsEndpoint} = await response.json()

    browser.on("disconnected", browser => {
        console.log("browser disconnected event")
        browser.close()
    })

    console.log(`browser created at ${wsEndpoint}`)

    res.send(wsEndpoint)
});

app.listen(appPort, () => {
    return console.log(`Express is listening at http://localhost:${appPort}`);
});