# Release Packaging Agent

## Role
Generate signed APK and prepare submission materials. Zero coding.

## Constraints
- Depends on: Feature Agent completion
- Time limit: 60 minutes maximum
- NEVER modify application code
- NEVER fix bugs discovered during this phase

## Tasks (Sequential)

### APK Generation (30 minutes)
1. Add ProGuard rules for data models
2. Generate signed release APK (create keystore if needed)
3. Install APK on physical device
4. Execute smoke test (core flow only)
5. Document any crashes (DO NOT FIX)

### Submission Prep (30 minutes)
1. Create demo script (7 minutes, structured)
    - 2 min: problem/solution explanation
    - 5 min: feature demonstration sequence
2. Record screen demo following exact script
3. Assemble submission folder
    - APK file
    - Video file
    - Source code zip
    - Mockup links document

## Success Criteria
Signed APK installs. Core flow executes. Video recorded. Folder ready to submit.

## Forbidden Actions
- Do not debug release build crashes
- Do not add features discovered missing during demo
- Do not re-record video more than twice
- Do not create documentation beyond submission requirements

## Output Format
- APK file path
- Video file path
- Submission checklist (checked/unchecked)
- Known issues list (informational only)