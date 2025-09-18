# README.md

# KanoRAT

[![License](https://img.shields.io/badge/license-MIT-blue)](#license) [![Status](https://img.shields.io/badge/status-lab--only-orange)](#ethics--safety)

**Short pitch —** KanoRAT is an educational Android telemetry client and KanoRTC dashboard (Kotlin + Jetpack Compose) that demonstrates how device metadata and consenting telemetry flow in a controlled lab environment. Built for learning and research while transitioning from mobile development into cybersecurity.

> **Important:** KanoRAT is strictly for learning, research and lab-only use. It must **never** be deployed on devices you do not own or have explicit, documented consent to test. See `docs/ETHICS.md`.

---

## Table of contents

* [Overview](#overview)
* [Features (non-actionable)](#features-non-actionable)
* [Tech stack](#tech-stack)
* [Architecture](#architecture)
* [Quickstart (safe, high-level)](#quickstart-safe-high-level)
* [Screenshots & sanitisation](#screenshots--sanitisation)
* [Repo structure](#repo-structure)
* [Security, legal & ethics](#security-legal--ethics)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)

---

## Overview

KanoRAT is an educational Android client/server project to explore device metadata collection and telemetry pipelines in an isolated, consensual lab. The Android agent demonstrates permission-gated collection (device build fields, optional location, media metadata) and WorkManager-based scheduling for deferred uploads. The KanoRTC side visualises received telemetry and link logs.

**Learning goals**:

* Understand what metadata is exposed by Android devices and HTTP requests
* Build a simple, inspectable telemetry pipeline and dashboard for analysis
* Practice secure-by-design and responsible-use practices when exploring offensive/defensive concepts

---

## Features (non-actionable)

* Agent link logging: short link that records metadata when clicked
* Device metadata extraction: UA and Android `Build.*` fields, Android\_ID placeholder, SDK level, brand/model, etc.
* KanoRTC dashboard: client list, status, basic device attributes
* Acquired-data categories: Location (consented), Files (consented), Messages (consented)
* Background scheduling demo using WorkManager for deferred uploads
* Localisation (English + Russian) and light/dark theme support

> The app UI shows conceptual controls for actions (e.g., microphone/camera toggles). These are **not** to be used on devices without explicit consent and are shown for lab demonstration only.

---

## Tech stack

* Kotlin (Android) + Jetpack Compose
* WebRTC (experimental UI flows) for real-time concepts
* Retrofit (networking) and Chucker (debugging)
* WorkManager (background scheduling)
* Android Studio, Gradle

---

## Architecture

See `docs/architecture.png` for a sanitized architecture diagram. The high-level flow is:

1. Android Agent collects consented fields and schedules uploads via WorkManager.
2. Client uploads telemetry to KanoRTC API over HTTPS.
3. KanoRTC stores sanitized logs and presents them in a dashboard UI.
4. Admin interacts with dashboard in a lab browser to analyse telemetry.

---

## Quickstart (safe, high-level)

**Read `docs/ETHICS.md` before running any code.**

1. Build and run the Android app on an emulator or device you own.
2. Configure the server to a local address (e.g., `http://localhost:8000`) or a private lab network.
3. Use only synthetic or clearly consented data. When collecting screenshots or logs, redact IMEI and Android\_ID.

> Operational commands, server configs, and public exposure instructions are intentionally omitted from this README to prevent misuse.

---

## Screenshots & sanitisation

Keep sanitized screenshots under `docs/screenshots/`. Replace or redact any real device identifiers and IP addresses before publishing to GitHub or sharing publicly.

---

## Repo structure

```
/
├─ app/                           # Android app source (Kotlin, Compose)
├─ server/                        # OPTIONAL: server visualization code (if included)
├─ docs/
│  ├─ ETHICS.md                   # consent, legal, lab rules
│  ├─ architecture.png            # sanitized architecture diagram
│  ├─ SCREENSHOTS_GUIDE.md        # how to take & sanitize screenshots
│  └─ SECURITY.md                 # disclosure and vulnerability reporting
├─ .github/
│  └─ CONTRIBUTING.md
├─ LICENSE
├─ README.md                      # this file
└─ CHANGELOG.md
```

---

## Security, legal & ethics

See `docs/ETHICS.md` for a full policy. Key rules:

* Lab-only usage on devices you own or with explicit written consent.
* Do not deploy to third-party devices or distribute to others without documented permission.
* Sanitize all logs and screenshots before sharing.

---

## Roadmap

* Harden server with TLS & auth
* Add synthetic test harness for demos
* Add defensive parsing examples (SIEM playbook) in `docs/POSTURE.md`

---

## Contributing

If you contribute, follow the safe-testing guidelines in `docs/ETHICS.md` and `docs/SECURITY.md`.

---

## License

This project is released under the MIT License. See `LICENSE`.

---

# docs/ETHICS.md

# ETHICS & SAFE-USE POLICY

**Purpose:** This document describes the ethical, legal and operational constraints for using the KanoRAT project. It must be read and agreed before running any code or tests.

## 1. Acceptance

By running or contributing to this repository you agree to:

* Use the software only for lawful, ethical, and consensual research and testing.
* Not use or distribute the software to access devices you do not own or have explicit written permission to test.
* Follow the responsible disclosure process for any third-party vulnerability discovered during testing.

## 2. Lab rules (must follow)

* Use only emulator images or devices owned by you OR devices with explicit written permission from the device owner.
* Keep the test network isolated from the public internet unless you have formal, documented approval.
* Redact or syntheticize any real PII before publishing screenshots or logs.
* Maintain a test plan and a consent log for each test device.

## 3. Consent template (use this to get written permission)

```
I, <FULL NAME>, give permission to <RESEARCHER NAME> to perform controlled security testing on the device listed below for research and learning purposes.

Device: <device model / serial>
Owner: <owner name>
Date range: <start> to <end>
Scope: The researcher may collect device metadata and telemetry for analysis. The researcher will not exfiltrate or publish personal data without explicit, additional consent.

Signature: ____________________    Date: ___________
```

Store signed consent templates securely in your lab records.

## 4. Responsible disclosure

If you discover a vulnerability in third-party software while testing, follow the vendor's disclosure policy. If no policy exists, email security@<vendor-domain> and allow a reasonable disclosure period before publishing. Also notify the KanoRAT maintainers via the `SECURITY.md` contact.

## 5. Data handling & retention

* Minimise collected data; use synthetic data where possible.
* Retain telemetry only as long as needed for research; then delete securely.
* When publishing, redact IMEI, Android\_ID, serial numbers, IPs (or mask them), and any other PII.

## 6. Contact & reporting

For policy questions or to report an incident, contact: `kano@example.com` (replace with an email you control before publishing).

---

# CHANGELOG.md

# Changelog

All notable changes to this project will be documented in this file.

## \[Unreleased]

* Initial project structure and README
* ETHICS.md added
* Placeholder for sanitized architecture diagram

---

*End of generated files.*
