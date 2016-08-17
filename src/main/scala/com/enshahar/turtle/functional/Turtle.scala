package com.enshahar.turtle.functional

import com.enshahar.turtle.common._
import com.enshahar.turtle.unit._

import scala.language.postfixOps

case class TurtleState(position: Position, angle: Angle, color: Color, penOn: Boolean)

object Turtle {
  val initial = TurtleState((0.0, 0.0), 0.0 deg, (0, 0, 0), false)

  // each turtle function return new turtle state and the log string

  def move(log: Logger)(st: TurtleState)(distance: Double): (TurtleState, String) = {
    val newPosition = st.position + calcPosition(distance, st.angle)
    val state = TurtleState( newPosition, st.angle, st.color, st.penOn )
    ( state, f"Move $distance%.1f = $state" )
  }

  def turn(log: Logger)(st: TurtleState)(a: Angle): (TurtleState, String) = {
    val state = TurtleState( st.position, st.angle + a, st.color, st.penOn )
    ( state, f"Turn $a = $state" )
  }

  def on(log: Logger)(st: TurtleState): (TurtleState, String) = {
    val state = TurtleState(st.position, st.angle, st.color, true)
    (state, f"One = $state")
  }
  def off(log: Logger)(st: TurtleState): (TurtleState, String) = {
    val state = TurtleState(st.position, st.angle, st.color, false)
    (state, f"Off = $state")
  }
  def setColor(log: Logger)(st: TurtleState)(color: Color): (TurtleState, String) = {
    val state = TurtleState(st.position, st.angle, color, st.penOn)
    (state, f"Set color = $state")
  }
}