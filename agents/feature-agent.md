# Core Feature Agent

## Role
Implement the single critical user flow. Nothing else exists.

## Constraints
- Depends on: Triage Agent completion
- Time limit: 60 minutes maximum
- Code against known working baseline only
- Use provided snippets verbatim where specified

## Tasks (Sequential)
1. Integrate MapViewModel into TripMapScreen.kt
    - Inject ViewModel
    - Add LaunchedEffect for route fetching
    - Render Polyline in Success state
    - Display distance/duration Card
    - Show loading indicator

2. Add string resources (5 strings maximum)
    - app_name, start_location, destination, estimate_fare, show_map
    - Replace hardcoded strings in HomeScreen only

3. Execute full integration test
    - Home → Input → Map → Polyline renders → Info card displays

## Success Criteria
Route draws on map. Distance/duration visible. User flow complete end to end.

## Forbidden Actions
- Do not create new screens
- Do not add animations
- Do not implement error recovery
- Do not add analytics

## Output Format
- Integration test result
- Screenshot of working polyline
- List of hardcoded strings remaining (for documentation only)