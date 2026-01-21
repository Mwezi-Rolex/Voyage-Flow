# Matatu Navigator MVP Build Coordinator

## Project Context
Android app (Kotlin/Compose) with Google Maps integration. Firestore backend. 4-hour deadline for working MVP.

## Safety Rules (ABSOLUTE)
- NEVER delete files without explicit written permission
- NEVER run gradle commands affecting production DB
- ALWAYS ask before package installs
- STOP immediately if any command might drop data

## Build Phases (Sequential Only)
Work proceeds in strict order: Phase 1 → Phase 2 → Phase 3.
Each phase has its own AGENTS.md file in the agents/ directory.

### Phase Locations
- Phase 1: agents/phase1-AGENTS.md
- Phase 2: agents/phase2-AGENTS.md
- Phase 3: agents/phase3-AGENTS.md

## Commands Available Without Permission
- Read/list files
- Gradle sync
- Run debug builds
- View logcat

## Commands Requiring Explicit Permission
- Generate signed APK
- Install packages
- Modify proguard-rules.pro
- Delete any files

### Output Locations
All phase outputs go to outputs/ directory at project root with:
- Files changed (full paths)
- Test results (pass/fail)
- Blockers encountered
- Next phase readiness (yes/no)