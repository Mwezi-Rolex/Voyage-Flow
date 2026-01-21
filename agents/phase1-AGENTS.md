# Phase 1: Triage Specialist

## Role
Crash elimination only. Make app launch without exceptions.

## Time Budget
60 minutes maximum. If blocked for >15 minutes on single issue, document and move on.

## Task Checklist
Execute in this exact order:

### Task 1.1: API Key Configuration [15 min]
1. Open `local.properties`
2. Verify `MAPS_API_KEY` exists and is non-empty
3. Open `app/build.gradle.kts`
4. Locate buildConfigField for MAPS_API_KEY
5. Ensure syntax: `buildConfigField("String", "MAPS_API_KEY", "\"${localProperties.getProperty(\"MAPS_API_KEY\")}\"")`
6. Sync Gradle
7. Test: Can BuildConfig.MAPS_API_KEY be accessed?

### Task 1.2: Create MapViewModel [20 min]
1. Create file: `app/src/main/java/com/example/matatu_navigation/MapViewModel.kt`
2. Copy exact code from battle plan document
3. Verify imports resolve
4. Sync Gradle
5. Test: Does `viewModel<MapViewModel>()` compile in TripMapScreen?

### Task 1.3: Wire Navigation [15 min]
1. Open `HomeScreen.kt`
2. Add "Show Map" button in Row next to "Estimate Fare"
3. Wire onClick to `onOpenMap` lambda
4. Verify lambda signature matches navigation graph

### Task 1.4: Integration Test [10 min]
1. Run app in debug mode
2. Enter origin: "Nairobi"
3. Enter destination: "Nakuru"
4. Click "Show Map"
5. Expected: Navigation occurs, no crash
6. NOT expected: Polyline rendering (that's Phase 2)

## Success Criteria
- App launches without crashing
- Navigation from HomeScreen to MapScreen succeeds
- No exceptions in logcat during core flow

## Forbidden Actions
- Do not add error toasts
- Do not implement loading states
- Do not validate inputs
- Do not style anything
- Do not add logging beyond crash debugging

## Output Format
Write to `outputs/phase1-output.md`:
```
## Phase 1 Complete

### Files Modified
- local.properties (line X)
- app/build.gradle.kts (line Y)
- app/src/.../MapViewModel.kt (created)
- app/src/.../HomeScreen.kt (line Z)

### Test Result
PASS/FAIL: [result]

### Logcat Snapshot
[paste relevant lines]

### Blockers
[list any issues that consumed >15 min]

### Phase 2 Ready
YES/NO
```