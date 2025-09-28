
# KanoRAT

[![Status](https://img.shields.io/badge/status-lab--only-orange)](#ethics--safety)

KanoRAT is a research-oriented Android client and KanoRTC dashboard that demonstrates permission-gated telemetry collection, short-link logging, and a simple telemetry visualization dashboard. I built it to transition from mobile development into cybersecurity by exploring device metadata, telemetry pipelines, and responsible red-team thinking — all inside an isolated, consented lab environment. The app works with the server to fetch data collected from consent devices. The backend side was built using the Django REST framework for APIs and has its own admin panel. Currently, KanoRAT has two main features (other features are being developed) :

- **Link Logger** – a simple metadata logger behind a short link for demonstrating what a typical HTTP request reveals (example payload is provided below).
- **KanoRTC** – a dashboard that lists target devices connected over WebRTC **data channels** and displays device details (an example is provided below) and the data shared by the KanoRTC client. KanoRTC client could be an app or a scripted code block that is programmed to send data to the server.

> ⚠️ **Ethics & legality**
>
> *Do not deploy this on devices you do not own or control.* This project is **not** a surveillance tool. It is a **teaching artifact** for privacy awareness, blue‑team hardening, and lawful red‑team exercises **with informed consent**.

---

## Table of contents

- [Motivation](#motivation)
- [Project summary](#project-summary)
- [Tech stack](#tech-stack)
- [Key features](#key-features)
- [Contact & reporting](#contact--reporting)
- [Disclaimer](#disclaimer)


---

## Motivation

I built KanoRAT to:

- Move from mobile development to cybersecurity by experimenting with telemetry, device metadata, and secure design patterns.
- Learn what metadata is commonly exposed by mobile devices and how telemetry pipelines behave end-to-end.
- Practice secure-by-design and responsible disclosure behaviour while exploring concepts occasionally associated with red-team operations — within a strictly ethical, lab-only context.

---

## Project summary

- **Agent (Android)**: Kotlin + Jetpack Compose app that collects permissioned device attributes and uploads telemetry to a server (in a lab). Demonstrates WorkManager for scheduled and reliable uploads and uses Retrofit for networking (debugging via Chucker).
- **Server / Dashboard (KanoRTC)**: Visualization UI that displays link logs, client lists, device details, and acquired-data categories (Location, Files, Messages) — only showing what is explicitly consented and uploaded.
- **Link logging**: A short link captures request-level metadata (IP, UA, headers) when clicked and displays these entries in the dashboard.

> All sensitive actions (camera/mic access, file reads) must be performed only in an emulator or a device with explicit consent. The UI includes conceptual controls that are disabled unless used in a controlled test.


---

## 📱 App Screens & Flow


### 1️⃣ Main Screen
<img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/main.png" width="250"> |
<img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/nav_drawer.png" width="250">


## Demo Agent screen — Service & Permission Controls

**1. As shown, there are buttons to start and stop endless service on Android phones.**

<img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/service.png" width="250" alt="Service controls">

- That Service is supposed to work endlessly in the background, even after rebooting, until it is stopped by the stop command. But in some Android versions and other cases, this service might be cancelled by the system. The purpose of this service is to enable us to execute RCE (remote code execution) and establish a stable connection with the device.

**2. Bottom, there is a row of buttons. All these buttons currently do one function: to gain permission and display acquired data on the screen. These functions are ready to implement on Agent devices of KanoRAT.**

- As an example, when "Open Images and Videos" is clicked, the example screen will be like this:

| Lists images and videos | Info about selected Image | 
|-----------:|:-----------:|
| <img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/images.png" width="250" alt="Lists images and videos"> | <img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/imagedesc.png" width="250" alt="Info about selected Image"> |

---

### 2️⃣ Link Logs

## Link logging screen 
| Link logging screen with IPs | Info about selected IP | 
|-----------:|:-----------:|
| <img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/linklogger.png" width="250" alt="Link logging screen with IPs"> | <img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/link_desc.png" width="250" alt="Info about selected IP"> |


**1. As shown, there is an agent link with a copy button. Once this agent link is clicked, the HTTP request will be sent from the target device to our server. Server will extract metadata from the request, then send important information about the  target device.**

---

### 3️⃣ Remote Access Toolkit (RAT) Section

- **Client - a device that should be tracked by KanoRAT**
- **Provider - it is an agent app or block of code that establishes a connection with KanoRAT**

| KanoRAT Screen with Clients | Selected Client Description Screen | 
|-----------:|:-----------:|
| <img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/kanortc.png" width="250" alt="KanoRAT Screen with Clients"> | <img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/kanortcdesc.png" width="250" alt="Selected Client Description Screen"> |

---

### 4️⃣ Settings Screen
<img src="https://github.com/HasanAnorov/KanoRAT/blob/feature/webrtc/app/src/main/java/com/ierusalem/androrat/core/readme_sources/settings.png" width="250">

## UI elements
- *Language* — current app locale shown on the right (e.g., `English or Russian`). Opens a language picker to change the app locale.
- *App Theme* — a light / dark mode toggle. Changes the app theme immediately and persists the choice.
- *Require Login* — a lock toggle that enforces whether authentication should be enabled or not.

---

## Tech stack

**Mobile & client**
- 📱 **Kotlin** — Android app (Jetpack Compose UI)
- 🎨 **Jetpack Compose** — modern declarative UI
- 🔊 **WebRTC** — real-time comms concepts / experimental RTC flows
- 🔌 **Retrofit** — HTTP client for API calls (development: Chucker)
- ⏱️ **WorkManager** — background scheduling & reliable uploads
- 🛠️ **Android Studio**, **Gradle** (Kotlin DSL)

**Server & backend**
- 🐍 **Python** / **Django** + **Django REST Framework** — telemetry API
- 🗄️ **PostgreSQL** — production-ready DB (or SQLite for local dev)
- 🔁 **Gunicorn + Nginx** — recommended production stack (reverse proxy + WSGI)
---

## Example: Link Logger Payload

```json
{
  "ip": "203.0.113.42",
  "browser": "Yandex",
  "browserVersion": "131.0",
  "os": "Android",
  "osVersion": "13",
  "device": "Xiaomi",
  "isMobile": true,
  "isTablet": false,
  "isPC": false,
  "isBot": false
}
```

## Example: Device Details Payload

```json
{
  "brand": "Google",
  "deviceId": "x...x",
  "model": "Pixel 8",
  "id": "sdf765s5f7sf",
  "sdk": 33,
  "manufacturer": "Google",
  "hardware": "bluejay",
  "bootloader": "cloudripper-123456",
  "user": "android-build",
  "type": "user",
  "base": "TKQ1.230805.001",
  "incremental": "123456789",
  "board": "bluejay",
  "host": "abfarm-123",
  "fingerprint": "x.....x",
  "display": "TQ3A01",
  "imei": null,
  "versionCode": 120,
  "timestamp": "2025-09-20T12:00:00Z",
  ...
}
```

> **PII note:** Access to `imei` is restricted on Android 10+ and typically unavailable to third‑party apps. Do **not** store plaintext identifiers; prefer hashed, salted tokens (e.g., `imeiSha256`) or avoid collecting them entirely.


## Key features

- Link logs: agent URL - http://example.agent.url/, when clicked by the target, an HTTP request will be sent to the Server and display the Agent browser. The server will extract important information from metadata and store it. KanoRAT's Link logs section will retrieve the target's metadata and show us visually.
- KanoRTC dashboard: lists clients, status (online/offline), and “Acquired Data” categories.
- WorkManager-based scheduling to simulate deferred telemetry uploads (network-aware).
- Localization (English + Russian) and theme toggle (light/dark).
- Debug tooling integration (Chucker) for development only.

## Contact & reporting

For policy questions or to report an incident, contact: `anorovhasan@gmail.com`.

## Disclaimer

This repository is provided **as‑is** for **educational** purposes. The authors and contributors are **not responsible** for any misuse or damage resulting from running this project outside lawful, consented environments.
