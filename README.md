# Reproducing bug of "disconnected" event
**Steps**
1. In one terminal run "playwright-server":

```
cd playwright-server && npm install && npm run build && npm run start
```

(for debug: `DEBUG=pw:browser,pw:protocol,pw:channel npm run start`)

2. In second terminal run "App.java" connecting over CDP:

```
mvn package exec:java
```

**Expected**

I expect printed "browser disconnected event" in logs of "playwright-server", like:
```
Express is listening at http://localhost:3000
browser created at ws://127.0.0.1:3001/devtools/browser/44752463-3b05-4c94-9035-ec1f6449df69
browser disconnected event
```

**Actual**

There is no "browser disconnected event" log entry, which means event is not triggered.
```
Express is listening at http://localhost:3000
browser created at ws://127.0.0.1:3001/devtools/browser/24914a27-4d65-4c30-8476-d560f087a596
```
