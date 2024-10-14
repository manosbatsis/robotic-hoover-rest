package com.github.manosbatsis.robotichooverrest.domain.instruction

import com.github.manosbatsis.robotichooverrest.api.instruction.ValidGridPosition
import java.util.LinkedList

/**
 * @param initialPosition the initial position. Will be corrected to the closest position within bounds.
 * @property maxPosition the max X and Y coordinates this hoover can move to, inclusive.
 * @param dirtyPositions the patches assumed to be dirty.
 * @constructor Creates a new instance already placed at its effective initial position.
 */
class HooverState(
    initialPosition: ValidGridPosition,
    private val maxPosition: ValidGridPosition,
    dirtyPositions: Collection<ValidGridPosition>
) {

    /** Used to keep track of which positions are still dirty. Mutable copy of dirty positions given as input */
    private val dirty: MutableSet<ValidGridPosition> = dirtyPositions.toMutableSet()

    /** Maintains a complete history of effective positions from initial to final. Takes skidding into account */
    val positionStack = LinkedList<ValidGridPosition>()

    /** The current position within the given grid for this hoover */
    val currentPosition: ValidGridPosition
        get() = positionStack.peek()

    /** Maintains a complete history of cleaned positions */
    val cleanedPatches = LinkedList<ValidGridPosition>()

    /** The number of patches that have already been cleaned */
    val cleanCount
        get() = cleanedPatches.size

    init {
        // Correct the initial position to the closest within bounds.
        fun toEffective(i: Int, max: Int) = when{
            i < 0 -> 0
            i > max -> max
            else -> i
        }
        moveToEffective(
            ValidGridPosition(
                x = toEffective(initialPosition.x, maxPosition.x),
                y = toEffective(initialPosition.y, maxPosition.y),
            )
        )
    }

    /** Move to the new position and clean if still dirty */
    private fun moveToEffective(position: ValidGridPosition): HooverState {
        positionStack.push(position)
        if (dirty.remove(position)) cleanedPatches.push(position)
        return this
    }

    /** Move to a new position northward, possibly skidding */
    fun moveNorth(): HooverState = moveToEffective(currentPosition.copy(
        y = minOf(currentPosition.y + 1, maxPosition.y)))

    /** Move to a new position southward, possibly skidding */
    fun moveSouth(): HooverState = moveToEffective(currentPosition.copy(
        y = maxOf(currentPosition.y - 1, 0)))

    /** Move to a new position eastward, possibly skidding */
    fun moveEast(): HooverState = moveToEffective(currentPosition.copy(
        x = minOf(currentPosition.x + 1, maxPosition.x)))

    /** Move to a new position westward, possibly skidding */
    fun moveWest(): HooverState = moveToEffective(currentPosition.copy(
        x = maxOf(currentPosition.x - 1, 0)))

    /** Move to a new position towards the given [direction], possibly skidding */
    fun move(direction: CardinalDirection): HooverState = when (direction) {
        CardinalDirection.N -> moveNorth()
        CardinalDirection.S -> moveSouth()
        CardinalDirection.E -> moveEast()
        CardinalDirection.W -> moveWest()
    }
}