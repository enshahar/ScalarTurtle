package com.enshahar.turtle.common

/**
  * Created by hyunsok on 2016-08-14.
  */
trait Graphics {
  /* The position is general Cartesian coordinate and have no limit
     Implementor can crop the drawing action or just rescale the canvas as freely
   */
  def lineTo(pos: Position)
  def moveTo(pos: Position)
  def setColor(color: Color)
  def clear()
}
