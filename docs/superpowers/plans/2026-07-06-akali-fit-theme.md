# Akali Fit Theme Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Apply a simple black-and-purple Akali Fit theme to all four Swing screens without changing application behavior.

**Architecture:** A single package-level theme utility owns visual constants and recursively styles Swing component trees. Existing frames retain their layouts, actions, controllers, and data flow; they only update visible branding and call the theme utility after constructing content.

**Tech Stack:** Java 17, Swing, JUnit 5

---

### Task 1: Shared theme

**Files:**
- Create: `src/view/AkaliFitTheme.java`
- Create: `tests/view/AkaliFitThemeTest.java`

- [ ] Write tests asserting the shared background/accent colors and recursive button/panel styling.
- [ ] Compile tests and confirm failure because `AkaliFitTheme` does not exist.
- [ ] Implement constants plus `apply(Component)` handling panels, labels, buttons, inputs, combo boxes, tables, scroll panes, and titled borders.
- [ ] Compile tests and production sources successfully.

### Task 2: Apply branding and theme

**Files:**
- Modify: `src/view/LoginFrame.java`
- Modify: `src/view/DashboardFrame.java`
- Modify: `src/view/MemberFrame.java`
- Modify: `src/view/MembershipFrame.java`
- Test: `tests/view/AkaliFitThemeTest.java`

- [ ] Add tests asserting content panels use the shared background and expose `Akali Fit` branding.
- [ ] Confirm the new assertions fail against the current white UI.
- [ ] Change visible product/window titles to `Akali Fit` and invoke `AkaliFitTheme.apply(...)` after each content tree is built.
- [ ] Compile all sources and tests; inspect the diff to confirm action listeners and controller calls are unchanged.

### Task 3: Verify and commit

**Files:**
- Verify all files above only.

- [ ] Compile production and test sources with the local dependency classpath.
- [ ] Verify compiled frames contain existing navigation/controller methods.
- [ ] Commit only theme-related files with `feat: apply Akali Fit theme`.
