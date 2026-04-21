# Go To Declaration

![Build](https://github.com/aqn3130/Navigate-to-declaration/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

<!-- Plugin description -->
Bridges **Handlebars/Mustache templates** and **Kotlin** by extending the *Go To Declaration* action. Click on any `{{variable}}` inside a `.hbs` file and jump straight to the matching Kotlin `val`/`var` property in your project.

## Features

- Click any `{{variable}}` in a Handlebars (`.hbs`) template to navigate to the corresponding Kotlin property declaration
- Searches across all Kotlin files in the project scope
- Compatible with both K1 and K2 Kotlin compiler modes
- Requires the [Handlebars/Mustache](https://plugins.jetbrains.com/plugin/6884-handlebars-mustache) plugin

## Usage

1. Open a `.hbs` template file
2. Place the cursor on a `{{variableName}}` expression
3. Press <kbd>Ctrl+B</kbd> (or <kbd>Cmd+B</kbd> / <kbd>Cmd+Click</kbd> on macOS)
4. The IDE will jump to the matching Kotlin `val` or `var` with that name
<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Go To Declaration"</kbd> >
  <kbd>Install</kbd>

- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/aqn3130/Navigate-to-declaration/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>
