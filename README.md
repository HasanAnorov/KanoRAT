
# KanoRAT

[![Status](https://img.shields.io/badge/status-lab--only-orange)](#ethics--safety)

**Short pitch —** KanoRAT is an educational Android telemetry client and KanoRTC dashboard (Kotlin + Jetpack Compose) that demonstrates how device metadata and consenting telemetry flow in a controlled lab environment. Built for learning and research while transitioning from mobile development into cybersecurity.

> **Important:** KanoRAT is strictly for learning, research and lab-only use. It must **never** be deployed on devices you do not own or have explicit, documented consent to test. See `docs/ETHICS.md`.

---

## Table of contents

* [Overview](#overview)
* [Features (non-actionable)](#features-non-actionable)
* [Tech stack](#tech-stack)
* [Architecture](#architecture)

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

