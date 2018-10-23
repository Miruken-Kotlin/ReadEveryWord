package com.miruken.mvc.android.component

import android.support.annotation.IdRes
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import com.miruken.context.Context
import com.miruken.context.Contextual
import com.miruken.context.requireContext
import com.miruken.mvc.android.ViewRegion
import com.miruken.mvc.view.addRegion
import kotlin.math.ceil

class TableComponent(
        context: Context,
        private val table:   TableLayout,
        private val columns: Int
): Component(context) {
    private val _rows  = mutableListOf<TableRow>()
    private var _count = 0

    constructor(
            contextual: Contextual,
            table:      TableLayout,
            columns:    Int
    ): this(contextual.requireContext(), table, columns)

    init { table.removeAllViews() }

    fun add(): Context {
        _count++
        val cell = ViewRegion(table.context)
        val row  = currentRow().apply { addView(cell) }
        return context.addRegion(cell).apply {
            contextEnded += { _ ->
                removeCell(row, cell)
            }
            childContextEnded += { _ ->
                removeCell(row, cell)
            }
        }
    }

    private fun currentRow(): TableRow {
        if (_rows.isEmpty() ||
                ceil(_count.toDouble() / columns) > _rows.size) {
            return TableRow(table.context).also {
                _rows.add(it)
                table.addView(it)
            }
        }
        return _rows.last()
    }

    private fun removeCell(row: TableRow, cell: ViewRegion) {
        row.removeView(cell)
        if (row.childCount == 0) {
            table.removeView(row)
        }
    }
}

fun Contextual.table(
        table: TableLayout, columns: Int
) = TableComponent(this, table, columns)

fun Contextual.table(
        parent: View, @IdRes tableId: Int, columns: Int
) = TableComponent(this, parent.findViewById(tableId), columns)