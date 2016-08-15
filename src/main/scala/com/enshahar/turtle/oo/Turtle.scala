/**
  * Created by hyunsok on 2016-08-12.
  */
package com.enshahar.turtle.oo

import com.enshahar.turtle.common._
import com.enshahar.turtle.unit._

import scala.language.postfixOps

class Turtle(log: Logger, drawer: Graphics) {
  private var position: Position = (0.0, 0.0)
  private var angle: Angle = 0.0 deg
  private var color: Color = (0, 0, 0)
  private var penOn = false

  drawer.moveTo(0,0)

  def move(distance: Double) = {
    val diff = calcPosition(distance, angle)
    position += diff
    if(penOn) {
      drawer.lineTo(position)
    } else {
      drawer.moveTo(position)
    }
    log(f"Move $distance%.1f = ($position, $angle, $color, $penOn)")
  }

  def turn(a: Angle): Unit = {
    angle += a
    log(f"Turn $a = ($position, $angle, $color, $penOn)")
  }

  def On(): Unit = penOn = true
  def Off(): Unit = penOn = false

  def setColor(c: Color) = {
    color = c
    drawer.setColor(c)
  }

  private def calcPosition( d:  Double, a: Angle ): Position = (d * math.cos(a.toRadians),
     d * math.sin(a.toRadians))

  private implicit final class PositionOps(x:Position) extends Position(x._1, x._2) {
    def +(p:Position):Position = (_1 + p._1, _2 + p._2)
    def -(p:Position):Position = (_1 - p._1, _2 - p._2)
  }
}
