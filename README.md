# Voyage Flow - Nairobi Transport Navigator

Voyage Flow is an Android mobile application designed to digitize the public transport experience in Nairobi. It addresses the chaos of the current system, where commuters rely on asking conductors or strangers for directions, which is often unreliable and anxiety-inducing. The platform provides a centralized system for route discovery, real-time tracking, and language accessibility.

### Problem Definition
Commuters in Nairobi often face significant challenges:
* **Uncertainty:** Stage locations frequently shift without notice, causing passengers to waste time guessing where to board.
* **Anxiety:** Introverts and tourists struggle with the need to constantly ask touts for help.
* **Language Barriers:** Foreign visitors who do not speak Swahili or Sheng often feel intimidated and vulnerable to exploitation.

### Proposed Solution
Voyage Flow offers a centralized platform that grants users autonomy through:
* **Real-Time Navigation:** Embedded Google Maps integration allowing users to track their live location relative to their destination, reducing the anxiety of missing a stop.
* **Static Routes (My Routes):** A personalization feature that allows users to save frequently used routes (e.g., "Friday Gym") for quick access without re-entering search details.
* **Universal Usability:** A dedicated Language Preference Setting supporting multiple languages (English, French, German) to assist non-native users.
* **Self-Support:** An integrated FAQ section to help users resolve common issues autonomously, giving them an internal locus of control.

### System Requirements & Features
The system is built on prioritized functional requirements:
* **REQ-1 (Account Management):** Secure account creation to save user preferences and route history.
* **REQ-2 (Map Integration):** Utilizes Google Maps API for real-time tracking and route visualization.
* **REQ-3 (Personalization):** Enables saving of specific "Static Routes" for recurring journeys.
* **REQ-8 (Reliability):** Designed to function accurately even in low-connectivity areas by caching recent route data.

### Tech Stack
* **Platform:** Native Android Development
* **API:** Google Maps SDK
* **Architecture:** User-Centered Design (UCD) focusing on Shneiderman’s Golden Rules.

### Use Case Example: The Tourist
1.  **Goal:** A tourist wants to explore Nairobi but only speaks German.
2.  **Action:** They open Voyage Flow and use the "Language Preference" setting to switch the interface to German.
3.  **Result:** The user can now search for a route, view the map, and navigate to their destination safely without needing to ask a stranger for translation assistance.
