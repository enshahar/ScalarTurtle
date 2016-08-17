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

  def getPosition = position
  def getAngle = angle
  def getColor = color
  def getPenStatus = penOn

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

  def on(): Unit = { penOn = true; log(f"On = ($position, $angle, $color, $penOn)") }
  def off(): Unit = { penOn = false; log(f"Off = ($position, $angle, $color, $penOn)") }

  def setColor(c: Color) = {
    color = c
    drawer.setColor(c)
    log(f"Set color = ($position, $angle, $color, $penOn)")
  }
}
