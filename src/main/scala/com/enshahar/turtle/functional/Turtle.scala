package com.enshahar.turtle.functional

import com.enshahar.turtle.common._
import com.enshahar.turtle.unit._

import scala.language.postfixOps

case class TurtleState(position: Position, angle: Angle, color: Color, penOn: Boolean)

object Turtle {
  val initial = TurtleState((0.0, 0.0), 0.0 deg, (0, 0, 0), false)

  // each turtle function return new turtle state and the log string

  val move = (logAndDraw: (String, TurtleState) => Unit) => (distance: Double) => (st: TurtleState) => {
    val state = st.copy( position = st.position + calcPosition(distance, st.angle) )
    logAndDraw( f"Move $distance%.1f = $state", state)
    state
  }

  val turn = (logAndDraw: (String, TurtleState) => Unit) => (a: Angle) => (st: TurtleState) => {
    val state = st.copy( angle = st.angle + a )
    logAndDraw( f"Turn $a = $state", state)
    state
  }

  val on = (logAndDraw: (String, TurtleState) => Unit) => (st: TurtleState) => {
    val state = st.copy( penOn = true )
    logAndDraw( f"On = $state", state)
    state
  }

  val off = (logAndDraw: (String, TurtleState) => Unit) => (st: TurtleState) => {
    val state = st.copy( penOn = false )
    logAndDraw( f"Off = $state", state)
    state
  }

  val setColor = (logAndDraw: (String, TurtleState) => Unit) => (color: Color) => (st: TurtleState) => {
    val state = st.copy( color = color )
    logAndDraw( f"Set color = $state", state)
    state
  }
}