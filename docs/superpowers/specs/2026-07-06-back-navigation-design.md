# Back Navigation Design

## Goal

Add a visible `Kembali` button to the member-management and membership-management screens so users can return to the dashboard.

## Behavior

- `MemberFrame` and `MembershipFrame` each display a `Kembali` button in the page header.
- Clicking the button opens one `DashboardFrame` and disposes the current frame.
- Existing member CRUD and membership-extension behavior remains unchanged.

## Structure

Each frame creates its header through a package-visible static helper that accepts the back button. The production constructor binds the button to the navigation action. This keeps component placement testable without constructing a `JFrame` in a headless test environment.

## Verification

- Component tests confirm both headers contain a button labeled `Kembali`.
- Production sources compile successfully.
- Existing tests remain unchanged and are run when the Maven test runner is available.
