# Phase 2: Core Feature Implementation

## Preconditions
- Phase 1 complete and passing
- `outputs/phase1-output.md` exists with "Phase 2 Ready: YES"

## Role
Implement polyline rendering and UI polish. Nothing else.

## Time Budget
60 minutes maximum.

## Task Checklist

### Task 2.1: MapViewModel Integration [30 min]
1. Open `TripMapScreen.kt`
2. Inject MapViewModel: `val mapViewModel: MapViewModel = viewModel()`
3. Add LaunchedEffect for route fetching:
```kotlin
LaunchedEffect(origin, destination) {
    mapViewModel.getRoute(origin, destination)
}
```
4. Observe uiState: `val uiState by mapViewModel.uiState.collectAsState()`
5. In GoogleMap composable, add when expression:
    - Loading: Show CircularProgressIndicator
    - Success: Draw Polyline from route data
    - Error: Show Text with error message
6. Below map, add Card displaying:
    - Distance (from uiState.distance)
    - Duration (from uiState.duration)

### Task 2.2: String Resources [15 min]
1. Open `res/values/strings.xml`
2. Add these strings only:
    - app_name: "Voyage Flow"
    - start_location: "Start location"
    - destination: "Destination"
    - estimate_fare: "Estimate Fare"
    - show_map: "Show Map"
3. Open `HomeScreen.kt`
4. Replace hardcoded Button/TextField strings with `stringResource(R.string.X)`

### Task 2.3: Integration Test [15 min]
1. Run debug build
2. Execute full flow:
    - Home → Input "Nairobi" and "Nakuru" → Show Map
3. Verify:
    - Polyline draws on map
    - Info card appears with distance/duration
    - Values are non-zero

## Success Criteria
- Polyline renders between origin and destination
- Distance and duration display in card
- No crashes during flow
- Strings externalized

## Forbidden Actions
- Do not create new screens
- Do not add animations
- Do not implement retry logic
- Do not add analytics
- Do not modify navigation graph

## Output Format
Write to `outputs/phase2-output.md`:
```
## Phase 2 Complete

### Files Modified
- TripMapScreen.kt (lines X-Y)
- res/values/strings.xml (lines A-B)
- HomeScreen.kt (lines C-D)

### Integration Test Result
PASS/FAIL: [result]

### Screenshot Path
[path to screenshot showing polyline]

### Remaining Hardcoded Strings
[list for documentation only]

### Phase 3 Ready
YES/NO
```