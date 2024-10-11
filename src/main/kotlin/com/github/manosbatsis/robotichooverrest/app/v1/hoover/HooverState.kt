package com.github.manosbatsis.robotichooverrest.app.v1.hoover

class HooverState private constructor(
    var position: HooverPosition,
    val room: RoomDimensions,
    val dirtyPatches: MutableSet<HooverPosition>
) {
    companion object {
        fun of(
            position: HooverPosition,
            room: RoomDimensions,
            dirtyPatches: Set<HooverPosition>
        ) = HooverState(position, room, dirtyPatches.toMutableSet())
    }

    /** The number of patches that have already been cleaned */
    var cleanedPatches = 0
        private set

    /** Move to the new position and clean if still dirty */
    private fun moveTo(newPosition: HooverPosition): HooverState {
        position = newPosition
        if (dirtyPatches.remove(position)) cleanedPatches++
        return this
    }

    /** Create a new position instance northward, possibly skidding */
    fun moveNorth(): HooverState = moveTo(position.copy(y = minOf(position.y + 1, room.maxPositionY)))

    /** Create a new position instance southward, possibly skidding */
    fun moveSouth(): HooverState = moveTo(position.copy(y = maxOf(position.y - 1, 0)))

    /** Create a new position instance eastward, possibly skidding */
    fun moveEast(): HooverState = moveTo(position.copy(x = minOf(position.x + 1, room.maxPositionX)))

    /** Create a new position instance westward, possibly skidding */
    fun moveWest(): HooverState = moveTo(position.copy(x = maxOf(position.x - 1, 0)))

    /** Create a new position instance towards the given [CardinalDirection], possibly skidding */
    fun move(direction: CardinalDirection): HooverState = when (direction) {
        CardinalDirection.N -> moveNorth()
        CardinalDirection.S -> moveSouth()
        CardinalDirection.E -> moveEast()
        CardinalDirection.W -> moveWest()
    }
}