
# KanoRAT

[![Status](https://img.shields.io/badge/status-lab--only-orange)](#ethics--safety)

KanoRAT is a research-oriented Android client and KanoRTC dashboard that demonstrates permission-gated telemetry collection, short-link logging, and a simple telemetry visualization dashboard. It was built to help a mobile developer transition into cybersecurity by exploring device metadata, telemetry pipelines, and responsible red-team thinking — all inside an isolated, consented lab environment.

- **Link Logger** – a simple metadata logger behind a short link for demonstrating what a typical HTTP request reveals (IP, user‑agent, device hints, etc.).
- **KanoRTC** – a dashboard that lists **opt‑in** client devices connected over WebRTC **data channels** and displays device details the user explicitly shared.

> ⚠️ **Ethics & legality**
>
> *Do not deploy this on devices you do not own or control.* This project is **not** a surveillance tool. It is a **teaching artifact** for privacy awareness, blue‑team hardening, and lawful red‑team exercises **with informed consent**.

## Highlights

- 📋 **Metadata awareness** – demonstrate what a click can reveal (browser, OS, device type, isMobile/isBot flags, etc.).
- 🔌 **WebRTC data channel** – near‑real‑time client ↔ server telemetry in controlled labs.
- 🧭 **Opt‑in scopes** – clients choose what to share (e.g., device model) and can revoke at any time.
- 🔐 **Security by default** – TLS everywhere, audit logging, and visible foreground notifications on Android.
- 🧱 **Offline safe queue** – WorkManager queues uploads until network is available (no silent background exfiltration).
- 🧩 **Modern Android** – Kotlin, Jetpack Compose UI, Retrofit + Chucker for networking, WorkManager for scheduling.

---

## Table of contents

- [Motivation](#motivation)
- [Project summary](#project-summary)
- [Tech stack](#tech-stack)
- [Key features (non-actionable)](#key-features-non-actionable)
- [Contact & reporting](#contact--reporting)
- [Disclaimer](#disclaimer)


---

## Motivation

I built KanoRAT to:

- Move from mobile development to cybersecurity by experimenting with telemetry, device metadata, and secure design patterns.
- Learn what metadata is commonly exposed by mobile devices and how telemetry pipelines behave end-to-end.
- Practice secure-by-design and responsible disclosure behavior while exploring concepts occasionally associated with red-team operations — within a strictly ethical, lab-only context.

---

## Project summary

- **Agent (Android)**: Kotlin + Jetpack Compose app that collects permissioned device attributes and uploads telemetry to a server (in a lab). Demonstrates WorkManager for scheduled uploads and uses Retrofit for networking (debugging via Chucker).
- **Server / Dashboard (KanoRTC)**: Visualization UI that displays link logs, client lists, device details, and acquired-data categories (Location, Files, Messages) — only showing what is explicitly consented and uploaded.
- **Link logging**: A short link captures request-level metadata (IP, UA, headers) when clicked and displays these entries in the dashboard.

> All sensitive actions (camera/mic access, file reads) must be performed only in an emulator or a device with explicit consent. The UI includes conceptual controls that are disabled unless used in a controlled test.

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

## Key features (non-actionable)

- Link logs: agent URL - http://example.agent.url/, when clicked by the target, an HTTP request will be sent to the Server and display the Agent browser. The server will extract important information from metadata and store it. KanoRAT's Link logs section will retrieve the target's metadata and show us visually.
- KanoRTC dashboard: lists clients, status (online/offline), and “Acquired Data” categories.
- WorkManager-based scheduling to simulate deferred telemetry uploads (network-aware).
- Localization (English + Russian) and theme toggle (light/dark).
- Debug tooling integration (Chucker) for development only.

## Contact & reporting

For policy questions or to report an incident, contact: `kano@example.com` (replace with an email you control before publishing).

## Disclaimer

This repository is provided **as‑is** for **educational** purposes. The authors and contributors are **not responsible** for any misuse or damage resulting from running this project outside lawful, consented environments.




