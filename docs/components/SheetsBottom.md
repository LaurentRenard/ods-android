---
layout: detail
title: "Sheets: bottom"
description: Bottom Sheets are surfaces anchored to the bottom of the screen that present users supplement content.
---

Use Sheets bottom to:

* Display content that complements the screen’s primary content
* Expose all complements options

<br>**On this page**

* [Specifications references](#specifications-references)
* [Accessibility](#accessibility)
* [Implementation](#implementation)
    * [Jetpack Compose](#jetpack-compose)

---

## Specifications references

- [Design System Manager - Sheets](https://system.design.orange.com/0c1af118d/p/81f927-sheets-bottom/b/47b99b)
- [Material Design - Sheets: bottom](https://material.io/components/sheets-bottom)

## Accessibility

Please follow [accessibility criteria for development](https://a11y-guidelines.orange.com/en/mobile/android/development/)

## Implementation

![BottomSheet light](images/sheetbottom_light.png) ![BottomSheet dark](images/sheetbottom_dark.png)

The contents within a bottom sheet should follow their own accessibility guidelines, such as images having content descriptions set on them.

### Jetpack Compose

```kotlin
OdsBottomSheetScaffold(
    sheetContent = {
        // Put here the content of the sheet
    },
    modifier = Modifier,
    scaffoldState = rememberBottomSheetScaffoldState(),
    topBar = null,
    snackbarHost = {},
    floatingActionButton = {},
    floatingActionButtonPosition = FabPosition.End,
    sheetGesturesEnabled = true,
    sheetPeekHeight = 56.dp,
    content = {
        // Put here the screen content
    }
)
```
