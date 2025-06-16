package com.ierusalem.androrat.core.utils

import android.database.Cursor

fun Cursor.getStringOrNull(columnIndex: Int): String? =
    if (columnIndex != -1) getString(columnIndex) else null

fun Cursor.getLongOrNull(columnIndex: Int): Long? =
    if (columnIndex != -1) getLong(columnIndex) else null

fun Cursor.getIntOrNull(columnIndex: Int): Int? =
    if (columnIndex != -1) getInt(columnIndex) else null
