# Akali Fit Theme Design

## Goal

Apply a simple, consistent black-and-purple visual identity and the `Akali Fit` brand to Login, Dashboard, Data Member, and Membership screens without changing application behavior.

## Visual System

- Window background: near-black.
- Content panels and form sections: dark charcoal.
- Primary accent and action buttons: purple.
- Primary text: white; secondary text: light gray.
- Inputs and tables: dark surfaces with readable light text and purple selection highlights.
- Borders and spacing remain restrained to preserve a simple desktop UI.

## Architecture

Add one `AkaliFitTheme` utility in the `view` package. It owns shared colors and applies styles recursively to Swing component trees. Each frame constructs its existing UI, then applies the shared theme. Page-specific layout and event handling remain inside the existing frames.

## Branding

- Replace visible `Gym Membership System` branding with `Akali Fit`.
- Update window titles to use the `Akali Fit` name.
- Keep Indonesian feature labels and messages unchanged.

## Scope

The change covers `LoginFrame`, `DashboardFrame`, `MemberFrame`, and `MembershipFrame`. It does not change database access, validation, navigation behavior, CRUD behavior, or membership-extension behavior.

## Verification

- Component tests validate core theme colors and branding text.
- All production sources compile.
- Existing tests are compiled and run when a test runner is available.
