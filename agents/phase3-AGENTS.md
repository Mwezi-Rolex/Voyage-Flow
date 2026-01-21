# Phase 3: Release Packaging

## Preconditions
- Phase 2 complete and passing
- `outputs/phase2-output.md` exists with "Phase 3 Ready: YES"

## Role
Generate signed APK and submission materials. ZERO code changes.

## Time Budget
60 minutes maximum.

## CRITICAL RULE
If you discover bugs during this phase, document them but DO NOT FIX THEM.
This is release packaging only.

## Task Checklist

### Task 3.1: ProGuard Configuration [10 min]
1. Open `proguard-rules.pro`
2. Add these rules:
```
-keep class com.example.matatu_navigation.models.** { *; }
-keep class com.example.matatu_navigation.** { *; }
```
3. Save file (do not run build yet)

### Task 3.2: Generate Signed APK [20 min]
**REQUIRES USER PERMISSION**
Before proceeding, output this message:
```
I need permission to generate a signed APK. This will:
1. Create a new keystore (if none exists)
2. Build release variant
3. Sign APK with generated key

Reply YES to proceed. I will document the keystore path and password.
```

After receiving YES:
1. Navigate: Build > Generate Signed Bundle / APK
2. Select: APK
3. If no keystore exists, create one:
    - Key store path: `[project]/release-key.jks`
    - Password: [user will provide]
4. Build variant: release
5. Generate APK

### Task 3.3: Test Release Build [10 min]
1. Locate: `app/release/app-release.apk`
2. Request user install on physical device
3. Execute smoke test:
    - Launch app
    - Input origin/destination
    - Navigate to map
    - Verify polyline renders
4. Document any crashes (do not debug)

### Task 3.4: Demo Script [20 min]
Write to `outputs/demo-script.md`:
```
# 7-Minute Demo Script

## Segment 1: Problem/Solution (2 min)
- Explain: Chaotic matatu system in Nairobi
- Solution: Voyage Flow provides route planning

## Segment 2: Feature Demo (5 min)
1. [00:00] Show HomeScreen clean UI
2. [00:30] Demonstrate "Popular Routes" from Firestore
3. [01:00] Click route → RouteDetailsScreen
4. [01:30] Click "Show Route on Map" → polyline renders
5. [02:30] Back to Home → custom origin/destination
6. [03:00] Click "Estimate Fare" → FareEstimatorScreen
7. [03:30] Back → Click "Show Map" → custom route polyline
8. [04:30] Show distance/duration card
9. [05:00] Navigate to Settings (placeholder screen)
```

## Success Criteria
- Signed APK generated and tested
- Demo script written
- All deliverables documented

## Forbidden Actions
- Do not modify application code
- Do not debug crashes found during testing
- Do not add features discovered missing
- Do not re-record demo more than twice

## Output Format
Write to `outputs/phase3-output.md`:
```
## Phase 3 Complete

### APK Details
- Path: [full path to app-release.apk]
- Keystore: [path]
- Keystore password: [user-provided password]
- Build variant: release
- File size: [size in MB]

### Smoke Test Result
PASS/FAIL: [result]

### Known Issues (Not Fixed)
[list any bugs found during testing]

### Deliverables Ready
- [ ] Signed APK
- [ ] Demo script
- [ ] Source code (already exists)
- [ ] Mockup links (user to provide)

### Submission Checklist
- [ ] APK tested on device
- [ ] Demo video recorded
- [ ] Folder structure created
- [ ] All files zipped
```
```

## Usage in Android Studio

Click Gemini in the tool window bar, select the Agent tab, describe the task you want the agent to perform, and review and approve any changes as the agent works to accomplish the task. 

### Phase 1 Prompt
```
Execute Phase 1 triage tasks from app/src/main/java/com/example/matatu_navigation/phase1/AGENTS.md
```

### Phase 2 Prompt
```
Phase 1 is complete. Execute Phase 2 feature implementation from app/src/main/java/com/example/matatu_navigation/phase2/AGENTS.md
```

### Phase 3 Prompt
```
Phase 2 is complete. Execute Phase 3 release packaging from app/src/main/java/com/example/matatu_navigation/phase3/AGENTS.md