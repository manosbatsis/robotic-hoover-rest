/*
 * Copyright (C) 2024 Manos Batsis

 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Foobar is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with Foobar. If not, see
 * <https://www.gnu.org/licenses/>.
 */
package com.github.manosbatsis.robotichooverrest.domain.instruction

import com.github.manosbatsis.robotichooverrest.api.instruction.GridPosition
import java.util.LinkedList

/**
 * A state for our hoover robot.
 *
 * @param initialPosition the initial position. Will be corrected to the closest
 *   position within bounds.
 * @param dirtyPositions the patches assumed to be dirty.
 * @property maxPosition the max X and Y coordinates this hoover can move to,
 *   inclusive.
 * @constructor Creates a new instance already placed at its effective initial
 *   position.
 */
class HooverRobotState(
    initialPosition: GridPosition,
    private val maxPosition: GridPosition,
    dirtyPositions: Collection<GridPosition>
) {

    /**
     * Used to keep track of which positions are still dirty. Mutable copy of
     * dirty positions given as input
     */
    private val dirty: MutableSet<GridPosition> = dirtyPositions.toMutableSet()

    /**
     * Maintains a complete history of effective positions from initial to
     * final. Takes skidding into account
     */
    val positionStack = LinkedList<GridPosition>()

    /** The current position within the given grid for this hoover */
    val currentPosition: GridPosition
        get() = positionStack.peek()

    /** Maintains a complete history of cleaned positions */
    val cleanedPatches = LinkedList<GridPosition>()

    /** The number of patches that have already been cleaned */
    val cleanCount
        get() = cleanedPatches.size

    init {
        // Correct the initial position to the closest within bounds.
        fun toEffective(i: Int, max: Int) =
            when {
                i < 0 -> 0
                i > max -> max
                else -> i
            }
        moveToEffective(
            GridPosition(
                x = toEffective(initialPosition.x, maxPosition.x),
                y = toEffective(initialPosition.y, maxPosition.y),
            ))
    }

    /** Move to the new position and clean if still dirty */
    private fun moveToEffective(position: GridPosition): HooverRobotState {
        positionStack.push(position)
        if (dirty.remove(position)) cleanedPatches.push(position)
        return this
    }

    /** Move to a new position northward, possibly skidding */
    fun moveNorth(): HooverRobotState =
        moveToEffective(
            currentPosition.copy(
                y = minOf(currentPosition.y + 1, maxPosition.y)))

    /** Move to a new position southward, possibly skidding */
    fun moveSouth(): HooverRobotState =
        moveToEffective(
            currentPosition.copy(y = maxOf(currentPosition.y - 1, 0)))

    /** Move to a new position eastward, possibly skidding */
    fun moveEast(): HooverRobotState =
        moveToEffective(
            currentPosition.copy(
                x = minOf(currentPosition.x + 1, maxPosition.x)))

    /** Move to a new position westward, possibly skidding */
    fun moveWest(): HooverRobotState =
        moveToEffective(
            currentPosition.copy(x = maxOf(currentPosition.x - 1, 0)))

    /**
     * Move to a new position towards the given [direction], possibly skidding
     */
    fun move(direction: CardinalDirection): HooverRobotState =
        when (direction) {
            CardinalDirection.N -> moveNorth()
            CardinalDirection.S -> moveSouth()
            CardinalDirection.E -> moveEast()
            CardinalDirection.W -> moveWest()
        }
}
